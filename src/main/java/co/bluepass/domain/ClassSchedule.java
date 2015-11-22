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
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The type Class schedule.
 */
@Entity
@Table(name = "CLASS_SCHEDULE",
		uniqueConstraints = @UniqueConstraint(columnNames = {"start_time", "end_time", "actionschedule_id"}))
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClassSchedule implements Serializable {

	@JsonView(Views.ClassScheduleSummary.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.ClassScheduleSummary.class)
    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "start_time", nullable = false)
    private DateTime startTime;

    @JsonView(Views.ClassScheduleSummary.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "end_time", nullable = false)
    private DateTime endTime;

    @Column(name = "enable")
    private Boolean enable = true;

    @Column(name = "finished")
    private Boolean finished = false;

    @Column(name = "etc")
    private String etc;

    @JsonView(Views.ClassScheduleSummary.class)
    @ManyToOne
    private ActionSchedule actionSchedule;

    @JsonView(Views.ClassScheduleSummary.class)
    @ManyToOne
    private Action action;

    @JsonView(Views.ClassScheduleSummary.class)
    @ManyToOne
    private Club club;

    @JsonView(Views.ClassScheduleSummary.class)
    @ManyToOne
    private Instructor instructor;

    @ManyToOne
    private CommonCode category;

    @Column(name = "reservation_count")
    private int reservationCount;

    @Column(name = "cancel_count")
    private int cancelCount;

    @Column(name = "attend_count")
    private int attendCount;

    @Column(name = "absence_count")
    private int absenceCount;

    @Column(name = "remind_count")
    private int remindCount;

    /**
     * Instantiates a new Class schedule.
     */
    public ClassSchedule() {
	}

    /**
     * Instantiates a new Class schedule.
     *
     * @param startTime      the start time
     * @param endTime        the end time
     * @param actionSchedule the action schedule
     * @param action         the action
     * @param club           the club
     * @param instructor     the instructor
     * @param category       the category
     */
    public ClassSchedule(DateTime startTime, DateTime endTime, ActionSchedule actionSchedule,
			Action action, Club club, Instructor instructor, CommonCode category) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.actionSchedule = actionSchedule;
		this.action = actionSchedule.getAction();
		this.category = category;
		this.club = club;
		this.instructor = instructor;
	}

    /**
     * Instantiates a new Class schedule.
     *
     * @param startTime      the start time
     * @param endTime        the end time
     * @param actionSchedule the action schedule
     */
    public ClassSchedule(DateTime startTime, DateTime endTime, ActionSchedule actionSchedule) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.actionSchedule = actionSchedule;
		this.action = actionSchedule.getAction();
		this.category = actionSchedule.getAction().getCategory();
		this.club = actionSchedule.getClub();
		this.instructor = actionSchedule.getInstructor();
		this.enable = true;
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
     * Gets enable.
     *
     * @return the enable
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * Sets enable.
     *
     * @param enable the enable
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * Gets finished.
     *
     * @return the finished
     */
    public Boolean getFinished() {
        return finished;
    }

    /**
     * Sets finished.
     *
     * @param finished the finished
     */
    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    /**
     * Gets etc.
     *
     * @return the etc
     */
    public String getEtc() {
        return etc;
    }

    /**
     * Sets etc.
     *
     * @param etc the etc
     */
    public void setEtc(String etc) {
        this.etc = etc;
    }

    /**
     * Gets action schedule.
     *
     * @return the action schedule
     */
    public ActionSchedule getActionSchedule() {
        return actionSchedule;
    }

    /**
     * Sets action schedule.
     *
     * @param actionSchedule the action schedule
     */
    public void setActionSchedule(ActionSchedule actionSchedule) {
        this.actionSchedule = actionSchedule;
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
     * Gets instructor.
     *
     * @return the instructor
     */
    public Instructor getInstructor() {
		return instructor;
	}

    /**
     * Sets instructor.
     *
     * @param instructor the instructor
     */
    public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

    /**
     * Gets category.
     *
     * @return the category
     */
    public CommonCode getCategory() {
		return category;
	}

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(CommonCode category) {
		this.category = category;
	}

    /**
     * Gets reservation count.
     *
     * @return the reservation count
     */
    public int getReservationCount() {
		return reservationCount;
	}

    /**
     * Sets reservation count.
     *
     * @param reservationCount the reservation count
     */
    public void setReservationCount(int reservationCount) {
		this.reservationCount = reservationCount;
	}

    /**
     * Gets cancel count.
     *
     * @return the cancel count
     */
    public int getCancelCount() {
		return cancelCount;
	}

    /**
     * Sets cancel count.
     *
     * @param cancelCount the cancel count
     */
    public void setCancelCount(int cancelCount) {
		this.cancelCount = cancelCount;
	}

    /**
     * Gets attend count.
     *
     * @return the attend count
     */
    public int getAttendCount() {
		return attendCount;
	}

    /**
     * Sets attend count.
     *
     * @param attendCount the attend count
     */
    public void setAttendCount(int attendCount) {
		this.attendCount = attendCount;
	}

    /**
     * Gets absence count.
     *
     * @return the absence count
     */
    public int getAbsenceCount() {
		return absenceCount;
	}

    /**
     * Sets absence count.
     *
     * @param absenceCount the absence count
     */
    public void setAbsenceCount(int absenceCount) {
		this.absenceCount = absenceCount;
	}

    /**
     * Gets remind count.
     *
     * @return the remind count
     */
    public int getRemindCount() {
		return remindCount;
	}

    /**
     * Sets remind count.
     *
     * @param remindCount the remind count
     */
    public void setRemindCount(int remindCount) {
		this.remindCount = remindCount;
	}

    /**
     * Update.
     *
     * @param startTime      the start time
     * @param endTime        the end time
     * @param actionSchedule the action schedule
     */
    public void update(DateTime startTime, DateTime endTime, ActionSchedule actionSchedule) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.actionSchedule = actionSchedule;
		this.action = actionSchedule.getAction();
		this.category = actionSchedule.getAction().getCategory();
		this.club = actionSchedule.getClub();
		this.instructor = actionSchedule.getInstructor();
		this.enable = true;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassSchedule classSchedule = (ClassSchedule) o;

        return Objects.equals(id, classSchedule.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClassSchedule{" +
                "id=" + id +
                ", startTime='" + startTime + "'" +
                ", endTime='" + endTime + "'" +
                ", enable='" + enable + "'" +
                ", finished='" + finished + "'" +
                ", etc='" + etc + "'" +
                '}';
    }

    /**
     * Add reservation.
     */
    public void addReservation() {
		this.reservationCount++;
		this.remindCount++;
	}

    /**
     * Cancel reservation.
     */
    public void cancelReservation() {
		if(reservationCount > 0){
			this.reservationCount--;
			this.remindCount--;
			this.cancelCount++;
		}
	}

    /**
     * Attend reservation.
     */
    public void attendReservation() {
		if(reservationCount > 0){
			this.attendCount++;
			this.remindCount--;
		}
	}

    /**
     * Absence reservation.
     */
    public void absenceReservation() {
		if(reservationCount > 0){
			this.absenceCount++;
			this.remindCount--;
		}
	}
}
