package co.bluepass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import co.bluepass.domain.util.CustomDateTimeDeserializer;
import co.bluepass.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The type Ticket history.
 */
@Entity
@Table(name = "TICKET_HISTORY")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class TicketHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private CommonCode ticket;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "closed")
    private Boolean closed = false;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "request_date", nullable = false)
    private DateTime requestDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "activated_date", nullable = false)
    private DateTime activatedDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "close_date", nullable = false)
    private DateTime closeDate;

    /**
     * Instantiates a new Ticket history.
     */
    public TicketHistory() {
	}

    /**
     * Instantiates a new Ticket history.
     *
     * @param user        the user
     * @param ticket      the ticket
     * @param requestDate the request date
     */
    public TicketHistory(User user, CommonCode ticket, DateTime requestDate) {
    	this.user = user;
    	this.ticket = ticket;
    	this.requestDate = requestDate;
    	this.activated = false;
    	this.closed = false;
    }

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
     * Gets activated.
     *
     * @return the activated
     */
    public Boolean getActivated() {
        return activated;
    }

    /**
     * Sets activated.
     *
     * @param activated the activated
     */
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    /**
     * Gets closed.
     *
     * @return the closed
     */
    public Boolean getClosed() {
		return closed;
	}

    /**
     * Get remain days int.
     *
     * @return the int
     */
    public int getRemainDays(){
    	int remain = 0;
    	if(this.activatedDate != null && this.closeDate != null){
    		if(this.closeDate.isBeforeNow()){
    			remain = 0;
    		}else{
    			remain = Days.daysBetween(DateTime.now(), closeDate).getDays() + 1;
    		}
    	}
    	return remain;
    }

    /**
     * Sets closed.
     *
     * @param closed the closed
     */
    public void setClosed(Boolean closed) {
		this.closed = closed;
	}

    /**
     * Gets request date.
     *
     * @return the request date
     */
    public DateTime getRequestDate() {
        return requestDate;
    }

    /**
     * Sets request date.
     *
     * @param requestDate the request date
     */
    public void setRequestDate(DateTime requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Gets activated date.
     *
     * @return the activated date
     */
    public DateTime getActivatedDate() {
        return activatedDate;
    }

    /**
     * Sets activated date.
     *
     * @param activatedDate the activated date
     */
    public void setActivatedDate(DateTime activatedDate) {
        this.activatedDate = activatedDate;
    }

    /**
     * Gets close date.
     *
     * @return the close date
     */
    public DateTime getCloseDate() {
        return closeDate;
    }

    /**
     * Sets close date.
     *
     * @param closeDate the close date
     */
    public void setCloseDate(DateTime closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets ticket.
     *
     * @return the ticket
     */
    public CommonCode getTicket() {
        return ticket;
    }

    /**
     * Sets ticket.
     *
     * @param commonCode the common code
     */
    public void setTicket(CommonCode commonCode) {
        this.ticket = commonCode;
    }

    /**
     * Update.
     *
     * @param ticket        the ticket
     * @param activated     the activated
     * @param closed        the closed
     * @param activatedDate the activated date
     * @param closeDate     the close date
     */
    public void update(CommonCode ticket, Boolean activated, Boolean closed, DateTime activatedDate, DateTime closeDate) {
    	this.ticket = ticket;
    	this.activated = activated;
    	this.closed = closed;
    	this.activatedDate = activatedDate;
    	this.closeDate = closeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketHistory ticketHistory = (TicketHistory) o;

        return Objects.equals(id, ticketHistory.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TicketHistory{" +
                "id=" + id +
                ", activated='" + activated + "'" +
                ", requestDate='" + requestDate + "'" +
                ", activatedDate='" + activatedDate + "'" +
                ", closeDate='" + closeDate + "'" +
                '}';
    }
}
