package co.bluepass.web.rest.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import co.bluepass.domain.CommonCode;
import co.bluepass.domain.RegisterStatus;
import co.bluepass.domain.Zip;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.List;

/**
 * The type User dto.
 */
public class UserDTO {

	private Long id;

	@NotNull
    @Email
    @Size(min = 5, max = 100)
    private String email;

    @NotNull
    @Size(min = 5, max = 100)
    private String password;

    @Size(max = 100)
    private String name;

    @Size(min = 2, max = 5)
    private String langKey;


    //@NotNull
    @Pattern(regexp = "^([0-9]{6})$")
    private String zipcode;

    //@NotNull
    private String address1;

    private String address2;

    //@NotNull
    private int age;

    //@NotEmpty
    private String gender;

    private String phoneNumber;

    private Long favorSiteId;

    private List<Long> favorCategoryId;

    private Long jacketSizeId;

    private Long pantsSizeId;

    private List<String> roles;

    private RegisterStatus registerStatus;

    private Long ticketId;

    private Integer exersizeCount;

    /**
     * Instantiates a new User dto.
     */
    public UserDTO() {
    }

    /**
     * Instantiates a new User dto.
     *
     * @param id              the id
     * @param password        the password
     * @param name            the name
     * @param email           the email
     * @param langKey         the lang key
     * @param roles           the roles
     * @param registerStatus  the register status
     * @param zipcode         the zipcode
     * @param address1        the address 1
     * @param address2        the address 2
     * @param age             the age
     * @param gender          the gender
     * @param phoneNumber     the phone number
     * @param exersizeCount   the exersize count
     * @param favorSiteId     the favor site id
     * @param favorCategoryId the favor category id
     * @param ticketId        the ticket id
     */
    public UserDTO(Long id, String password, String name, String email, String langKey,
                   List<String> roles, RegisterStatus registerStatus,
                   String zipcode, String address1, String address2, int age, String gender,
                   String phoneNumber, Integer exersizeCount, Long favorSiteId,
                   List<Long> favorCategoryId, Long ticketId) {
    	this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.langKey = langKey;
        this.roles = roles;
        this.registerStatus = registerStatus;
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.exersizeCount = exersizeCount;
        this.favorSiteId = favorSiteId;
        this.favorCategoryId = favorCategoryId;
        this.ticketId = ticketId;
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
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets lang key.
     *
     * @return the lang key
     */
    public String getLangKey() {
        return langKey;
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
     * Gets address 1.
     *
     * @return the address 1
     */
    public String getAddress1() {
		return address1;
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
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
		return age;
	}

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender() {
		return gender;
	}

    /**
     * Gets roles.
     *
     * @return the roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Gets register status.
     *
     * @return the register status
     */
    public RegisterStatus getRegisterStatus() {
		return registerStatus;
	}

    /**
     * Sets register status.
     *
     * @param registerStatus the register status
     */
    public void setRegisterStatus(RegisterStatus registerStatus) {
		this.registerStatus = registerStatus;
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
     * Gets favor site id.
     *
     * @return the favor site id
     */
    public Long getFavorSiteId() {
		return favorSiteId;
	}

    /**
     * Sets favor site id.
     *
     * @param favorSiteId the favor site id
     */
    public void setFavorSiteId(Long favorSiteId) {
		this.favorSiteId = favorSiteId;
	}

    /**
     * Gets favor category id.
     *
     * @return the favor category id
     */
    public List<Long> getFavorCategoryId() {
		return favorCategoryId;
	}

    /**
     * Sets favor category id.
     *
     * @param favorCategoryId the favor category id
     */
    public void setFavorCategoryId(List<Long> favorCategoryId) {
		this.favorCategoryId = favorCategoryId;
	}

    /**
     * Gets jacket size id.
     *
     * @return the jacket size id
     */
    public Long getJacketSizeId() {
		return jacketSizeId;
	}

    /**
     * Sets jacket size id.
     *
     * @param jacketSizeId the jacket size id
     */
    public void setJacketSizeId(Long jacketSizeId) {
		this.jacketSizeId = jacketSizeId;
	}

    /**
     * Gets pants size id.
     *
     * @return the pants size id
     */
    public Long getPantsSizeId() {
		return pantsSizeId;
	}

    /**
     * Sets pants size id.
     *
     * @param pantsSizeId the pants size id
     */
    public void setPantsSizeId(Long pantsSizeId) {
		this.pantsSizeId = pantsSizeId;
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
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
		this.gender = gender;
	}

    /**
     * Gets exersize count.
     *
     * @return the exersize count
     */
    public Integer getExersizeCount() {
		return exersizeCount;
	}

    /**
     * Sets roles.
     *
     * @param roles the roles
     */
    public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
    public String toString() {
        return "UserDTO{" +
        ", password='" + password + '\'' +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", langKey='" + langKey + '\'' +
        ", roles=" + roles +
        '}';
    }
}
