package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.bluepass.domain.AddressIndex;
import co.bluepass.repository.AddressIndexRepository;
import co.bluepass.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The type Address index resource.
 */
@RestController
@RequestMapping("/api")
public class AddressIndexResource {

    private final Logger log = LoggerFactory.getLogger(AddressIndexResource.class);

    @Inject
    private AddressIndexRepository addressIndexRepository;

    /**
     * Create response entity.
     *
     * @param addressIndex the address index
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/addressIndexs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressIndex> create(@RequestBody AddressIndex addressIndex) throws URISyntaxException {
        log.debug("REST request to save AddressIndex : {}", addressIndex);
        if (addressIndex.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new addressIndex cannot already have an ID").body(null);
        }
        AddressIndex result = addressIndexRepository.save(addressIndex);
        return ResponseEntity.created(new URI("/api/addressIndexs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("addressIndex", result.getId().toString()))
                .body(result);
    }

    /**
     * Update response entity.
     *
     * @param addressIndex the address index
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/addressIndexs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressIndex> update(@RequestBody AddressIndex addressIndex) throws URISyntaxException {
        log.debug("REST request to update AddressIndex : {}", addressIndex);
        if (addressIndex.getId() == null) {
            return create(addressIndex);
        }
        AddressIndex result = addressIndexRepository.save(addressIndex);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("addressIndex", addressIndex.getId().toString()))
                .body(result);
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @RequestMapping(value = "/addressIndexs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AddressIndex> getAll() {
        log.debug("REST request to get all AddressIndexs");
        return addressIndexRepository.findAll();
    }

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/addressIndexs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressIndex> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get AddressIndex : {}", id);
        AddressIndex addressIndex = addressIndexRepository.findOne(id);
        if (addressIndex == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(addressIndex, HttpStatus.OK);
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @RequestMapping(value = "/addressIndexs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete AddressIndex : {}", id);
        addressIndexRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("addressIndex", id.toString())).build();
    }
}
