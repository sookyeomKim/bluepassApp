package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;

import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.TicketHistory;
import co.bluepass.domain.User;
import co.bluepass.domain.Zip;
import co.bluepass.repository.CommonCodeRepository;
import co.bluepass.repository.TicketHistoryRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.repository.ZipRepository;
import co.bluepass.security.AuthoritiesConstants;
import co.bluepass.web.rest.dto.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * The type User resource.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private CommonCodeRepository commonCodeRepository;

    @Inject
    private ZipRepository zipRepository;

    @Inject
    private TicketHistoryRepository ticketHistoryRepository;

    /**
     * Gets all.
     *
     * @return the all
     */
    @RequestMapping(value = "/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<User> getAll() {
        log.debug("REST request to get all Users");
        return userRepository.findAll();
    }

    /**
     * Gets user.
     *
     * @param email    the email
     * @param response the response
     * @return the user
     */
    @RequestMapping(value = "/users/{email}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public User getUser(@PathVariable String email, HttpServletResponse response) {
        log.debug("REST request to get User : {}", email);
        User user = userRepository.findOneByEmail(email);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return user;
    }


    /**
     * Modify response entity.
     *
     * @param email    the email
     * @param dto      the dto
     * @param response the response
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/users/{email}/modify",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> modify(
    		@PathVariable String email,
    		@Valid @RequestBody UserDTO dto,
    		HttpServletResponse response) throws URISyntaxException {
    	log.debug("REST request to get User : {}", email);
    	log.debug("Request DTO : {}", dto);
        User user = userRepository.findOneByEmail(email);
        if (user.getId() == null) {
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        updateUserInfo(user, dto);

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

	private void updateUserInfo(User user, UserDTO dto) {

    	Zip favorSite = zipRepository.findOne(dto.getFavorSiteId());
    	CommonCode jacketSize = commonCodeRepository.findOne(dto.getJacketSizeId());
    	CommonCode pantsSize = commonCodeRepository.findOne(dto.getPantsSizeId());
    	CommonCode favorCategory = null;
    	if(dto.getFavorCategoryId() != null && !dto.getFavorCategoryId().isEmpty()){
    		favorCategory = commonCodeRepository.findOne(dto.getFavorCategoryId().get(0));
    	}

		user.setZipcode(dto.getZipcode());
		user.setAddress1(dto.getAddress1());
		user.setAddress2(dto.getAddress2());
		user.setAge(dto.getAge());
		user.setGender(dto.getGender());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setFavorCategory(favorCategory);
		user.setFavorSite(favorSite);
		user.setJacketSize(jacketSize);
		user.setPantsSize(pantsSize);
	}


    /**
     * Gets user ticket.
     *
     * @param id       the id
     * @param response the response
     * @return the user ticket
     */
    @RequestMapping(value = "/users/{id}/ticket",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public TicketHistory getUserTicket(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get User : {}", id);
        User user = userRepository.findOne(id);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        TicketHistory ticketHistory = ticketHistoryRepository.findTop1ByUserAndActivatedAndClosed(user, true, false);
        return ticketHistory;
    }
}
