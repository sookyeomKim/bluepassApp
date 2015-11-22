package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.bluepass.domain.Zip;
import co.bluepass.repository.ZipRepository;
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

/**
 * The type Zip resource.
 */
@RestController
@RequestMapping("/api")
public class ZipResource {

    private final Logger log = LoggerFactory.getLogger(ZipResource.class);

    @Inject
    private ZipRepository zipRepository;

    /**
     * Create response entity.
     *
     * @param zip the zip
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/zips",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Zip zip) throws URISyntaxException {
        log.debug("REST request to save Zip : {}", zip);
        if (zip.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new zip cannot already have an ID").build();
        }
        zipRepository.save(zip);
        return ResponseEntity.created(new URI("/api/zips/" + zip.getId())).build();
    }

    /**
     * Update response entity.
     *
     * @param zip the zip
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/zips",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Zip zip) throws URISyntaxException {
        log.debug("REST request to update Zip : {}", zip);
        if (zip.getId() == null) {
            return create(zip);
        }
        zipRepository.save(zip);
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
    @RequestMapping(value = "/zips",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Zip>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Zip> page = zipRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zips", offset, limit);
        return new ResponseEntity<List<Zip>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Search zip list.
     *
     * @param sido  the sido
     * @param gugun the gugun
     * @param dong  the dong
     * @return the list
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/zips/search",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Zip> searchZip(
    		@RequestParam(value = "sido" , required = false) String sido,
    		@RequestParam(value = "gugun" , required = false) String gugun,
    		@RequestParam(value = "dong" , required = true) String dong)
    				throws URISyntaxException {

    	List<Zip> result = zipRepository.findByDongLike(dong + "%");

    	return result;
    }

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/zips/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Zip> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Zip : {}", id);
        Zip zip = zipRepository.findOne(id);
        if (zip == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(zip, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/zips/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Zip : {}", id);
        zipRepository.delete(id);
    }
}
