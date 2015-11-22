package co.bluepass.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Partner request.
 */
@Entity
@Table(name = "PARTNER_REQUEST")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartnerRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "club_name", nullable = false)
    private String clubName;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Pattern(regexp = "^([0-9]{6})$")
    @Column(length = 6)
    private String zipcode;

    @Column(length = 255)
    private String address1;

    @Column(length = 100)
    private String address2;

    @NotNull
    @Pattern(regexp = "^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    private User user;

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
     * Gets zipcode.
     *
     * @return the zipcode
     */
    public String getZipcode() {
		return zipcode;
	}

    /**
     * Sets zipcode.
     *
     * @param zipcode the zipcode
     */
    public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

    /**
     * Gets address 1.
     *
     * @return the address 1
     */
    public String getAddress1() {
		return address1;
	}

    /**
     * Sets address 1.
     *
     * @param address1 the address 1
     */
    public void setAddress1(String address1) {
		this.address1 = address1;
	}

    /**
     * Gets address 2.
     *
     * @return the address 2
     */
    public String getAddress2() {
		return address2;
	}

    /**
     * Sets address 2.
     *
     * @param address2 the address 2
     */
    public void setAddress2(String address2) {
		this.address2 = address2;
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

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
		return user;
	}

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
		this.user = user;
		this.userName = user.getName();
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartnerRequest partnerRequest = (PartnerRequest) o;

        return Objects.equals(id, partnerRequest.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	@Override
	public String toString() {
		return "PartnerRequest [id=" + id + ", clubName=" + clubName
				+ ", userName=" + userName + ", zipcode=" + zipcode
				+ ", address1=" + address1 + ", address2=" + address2
				+ ", phoneNumber=" + phoneNumber + ", message=" + message
				+ ", user=" + user + "]";
	}
}
