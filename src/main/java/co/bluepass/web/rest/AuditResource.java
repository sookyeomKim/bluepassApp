package co.bluepass.web.rest;

import co.bluepass.security.AuthoritiesConstants;
import co.bluepass.service.AuditEventService;
import co.bluepass.web.propertyeditors.LocaleDateTimeEditor;
import org.joda.time.DateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;

/**
 * The type Audit resource.
 */
@RestController
@RequestMapping("/api")
public class AuditResource {

    @Inject
    private AuditEventService auditEventService;

    /**
     * Init binder.
     *
     * @param binder the binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(DateTime.class, new LocaleDateTimeEditor("yyyy-MM-dd", false));
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @RequestMapping(value = "/audits/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findAll() {
        return auditEventService.findAll();
    }

    /**
     * Find by dates list.
     *
     * @param fromDate the from date
     * @param toDate   the to date
     * @return the list
     */
    @RequestMapping(value = "/audits/byDates",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findByDates(@RequestParam(value = "fromDate") DateTime fromDate,
                                    @RequestParam(value = "toDate") DateTime toDate) {
        return auditEventService.findByDates(fromDate, toDate);
    }
}
