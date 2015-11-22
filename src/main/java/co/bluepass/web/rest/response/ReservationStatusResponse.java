package co.bluepass.web.rest.response;

import java.util.List;

/**
 * The type Reservation status response.
 */
public class ReservationStatusResponse {

	private boolean reservatable;

	private List<String> messages;

	private boolean alreadyReservated;

    /**
     * Is reservatable boolean.
     *
     * @return the boolean
     */
    public boolean isReservatable() {
		return reservatable;
	}

    /**
     * Sets reservatable.
     *
     * @param reservatable the reservatable
     */
    public void setReservatable(boolean reservatable) {
		this.reservatable = reservatable;
	}

    /**
     * Gets messages.
     *
     * @return the messages
     */
    public List<String> getMessages() {
		return messages;
	}

    /**
     * Is already reservated boolean.
     *
     * @return the boolean
     */
    public boolean isAlreadyReservated() {
		return alreadyReservated;
	}

    /**
     * Sets already reservated.
     *
     * @param alreadyReservated the already reservated
     */
    public void setAlreadyReservated(boolean alreadyReservated) {
		this.alreadyReservated = alreadyReservated;
	}

    /**
     * Sets messages.
     *
     * @param messages the messages
     */
    public void setMessages(List<String> messages) {
		this.messages = messages;
        this.reservatable = messages.size() <= 0;
	}
}
