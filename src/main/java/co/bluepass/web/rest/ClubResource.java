package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;

import co.bluepass.domain.Action;
import co.bluepass.domain.ActionImage;
import co.bluepass.domain.ActionSchedule;
import co.bluepass.domain.AddressIndex;
import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.Feature;
import co.bluepass.domain.Image;
import co.bluepass.domain.Instructor;
import co.bluepass.domain.Reservation;
import co.bluepass.domain.ReservationHistory;
import co.bluepass.domain.User;
import co.bluepass.repository.ActionImageRepository;
import co.bluepass.repository.ActionRepository;
import co.bluepass.repository.ActionScheduleRepository;
import co.bluepass.repository.AddressIndexRepository;
import co.bluepass.repository.ClubRepository;
import co.bluepass.repository.CommonCodeRepository;
import co.bluepass.repository.FeatureRepository;
import co.bluepass.repository.InstructorRepository;
import co.bluepass.repository.ReservationHistoryRepository;
import co.bluepass.repository.ReservationRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.security.SecurityUtils;
import co.bluepass.web.rest.dto.ClubDTO;
import co.bluepass.web.rest.jsonview.Views;
import co.bluepass.web.rest.util.PaginationUtil;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Club resource.
 */
@RestController
@RequestMapping("/api")
public class ClubResource {

	private final Logger log = LoggerFactory.getLogger(ClubResource.class);

	@Inject
	private ClubRepository clubRepository;

	@Inject
	private InstructorRepository instructorRepository;

	@Inject
	private ActionRepository actionRepository;

	@Inject
	private ActionImageRepository actionImageRepository;

	@Inject
	private UserRepository userRepository;

	@Inject
	private CommonCodeRepository commonCodeRepository;

	@Inject
	private FeatureRepository featureRepository;

    @Inject
    private ActionScheduleRepository actionScheduleRepository;

	@Inject
	private ReservationRepository reservationRepository;

	@Inject
	private ReservationHistoryRepository reservationHistoryRepository;

	@Inject
	private AddressIndexRepository addressIndexRepository;

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/clubs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> create(@Valid @RequestBody ClubDTO dto)
			throws URISyntaxException {
		log.debug("REST request to save Club Information : {}", dto);
		if (dto.getId() != null) {
			return ResponseEntity.badRequest()
					.header("Failure", "클럽이 이미 아이디를 가지고 있습니다.").build();
		}

		CommonCode category = dto.getCategory();

		Club club = new Club(null, dto.getName(), dto.getLicenseNumber(),
				dto.getPhoneNumber(), dto.getZipcode(), dto.getAddress1(),
				dto.getAddress2(), dto.getOldAddress(), dto.getAddressSimple(),
				dto.getDescription(), dto.getHomepage(), dto.getOnlyFemale(),
				category, dto.getManagerMobile(), dto.getNotificationType(),
				dto.getReservationClose());

		User loginUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
		club.setCreator(loginUser);

		Club saved = clubRepository.save(club);

		List<CommonCode> featureCodes = null;
		if(dto.getFeatures() != null) {
			//featureCodes = commonCodeRepository.findByNameIn(dto.getFeatures());
			featureCodes = commonCodeRepository.findAll(Arrays.asList(dto.getFeatures()));
			if(featureCodes != null && !featureCodes.isEmpty()) {
				List<Feature> features = new ArrayList<Feature>();
				for (CommonCode featureCode : featureCodes) {
					features.add(new Feature(saved, featureCode));
				}
				featureRepository.save(features);
			}
		}

		try {
			if(StringUtils.isNotEmpty(saved.getOldAddress())){
				addressIndexRepository.save(new AddressIndex(saved.getOldAddress()));
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return ResponseEntity.created(new URI("/api/clubs/" + club.getId())).build();
	}

    /**
     * Update response entity.
     *
     * @param dto       the dto
     * @param request   the request
     * @param principal the principal
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/clubs", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> update(@Valid @RequestBody ClubDTO dto,
			HttpServletRequest request, Principal principal) throws URISyntaxException {
		log.debug("REST request to update Club : {}", dto);

		if (dto.getId() == null) {
			return ResponseEntity.badRequest()
					.header("Failure", "수정할 클럽의 아이디가 없습니다.").build();
		}

		Club club = clubRepository.findOne(dto.getId());

		if(!request.isUserInRole("ROLE_ADMIN") && !club.getCreator().getEmail().equals(SecurityUtils.getCurrentLogin())){
			return ResponseEntity.badRequest().header("Failure", "클럽을 수정할 권한이 없습니다.").build();
		}

		CommonCode category = dto.getCategory();

		club.update(dto.getName(), dto.getLicenseNumber(),
				dto.getPhoneNumber(), dto.getZipcode(), dto.getAddress1(),
				dto.getAddress2(), dto.getOldAddress(), dto.getAddressSimple(),
				dto.getDescription(), dto.getHomepage(), dto.getOnlyFemale(),
				category, dto.getManagerMobile(),
				dto.getNotificationType(), dto.getReservationClose());

		clubRepository.save(club);

		List<CommonCode> featureCodes = null;
		if(dto.getFeatures() != null) {
			List<Feature> oldFeatures = featureRepository.findByClub(club);
			featureRepository.delete(oldFeatures);
			//featureCodes = commonCodeRepository.findByNameIn(dto.getFeatures());
			featureCodes = commonCodeRepository.findAll(Arrays.asList(dto.getFeatures()));
			if(featureCodes != null && !featureCodes.isEmpty()) {
				List<Feature> features = new ArrayList<Feature>();
				for (CommonCode featureCode : featureCodes) {
					features.add(new Feature(club, featureCode));
				}
				featureRepository.save(features);
			}
		}

		try {
			if(StringUtils.isNotEmpty(club.getOldAddress())){
				addressIndexRepository.save(new AddressIndex(club.getOldAddress()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return ResponseEntity.ok().build();
	}

    /**
     * Gets all.
     *
     * @param offset the offset
     * @param limit  the limit
     * @param name   the name
     * @return the all
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/clubs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Club>> getAll(
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit,
			@RequestParam(value = "name", required = false) String name
			)
			throws URISyntaxException {
		Page<Club> page = null;

		if(StringUtils.isNotEmpty(name)){
			page = clubRepository.findByNameLike("%" + name + "%", PaginationUtil.generatePageRequest(offset, limit, "id", "desc"));
		}else{
			page = clubRepository.findAll(PaginationUtil.generatePageRequest(offset, limit, "id", "desc"));
		}

		for (Club club : page) {
			List<Feature> features = featureRepository.findByClub(club);
			if(features != null && !features.isEmpty()){
				List<CommonCode> featureList = new ArrayList<CommonCode>();
				for (Feature feature : features) {
					featureList.add(feature.getCode());
				}
				club.setFeatures(featureList);
			}
		}

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
				page, "/api/clubs", offset, limit);
		return new ResponseEntity<List<Club>>(page.getContent(), headers,
				HttpStatus.OK);
	}

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/clubs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Club> get(@PathVariable Long id,
			HttpServletResponse response) {
		log.debug("REST request to get Club : {}", id);
		Club club = clubRepository.findOne(id);
		if (club == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Feature> features = featureRepository.findByClub(club);
		if(features != null && !features.isEmpty()){
			List<CommonCode> featureList = new ArrayList<CommonCode>();
			for (Feature feature : features) {
				featureList.add(feature.getCode());
			}
			club.setFeatures(featureList);
		}

		return new ResponseEntity<>(club, HttpStatus.OK);
	}

    /**
     * Delete.
     *
     * @param id      the id
     * @param request the request
     */
    @RequestMapping(value = "/clubs/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void delete(@PathVariable Long id, HttpServletRequest request) {
		log.debug("REST request to delete Club : {}", id);

		if (id == null || id <= 0) {
			return;
		}

		Club club = clubRepository.findOne(id);

		if(!request.isUserInRole("ROLE_ADMIN") && !club.getCreator().getEmail().equals(SecurityUtils.getCurrentLogin())){
			return;
		}
		clubRepository.delete(id);
	}

    /**
     * Gets instructors by club id.
     *
     * @param id       the id
     * @param response the response
     * @return the instructors by club id
     */
    @RequestMapping(value = "/clubs/{id}/instructors", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Instructor>> getInstructorsByClubId(
			@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get Instructors by club id : {}", id);
		Club club = clubRepository.findOne(id);
		if (club == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<Instructor> instructors = instructorRepository.findByClub(club);
		return new ResponseEntity<List<Instructor>>(instructors, HttpStatus.OK);
	}

    /**
     * Gets actions by club id.
     *
     * @param id       the id
     * @param response the response
     * @return the actions by club id
     */
    @RequestMapping(value = "/clubs/{id}/actions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@JsonView(Views.ActionSummary.class)
	public ResponseEntity<List<Action>> getActionsByClubId(
			@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get Actions by club id : {}", id);
		Club club = clubRepository.findOne(id);
		if (club == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<Action> actions = actionRepository.findByClub(club);

		for (Action action : actions) {

	        List<ActionImage> actionImages = actionImageRepository.findByAction(action);
	        if(actionImages != null && !actionImages.isEmpty()){
	        	List<Image> images = new ArrayList<Image>();
	        	for (ActionImage actionImage : actionImages) {
	        		images.add(actionImage.getImage());
	        	}
	        	action.setImages(images);
	        }
		}

		return new ResponseEntity<List<Action>>(actions, HttpStatus.OK);
	}

    /**
     * Gets schedules by club id.
     *
     * @param id       the id
     * @param response the response
     * @param offset   the offset
     * @param limit    the limit
     * @return the schedules by club id
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/clubs/{id}/schedules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<ActionSchedule>> getSchedulesByClubId(
			@PathVariable Long id, HttpServletResponse response,
			@RequestParam(value = "page" , required = false) Integer offset,
            @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
		log.debug("REST request to get Actions by club id : {}", id);
		Club club = clubRepository.findOne(id);
		if (club == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<ActionSchedule> result = null;
    	HttpHeaders headers = null;

    	if(offset != null && offset == -1){
    		result = actionScheduleRepository.findByClub(club);
    		return new ResponseEntity<List<ActionSchedule>>(result, HttpStatus.OK);
    	}else {
    		Page<ActionSchedule> page = actionScheduleRepository.findByClub(club, PaginationUtil.generatePageRequest(offset, limit));
    		headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actionSchedules", offset, limit);
    		result = page.getContent();
    		return new ResponseEntity<List<ActionSchedule>>(result, headers, HttpStatus.OK);
    	}
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
    @RequestMapping(value = "/clubs/{id}/customers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
			reservationHistories = reservationHistoryRepository.findByClubIdAndUsedAndStartTimeBetween(id, true, monthStartDate, monthEndDate);
		}else{
			reservationHistories = reservationHistoryRepository.findByClubIdAndUsed(id, true);
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

	private Sort sortByIdDesc() {
        return new Sort(Sort.Direction.DESC, "id");
    }
}
