package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.bluepass.domain.Feature;
import co.bluepass.repository.FeatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * The type Feature resource.
 */
@RestController
@RequestMapping("/api")
public class FeatureResource {

    private final Logger log = LoggerFactory.getLogger(FeatureResource.class);

    @Inject
    private FeatureRepository featureRepository;

    /**
     * Create response entity.
     *
     * @param feature the feature
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/features",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Feature feature) throws URISyntaxException {
        log.debug("REST request to save Feature : {}", feature);
        if (feature.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new feature cannot already have an ID").build();
        }
        featureRepository.save(feature);
        return ResponseEntity.created(new URI("/api/features/" + feature.getId())).build();
    }

    /**
     * Update response entity.
     *
     * @param feature the feature
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/features",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Feature feature) throws URISyntaxException {
        log.debug("REST request to update Feature : {}", feature);
        if (feature.getId() == null) {
            return create(feature);
        }
        featureRepository.save(feature);
        return ResponseEntity.ok().build();
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @RequestMapping(value = "/features",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Feature> getAll() {
        log.debug("REST request to get all Features");
        return featureRepository.findAll();
    }

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/features/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Feature> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Feature : {}", id);
        Feature feature = featureRepository.findOne(id);
        if (feature == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(feature, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/features/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Feature : {}", id);
        featureRepository.delete(id);
    }
}
