package co.bluepass.web.rest.response;

import java.util.List;

/**
 * The type Reservation cancel response.
 */
public class ReservationCancelResponse {

	private boolean cancelable;

	private List<String> messages;

    /**
     * Is cancelable boolean.
     *
     * @return the boolean
     */
    public boolean isCancelable() {
		return cancelable;
	}

    /**
     * Sets cancelable.
     *
     * @param cancelable the cancelable
     */
    public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
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
     * Sets messages.
     *
     * @param messages the messages
     */
    public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
