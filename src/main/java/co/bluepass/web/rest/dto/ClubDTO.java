package co.bluepass.web.rest.dto;

import java.util.Arrays;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import co.bluepass.domain.CommonCode;

/**
 * The type Club dto.
 */
public class ClubDTO {

	private Long id;

	@NotNull
	@Size(max = 100)
	private String name;

	@Pattern(regexp = "^([0-9]{10})$", message = "사업자등록번호는 10자리의 숫자로 이루어져야 합니다.")
	private String licenseNumber;

	@Pattern(regexp = "^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$")
	private String phoneNumber;

	@Pattern(regexp = "^([0-9]{5})$")
	private String zipcode;

	private String address1;

	private String address2;

	private String oldAddress;

	private String addressSimple;

	private Boolean onlyFemale;

	private String description;

	private String homepage;

	private Long[] features;

	private CommonCode category;

	private String managerMobile;

    private String notificationType;

    private String reservationClose;

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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets license number.
     *
     * @return the license number
     */
    public String getLicenseNumber() {
		return licenseNumber;
	}

    /**
     * Sets license number.
     *
     * @param licenseNumber the license number
     */
    public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
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
     * Gets old address.
     *
     * @return the old address
     */
    public String getOldAddress() {
		return oldAddress;
	}

    /**
     * Sets old address.
     *
     * @param oldAddress the old address
     */
    public void setOldAddress(String oldAddress) {
		this.oldAddress = oldAddress;
	}

    /**
     * Gets address simple.
     *
     * @return the address simple
     */
    public String getAddressSimple() {
		return addressSimple;
	}

    /**
     * Sets address simple.
     *
     * @param addressSimple the address simple
     */
    public void setAddressSimple(String addressSimple) {
		this.addressSimple = addressSimple;
	}

    /**
     * Gets only female.
     *
     * @return the only female
     */
    public Boolean getOnlyFemale() {
		return onlyFemale;
	}

    /**
     * Sets only female.
     *
     * @param onlyFemale the only female
     */
    public void setOnlyFemale(Boolean onlyFemale) {
		this.onlyFemale = onlyFemale;
	}

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
		return description;
	}

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
		this.description = description;
	}

    /**
     * Gets homepage.
     *
     * @return the homepage
     */
    public String getHomepage() {
		return homepage;
	}

    /**
     * Sets homepage.
     *
     * @param homepage the homepage
     */
    public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

    /**
     * Get features long [ ].
     *
     * @return the long [ ]
     */
    public Long[] getFeatures() {
		return features;
	}

    /**
     * Sets features.
     *
     * @param features the features
     */
    public void setFeatures(Long[] features) {
		this.features = features;
	}

    /**
     * Gets category.
     *
     * @return the category
     */
    public CommonCode getCategory() {
		return category;
	}

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(CommonCode category) {
		this.category = category;
	}

    /**
     * Gets manager mobile.
     *
     * @return the manager mobile
     */
    public String getManagerMobile() {
		return managerMobile;
	}

    /**
     * Sets manager mobile.
     *
     * @param managerMobile the manager mobile
     */
    public void setManagerMobile(String managerMobile) {
		this.managerMobile = managerMobile;
	}

    /**
     * Gets notification type.
     *
     * @return the notification type
     */
    public String getNotificationType() {
		return notificationType;
	}

    /**
     * Sets notification type.
     *
     * @param notificationType the notification type
     */
    public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

    /**
     * Gets reservation close.
     *
     * @return the reservation close
     */
    public String getReservationClose() {
		return reservationClose;
	}

    /**
     * Sets reservation close.
     *
     * @param reservationClose the reservation close
     */
    public void setReservationClose(String reservationClose) {
		this.reservationClose = reservationClose;
	}

	@Override
	public String toString() {
		return "ClubDTO [id=" + id + ", name=" + name + ", licenseNumber=" + licenseNumber + ", phoneNumber="
				+ phoneNumber + ", zipcode=" + zipcode + ", address1=" + address1 + ", address2=" + address2
				+ ", oldAddress=" + oldAddress + ", addressSimple=" + addressSimple + ", onlyFemale=" + onlyFemale
				+ ", description=" + description + ", homepage=" + homepage + ", features=" + Arrays.toString(features)
				+ ", category=" + category + ", managerMobile=" + managerMobile + ", notificationType="
				+ notificationType + ", reservationClose=" + reservationClose + "]";
	}

}
