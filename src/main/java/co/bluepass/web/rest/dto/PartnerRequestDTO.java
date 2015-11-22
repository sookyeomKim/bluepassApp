package co.bluepass.web.rest.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The type Partner request dto.
 */
public class PartnerRequestDTO {

	@NotEmpty
	private String clubName;

	@NotEmpty
	private String userName;

	@NotEmpty
	private String address;

	@NotEmpty
	@Pattern(regexp = "^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$")
	private String phoneNumber;

	@NotEmpty
	private String message;

    /**
     * Gets club name.
     *
     * @return the club name
     */
    public String getClubName() {
		return clubName;
	}

    /**
     * Sets club name.
     *
     * @param clubName the club name
     */
    public void setClubName(String clubName) {
		this.clubName = clubName;
	}

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
		return userName;
	}

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
		this.userName = userName;
	}

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
		return address;
	}

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
		this.address = address;
	}

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
		return phoneNumber;
	}

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
		return message;
	}

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
		this.message = message;
	}

}
