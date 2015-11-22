package co.bluepass.web.rest.dto;

import javax.validation.constraints.NotNull;

/**
 * The type Ticket history dto.
 */
public class TicketHistoryDTO {

	private Long id;

	@NotNull
	private Long userId;

	@NotNull
	private Long ticketId;

	private Boolean activated;

	private Boolean closed;

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
     * Gets ticket id.
     *
     * @return the ticket id
     */
    public Long getTicketId() {
		return ticketId;
	}

    /**
     * Sets ticket id.
     *
     * @param ticketId the ticket id
     */
    public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
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
     * Sets closed.
     *
     * @param closed the closed
     */
    public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	@Override
	public String toString() {
		return "TicketHistoryDTO [id=" + id + ", userId=" + userId
				+ ", ticketId=" + ticketId + ", activated=" + activated
				+ ", closed=" + closed + "]";
	}
}
