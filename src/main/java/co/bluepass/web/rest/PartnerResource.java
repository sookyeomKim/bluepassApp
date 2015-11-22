package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;

import co.bluepass.domain.Action;
import co.bluepass.domain.ActionImage;
import co.bluepass.domain.ActionSchedule;
import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.Feature;
import co.bluepass.domain.Image;
import co.bluepass.domain.Instructor;
import co.bluepass.domain.User;
import co.bluepass.repository.ActionImageRepository;
import co.bluepass.repository.ActionRepository;
import co.bluepass.repository.ActionScheduleRepository;
import co.bluepass.repository.ClubRepository;
import co.bluepass.repository.CommonCodeRepository;
import co.bluepass.repository.FeatureRepository;
import co.bluepass.repository.InstructorRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.security.SecurityUtils;
import co.bluepass.web.rest.dto.ClubDTO;
import co.bluepass.web.rest.util.PaginationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Partner resource.
 */
@RestController
@RequestMapping("/api/partner")
public class PartnerResource {

	private final Logger log = LoggerFactory.getLogger(PartnerResource.class);

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


    /**
     * Gets clubs.
     *
     * @param offset the offset
     * @param limit  the limit
     * @return the clubs
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/clubs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Club>> getClubs(
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit)
			throws URISyntaxException {

		User loginUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());

		Page<Club> page = clubRepository.findByCreator(loginUser, PaginationUtil.generatePageRequest(offset, limit));

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
				page, "/api/partner/clubs", offset, limit);
		return new ResponseEntity<List<Club>>(page.getContent(), headers,
				HttpStatus.OK);
	}

    /**
     * Gets actions.
     *
     * @param offset the offset
     * @param limit  the limit
     * @return the actions
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/actions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Action>> getActions(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	User loginUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
        Page<Action> page = actionRepository.findByCreator(loginUser, PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actions", offset, limit);
        return new ResponseEntity<List<Action>>(page.getContent(), headers, HttpStatus.OK);
    }
}
