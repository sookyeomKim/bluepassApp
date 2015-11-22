package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;

import co.bluepass.domain.Club;
import co.bluepass.domain.Instructor;
import co.bluepass.repository.ClubRepository;
import co.bluepass.repository.InstructorRepository;

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
 * The type Instructor resource.
 */
@RestController
@RequestMapping("/api")
public class InstructorResource {

    private final Logger log = LoggerFactory.getLogger(InstructorResource.class);

    @Inject
    private InstructorRepository instructorRepository;

    @Inject
    private ClubRepository clubRepository;

    /**
     * Create response entity.
     *
     * @param instructor the instructor
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/instructors",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Instructor instructor) throws URISyntaxException {
        log.debug("REST request to save Instructor : {}", instructor);
        if (instructor.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instructor cannot already have an ID").build();
        }
        instructorRepository.save(instructor);
        return ResponseEntity.created(new URI("/api/instructors/" + instructor.getId())).build();
    }

    /**
     * Update response entity.
     *
     * @param instructor the instructor
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/instructors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Instructor instructor) throws URISyntaxException {
        log.debug("REST request to update Instructor : {}", instructor);
        if (instructor.getId() == null) {
            return create(instructor);
        }
        instructorRepository.save(instructor);
        return ResponseEntity.ok().build();
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @RequestMapping(value = "/instructors",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Instructor> getAll() {
        log.debug("REST request to get all Instructors");
        return instructorRepository.findAll();
    }

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/instructors/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Instructor> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Instructor : {}", id);
        Instructor instructor = instructorRepository.findOne(id);
        if (instructor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(instructor, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/instructors/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Instructor : {}", id);
        instructorRepository.delete(id);
    }
}
