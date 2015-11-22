package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;

import co.bluepass.domain.Action;
import co.bluepass.domain.ActionSchedule;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.Instructor;
import co.bluepass.domain.Reservation;
import co.bluepass.domain.User;
import co.bluepass.proxy.SmsService;
import co.bluepass.repository.ActionRepository;
import co.bluepass.repository.ActionScheduleRepository;
import co.bluepass.repository.ClassScheduleRepository;
import co.bluepass.repository.InstructorRepository;
import co.bluepass.repository.ReservationRepository;
import co.bluepass.service.ScheduleService;
import co.bluepass.web.propertyeditors.DateTimeEditor;
import co.bluepass.web.rest.dto.ActionScheduleDTO;
import co.bluepass.web.rest.util.PaginationUtil;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.DateTime;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The type Action schedule resource.
 */
@RestController
@RequestMapping("/api")
public class ActionScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ActionScheduleResource.class);

    @Inject
    private ActionScheduleRepository actionScheduleRepository;

    @Inject
    private ActionRepository actionRepository;

    @Inject
    private InstructorRepository instructorRepository;

    @Inject
    private ClassScheduleRepository classScheduleRepository;

    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private ScheduleService scheduleService;

    /*@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(DateTime.class, new DateTimeEditor("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", true));
    }*/

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/actionSchedules",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ActionScheduleDTO dto) throws URISyntaxException {
        log.debug("REST request to save ActionSchedule : {}", dto);
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new actionSchedule cannot already have an ID").build();
        }

        Action action = actionRepository.findOne(dto.getActionId());
        if (action == null) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Failure", "action not found for id").build();
        }

        Club club = action.getClub();
        Instructor instructor = instructorRepository.findOne(dto.getInstructorId());

		ActionSchedule actionSchedule = new ActionSchedule(dto.getDay(),
				dto.getStartTime(), dto.getEndTime(), dto.getStartDate(),
				dto.getEndDate(), dto.getScheduleType(),
				dto.getAttendeeLimit(), club, instructor, action);

		ActionSchedule saved = actionScheduleRepository.save(actionSchedule);

		scheduleService.createClassSchedules(saved);

        return ResponseEntity.created(new URI("/api/actionSchedules/" + dto.getId())).build();
    }

    /**
     * Update response entity.
     *
     * @param dto the dto
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/actionSchedules",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ActionScheduleDTO dto) throws URISyntaxException {
        log.debug("REST request to update ActionSchedule : {}", dto);
        if (dto.getId() == null) {
            return create(dto);
        }

        ActionSchedule found = actionScheduleRepository.findOne(dto.getId());

        Action action = actionRepository.findOne(dto.getActionId());
        Club club = action.getClub();
        Instructor instructor = instructorRepository.findOne(dto.getInstructorId());

        found.update(dto.getDay(),
				dto.getStartTime(), dto.getEndTime(), dto.getStartDate(),
				dto.getEndDate(), dto.getScheduleType(),
				dto.getAttendeeLimit(), club, instructor, action);

        found.setEnable(true);
        ActionSchedule saved = actionScheduleRepository.save(found);

        scheduleService.createClassSchedules(saved);

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
    @RequestMapping(value = "/actionSchedules",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ActionSchedule>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
            @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        log.debug("REST request to get all ActionSchedules");

        List<ActionSchedule> result = null;
    	HttpHeaders headers = null;
    	if(offset != null && offset == -1){
    		result = actionScheduleRepository.findAll();
    		return new ResponseEntity<List<ActionSchedule>>(result, HttpStatus.OK);
    	}else {
    		Page<ActionSchedule> page = actionScheduleRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
    		headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actionSchedules", offset, limit);
    		result = page.getContent();
    		return new ResponseEntity<List<ActionSchedule>>(result, headers, HttpStatus.OK);
    	}
    }

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/actionSchedules/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ActionSchedule> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get ActionSchedule : {}", id);
        ActionSchedule actionSchedule = actionScheduleRepository.findOne(id);
        if (actionSchedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(actionSchedule, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/actionSchedules/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ActionSchedule : {}", id);
        actionScheduleRepository.delete(id);
    }

    /**
     * Active response entity.
     *
     * @param id       the id
     * @param enable   the enable
     * @param request  the request
     * @param response the response
     * @return the response entity
     * @throws Exception the exception
     */
    @RequestMapping(value = "/actionSchedules/{id}/active",
    		method = RequestMethod.POST,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ActionSchedule> active(
    		@PathVariable Long id,
    		@RequestParam(value = "enable" , required = true) boolean enable,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	log.debug("REST request to get ActionSchedule : {}", id);
    	ActionSchedule actionSchedule = actionScheduleRepository.findOne(id);
    	if (actionSchedule == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}

    	List<ClassSchedule> schedules = classScheduleRepository.findByActionSchedule(actionSchedule);

    	//List<Reservation> reservations = reservationRepository.findByCanceledAndUsedAndClassScheduleIn(false, false, schedules);

    	List<Reservation> reservations = reservationRepository.findByClassScheduleIn(schedules);

    	if(!request.isUserInRole("ROLE_ADMIN") && reservations != null && !reservations.isEmpty()){
    		throw new Exception("예약된 사용자가 있습니다.");
    	}

    	Map<Long, ClassSchedule> schedulesMap = new HashMap<Long, ClassSchedule>();
    	for (ClassSchedule classSchedule : schedules) {
    		classSchedule.setEnable(enable);
    		schedulesMap.put(classSchedule.getId(), classSchedule);
    	}
    	classScheduleRepository.save(schedules);

    	Set<ClassSchedule> deleteSchedules = new HashSet<ClassSchedule>();
    	Set<Reservation> deleteReservations = new HashSet<Reservation>();

    	DateTime now = DateTime.now();
    	DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm");
    	StringBuffer sb = null;

    	for (Reservation reservation : reservations) {
			if(reservation.getCanceled() || reservation.getUsed() || reservation.getStartTime().isBefore(now)){
				//수정
			}else{
				//삭제
				if(!enable){
					if(reservation.getClassSchedule().getStartTime().isBefore(DateTime.now())){
    					continue;
    				}

    				deleteReservations.add(reservation);
    				deleteSchedules.add(reservation.getClassSchedule());

    				sb = new StringBuffer("[블루패스]");
    				sb.append(reservation.getClassSchedule().getStartTime().toString(fmt)).append(" ")
    				.append(reservation.getClub().getName()).append(" ")
    				.append(reservation.getClassSchedule().getAction().getTitle()).append(" ")
    				.append("수업이 업체 사정으로 인해 취소되었습니다. 해당 프로그램은 다른 요일과 시간에 예약 가능하니 새로운 스케쥴을 확인해 주세요.");
    				SmsService.sendSms(sb.toString(), reservation.getUser());
				}
			}

			schedulesMap.remove(reservation.getClassSchedule().getId());
		}

    	deleteSchedules.addAll(schedulesMap.values());

    	/*if(StringUtils.isNotEmpty(actionSchedule.getClub().getManagerMobile())) {
    		sb = new StringBuffer("[블루패스]");
    		sb.append(actionSchedule.getStartTime().toString(fmt)).append(" ")
    		.append(actionSchedule.getClub().getName()).append(" ")
    		.append(actionSchedule.getAction().getTitle()).append(" ")
    		.append("수업이 취소되었습니다.");
    		SmsService.sendSms(sb.toString(), actionSchedule.getClub().getManagerMobile(), actionSchedule.getClub().getName());
    	}*/

    	reservationRepository.delete(deleteReservations);
    	classScheduleRepository.delete(deleteSchedules);

    	//classScheduleRepository.countByFinishedAndActionSchedule(false, actionSchedule);

    	actionSchedule.setEnable(enable);

    	actionScheduleRepository.save(actionSchedule);

    	if(enable){
    		scheduleService.createClassSchedules(actionSchedule);
    	}

    	return new ResponseEntity<>(actionSchedule, HttpStatus.OK);
    }
}
