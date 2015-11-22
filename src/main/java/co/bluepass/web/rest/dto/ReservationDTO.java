package co.bluepass.web.rest.dto;

import javax.validation.constraints.NotNull;

/**
 * The type Reservation dto.
 */
public class ReservationDTO {

	@NotNull
	private Long userId;

	@NotNull
	private Long scheduleId;

	private Boolean cancel;

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {
		return userId;
	}

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Long userId) {
		this.userId = userId;
	}

    /**
     * Gets schedule id.
     *
     * @return the schedule id
     */
    public Long getScheduleId() {
		return scheduleId;
	}

    /**
     * Sets schedule id.
     *
     * @param scheduleId the schedule id
     */
    public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

    /**
     * Gets cancel.
     *
     * @return the cancel
     */
    public Boolean getCancel() {
		return cancel;
	}

    /**
     * Sets cancel.
     *
     * @param cancel the cancel
     */
    public void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}

	@Override
	public String toString() {
		return "ReservationDTO [userId=" + userId + ", scheduleId="
				+ scheduleId + ", cancel=" + cancel + "]";
	}

}
