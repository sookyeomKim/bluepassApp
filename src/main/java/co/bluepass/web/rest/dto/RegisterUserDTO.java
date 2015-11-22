package co.bluepass.web.rest.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import co.bluepass.domain.CommonCode;
import co.bluepass.domain.Zip;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.List;

/**
 * The type Register user dto.
 */
public class RegisterUserDTO {

    @NotNull
    private Long id;

    @Size(max = 100)
    private String name;

    @Pattern(regexp = "^([0-9]{6})$")
    private String zipcode;

    @NotNull
    private String address1;

    @NotNull
    private String address2;

    @NotNull
    private int age;

    @NotEmpty
    private String gender;

    @NotEmpty
    private String phoneNumber;

    private Long favorSiteId;

    private Long[] favorCategoryId;

    private Long jacketSizeId;

    private Long pantsSizeId;

    @NotNull
    private Long ticketId;

    private Integer exersizeCount;

    /**
     * Instantiates a new Register user dto.
     */
    public RegisterUserDTO() {
    }

    /**
     * Instantiates a new Register user dto.
     *
     * @param name     the name
     * @param email    the email
     * @param zipcode  the zipcode
     * @param address1 the address 1
     * @param address2 the address 2
     * @param age      the age
     * @param gender   the gender
     */
    public RegisterUserDTO(String name, String email,
                   String zipcode, String address1, String address2, int age, String gender) {
        this.name = name;
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
        this.age = age;
        this.gender = gender;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
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
     * Get favor category id long [ ].
     *
     * @return the long [ ]
     */
    public Long[] getFavorCategoryId() {
		return favorCategoryId;
	}

    /**
     * Sets favor category id.
     *
     * @param favorCategoryId the favor category id
     */
    public void setFavorCategoryId(Long[] favorCategoryId) {
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
}
