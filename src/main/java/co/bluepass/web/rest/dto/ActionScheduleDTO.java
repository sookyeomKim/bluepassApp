package co.bluepass.web.rest.dto;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

/**
 * The type Action schedule dto.
 */
public class ActionScheduleDTO {

	private Long id;

	@NotNull
	private Long actionId;

	@NotNull
	private String day;

	@NotNull
	private String startTime;

	@NotNull
	private String endTime;

	private DateTime startDate;

	private DateTime endDate;

	@NotNull
	private String scheduleType;

	private Integer attendeeLimit;

	@NotNull
	private Long instructorId;

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
     * Gets action id.
     *
     * @return the action id
     */
    public Long getActionId() {
		return actionId;
	}

    /**
     * Sets action id.
     *
     * @param actionId the action id
     */
    public void setActionId(Long actionId) {
		this.actionId = actionId;
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
     * Gets instructor id.
     *
     * @return the instructor id
     */
    public Long getInstructorId() {
		return instructorId;
	}

    /**
     * Sets instructor id.
     *
     * @param instructorId the instructor id
     */
    public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}

	@Override
	public String toString() {
		return "ActionScheduleDTO [id=" + id + ", actionId=" + actionId
				+ ", day=" + day + ", startTime=" + startTime + ", endTime="
				+ endTime + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", scheduleType=" + scheduleType + ", attendeeLimit="
				+ attendeeLimit + ", instructorId=" + instructorId + "]";
	}

}
