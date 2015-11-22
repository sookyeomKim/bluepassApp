package co.bluepass.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.fasterxml.jackson.annotation.JsonView;

import co.bluepass.domain.Action;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.Instructor;
import co.bluepass.domain.Reservation;
import co.bluepass.domain.User;
import co.bluepass.repository.ActionRepository;
import co.bluepass.repository.ClassScheduleRepository;
import co.bluepass.repository.ClubRepository;
import co.bluepass.repository.CommonCodeRepository;
import co.bluepass.repository.InstructorRepository;
import co.bluepass.repository.ReservationRepository;
import co.bluepass.repository.condition.ClassScheduleSpec;
import co.bluepass.web.rest.dto.ClassScheduleDTO;
import co.bluepass.web.rest.dto.ClassScheduleSimple;
import co.bluepass.web.rest.jsonview.Views;
import co.bluepass.web.rest.util.PaginationUtil;

/**
 * The type Class schedule resource.
 */
@RestController
@RequestMapping("/api")
public class ClassScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ClassScheduleResource.class);

    @Inject
    private ClassScheduleRepository classScheduleRepository;

    @Inject
    private ActionRepository actionRepository;

    @Inject
    private ClubRepository clubRepository;

	@Inject
	private InstructorRepository instructorRepository;

	@Inject
	private CommonCodeRepository commonCodeRepository;

	@Inject
	private ReservationRepository reservationRepository;

    /**
     * Create response entity.
     *
     * @param classSchedule the class schedule
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/classSchedules",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ClassSchedule classSchedule) throws URISyntaxException {
        log.debug("REST request to save ClassSchedule : {}", classSchedule);
        if (classSchedule.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new classSchedule cannot already have an ID").build();
        }
        classScheduleRepository.save(classSchedule);
        return ResponseEntity.created(new URI("/api/classSchedules/" + classSchedule.getId())).build();
    }

    /**
     * Update response entity.
     *
     * @param dto     the dto
     * @param request the request
     * @return the response entity
     * @throws Exception the exception
     */
    @RequestMapping(value = "/classSchedules",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ClassScheduleDTO dto, HttpServletRequest request) throws Exception {
        log.debug("REST request to update ClassSchedule : {}", dto);
        if (dto.getId() == null) {
        	return ResponseEntity.badRequest().header("Failure", "수정할 클래스의 아이디가 없습니다.").build();
        }

        ClassSchedule classSchedule = classScheduleRepository.findOne(dto.getId());
        /*if(classSchedule != null){
        	List<ClassSchedule> param = new ArrayList<>();
        	param.add(classSchedule);
        	List<Reservation> reservations = reservationRepository.findByClassScheduleIn(param);
        	if(!request.isUserInRole("ROLE_ADMIN") && reservations != null && !reservations.isEmpty()){
        		throw new Exception("예약된 사용자가 있습니다.");
        	}
        }*/

        if(dto.getEnable() != null) {
        	// 비활성화
        	classSchedule.setEnable(dto.getEnable());
        }

        if(dto.getFinished() != null) {
        	// 마감여부
        	classSchedule.setFinished(dto.getFinished());
        }

        classScheduleRepository.save(classSchedule);
        return ResponseEntity.ok().build();
    }

    /**
     * Gets all.
     *
     * @param offset       the offset
     * @param limit        the limit
     * @param startDate    the start date
     * @param endDate      the end date
     * @param startTime    the start time
     * @param endTime      the end time
     * @param address      the address
     * @param actionId     the action id
     * @param clubId       the club id
     * @param instructorId the instructor id
     * @param reservated   the reservated
     * @param category     the category
     * @param enable       the enable
     * @param used         the used
     * @param finished     the finished
     * @return the all
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/classSchedules",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @JsonView(Views.ClassScheduleSummary.class)
    //https://github.com/Lemniscate/spring-json-views
    public ResponseEntity<List<ClassSchedule>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
            @RequestParam(value = "per_page", required = false) Integer limit,
    		@RequestParam(value = "startDate", required = false) String startDate,
    		@RequestParam(value = "endDate", required = false) String endDate,
    		@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "actionId", required = false) Long actionId,
			@RequestParam(value = "clubId", required = false) Long clubId,
			@RequestParam(value = "instructorId", required = false) Long instructorId,
			@RequestParam(value = "reservated", required = false, defaultValue = "false") Boolean reservated,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "enable", required = false, defaultValue = "true") Boolean enable,
			@RequestParam(value = "used", required = false) Boolean used,
			@RequestParam(value = "finished", required = false) Boolean finished
			)
        throws URISyntaxException {
        log.debug("REST request to get all ClassSchedules");

        Specifications<ClassSchedule> spec = buildSpec(startDate, endDate, startTime, endTime, address, actionId,
				clubId, instructorId, reservated, category, enable, used, finished, offset);

    	Page<ClassSchedule> page = classScheduleRepository.findAll(spec, PaginationUtil.generatePageRequest(offset, limit, "startTime", "ASC"));
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/classSchedules", offset, limit);

    	return new ResponseEntity<List<ClassSchedule>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Gets count.
     *
     * @param offset       the offset
     * @param limit        the limit
     * @param startDate    the start date
     * @param endDate      the end date
     * @param startTime    the start time
     * @param endTime      the end time
     * @param address      the address
     * @param actionId     the action id
     * @param clubId       the club id
     * @param instructorId the instructor id
     * @param reservated   the reservated
     * @param category     the category
     * @param enable       the enable
     * @param used         the used
     * @param finished     the finished
     * @return the count
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/classSchedules/count",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public int getCount(@RequestParam(value = "page" , required = false) Integer offset,
    		@RequestParam(value = "per_page", required = false) Integer limit,
    		@RequestParam(value = "startDate", required = false) String startDate,
    		@RequestParam(value = "endDate", required = false) String endDate,
    		@RequestParam(value = "startTime", required = false) String startTime,
    		@RequestParam(value = "endTime", required = false) String endTime,
    		@RequestParam(value = "address", required = false) String address,
    		@RequestParam(value = "actionId", required = false) Long actionId,
    		@RequestParam(value = "clubId", required = false) Long clubId,
    		@RequestParam(value = "instructorId", required = false) Long instructorId,
    		@RequestParam(value = "reservated", required = false, defaultValue = "false") Boolean reservated,
    		@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "enable", required = false, defaultValue = "true") Boolean enable,
			@RequestParam(value = "used", required = false) Boolean used,
			@RequestParam(value = "finished", required = false) Boolean finished
    		)
    				throws URISyntaxException {
    	log.debug("REST request to get all ClassSchedules");

    	Specifications<ClassSchedule> spec = buildSpec(startDate, endDate, startTime, endTime, address, actionId,
    			clubId, instructorId, reservated, category, enable, used, finished, offset);

    	int size = classScheduleRepository.findAll(spec, PaginationUtil.generatePageRequest(offset, limit, "startTime", "ASC")).getSize();

    	return size;
    }

	private Specifications<ClassSchedule> buildSpec(String startDate, String endDate, String startTime, String endTime,
			String address, Long actionId, Long clubId, Long instructorId, Boolean reservated, String category,
			Boolean enable, Boolean used, Boolean finished, Integer offset) {
		if(offset != null && offset.equals(-1)){
			startDate = "150801";
		} else if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
        	startDate = "150801";
    		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyMMdd");
    		endDate = fmt.print(DateTime.now());
        }

        Specifications<ClassSchedule> spec = Specifications.where(ClassScheduleSpec.startDate(startDate));
        spec = spec.and(ClassScheduleSpec.endDate(endDate));

        if(StringUtils.isNotEmpty(startTime)) {
        	spec = spec.and(ClassScheduleSpec.startTime(startTime));
        }

        if(StringUtils.isNotEmpty(endTime)) {
        	spec = spec.and(ClassScheduleSpec.endTime(endTime));
        }

        if(actionId != null) {
        	Action action = actionRepository.findOne(actionId);
        	if(action != null) {
        		spec = spec.and(ClassScheduleSpec.action(action));
        	}
        }

        if(clubId != null) {
        	Club club = clubRepository.findOne(clubId);
        	if(club != null) {
        		spec = spec.and(ClassScheduleSpec.club(club));
        	}
        }

        if(instructorId != null) {
        	Instructor instructor = instructorRepository.findOne(instructorId);
        	if(instructor != null) {
        		spec = spec.and(ClassScheduleSpec.instructor(instructor));
        	}
        }

        if(StringUtils.isNotEmpty(category) && !"all".equalsIgnoreCase(category)) {
        	CommonCode categoryParent = commonCodeRepository.findOneByName("CATEGORY_SPORTART");
    		CommonCode categoryCode = commonCodeRepository.findByParentAndOption1(categoryParent, category);
    		if(category != null) {
    			spec = spec.and(ClassScheduleSpec.category(categoryCode));
    		}
        }

    	if(!"all".equalsIgnoreCase(address) && StringUtils.isNotEmpty(address)) {
    		/*String[] addresses = address.split(" ");
    		if(addresses != null && addresses.length == 2){
    			spec = spec.and(ClassScheduleSpec.address(addresses[0], addresses[1]));
    		}*/
    		spec = spec.and(ClassScheduleSpec.oldAddress(address));
    	}

    	if(reservated) {
    		spec = spec.and(ClassScheduleSpec.reservatated());
    	}

    	if(enable != null) {
    		spec = spec.and(ClassScheduleSpec.enabled(enable));
    	}

    	if(used != null) {
    		spec = spec.and(ClassScheduleSpec.used(used));
    	}

    	if(finished != null) {
    		spec = spec.and(ClassScheduleSpec.finished(finished));
    	}
		return spec;
	}

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/classSchedules/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClassSchedule> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get ClassSchedule : {}", id);
        ClassSchedule classSchedule = classScheduleRepository.findOne(id);
        if (classSchedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(classSchedule, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/classSchedules/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ClassSchedule : {}", id);
        classScheduleRepository.delete(id);
    }


    /**
     * Gets reservated users.
     *
     * @param id              the id
     * @param includeCanceled the include canceled
     * @param includeUsed     the include used
     * @param response        the response
     * @return the reservated users
     */
    @RequestMapping(value = "/classSchedules/{id}/users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Set<User>> getReservatedUsers(@PathVariable Long id,
    		@RequestParam(defaultValue = "false", required = false) Boolean includeCanceled,
    		@RequestParam(defaultValue = "true", required = false) Boolean includeUsed,
    		HttpServletResponse response) {
    	log.debug("REST request to get ClassSchedule : {}", id);
        ClassSchedule classSchedule = classScheduleRepository.findOne(id);
        if (classSchedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Reservation> reservations = reservationRepository.findByClassSchedule(classSchedule);
        if (reservations == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<User> users = new HashSet<User>();
        for (Reservation r : reservations) {
        	if(!includeCanceled && r.getCanceled()){
        		continue;
        	}
        	if(!includeUsed && r.getUsed()){
        		continue;
        	}
        	users.add(r.getUser());
		}

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Gets reservations.
     *
     * @param id              the id
     * @param includeCanceled the include canceled
     * @param response        the response
     * @return the reservations
     */
    @RequestMapping(value = "/classSchedules/{id}/reservations",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @JsonView(Views.ReservationSummary.class)
    public ResponseEntity<List<Reservation>> getReservations(@PathVariable Long id,
    		@RequestParam(defaultValue = "false", required = false) Boolean includeCanceled,
    		HttpServletResponse response) {
    	log.debug("REST request to get ClassSchedule : {}", id);
    	ClassSchedule classSchedule = classScheduleRepository.findOne(id);
    	if (classSchedule == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}

    	List<Reservation> reservations = null;

    	if(includeCanceled){
    		reservations = reservationRepository.findByClassSchedule(classSchedule);
    	}else{
    		reservations = reservationRepository.findByClassScheduleAndCanceledOrClassScheduleAndCanceledIsNull(classSchedule, false, classSchedule);
    	}

    	if (reservations == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}

    	return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
