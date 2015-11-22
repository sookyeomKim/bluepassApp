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
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The type Action schedule.
 */
@Entity
@Table(name = "ACTION_SCHEDULE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActionSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "day", length = 1, nullable = false)
    private String day;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private String startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private String endTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "start_date", nullable = false)
    private DateTime startDate;

    //@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "end_date", nullable = false)
    private DateTime endDate;

    @NotNull
    @Column(name = "schedule_type", nullable = false, columnDefinition = "ENUM('반복', '하루', '기간')")
    private String scheduleType;

    @JsonView(Views.ClassScheduleSummary.class)
    @Column(name = "attendee_limit")
    private Integer attendeeLimit;

    @ManyToOne
    private Club club;

    @ManyToOne
    private Instructor instructor;

    @ManyToOne
    private Action action;

    @Column(name = "enable")
    private Boolean enable = true;

    /**
     * Instantiates a new Action schedule.
     */
    public ActionSchedule() {
	}

    /**
     * Instantiates a new Action schedule.
     *
     * @param day           the day
     * @param startTime     the start time
     * @param endTime       the end time
     * @param startDate     the start date
     * @param endDate       the end date
     * @param scheduleType  the schedule type
     * @param attendeeLimit the attendee limit
     * @param club          the club
     * @param instructor    the instructor
     * @param action        the action
     */
    public ActionSchedule(String day, String startTime, String endTime,
			DateTime startDate, DateTime endDate, String scheduleType,
			Integer attendeeLimit, Club club, Instructor instructor,
			Action action) {
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.scheduleType = scheduleType;
		this.attendeeLimit = attendeeLimit;
		this.club = club;
		this.instructor = instructor;
		this.action = action;
	}

    /**
     * Update.
     *
     * @param day           the day
     * @param startTime     the start time
     * @param endTime       the end time
     * @param startDate     the start date
     * @param endDate       the end date
     * @param scheduleType  the schedule type
     * @param attendeeLimit the attendee limit
     * @param club          the club
     * @param instructor    the instructor
     * @param action        the action
     */
    public void update(String day, String startTime, String endTime,
			DateTime startDate, DateTime endDate, String scheduleType,
			Integer attendeeLimit, Club club, Instructor instructor,
			Action action) {
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.scheduleType = scheduleType;
		this.attendeeLimit = attendeeLimit;
		this.club = club;
		this.instructor = instructor;
		this.action = action;
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
     * Gets day.
     *
     * @return the day
     */
    public String getDay() {
        return day;
    }

    /**
     * Sets day.
     *
     * @param day the day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public DateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public DateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets schedule type.
     *
     * @return the schedule type
     */
    public String getScheduleType() {
        return scheduleType;
    }

    /**
     * Sets schedule type.
     *
     * @param scheduleType the schedule type
     */
    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    /**
     * Gets attendee limit.
     *
     * @return the attendee limit
     */
    public Integer getAttendeeLimit() {
        return attendeeLimit;
    }

    /**
     * Sets attendee limit.
     *
     * @param attendeeLimit the attendee limit
     */
    public void setAttendeeLimit(Integer attendeeLimit) {
        this.attendeeLimit = attendeeLimit;
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

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActionSchedule actionSchedule = (ActionSchedule) o;

        return Objects.equals(id, actionSchedule.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ActionSchedule{" +
                "id=" + id +
                ", day='" + day + "'" +
                ", startTime='" + startTime + "'" +
                ", endTime='" + endTime + "'" +
                ", startDate='" + startDate + "'" +
                ", endDate='" + endDate + "'" +
                ", scheduleType='" + scheduleType + "'" +
                ", attendeeLimit='" + attendeeLimit + "'" +
                '}';
    }
}
