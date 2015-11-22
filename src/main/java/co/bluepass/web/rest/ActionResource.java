package co.bluepass.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

import co.bluepass.domain.Action;
import co.bluepass.domain.ActionImage;
import co.bluepass.domain.ActionSchedule;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.Feature;
import co.bluepass.domain.Image;
import co.bluepass.domain.Instructor;
import co.bluepass.domain.Reservation;
import co.bluepass.domain.ReservationHistory;
import co.bluepass.domain.User;
import co.bluepass.domain.Zip;
import co.bluepass.exception.ImageNotFoundException;
import co.bluepass.repository.ActionImageRepository;
import co.bluepass.repository.ActionRepository;
import co.bluepass.repository.ActionScheduleRepository;
import co.bluepass.repository.ClassScheduleRepository;
import co.bluepass.repository.ClubRepository;
import co.bluepass.repository.CommonCodeRepository;
import co.bluepass.repository.FeatureRepository;
import co.bluepass.repository.InstructorRepository;
import co.bluepass.repository.ReservationHistoryRepository;
import co.bluepass.repository.ReservationRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.repository.ZipRepository;
import co.bluepass.repository.condition.ActionSpec;
import co.bluepass.security.SecurityUtils;
import co.bluepass.service.ImageService;
import co.bluepass.web.rest.dto.ActionDTO;
import co.bluepass.web.rest.jsonview.Views;
import co.bluepass.web.rest.util.PaginationUtil;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * The type Action resource.
 */
@RestController
@RequestMapping("/api")
public class ActionResource {

    private final Logger log = LoggerFactory.getLogger(ActionResource.class);

    @Inject
    private ActionRepository actionRepository;

    @Inject
    private ActionScheduleRepository actionScheduleRepository;

	@Inject
	private CommonCodeRepository commonCodeRepository;

	@Inject
	private UserRepository userRepository;

	@Inject
	private ClubRepository clubRepository;

	@Inject
	private ImageService imageService;

	@Inject
	private ActionImageRepository actionImageRepository;

	@Inject
	private FeatureRepository featureRepository;

	@Inject
	private ClassScheduleRepository classScheduleRepository;

	@Inject
	private ZipRepository zipRepository;

	@Inject
	private InstructorRepository instructorRepository;

	@Inject
	private ReservationRepository reservationRepository;

	@Inject
	private ReservationHistoryRepository reservationHistoryRepository;

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/actions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ActionDTO dto) throws URISyntaxException {
        log.debug("REST request to save Action : {}", dto);
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new action cannot already have an ID").build();
        }

        CommonCode category = dto.getCategory();

		User loginUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());

		Club club = clubRepository.findOne(dto.getClubId());

		Zip zip = zipRepository.findByZipcode(club.getZipcode());

		Action action = new Action(dto.getTitle(), dto.getDescription(),
				dto.getShortDescription(), category, club, zip,
				dto.getUseLimitType(), dto.getUseLimitValue(),
				dto.getMovieIds(), loginUser);

		actionRepository.save(action);

		List<ActionImage> actionImages = null;
		if(dto.getImageIds() != null && dto.getImageIds().length > 0) {
			List<Image> images = imageService.findByIdIn(dto.getImageIds());
			if(images != null && !images.isEmpty()){
				actionImages = new ArrayList<ActionImage>();
				for (Image image : images) {
					ActionImage ai = new ActionImage(action, image);
					actionImages.add(ai);
				}
				actionImageRepository.save(actionImages);
			}
		}

        return ResponseEntity.created(new URI("/api/actions/" + action.getId())).build();
    }

    /**
     * Update response entity.
     *
     * @param dto     the dto
     * @param request the request
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/actions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ActionDTO dto, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Action : {}", dto);
        if (dto.getId() == null) {
            return create(dto);
        }

        Action action = actionRepository.findOne(dto.getId());

        if(!request.isUserInRole("ROLE_ADMIN") && !action.getCreator().getEmail().equals(SecurityUtils.getCurrentLogin())){
        	return ResponseEntity.badRequest().header("Failure", "Has not Authority").build();
        }

        CommonCode category = dto.getCategory();

		Club club = clubRepository.findOne(dto.getClubId());

		action.update(dto.getTitle(), dto.getDescription(), dto.getShortDescription(),
				category, club, dto.getUseLimitType(), dto.getUseLimitValue(),
				dto.getMovieIds(), action.getCreator());

        actionRepository.save(action);

        List<ActionImage> actionImages = null;
		if(dto.getImageIds() != null && dto.getImageIds().length > 0) {
			List<Image> images = imageService.findByIdIn(dto.getImageIds());
			if(images != null && !images.isEmpty()){
				actionImages = new ArrayList<ActionImage>();
				for (Image image : images) {
					ActionImage ai = new ActionImage(action, image);
					actionImages.add(ai);
				}
				actionImageRepository.save(actionImages);
			}
		}

        return ResponseEntity.ok().build();
    }

    /**
     * Gets all by class schedule.
     *
     * @param offset   the offset
     * @param limit    the limit
     * @param category the category
     * @return the all by class schedule
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/actionsBySchedule",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Action>> getAllByClassSchedule(
    		@RequestParam(value = "page" , required = false) Integer offset,
    		@RequestParam(value = "per_page", required = false) Integer limit,
    		@RequestParam(value = "category", required = false) String category
    		) throws URISyntaxException {

    	List<Action> result = new ArrayList<>();
    	List<ClassSchedule> schedules = null;
    	Page<ClassSchedule> page = null;
    	HttpHeaders headers = new HttpHeaders();



    	if(StringUtils.isNotEmpty(category)) {
    		CommonCode categoryParent = commonCodeRepository.findOneByName("CATEGORY_SPORTART");
    		CommonCode categoryCode = commonCodeRepository.findByParentAndOption1(categoryParent, category);

    		if(offset != null && offset == -1){
    			schedules = classScheduleRepository.findDistinctActionByCategoryAndStartTimeGreaterThanEqualOrderByStartTimeAsc(categoryCode, DateTime.now());
    		} else {
    			page = classScheduleRepository.findDistinctActionByCategoryAndStartTimeGreaterThanEqual(categoryCode, DateTime.now(),
    					PaginationUtil.generatePageRequest(offset, limit, "startTime", "asc"));
    			schedules = page.getContent();
    			headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actionsBySchedule", offset, limit);
    		}
    	} else if(offset != null && offset == -1){
    		schedules = classScheduleRepository.findDistinctActionByStartTimeGreaterThanEqualOrderByStartTimeAsc(DateTime.now());
    	} else {
    		page = classScheduleRepository.findDistinctActionByStartTimeGreaterThanEqual(DateTime.now(),
    				PaginationUtil.generatePageRequest(offset, limit, "startTime", "asc"));
    		schedules = page.getContent();
    		headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actionsBySchedule", offset, limit);
    	}

    	for (ClassSchedule classSchedule : schedules) {
    		List<ActionImage> actionImages = actionImageRepository.findByAction(classSchedule.getAction());
    		if(actionImages != null && !actionImages.isEmpty()){
    			List<Image> images = new ArrayList<Image>();
    			for (ActionImage actionImage : actionImages) {
    				images.add(actionImage.getImage());
    			}
    			classSchedule.getAction().setImages(images);
    		}
    		result.add(classSchedule.getAction());
		}

        return new ResponseEntity<List<Action>>(result, headers, HttpStatus.OK);
    }


    /**
     * Gets all.
     *
     * @param offset   the offset
     * @param limit    the limit
     * @param category the category
     * @param address  the address
     * @return the all
     * @throws URISyntaxException      the uri syntax exception
     * @throws JsonProcessingException the json processing exception
     */
    @RequestMapping(value = "/actions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @JsonView(Views.ActionSummary.class)
    public ResponseEntity<List<Action>> getAll(
    		@RequestParam(value = "page" , required = false) Integer offset,
    		@RequestParam(value = "per_page", required = false) Integer limit,
    		@RequestParam(value = "category", required = false) String category,
    		@RequestParam(value = "address", required = false) String address
    		) throws URISyntaxException, JsonProcessingException {

    	Specifications<Action> spec = null;

    	if(StringUtils.isNotEmpty(category)) {
    		CommonCode categoryParent = commonCodeRepository.findOneByName("CATEGORY_SPORTART");
    		CommonCode categoryCode = null;
    		if(!"all".equalsIgnoreCase(category)){
    			categoryCode = commonCodeRepository.findByParentAndOption1(categoryParent, category);
    			if(categoryCode != null){
    				spec = spec == null
    						? Specifications.where(ActionSpec.category(categoryCode))
    						: spec.and(ActionSpec.category(categoryCode));
    			}
    		}
    	}

    	if(StringUtils.isNotEmpty(address) && !"all".equalsIgnoreCase(address)) {
    		/*String[] addresses = address.split(" ");
    		if(addresses != null && addresses.length == 2){
    			spec = spec == null
						? Specifications.where(ActionSpec.address(addresses[0], addresses[1]))
						: spec.and(ActionSpec.address(addresses[0], addresses[1]));
    		}*/
    		spec = spec == null
					? Specifications.where(ActionSpec.oldAddress(address))
					: spec.and(ActionSpec.oldAddress(address));
    	}

    	Page<Action> page = actionRepository.findAll(spec, PaginationUtil.generatePageRequest(offset, limit));
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actions", offset, limit);

    /*		Zip zip = zipId == null ? null : zipRepository.findOne(zipId);

    		if(offset != null && offset == -1){
    			if(categoryCode == null && zip == null) {
    				result = actionRepository.findAll();
    			}else if(categoryCode == null) {
    				result = actionRepository.findByZip(zip);
    			}else if(zip == null) {
    				result = actionRepository.findByCategory(categoryCode);
    			}else {
    				result = actionRepository.findByCategoryAndZip(categoryCode, zip);
    			}
    		} else {

    			if(categoryCode == null && zip == null) {
    				page = actionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
    			}else if(categoryCode == null) {
    				page = actionRepository.findByZip(zip, PaginationUtil.generatePageRequest(offset, limit));
    			}else if(zip == null) {
    				page = actionRepository.findByCategory(categoryCode, PaginationUtil.generatePageRequest(offset, limit));
    			}else {

    			}
    			result = page.getContent();
    			headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actions", offset, limit);
    		}
    	} else if(offset != null && offset == -1){
    		result = actionRepository.findAll();
    	} else {
    		page = actionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
    		result = page.getContent();
    		headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actions", offset, limit);
    	} */

        /*for (Action action : result) {

	        List<ActionImage> actionImages = actionImageRepository.findByAction(action);
	        if(actionImages != null && !actionImages.isEmpty()){
	        	List<Image> images = new ArrayList<Image>();
	        	for (ActionImage actionImage : actionImages) {
	        		images.add(actionImage.getImage());
	        	}
	        	action.setImages(images);
	        }
		}*/

    	/*ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    	ObjectWriter objectWriter = objectMapper.writerWithView(Views.ActionSummary.class);
        return new ResponseEntity<String>(objectWriter.writeValueAsString(page.getContent()), headers, HttpStatus.OK);*/

        return new ResponseEntity<List<Action>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Gets category actions.
     *
     * @param category the category
     * @param offset   the offset
     * @param limit    the limit
     * @return the category actions
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/{category}/actions",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Action>> getCategoryActions(
    		@PathVariable String category,
    		@RequestParam(value = "page" , required = false) Integer offset,
    		@RequestParam(value = "per_page", required = false) Integer limit)
    				throws URISyntaxException {

    	CommonCode categoryParent = commonCodeRepository.findOneByName("CATEGORY_SPORTART");
		CommonCode categoryCode = commonCodeRepository.findByParentAndOption1(categoryParent, category);

    	Page<Action> page = actionRepository.findByCategory(categoryCode, PaginationUtil.generatePageRequest(offset, limit));

    	/*for (Action action : page) {

    		List<ActionImage> actionImages = actionImageRepository.findByAction(action);
    		if(actionImages != null && !actionImages.isEmpty()){
    			List<Image> images = new ArrayList<Image>();
    			for (ActionImage actionImage : actionImages) {
    				images.add(actionImage.getImage());
    			}
    			action.setImages(images);
    		}
    	}*/

    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actions", offset, limit);
    	return new ResponseEntity<List<Action>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/actions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Action> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Action : {}", id);
        Action action = actionRepository.findOne(id);
        if (action == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(action.getClub() != null){
        	List<Feature> features = featureRepository.findByClub(action.getClub());
    		if(features != null && !features.isEmpty()){
    			List<CommonCode> featureList = new ArrayList<CommonCode>();
    			for (Feature feature : features) {
    				featureList.add(feature.getCode());
    			}
    			action.getClub().setFeatures(featureList);
    		}
        }

        /*List<ActionImage> actionImages = actionImageRepository.findByAction(action);
        if(actionImages != null && !actionImages.isEmpty()){
        	List<Image> images = new ArrayList<Image>();
        	for (ActionImage actionImage : actionImages) {
        		images.add(actionImage.getImage());
        	}
        	action.setImages(images);
        }*/

        return new ResponseEntity<>(action, HttpStatus.OK);
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @RequestMapping(value = "/actions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Action : {}", id);

        Action action = actionRepository.findOne(id);

        if(classScheduleRepository.countByAction(action) > 0){
        	return ResponseEntity.badRequest().header("Failure", "Has not Authority").build();
        }

        List<ActionImage> actionImages = actionImageRepository.findByAction(action);
        actionRepository.delete(id);
        if(actionImages != null && !actionImages.isEmpty()) {
        	for (ActionImage actionImage : actionImages) {
        		try {
					imageService.delete(actionImage.getImage());
				} catch (ImageNotFoundException e) {
					e.printStackTrace();
				}
        	}
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Delete action image.
     *
     * @param id      the id
     * @param imageId the image id
     * @throws ImageNotFoundException the image not found exception
     */
    @RequestMapping(value = "/actions/{id}/image/{imageId}",
    		method = RequestMethod.DELETE,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void deleteActionImage(@PathVariable Long id, @PathVariable Long imageId) throws ImageNotFoundException {
    	log.debug("REST request to delete Action : {}", id);

    	Action action = actionRepository.findOne(id);
    	Image image = imageService.findById(imageId);

    	if(action != null && image != null) {
    		ActionImage actionImage = actionImageRepository.findByActionAndImage(action, image);
    		actionImageRepository.delete(actionImage);
    		imageService.delete(image);
    	}
    }


    /**
     * Gets schedules by action id.
     *
     * @param id       the id
     * @param response the response
     * @return the schedules by action id
     */
    @RequestMapping(value = "/actions/{id}/actionSchedules",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ActionSchedule>> getSchedulesByActionId(
    		@PathVariable Long id, HttpServletResponse response) {
    	log.debug("REST request to get ActionSchedules by Action id : {}", id);
    	Action action = actionRepository.findOne(id);
    	if (action == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    	List<ActionSchedule> actionSchedules = actionScheduleRepository.findByAction(action);
    	return new ResponseEntity<List<ActionSchedule>>(actionSchedules, HttpStatus.OK);
    }


    /**
     * Gets instructors by action id.
     *
     * @param id       the id
     * @param response the response
     * @return the instructors by action id
     */
    @RequestMapping(value = "/actions/{id}/instructors",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Instructor>> getInstructorsByActionId(
    		@PathVariable Long id, HttpServletResponse response) {
    	log.debug("REST request to get instructors by Action id : {}", id);
    	Action action = actionRepository.findOne(id);
    	if (action == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	List<ActionSchedule> actionSchedules = actionScheduleRepository.findDistinctInstructorByAction(action);
    	List<Instructor> instructors = new ArrayList<Instructor>();
    	Map<Long, Instructor> instructorMap = new HashMap<Long, Instructor>();
    	for (ActionSchedule actionSchedule : actionSchedules) {
    		if(!instructorMap.containsKey(actionSchedule.getInstructor().getId())){
    			instructors.add(actionSchedule.getInstructor());
    			instructorMap.put(actionSchedule.getInstructor().getId(), actionSchedule.getInstructor());
    		}
		}
    	return new ResponseEntity<List<Instructor>>(instructors, HttpStatus.OK);
    }


    /**
     * Gets using count.
     *
     * @param id        the id
     * @param userid    the userid
     * @param yearMonth the year month
     * @param response  the response
     * @return the using count
     */
    @RequestMapping(value = "/actions/{id}/usingcount",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map<String, Object>> getUsingCount(
    		@PathVariable Long id,
    		@RequestParam(required = true) Long userid,
    		@RequestParam(required = false) String yearMonth,
    		HttpServletResponse response) {
    	log.debug("REST request to get instructors by Action id : {}", id);
    	Action action = actionRepository.findOne(id);
    	User user = userRepository.findOne(userid);
    	if (action == null || user == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}

    	String useLimitType = action.getUseLimitType();
    	Integer useLimitCount = action.getUseLimitValue();

    	Integer totalUsingCount = reservationRepository.countByUserAndActionAndUsed(user, action, true);
    	Integer usingCountAtMonth = null;

    	if(StringUtils.isNotEmpty(yearMonth)){
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMM");
			DateTime monthStartDate = formatter.parseDateTime(yearMonth);
			DateTime monthEndDate = monthStartDate.withTime(23, 59, 59, 59);
			usingCountAtMonth = reservationRepository.countByUserAndActionAndUsedAndStartTimeBetween(user, action, true, monthStartDate, monthEndDate);
		}

    	int remainCount = 0;
    	if("총".equals(useLimitType) && totalUsingCount != null){
    		remainCount = useLimitCount - totalUsingCount;
    	}else if("월".equals(useLimitType)){
    		if(usingCountAtMonth != null){
    			remainCount = useLimitCount - usingCountAtMonth;
    		}else{
    			remainCount = useLimitCount;
    		}
    	}else{
    		remainCount = -1;
    	}

    	Map<String, Object> resultMap = new HashMap<>();
    	resultMap.put("useLimitType", useLimitType);
    	resultMap.put("useLimitCount", useLimitCount);
    	resultMap.put("totalUsingCount", totalUsingCount);
    	resultMap.put("usingCountAtMonth", usingCountAtMonth);
    	resultMap.put("remainCount", remainCount);

    	return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }


    /**
     * Gets customers by club id.
     *
     * @param id        the id
     * @param yearMonth the year month
     * @param response  the response
     * @return the customers by club id
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/actions/{id}/customers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<User>> getCustomersByClubId(
			@PathVariable Long id, @RequestParam String yearMonth, HttpServletResponse response)
					throws URISyntaxException {
		log.debug("REST request to get Actions by club id : {}", id);
		List<User> users = new ArrayList<User>();
		List<ReservationHistory> reservationHistories = null;

		if(StringUtils.isNotEmpty(yearMonth)){
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMM");
			DateTime monthStartDate = formatter.parseDateTime(yearMonth);
			DateTime monthEndDate = monthStartDate.dayOfMonth().withMaximumValue();
			monthEndDate = monthEndDate.withTime(23, 59, 59, 59);
			reservationHistories = reservationHistoryRepository.findByActionIdAndUsedAndStartTimeBetween(id, true, monthStartDate, monthEndDate);
		}else{
			reservationHistories = reservationHistoryRepository.findByActionIdAndUsed(id, true);
		}

		if (reservationHistories == null || reservationHistories.isEmpty()) {
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}

		Set<Long> userIds = new HashSet<>();
        for (ReservationHistory history : reservationHistories) {
        	userIds.add(history.getUserId());
		}

        users = userRepository.findAll(userIds);

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
}
