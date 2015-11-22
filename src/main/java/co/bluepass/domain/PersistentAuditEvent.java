package co.bluepass.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Persistent audit event.
 */
@Entity
@Table(name = "PERSISTENT_AUDIT_EVENT")
public class PersistentAuditEvent  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String principal;

    @Column(name = "event_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime auditEventDate;
    @Column(name = "event_type")
    private String auditEventType;

    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="PERSISTENT_AUDIT_EVENT_DATA", joinColumns=@JoinColumn(name="event_id"))
    private Map<String, String> data = new HashMap<>();

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets principal.
     *
     * @return the principal
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * Sets principal.
     *
     * @param principal the principal
     */
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    /**
     * Gets audit event date.
     *
     * @return the audit event date
     */
    public DateTime getAuditEventDate() {
        return auditEventDate;
    }

    /**
     * Sets audit event date.
     *
     * @param auditEventDate the audit event date
     */
    public void setAuditEventDate(DateTime auditEventDate) {
        this.auditEventDate = auditEventDate;
    }

    /**
     * Gets audit event type.
     *
     * @return the audit event type
     */
    public String getAuditEventType() {
        return auditEventType;
    }

    /**
     * Sets audit event type.
     *
     * @param auditEventType the audit event type
     */
    public void setAuditEventType(String auditEventType) {
        this.auditEventType = auditEventType;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
