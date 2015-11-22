package co.bluepass.domain;

import co.bluepass.web.rest.jsonview.JsonViews;
import co.bluepass.web.rest.jsonview.Views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * The type Club.
 */
@Entity
@Table(name = "CLUB")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Club implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.SummaryBase.class)
    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Pattern(regexp = "^([0-9]{10})$", message = "사업자등록번호는 10자리의 숫자로 이루어져야 합니다.")
    @Column(name = "license_number")
    private String licenseNumber;

    @Pattern(regexp = "^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Pattern(regexp = "^([0-9]{5})$")
    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "old_address")
    private String oldAddress;

    @JsonView(Views.SummaryBase.class)
	@Column(name = "address_simple")
    private String addressSimple;

    @Size(max = 500)
	@Column(name = "description", length = 500)
    private String description;

	@Column(name = "homepage")
    private String homepage;

	@JsonView(Views.ActionSummary.class)
    @Column(name = "only_female")
    private Boolean onlyFemale;

    @ManyToMany(targetEntity = Feature.class, mappedBy = "code", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private List<CommonCode> features;

	@ManyToOne
    private CommonCode category;

    @ManyToOne
    private User creator;

    @Column(name = "manager_mobile")
    private String managerMobile;

    @Column(name = "notification_type")
    private String notificationType;

    @JsonView(Views.ClassScheduleSummary.class)
    @Column(name = "reservation_close")
    private String reservationClose;

    /**
     * Instantiates a new Club.
     */
    public Club() {
	}


	/*public Club(Long id, String name, String description, String licenseNumber,
			String phoneNumber, String homepage, String zipcode,
			String address1, String address2, String addressSimple,
			Boolean onlyFemale, String[] features, CommonCode category) {
		super();
		this.id = id;
		this.name = name;

		this.licenseNumber = licenseNumber;
		this.phoneNumber = phoneNumber;
		this.zipcode = zipcode;
		this.address1 = address1;
		this.address2 = address2;
		this.addressSimple = addressSimple;
		this.description = description;
		this.homepage = homepage;
		this.onlyFemale = onlyFemale;
		this.features = features;
		this.category = category;
	}*/


    /**
     * Instantiates a new Club.
     *
     * @param id               the id
     * @param name             the name
     * @param licenseNumber    the license number
     * @param phoneNumber      the phone number
     * @param zipcode          the zipcode
     * @param address1         the address 1
     * @param address2         the address 2
     * @param oldAddress       the old address
     * @param addressSimple    the address simple
     * @param description      the description
     * @param homepage         the homepage
     * @param onlyFemale       the only female
     * @param category         the category
     * @param managerMobile    the manager mobile
     * @param notificationType the notification type
     * @param reservationClose the reservation close
     */
    public Club(Long id, String name, String licenseNumber,
    		String phoneNumber, String zipcode, String address1, String address2,
			String oldAddress, String addressSimple,
			String description, String homepage, Boolean onlyFemale,
			CommonCode category, String managerMobile,
			String notificationType, String reservationClose) {
		super();
		this.id = id;
		this.name = name;
		this.licenseNumber = licenseNumber;
		this.phoneNumber = phoneNumber;
		this.zipcode = zipcode;
		this.address1 = address1;
		this.address2 = address2;
		this.oldAddress = oldAddress;
		this.addressSimple = addressSimple;
		this.description = description;
		this.homepage = homepage;
		this.onlyFemale = onlyFemale;
		this.category = category;
		this.managerMobile = managerMobile;
		this.notificationType = notificationType;
		this.reservationClose = reservationClose;
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
     * Gets features.
     *
     * @return the features
     */
    public List<CommonCode> getFeatures() {
		return features;
	}

    /**
     * Sets features.
     *
     * @param features the features
     */
    public void setFeatures(List<CommonCode> features) {
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
     * Gets creator.
     *
     * @return the creator
     */
    public User getCreator() {
		return creator;
	}

    /**
     * Sets creator.
     *
     * @param creator the creator
     */
    public void setCreator(User creator) {
		this.creator = creator;
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


    /**
     * Update.
     *
     * @param name             the name
     * @param licenseNumber    the license number
     * @param phoneNumber      the phone number
     * @param zipcode          the zipcode
     * @param address1         the address 1
     * @param address2         the address 2
     * @param oldAddress       the old address
     * @param addressSimple    the address simple
     * @param description      the description
     * @param homepage         the homepage
     * @param onlyFemale       the only female
     * @param category         the category
     * @param managerMobile    the manager mobile
     * @param notificationType the notification type
     * @param reservationClose the reservation close
     */
    public void update(String name, String licenseNumber,
   		String phoneNumber, String zipcode, String address1, String address2,
			String oldAddress, String addressSimple,
			String description, String homepage,
			Boolean onlyFemale,	CommonCode category, String managerMobile,
			String notificationType, String reservationClose) {
		this.name = name;
		this.licenseNumber = licenseNumber;
		this.phoneNumber = phoneNumber;
		this.zipcode = zipcode;
		this.address1 = address1;
		this.address2 = address2;
		this.oldAddress = oldAddress;
		this.addressSimple = addressSimple;
		this.description = description;
		this.homepage = homepage;
		this.onlyFemale = onlyFemale;
		this.category = category;
		this.managerMobile = managerMobile;
		this.notificationType = notificationType;
		this.reservationClose = reservationClose;
	}


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Club club = (Club) o;

        return Objects.equals(id, club.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", licenseNumber='" + licenseNumber + "'" +
                ", phoneNumber='" + phoneNumber + "'" +
                ", zipcode='" + zipcode + "'" +
                ", address1='" + address1 + "'" +
                ", address2='" + address2 + "'" +
                ", addressSimple='" + addressSimple + "'" +
                ", description='" + description + "'" +
                ", homepage='" + homepage + "'" +
                ", onlyFemale='" + onlyFemale + "'" +
                ", category='" + category + "'" +
                '}';
    }
}
