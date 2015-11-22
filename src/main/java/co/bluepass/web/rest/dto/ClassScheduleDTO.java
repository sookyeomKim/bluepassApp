package co.bluepass.web.rest.dto;

import org.joda.time.DateTime;

/**
 * The type Class schedule dto.
 */
public class ClassScheduleDTO {

    private Long id;

    private DateTime startTime;

    private DateTime endTime;

    private Boolean enable;

    private Boolean finished;

    private String etc;

    private Long actionId;

    private Long clubId;

    private Long instructorId;

    private Long categoryId;

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
     * Gets club id.
     *
     * @return the club id
     */
    public Long getClubId() {
		return clubId;
	}

    /**
     * Sets club id.
     *
     * @param clubId the club id
     */
    public void setClubId(Long clubId) {
		this.clubId = clubId;
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
     * Gets category id.
     *
     * @return the category id
     */
    public Long getCategoryId() {
		return categoryId;
	}

    /**
     * Sets category id.
     *
     * @param categoryId the category id
     */
    public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}
