package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.bluepass.domain.ReservationHistory;
import co.bluepass.domain.User;
import co.bluepass.repository.ReservationHistoryRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.security.SecurityUtils;
import co.bluepass.web.rest.util.HeaderUtil;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The type Reservation history resource.
 */
@RestController
@RequestMapping("/api")
public class ReservationHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ReservationHistoryResource.class);

    @Inject
    private ReservationHistoryRepository reservationHistoryRepository;

	@Inject
	private UserRepository userRepository;

    /**
     * Create response entity.
     *
     * @param reservationHistory the reservation history
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservationHistorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReservationHistory> create(@RequestBody ReservationHistory reservationHistory) throws URISyntaxException {
        log.debug("REST request to save ReservationHistory : {}", reservationHistory);
        if (reservationHistory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new reservationHistory cannot already have an ID").body(null);
        }
        ReservationHistory result = reservationHistoryRepository.save(reservationHistory);
        return ResponseEntity.created(new URI("/api/reservationHistorys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("reservationHistory", result.getId().toString()))
                .body(result);
    }

    /**
     * Update response entity.
     *
     * @param reservationHistory the reservation history
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservationHistorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReservationHistory> update(@RequestBody ReservationHistory reservationHistory) throws URISyntaxException {
        log.debug("REST request to update ReservationHistory : {}", reservationHistory);
        if (reservationHistory.getId() == null) {
            return create(reservationHistory);
        }
        ReservationHistory result = reservationHistoryRepository.save(reservationHistory);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("reservationHistory", reservationHistory.getId().toString()))
                .body(result);
    }

    /**
     * Gets all.
     *
     * @param offset the offset
     * @param limit  the limit
     * @return the all
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/reservationHistorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ReservationHistory>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ReservationHistory> page = reservationHistoryRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reservationHistorys", offset, limit);
        return new ResponseEntity<List<ReservationHistory>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Gets all my history.
     *
     * @param used     the used
     * @param canceled the canceled
     * @return the all my history
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/myReservationHistorys",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ReservationHistory> getAllMyHistory(
    		@RequestParam(value = "used" , required = false) Boolean used,
    		@RequestParam(value = "canceled" , required = false) Boolean canceled
    		) throws URISyntaxException {

    	User loginUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
    	List<ReservationHistory> list = reservationHistoryRepository.findByUserId(loginUser.getId());

    	if(used != null && canceled != null){
    		list = reservationHistoryRepository.findByUserIdAndUsedAndCanceled(loginUser.getId(), used, canceled);
    	}else if(used != null) {
    		list = reservationHistoryRepository.findByUserIdAndUsed(loginUser.getId(), used);
    	}else if(canceled != null) {
    		list = reservationHistoryRepository.findByUserIdAndCanceled(loginUser.getId(), canceled);
    	}else{
    		list = reservationHistoryRepository.findByUserId(loginUser.getId());
    	}

    	return list;
    }

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/reservationHistorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReservationHistory> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get ReservationHistory : {}", id);
        ReservationHistory reservationHistory = reservationHistoryRepository.findOne(id);
        if (reservationHistory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservationHistory, HttpStatus.OK);
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @RequestMapping(value = "/reservationHistorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete ReservationHistory : {}", id);
        reservationHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reservationHistory", id.toString())).build();
    }
}
