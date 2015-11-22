package co.bluepass.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import co.bluepass.domain.Action;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.RegisterStatus;
import co.bluepass.domain.Reservation;
import co.bluepass.domain.ReservationHistory;
import co.bluepass.domain.TicketHistory;
import co.bluepass.domain.User;
import co.bluepass.proxy.SmsService;
import co.bluepass.repository.ActionRepository;
import co.bluepass.repository.ClassScheduleRepository;
import co.bluepass.repository.ClubRepository;
import co.bluepass.repository.CommonCodeRepository;
import co.bluepass.repository.ReservationHistoryRepository;
import co.bluepass.repository.ReservationRepository;
import co.bluepass.repository.TicketHistoryRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.repository.condition.ClassScheduleSpec;
import co.bluepass.repository.condition.ReservationSpec;
import co.bluepass.security.SecurityUtils;
import co.bluepass.web.rest.dto.ReservationDTO;
import co.bluepass.web.rest.response.ReservationCancelResponse;
import co.bluepass.web.rest.response.ReservationStatusResponse;
import co.bluepass.web.rest.util.PaginationUtil;

/**
 * The type Reservation resource.
 */
@RestController
@RequestMapping("/api")
public class ReservationResource {

    private final Logger log = LoggerFactory.getLogger(ReservationResource.class);

    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ClassScheduleRepository classScheduleRepository;

    @Inject
    private TicketHistoryRepository ticketHistoryRepository;

    @Inject
    private CommonCodeRepository commonCodeRepository;

    @Inject
    private ClubRepository clubRepository;

    @Inject
    private ActionRepository actionRepository;

    @Inject
    private ReservationHistoryRepository reservationHistoryRepository;

    /**
     * Status response entity.
     *
     * @param dto the dto
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservations/status",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReservationStatusResponse> status(@RequestBody ReservationDTO dto) throws URISyntaxException {
    	log.debug("REST request to save Reservation : {}", dto);

    	ReservationStatusResponse response = new ReservationStatusResponse();
    	List<String> messages = new ArrayList<String>();

    	User user = userRepository.findOne(dto.getUserId());
    	if(user == null) {
    		messages.add("존재하지 않는 사용자입니다.");
    	}

    	if(user.getRegisterStatus() == null || user.getRegisterStatus().ordinal() < RegisterStatus.등록.ordinal()) {
    		messages.add("등록되지 않은 회원은 예약이 불가능합니다.");
    	}

    	ClassSchedule classSchedule = classScheduleRepository.findOne(dto.getScheduleId());

    	Reservation aleadyReservation = reservationRepository.findOneByUserAndClassScheduleAndCanceled(user, classSchedule, false);
    	if(aleadyReservation != null) {
    		messages.add("이미 예약한 스케쥴입니다.");
    		response.setAlreadyReservated(true);
    	}

    	if(classSchedule == null) {
    		messages.add("존재하지 않는 스케쥴입니다.");
    	}

    	if(classSchedule.getClub().getOnlyFemale() != null && classSchedule.getClub().getOnlyFemale()
    			&& "M".equalsIgnoreCase(user.getGender())) {
    		messages.add("여성전용 클럽으로 남성은 이용할 수 없습니다.");
    	}

    	if(classSchedule.getEnable() == null || !classSchedule.getEnable()) {
    		messages.add("제휴사의 사정에 의하여 지금은 진행되는 스케줄이 아닙니다.");
    	}

    	if(classSchedule.getFinished() == null || classSchedule.getFinished()) {
    		messages.add("클래스가 마감되어 예약이 불가능합니다.");
    	}

    	int reservationCount = reservationRepository.countByClassSchedule(classSchedule);
    	if(classSchedule.getActionSchedule().getAttendeeLimit() <= reservationCount) {
    		messages.add("클래스 정원 초과로 마감되어 예약이 불가능합니다.");
    	}

    	//클럽 별 마감 시간 체크
    	String closeCode = classSchedule.getClub().getReservationClose();
    	int closeStandMinutes = 60;
    	if(StringUtils.isNotEmpty(closeCode) && "1".equals(closeCode)){
    		closeStandMinutes = closeStandMinutes * 3;
    	}

    	int minDiff = Minutes.minutesBetween(DateTime.now(), classSchedule.getStartTime()).getMinutes();
    	if(minDiff <= closeStandMinutes) {
    		messages.add("예약가능한 시간이 아닙니다.");
    	}

    	TicketHistory th = ticketHistoryRepository.findTop1ByUserAndActivatedAndClosed(user, true, false);
    	if( th != null && classSchedule.getStartTime().isAfter(th.getCloseDate())){
    		messages.add("회원권 사용기간 이내의 수업을 예약해주세요!");
    	}

    	DateTime usersDateStart = classSchedule.getStartTime().withTime(0, 0, 0, 0);
    	DateTime usersDateEnd = classSchedule.getStartTime().withTime(23, 59, 59, 999);

    	int userReservationCountInDate = reservationRepository.countByUserAndStartTimeBetweenAndCanceled(user, usersDateStart, usersDateEnd, false);
    	if(userReservationCountInDate > 0){
    		messages.add("해당 날짜에 이미 예약한 수업이 있습니다.");
    	}

    	/*Specifications<Reservation> spec = Specifications.where(ReservationSpec.user(user));
    	spec = spec.and(ReservationSpec.crashTime("startTime", classSchedule.getStartTime(), classSchedule.getEndTime()));
    	spec = spec.or(ReservationSpec.crashTime("endTime", classSchedule.getStartTime(), classSchedule.getEndTime()));

    	int hasCrashCount = reservationRepository.count(spec);

    	if(hasCrashCount > 0) {
    		messages.add("해당 수업시간에 이미 예약된 다른 클래스가 있습니다.");
    	}*/

    	response.setMessages(messages);

    	return new ResponseEntity<ReservationStatusResponse>(response, HttpStatus.OK);
    }

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservations",
    		method = RequestMethod.POST,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody ReservationDTO dto) throws URISyntaxException {
    	log.debug("REST request to save Reservation : {}", dto);

    	User user = userRepository.findOne(dto.getUserId());
    	if(user == null) {
    		return ResponseEntity.badRequest().header("Failure", "존재하지 않는 사용자입니다.").build();
    	}

    	if(user.getRegisterStatus() == null || user.getRegisterStatus().ordinal() < RegisterStatus.등록.ordinal()) {
    		return ResponseEntity.badRequest().header("Failure", "등록되지 않은 회원은 예약이 불가능합니다.").build();
    	}

    	ClassSchedule classSchedule = classScheduleRepository.findOne(dto.getScheduleId());

    	Reservation aleadyReservation = reservationRepository.findOneByUserAndClassScheduleAndCanceled(user, classSchedule, false);
    	if(aleadyReservation != null) {
    		return ResponseEntity.badRequest().header("Failure", "이미 예약한 스케쥴입니다.").build();
    	}

    	if(classSchedule == null) {
    		return ResponseEntity.badRequest().header("Failure", "존재하지 않는 스케쥴입니다.").build();
    	}

    	if(classSchedule.getClub().getOnlyFemale() != null && classSchedule.getClub().getOnlyFemale()
    			&& "M".equalsIgnoreCase(user.getGender())) {
    		return ResponseEntity.badRequest().header("Failure", "여성전용 클럽으로 남성은 이용할 수 없습니다.").build();
    	}

    	if(classSchedule.getEnable() != null && !classSchedule.getEnable()) {
    		return ResponseEntity.badRequest().header("Failure", "제휴사의 사정에 의하여 지금은 진행되는 스케줄이 아닙니다.").build();
    	}

    	if(classSchedule.getFinished() != null && classSchedule.getFinished()) {
    		return ResponseEntity.badRequest().header("Failure", "클래스가 마감되어 예약이 불가능합니다.").build();
    	}

    	int reservationCount = reservationRepository.countByClassSchedule(classSchedule);
    	if(classSchedule.getActionSchedule().getAttendeeLimit() <= reservationCount) {
    		return ResponseEntity.badRequest().header("Failure", "클래스 정원 초과로 마감되어 예약이 불가능합니다.").build();
    	}

    	//클럽 별 마감 시간 체크
    	String closeCode = classSchedule.getClub().getReservationClose();
    	int closeStandMinutes = 60;
    	if(StringUtils.isNotEmpty(closeCode) && "1".equals(closeCode)){
    		closeStandMinutes = closeStandMinutes * 3;
    	}

    	int minDiff = Minutes.minutesBetween(DateTime.now(), classSchedule.getStartTime()).getMinutes();
    	if(minDiff <= closeStandMinutes) {
    		return ResponseEntity.badRequest().header("Failure", "예약가능한 시간이 아닙니다.").build();
    	}

    	TicketHistory th = ticketHistoryRepository.findTop1ByUserAndActivatedAndClosed(user, true, false);
    	if( th != null && classSchedule.getStartTime().isAfter(th.getCloseDate())){
    		return ResponseEntity.badRequest().header("Failure", "회원권 사용기간 이내의 수업을 예약해주세요!").build();
    	}

    	DateTime usersDateStart = classSchedule.getStartTime().withTime(0, 0, 0, 0);
    	DateTime usersDateEnd = classSchedule.getStartTime().withTime(23, 59, 59, 999);

    	int userReservationCountInDate = reservationRepository.countByUserAndStartTimeBetweenAndCanceled(user, usersDateStart, usersDateEnd, false);
    	if(userReservationCountInDate > 0){
    		return ResponseEntity.badRequest().header("Failure", "해당 날짜에 이미 예약한 수업이 있습니다.").build();
    	}

    	/*Specifications<Reservation> spec = Specifications.where(ReservationSpec.user(user));
    	spec = spec.and(ReservationSpec.crashTime("startTime", classSchedule.getStartTime(), classSchedule.getEndTime()));
    	spec = spec.or(ReservationSpec.crashTime("endTime", classSchedule.getStartTime(), classSchedule.getEndTime()));

    	int hasCrashCount = reservationRepository.count(spec);

    	if(hasCrashCount > 0) {
    		return ResponseEntity.badRequest().header("Failure", "해당 수업시간에 이미 예약된 다른 클래스가 있습니다.").build();
    	}*/

    	TicketHistory ticketHistory = ticketHistoryRepository.findTop1ByUserAndActivatedAndClosed(user, true, false);
    	CommonCode ticket = null;
    	if(ticketHistory != null) {
    		ticket = ticketHistory.getTicket();
    	}

    	Random rnd = new Random();
    	int checkCode = 100000 + rnd.nextInt(900000);

    	Reservation reservation = new Reservation(user, classSchedule, ticket, checkCode);

    	Reservation saved = reservationRepository.save(reservation);

    	ReservationHistory history = new ReservationHistory(saved);

    	reservationHistoryRepository.save(history);

    	classSchedule.addReservation();
    	classScheduleRepository.save(classSchedule);

    	DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm");

    	StringBuffer sb = new StringBuffer("[블루패스]");
    	sb.append(saved.getStartTime().toString(fmt)).append(" ")
    	.append(saved.getClub().getName()).append(" ")
    	.append(saved.getClassSchedule().getAction().getTitle()).append(" ")
    	.append("수업이 예약되셨습니다. 수업 시작 10분전까지 도착해야 원활하게 참여하실 수 있습니다.").append(" ")
    	.append("수업 전 예약 번호 [")
    	.append(checkCode)
    	.append("] 와 신분증을 카운터에 꼭 제시해주어야 입장 가능합니다.");

    	SmsService.sendSms(sb.toString(), user);

    	if(classSchedule.getClub() != null && StringUtils.isNotEmpty(classSchedule.getClub().getNotificationType())){
    		if("1".equals(classSchedule.getClub().getNotificationType())){
    	    	sb = new StringBuffer("[블루패스]");
    	    	sb.append(saved.getStartTime().toString(fmt)).append(" ")
    	    	.append(saved.getClub().getName()).append(" ")
    	    	.append(saved.getClassSchedule().getAction().getTitle()).append(" ")
    	    	.append("수업이 예약되었습니다.");

    	    	SmsService.sendSms(sb.toString(), classSchedule.getClub().getManagerMobile(), classSchedule.getClub().getName());
    		}
    	}

    	return ResponseEntity.created(new URI("/api/reservations/" + saved.getId())).build();
    }

    /**
     * Update response entity.
     *
     * @param dto the dto
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservations",
    		method = RequestMethod.PUT,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody ReservationDTO dto) throws URISyntaxException {
    	log.debug("REST request to update Reservation : {}", dto);

    	User user = userRepository.findOne(dto.getUserId());
    	ClassSchedule classSchedule = classScheduleRepository.findOne(dto.getScheduleId());

    	Reservation reservation = reservationRepository.findOneByUserAndClassSchedule(user, classSchedule);

    	if(reservation == null) {
    		return ResponseEntity.badRequest().header("Failure", "존재하지 않는 얘약입니다.").build();
    	}

    	if(dto.getCancel() != null) {
    		reservation.setCanceled(dto.getCancel());
    	}

    	reservationRepository.save(reservation);

    	ReservationHistory history = reservationHistoryRepository.findOneByReservationId(reservation.getId());
        history.update(reservation);
        reservationHistoryRepository.save(history);

        if(dto.getCancel() != null && dto.getCancel()){
        	classSchedule.cancelReservation();
        	classScheduleRepository.save(classSchedule);
        }

    	return ResponseEntity.ok().build();
    }

    /**
     * Gets all.
     *
     * @param offset the offset
     * @param limit  the limit
     * @return the all
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Reservation>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Reservation> page = reservationRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reservations", offset, limit);
        return new ResponseEntity<List<Reservation>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/reservations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Reservation> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Reservation : {}", id);
        Reservation reservation = reservationRepository.findOne(id);
        if (reservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/reservations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Reservation : {}", id);
        Reservation reservation = reservationRepository.findOne(id);
        ClassSchedule classSchedule = reservation.getClassSchedule();
        reservationRepository.delete(id);

        classSchedule.cancelReservation();
    	classScheduleRepository.save(classSchedule);
    }


    /**
     * Used check response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservations/{id}/used",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map<String, Object>> usedCheck(@PathVariable Long id) throws URISyntaxException {
    	log.debug("REST request to cancel Reservation : {}", id);

    	User user = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
    	Reservation reservation = reservationRepository.findOne(id);

    	Map<String, Object> resultMap = new HashMap<String, Object>();

    	if(reservation == null) {
    		resultMap.put("message", "존재하지 않는 얘약입니다.");
    		return ResponseEntity.badRequest().body(resultMap);
    	}

    	boolean isUsed = reservation.getUsed() == null ? false : reservation.getUsed();

    	resultMap.put("isUsed", isUsed);
    	return ResponseEntity.ok().body(resultMap);
    }


    /**
     * Cancel check response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservations/{id}/checkcancel",
    		method = RequestMethod.PUT,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReservationCancelResponse> cancelCheck(@PathVariable Long id) throws URISyntaxException {
    	log.debug("REST request to cancel Reservation : {}", id);

    	ReservationCancelResponse response = new ReservationCancelResponse();
    	response.setCancelable(true);

    	List<String> messages = new ArrayList<String>();

    	User user = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
    	if(user == null) {
    		messages.add("존재하지 않는 사용자입니다.");
    		response.setCancelable(false);
    	}

    	Reservation reservation = reservationRepository.findOne(id);

    	if(reservation == null) {
    		messages.add("존재하지 않는 예약입니다.");
    		response.setCancelable(false);
    	}

    	if(!reservation.getUser().getId().equals(user.getId())){
    		messages.add("예약 당사자가 아니어서 취소할 수 없습니다.");
    		response.setCancelable(false);
    	}

    	int timeDiff = 1;
    	ClassSchedule classSchedule = reservation.getClassSchedule();
    	if(classSchedule.getClub() != null && StringUtils.isNotEmpty(classSchedule.getClub().getReservationClose())){
    		if("1".equals(classSchedule.getClub().getNotificationType())){
    			timeDiff = 3;
    		}else if("0".equals(classSchedule.getClub().getReservationClose())){
    			timeDiff = 1;
    		}
    	}

    	if(reservation.getStartTime().isBefore(DateTime.now().plusHours(timeDiff))){
    		messages.add("지금 예약을 취소할 시 회원권 3일 삭감의 불이익을 받으실 수 있습니다.");
    		response.setCancelable(true);
    	}

    	if(response.isCancelable()){
    		messages.add("취소가 가능합니다.");
    	}

    	response.setMessages(messages);
    	return new ResponseEntity<ReservationCancelResponse>(response, HttpStatus.OK);
    }


    /**
     * Cancel reservation response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservations/{id}/cancel",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to cancel Reservation : {}", id);

        User user = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
        Reservation reservation = reservationRepository.findOne(id);

        if(reservation == null) {
        	return ResponseEntity.badRequest().header("Failure", "존재하지 않는 얘약입니다.").build();
        }

        if(!reservation.getUser().getId().equals(user.getId())){
        	return ResponseEntity.badRequest().header("Failure", "예약 당사자가 아니어서 취소할 수 없습니다.").build();
        }

        reservation.setCanceled(true);
        reservationRepository.save(reservation);

        ReservationHistory history = reservationHistoryRepository.findOneByReservationId(id);
        history.setCanceled(true);
        reservationHistoryRepository.save(history);

        ClassSchedule classSchedule = reservation.getClassSchedule();
        classSchedule.cancelReservation();
    	classScheduleRepository.save(classSchedule);

    	DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm");

    	StringBuffer sb = new StringBuffer("[블루패스]");

    	SmsService.sendSms(sb.toString(), user);

    	if(classSchedule.getClub() != null && StringUtils.isNotEmpty(classSchedule.getClub().getNotificationType())){
    		if("1".equals(classSchedule.getClub().getNotificationType())){
    	    	sb = new StringBuffer("[블루패스]");
    	    	sb.append(classSchedule.getStartTime().toString(fmt)).append(" ")
    	    	.append(classSchedule.getClub().getName()).append(" ")
    	    	.append(classSchedule.getAction().getTitle()).append(" ")
    	    	.append("수업 중 한 명이 예약을 취소했습니다.");

    	    	SmsService.sendSms(sb.toString(), classSchedule.getClub().getManagerMobile(), classSchedule.getClub().getName());
    		}
    	}

    	int timeDiff = 1;
    	String msg = null;
    	if(classSchedule.getClub() != null && StringUtils.isNotEmpty(classSchedule.getClub().getReservationClose())){
    		if("1".equals(classSchedule.getClub().getNotificationType())){
    			timeDiff = 3;
    		}else if("0".equals(classSchedule.getClub().getReservationClose())){
    			timeDiff = 1;
    		}
    	}

    	if(reservation.getStartTime().isBefore(DateTime.now().plusHours(timeDiff))){
    		TicketHistory ticket = ticketHistoryRepository.findTop1ByUserAndActivatedAndClosed(user, true, false);
    		DateTime closedTime = ticket.getCloseDate().minusDays(3);
    		ticket.setCloseDate(closedTime);
    		ticketHistoryRepository.save(ticket);
    		msg = "확정된 예약 취소로 인해 회원권 3일 삭감의 불이익을 받게 됩니다.";
    	}

        return ResponseEntity.ok().header("Message", msg).build();
    }


    /**
     * Attend response entity.
     *
     * @param id        the id
     * @param checkCode the check code
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservations/{id}/attend",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> attend(@PathVariable Long id, @RequestParam Integer checkCode) throws URISyntaxException {
        log.debug("REST request to get Reservation : {}", id);

        Reservation reservation = reservationRepository.findOne(id);

        if(checkCode == null || !checkCode.equals(reservation.getCheckCode())) {
        	return ResponseEntity.badRequest().header("Failure", "잘못된 출석코드입니다..").build();
        }

        reservation.setUsed(true);
        reservationRepository.save(reservation);

        ReservationHistory history = reservationHistoryRepository.findOneByReservationId(id);
        history.setUsed(true);
        reservationHistoryRepository.save(history);

        ClassSchedule classSchedule = reservation.getClassSchedule();
        classSchedule.attendReservation();
    	classScheduleRepository.save(classSchedule);

        return ResponseEntity.created(new URI("/api/reservations/attend")).build();
    }


    /**
     * Absence response entity.
     *
     * @param id      the id
     * @param request the request
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservations/{id}/absence",
    		method = RequestMethod.POST,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> absence(@PathVariable Long id, HttpServletRequest request) throws URISyntaxException {
    	log.debug("REST request to get Reservation : {}", id);
    	Reservation reservation = reservationRepository.findOne(id);
    	if(!request.isUserInRole("ROLE_ADMIN") && !reservation.getAction().getCreator().getEmail().equals(SecurityUtils.getCurrentLogin())){
    		return ResponseEntity.badRequest().header("Failure", "Has not Authority").build();
    	}

    	User user = reservation.getUser();

    	reservation.setUsed(false);
    	reservationRepository.save(reservation);

    	ReservationHistory history = reservationHistoryRepository.findOneByReservationId(id);
    	history.setUsed(false);
    	reservationHistoryRepository.save(history);

    	ClassSchedule classSchedule = reservation.getClassSchedule();
    	classSchedule.absenceReservation();
    	classScheduleRepository.save(classSchedule);

    	TicketHistory ticket = ticketHistoryRepository.findTop1ByUserAndActivatedAndClosed(user, true, false);
    	DateTime closedTime = ticket.getCloseDate().minusDays(3);
		ticket.setCloseDate(closedTime);
		ticketHistoryRepository.save(ticket);

    	return ResponseEntity.created(new URI("/api/reservations/attend")).build();
    }


    /**
     * Gets users class schedules.
     *
     * @param offset   the offset
     * @param limit    the limit
     * @param clubId   the club id
     * @param canceled the canceled
     * @param used     the used
     * @param actionId the action id
     * @return the users class schedules
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservations/myshchedules",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Reservation>> getUsersClassSchedules(
    		@RequestParam(value = "page" , required = false) Integer offset,
            @RequestParam(value = "per_page", required = false) Integer limit,
            @RequestParam(value = "clubId", required = false) Long clubId,
            @RequestParam(value = "canceled", required = false, defaultValue = "false") Boolean canceled,
            @RequestParam(value = "used", required = false) Boolean used,
            @RequestParam(value = "actionId", required = false) Long actionId
                                  )
        throws URISyntaxException {

    	User user = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
    	Specifications<Reservation> spec = Specifications.where(ReservationSpec.user(user));

    	if(clubId != null){
    		//spec.and(ReservationSpec.club(clubId));
    		Club club = clubRepository.findOne(clubId);
    		if(club != null) {
    			spec = spec.and(ReservationSpec.club(club));
    		}
    	}

    	if(actionId != null){
    		//spec.and(ReservationSpec.action(actionId));
    		Action action = actionRepository.findOne(actionId);
    		if(action != null) {
    			spec = spec.and(ReservationSpec.action(action));
    		}
    	}

    	if(canceled != null) {
    		spec = spec.and(ReservationSpec.canceled(canceled));
    	}

    	if(used == null){
    		spec = spec.and(ReservationSpec.usedIsNull());
    	}else{
    		spec = spec.and(ReservationSpec.used(used));
    	}

    	/*List<Order> orders = new ArrayList<Order>();
    	orders.add(new Order(Direction.ASC, "used"));
    	orders.add(new Order(Direction.ASC, "startTime"));
    	Sort sort = new Sort(orders);*/
    	Sort sort = new Sort("used", "startTime");

    	Page<Reservation> page = reservationRepository.findAll(spec, PaginationUtil.generatePageRequest(offset, limit, sort));

        //Page<Reservation> page = reservationRepository.findByUserOrderBy(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reservations/myshchedules", offset, limit);
        return new ResponseEntity<List<Reservation>>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * Gets using numbers by club.
     *
     * @param clubId    the club id
     * @param actionId  the action id
     * @param userId    the user id
     * @param yearMonth the year month
     * @return the using numbers by club
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservations/usingnumbers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Integer> getUsingNumbersByClub(
            @RequestParam(value = "clubId", required = true) Long clubId,
            @RequestParam(value = "actionId", required = false) Long actionId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "yearMonth", required = false) String yearMonth
                                  )
        throws URISyntaxException {

    	Club club = clubRepository.findOne(clubId);

    	Specifications<Reservation> spec = Specifications.where(ReservationSpec.club(club));

    	if(actionId != null){
    		Action action = actionRepository.findOne(actionId);
    		if(action != null) {
    			spec = spec.and(ReservationSpec.action(action));
    		}
    	}

    	if(userId != null){
    		User user = userRepository.findOne(userId);
    		if(user != null) {
    			spec = spec.and(ReservationSpec.user(user));
    		}
    	}

    	if(StringUtils.isNotEmpty(yearMonth)) {
        	spec = spec.and(ReservationSpec.yearMonth(yearMonth));
        }

    	Integer usingNumbers = reservationRepository.count(spec);
    	return new ResponseEntity<Integer>(usingNumbers, HttpStatus.OK);
    }

}
