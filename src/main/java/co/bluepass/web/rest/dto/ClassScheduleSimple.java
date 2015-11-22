package co.bluepass.web.rest.dto;

import org.joda.time.DateTime;

/**
 * The type Class schedule simple.
 */
public class ClassScheduleSimple {

	private Long id;
	private DateTime startTime;
	private DateTime endTime;
	private Long actionId;
	private String actionTitle;
	private Long instructorId;
	private String instructorName;
	private String clubAddressSimple;
	private String clubReservationClose;

    /**
     * Instantiates a new Class schedule simple.
     */
    public ClassScheduleSimple() {
	}

    /**
     * Instantiates a new Class schedule simple.
     *
     * @param id                   the id
     * @param startTime            the start time
     * @param endTime              the end time
     * @param actionId             the action id
     * @param actionTitle          the action title
     * @param instructorId         the instructor id
     * @param instructorName       the instructor name
     * @param clubAddressSimple    the club address simple
     * @param clubReservationClose the club reservation close
     */
    public ClassScheduleSimple(Long id, DateTime startTime, DateTime endTime, Long actionId, String actionTitle,
			Long instructorId, String instructorName, String clubAddressSimple, String clubReservationClose) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.actionId = actionId;
		this.actionTitle = actionTitle;
		this.instructorId = instructorId;
		this.instructorName = instructorName;
		this.clubAddressSimple = clubAddressSimple;
		this.clubReservationClose = clubReservationClose;
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
     * Gets action title.
     *
     * @return the action title
     */
    public String getActionTitle() {
		return actionTitle;
	}

    /**
     * Sets action title.
     *
     * @param actionTitle the action title
     */
    public void setActionTitle(String actionTitle) {
		this.actionTitle = actionTitle;
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

    /**
     * Gets instructor name.
     *
     * @return the instructor name
     */
    public String getInstructorName() {
		return instructorName;
	}

    /**
     * Sets instructor name.
     *
     * @param instructorName the instructor name
     */
    public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

    /**
     * Gets club address simple.
     *
     * @return the club address simple
     */
    public String getClubAddressSimple() {
		return clubAddressSimple;
	}

    /**
     * Sets club address simple.
     *
     * @param clubAddressSimple the club address simple
     */
    public void setClubAddressSimple(String clubAddressSimple) {
		this.clubAddressSimple = clubAddressSimple;
	}

    /**
     * Gets club reservation close.
     *
     * @return the club reservation close
     */
    public String getClubReservationClose() {
		return clubReservationClose;
	}

    /**
     * Sets club reservation close.
     *
     * @param clubReservationClose the club reservation close
     */
    public void setClubReservationClose(String clubReservationClose) {
		this.clubReservationClose = clubReservationClose;
	}

}
