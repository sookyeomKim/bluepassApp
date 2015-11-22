package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;

import co.bluepass.domain.Authority;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.RegisterStatus;
import co.bluepass.domain.TicketHistory;
import co.bluepass.domain.User;
import co.bluepass.repository.AuthorityRepository;
import co.bluepass.repository.CommonCodeRepository;
import co.bluepass.repository.TicketHistoryRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.security.AuthoritiesConstants;
import co.bluepass.security.SecurityUtils;
import co.bluepass.web.rest.dto.TicketHistoryDTO;
import co.bluepass.web.rest.util.PaginationUtil;

import org.joda.time.DateTime;
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
 * The type Ticket history resource.
 */
@RestController
@RequestMapping("/api")
public class TicketHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TicketHistoryResource.class);

    @Inject
    private TicketHistoryRepository ticketHistoryRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private CommonCodeRepository commonCodeRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/ticketHistory",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody TicketHistoryDTO dto) throws URISyntaxException {
        log.debug("REST request to save TicketHistory : {}", dto);
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new ticketHistory cannot already have an ID").build();
        }

        User user = userRepository.findOne(dto.getUserId());
        CommonCode ticket = commonCodeRepository.findOne(dto.getTicketId());

        TicketHistory ticketHistory = new TicketHistory(user, ticket, DateTime.now());

        ticketHistoryRepository.save(ticketHistory);
        return ResponseEntity.created(new URI("/api/ticketHistory/" + ticketHistory.getId())).build();
    }

    /**
     * Update response entity.
     *
     * @param dto the dto
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/ticketHistory",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody TicketHistoryDTO dto) throws URISyntaxException {
        log.debug("REST request to update TicketHistory : {}", dto);
        if (dto.getId() == null) {
        	return ResponseEntity.badRequest().header("Failure", "Ticket History Id가 없습니다.").build();
        }

        TicketHistory ticketHistory = ticketHistoryRepository.findOne(dto.getId());

        if(!ticketHistory.getUser().getId().equals(dto.getUserId())){
        	return ResponseEntity.badRequest().header("Failure", "Not ticket owner.").build();
        }

        DateTime activatedTime = null;
        DateTime closeTime = null;

        User user = userRepository.findOne(dto.getUserId());

        if(user == null){
        	return ResponseEntity.badRequest().header("Failure", "Not user").build();
        }

        Authority authority = authorityRepository.findOne(AuthoritiesConstants.REGISTER);
        if(!ticketHistory.getActivated() && dto.getActivated()){
            user.getAuthorities().add(authority);
            user.setRegisterStatus(RegisterStatus.등록);
            userRepository.saveAndFlush(user);
            activatedTime = DateTime.now();
            closeTime = activatedTime.plusDays(30);
        } else if(!dto.getActivated()) {
        	//취소
        	user.getAuthorities().remove(authority);
        	user.setRegisterStatus(RegisterStatus.미등록);
        	userRepository.saveAndFlush(user);
        }

        boolean closed = ticketHistory.getClosed();
        if(dto.getClosed() != null) {
        	closed = dto.getClosed();
        }

        CommonCode ticket = commonCodeRepository.findOne(dto.getTicketId());
        ticketHistory.update(ticket, dto.getActivated(), closed, activatedTime, closeTime);
        ticketHistoryRepository.save(ticketHistory);

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
    @RequestMapping(value = "/ticketHistory",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TicketHistory>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<TicketHistory> page = ticketHistoryRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ticketHistory", offset, limit);
        return new ResponseEntity<List<TicketHistory>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Get response entity.
     *
     * @param id       the id
     * @param response the response
     * @return the response entity
     */
    @RequestMapping(value = "/ticketHistory/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TicketHistory> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get TicketHistory : {}", id);
        TicketHistory ticketHistory = ticketHistoryRepository.findOne(id);
        if (ticketHistory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ticketHistory, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/ticketHistory/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete TicketHistory : {}", id);
        ticketHistoryRepository.delete(id);
    }
}
