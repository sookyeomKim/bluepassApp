package co.bluepass.service;

import co.bluepass.config.audit.AuditEventConverter;
import co.bluepass.domain.PersistentAuditEvent;
import co.bluepass.repository.PersistenceAuditEventRepository;
import org.joda.time.DateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Audit event service.
 */
@Service
@Transactional
public class AuditEventService {

    @Inject
    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    @Inject
    private AuditEventConverter auditEventConverter;

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<AuditEvent> findAll() {
        return auditEventConverter.convertToAuditEvent(persistenceAuditEventRepository.findAll());
    }

    /**
     * Find by dates list.
     *
     * @param fromDate the from date
     * @param toDate   the to date
     * @return the list
     */
    public List<AuditEvent> findByDates(DateTime fromDate, DateTime toDate) {
        List<PersistentAuditEvent> persistentAuditEvents =
            persistenceAuditEventRepository.findAllByAuditEventDateBetween(fromDate, toDate);

        return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    }
}
