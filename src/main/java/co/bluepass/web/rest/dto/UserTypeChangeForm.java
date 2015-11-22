package co.bluepass.web.rest.dto;

import javax.validation.constraints.NotNull;

/**
 * The type User type change form.
 */
public class UserTypeChangeForm {

	@NotNull
	private Long userId;

	@NotNull
	private String requestType;

	private Boolean remove;

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
     * Gets request type.
     *
     * @return the request type
     */
    public String getRequestType() {
		return requestType;
	}

    /**
     * Sets request type.
     *
     * @param requestType the request type
     */
    public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

    /**
     * Gets remove.
     *
     * @return the remove
     */
    public Boolean getRemove() {
		return remove;
	}

    /**
     * Sets remove.
     *
     * @param remove the remove
     */
    public void setRemove(Boolean remove) {
		this.remove = remove;
	}

}
