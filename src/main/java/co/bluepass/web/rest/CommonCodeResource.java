package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;

import co.bluepass.domain.CommonCode;
import co.bluepass.repository.CommonCodeRepository;
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

import java.util.List;
import java.util.Set;

/**
 * The type Common code resource.
 */
@RestController
@RequestMapping("/api")
public class CommonCodeResource {

    private final Logger log = LoggerFactory.getLogger(CommonCodeResource.class);

    @Inject
    private CommonCodeRepository commonCodeRepository;

    /**
     * Create response entity.
     *
     * @param commonCode the common code
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/commonCodes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody CommonCode commonCode) throws URISyntaxException {
        log.debug("REST request to save CommonCode : {}", commonCode);
        if (commonCode.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new commonCode cannot already have an ID").build();
        }

        commonCodeRepository.save(commonCode);
        return ResponseEntity.created(new URI("/api/commonCodes/" + commonCode.getId())).build();
    }

    /**
     * Update response entity.
     *
     * @param commonCode the common code
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/commonCodes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody CommonCode commonCode) throws URISyntaxException {
        log.debug("REST request to update CommonCode : {}", commonCode);
        if (commonCode.getId() == null) {
            return create(commonCode);
        }

        commonCodeRepository.save(commonCode);
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
    @RequestMapping(value = "/commonCodes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CommonCode>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {

    	List<CommonCode> result = null;
    	HttpHeaders headers = null;
    	if(offset != null && offset == -1){
    		result = commonCodeRepository.findAll();
    		return new ResponseEntity<List<CommonCode>>(result, HttpStatus.OK);
    	}else {
    		Page<CommonCode> page = commonCodeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
    		headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commonCodes", offset, limit);
    		result = page.getContent();
    		return new ResponseEntity<List<CommonCode>>(result, headers, HttpStatus.OK);
    	}
    }

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/commonCodes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CommonCode> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CommonCode : {}", id);
        CommonCode commonCode = commonCodeRepository.findOne(id);
        if (commonCode == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commonCode, HttpStatus.OK);
    }

    /**
     * Gets children.
     *
     * @param codeName the code name
     * @param response the response
     * @return the children
     */
    @RequestMapping(value = "/commonCodes/{codeName}/children",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Set<CommonCode>> getChildren(@PathVariable String codeName, HttpServletResponse response) {
    	log.debug("REST request to get children CommonCode of {}", codeName);
    	CommonCode commonCode = commonCodeRepository.findByName(codeName);
    	if (commonCode == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	if (commonCode.getChildren() == null || commonCode.getChildren().isEmpty()) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>(commonCode.getChildren(), HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/commonCodes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete CommonCode : {}", id);
        commonCodeRepository.delete(id);
    }
}
