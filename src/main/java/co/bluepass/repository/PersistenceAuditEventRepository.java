package co.bluepass.repository;

import co.bluepass.domain.PersistentAuditEvent;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Persistence audit event repository.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, String> {

    /**
     * Find by principal list.
     *
     * @param principal the principal
     * @return the list
     */
    List<PersistentAuditEvent> findByPrincipal(String principal);

    /**
     * Find by principal and audit event date after list.
     *
     * @param principal the principal
     * @param after     the after
     * @return the list
     */
    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, DateTime after);

    /**
     * Find all by audit event date between list.
     *
     * @param fromDate the from date
     * @param toDate   the to date
     * @return the list
     */
    List<PersistentAuditEvent> findAllByAuditEventDateBetween(DateTime fromDate, DateTime toDate);
}
