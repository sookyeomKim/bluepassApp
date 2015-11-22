package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;

import co.bluepass.domain.PartnerRequest;
import co.bluepass.domain.User;
import co.bluepass.repository.PartnerRequestRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * The type Partner request resource.
 */
@RestController
@RequestMapping("/api")
public class PartnerRequestResource {

    private final Logger log = LoggerFactory.getLogger(PartnerRequestResource.class);

    @Inject
    private PartnerRequestRepository partnerRequestRepository;

	@Inject
	private UserRepository userRepository;

    /**
     * Create response entity.
     *
     * @param partnerRequest the partner request
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/partnerRequests",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody PartnerRequest partnerRequest) throws URISyntaxException {
        log.debug("REST request to save PartnerRequest : {}", partnerRequest);
        if (partnerRequest.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new partnerRequest cannot already have an ID").build();
        }

        User loginUser = null;
        if(partnerRequest.getUser() == null || partnerRequest.getUser().getId() == null) {
        	loginUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
        } else if(partnerRequest.getUser().getId() > 0) {
        	loginUser = userRepository.findOne(partnerRequest.getUser().getId());
        }
        partnerRequest.setUser(loginUser);

        partnerRequestRepository.save(partnerRequest);
        return ResponseEntity.created(new URI("/api/partnerRequests/" + partnerRequest.getId())).build();
    }

    /**
     * Update response entity.
     *
     * @param partnerRequest the partner request
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/partnerRequests",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody PartnerRequest partnerRequest) throws URISyntaxException {
        log.debug("REST request to update PartnerRequest : {}", partnerRequest);
        if (partnerRequest.getId() == null) {
            return create(partnerRequest);
        }
        partnerRequestRepository.save(partnerRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @RequestMapping(value = "/partnerRequests",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PartnerRequest> getAll() {
        log.debug("REST request to get all PartnerRequests");
        return partnerRequestRepository.findAll();
    }

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/partnerRequests/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PartnerRequest> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get PartnerRequest : {}", id);
        PartnerRequest partnerRequest = partnerRequestRepository.findOne(id);
        if (partnerRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(partnerRequest, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/partnerRequests/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete PartnerRequest : {}", id);
        partnerRequestRepository.delete(id);
    }
}
