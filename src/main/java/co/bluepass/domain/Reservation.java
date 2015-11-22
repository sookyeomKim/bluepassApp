package co.bluepass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import co.bluepass.domain.util.CustomDateTimeDeserializer;
import co.bluepass.domain.util.CustomDateTimeSerializer;
import co.bluepass.web.rest.jsonview.Views;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The type Reservation.
 */
@Entity
@Table(name = "RESERVATION")
public class Reservation implements Serializable {

	@JsonView(Views.ReservationSummary.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reservation_method")
    private String reservationMethod;

    @JsonView(Views.ReservationSummary.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "reservation_time")
    private DateTime reservationTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "start_time")
    private DateTime startTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "end_time")
    private DateTime endTime;

    @JsonView(Views.ReservationSummary.class)
    @Column(name = "canceled")
    private Boolean canceled = false;

    @JsonView(Views.ReservationSummary.class)
    @Column(name = "used")
    private Boolean used;

    @JsonView(Views.ReservationSummary.class)
    @ManyToOne
    private User user;

    @ManyToOne
    private ClassSchedule classSchedule;

    @ManyToOne
    private Club club;

    @ManyToOne
    private Action action;

    @ManyToOne
    private CommonCode ticket;

    @Column(name = "check_code")
    private Integer checkCode;

    /**
     * Instantiates a new Reservation.
     */
    public Reservation() {
	}

    /**
     * Instantiates a new Reservation.
     *
     * @param user          the user
     * @param classSchedule the class schedule
     * @param ticket        the ticket
     */
    public Reservation(User user, ClassSchedule classSchedule, CommonCode ticket) {
		this.user = user;
		this.classSchedule = classSchedule;
		this.club = classSchedule.getClub();
		this.action = classSchedule.getAction();
		this.startTime = classSchedule.getStartTime();
		this.endTime = classSchedule.getEndTime();
		this.reservationTime = DateTime.now();
		this.canceled = false;
		this.ticket = ticket;
		if(ticket != null) {
			this.reservationMethod = ticket.getValue();
		} else {
			this.reservationMethod = "기타";
		}
	}

    /**
     * Instantiates a new Reservation.
     *
     * @param user          the user
     * @param classSchedule the class schedule
     * @param ticket        the ticket
     * @param checkCode     the check code
     */
    public Reservation(User user, ClassSchedule classSchedule, CommonCode ticket, Integer checkCode) {
		this.user = user;
		this.classSchedule = classSchedule;
		this.club = classSchedule.getClub();
		this.action = classSchedule.getAction();
		this.startTime = classSchedule.getStartTime();
		this.endTime = classSchedule.getEndTime();
		this.reservationTime = DateTime.now();
		this.canceled = false;
		this.ticket = ticket;
		if(ticket != null) {
			this.reservationMethod = ticket.getValue();
		} else {
			this.reservationMethod = "기타";
		}
		this.checkCode = checkCode;
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
     * Gets reservation method.
     *
     * @return the reservation method
     */
    public String getReservationMethod() {
        return reservationMethod;
    }

    /**
     * Sets reservation method.
     *
     * @param reservationMethod the reservation method
     */
    public void setReservationMethod(String reservationMethod) {
        this.reservationMethod = reservationMethod;
    }

    /**
     * Gets reservation time.
     *
     * @return the reservation time
     */
    public DateTime getReservationTime() {
        return reservationTime;
    }

    /**
     * Sets reservation time.
     *
     * @param reservationTime the reservation time
     */
    public void setReservationTime(DateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public DateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public DateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets canceled.
     *
     * @return the canceled
     */
    public Boolean getCanceled() {
        return canceled;
    }

    /**
     * Sets canceled.
     *
     * @param canceled the canceled
     */
    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    /**
     * Gets used.
     *
     * @return the used
     */
    public Boolean getUsed() {
        return used;
    }

    /**
     * Sets used.
     *
     * @param used the used
     */
    public void setUsed(Boolean used) {
        this.used = used;
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
     * Gets class schedule.
     *
     * @return the class schedule
     */
    public ClassSchedule getClassSchedule() {
        return classSchedule;
    }

    /**
     * Sets class schedule.
     *
     * @param classSchedule the class schedule
     */
    public void setClassSchedule(ClassSchedule classSchedule) {
        this.classSchedule = classSchedule;
    }

    /**
     * Gets club.
     *
     * @return the club
     */
    public Club getClub() {
        return club;
    }

    /**
     * Sets club.
     *
     * @param club the club
     */
    public void setClub(Club club) {
        this.club = club;
    }

    /**
     * Gets action.
     *
     * @return the action
     */
    public Action getAction() {
		return action;
	}

    /**
     * Sets action.
     *
     * @param action the action
     */
    public void setAction(Action action) {
		this.action = action;
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
     * Gets check code.
     *
     * @return the check code
     */
    public Integer getCheckCode() {
		return checkCode;
	}

    /**
     * Sets check code.
     *
     * @param checkCode the check code
     */
    public void setCheckCode(Integer checkCode) {
		this.checkCode = checkCode;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Reservation reservation = (Reservation) o;

        return Objects.equals(id, reservation.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationMethod='" + reservationMethod + "'" +
                ", reservationTime='" + reservationTime + "'" +
                ", startTime='" + startTime + "'" +
                ", endTime='" + endTime + "'" +
                ", canceled='" + canceled + "'" +
                ", used='" + used + "'" +
                '}';
    }
}
