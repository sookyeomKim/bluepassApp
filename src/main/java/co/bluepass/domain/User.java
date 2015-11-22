package co.bluepass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import co.bluepass.web.rest.jsonview.Views;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

/**
 * The type User.
 */
@Entity
@Table(name = "USER")
@Cache(usage = CacheConcurrencyStrategy.NONE )
public class User extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @JsonIgnore
    @NotNull
    @Size(min = 5, max = 100)
    @Column(length = 100)
    private String password;

    @JsonView(Views.ReservationSummary.class)
    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    private String langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "reset_date", nullable = true)
    private DateTime resetDate = null;

    //@JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Authority> authorities = new HashSet<>();

    //@NotNull
    @Pattern(regexp = "^([0-9]{6})$")
    @Column(length = 6)
    private String zipcode;

    //@NotNull
    @Column(length = 255)
    private String address1;

    @Column(length = 100)
    private String address2;

    //@NotNull
    @JsonView(Views.ReservationSummary.class)
    @Column
    private int age;

    //@NotEmpty
    @JsonView(Views.ReservationSummary.class)
    @Column(length = 1)
    private String gender;

    @JsonView(Views.ReservationSummary.class)
    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "favor_site")
    private Zip favorSite;

    @ManyToOne
    @JoinColumn(name = "favor_category")
    private CommonCode favorCategory;

    @ManyToOne
    @JoinColumn(name = "jacket_size")
    private CommonCode jacketSize;

    @ManyToOne
    @JoinColumn(name = "pants_size")
    private CommonCode pantsSize;

    @Column(name = "exercise_count")
    private Integer exerciseCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "register_status")
    private RegisterStatus registerStatus;

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
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets activated.
     *
     * @return the activated
     */
    public boolean getActivated() {
        return activated;
    }

    /**
     * Sets activated.
     *
     * @param activated the activated
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * Gets activation key.
     *
     * @return the activation key
     */
    public String getActivationKey() {
        return activationKey;
    }

    /**
     * Sets activation key.
     *
     * @param activationKey the activation key
     */
    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    /**
     * Gets reset key.
     *
     * @return the reset key
     */
    public String getResetKey() {
        return resetKey;
    }

    /**
     * Sets reset key.
     *
     * @param resetKey the reset key
     */
    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    /**
     * Gets reset date.
     *
     * @return the reset date
     */
    public DateTime getResetDate() {
       return resetDate;
    }

    /**
     * Sets reset date.
     *
     * @param resetDate the reset date
     */
    public void setResetDate(DateTime resetDate) {
       this.resetDate = resetDate;
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
     * Sets lang key.
     *
     * @param langKey the lang key
     */
    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    /**
     * Gets authorities.
     *
     * @return the authorities
     */
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    /**
     * Sets authorities.
     *
     * @param authorities the authorities
     */
    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
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
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
		return age;
	}

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(int age) {
		this.age = age;
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
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
		this.gender = gender;
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
     * Gets favor site.
     *
     * @return the favor site
     */
    public Zip getFavorSite() {
		return favorSite;
	}

    /**
     * Sets favor site.
     *
     * @param favorSite the favor site
     */
    public void setFavorSite(Zip favorSite) {
		this.favorSite = favorSite;
	}

    /**
     * Gets favor category.
     *
     * @return the favor category
     */
    public CommonCode getFavorCategory() {
		return favorCategory;
	}

    /**
     * Sets favor category.
     *
     * @param favorCategory the favor category
     */
    public void setFavorCategory(CommonCode favorCategory) {
		this.favorCategory = favorCategory;
	}

    /**
     * Gets jacket size.
     *
     * @return the jacket size
     */
    public CommonCode getJacketSize() {
		return jacketSize;
	}

    /**
     * Sets jacket size.
     *
     * @param jacketSize the jacket size
     */
    public void setJacketSize(CommonCode jacketSize) {
		this.jacketSize = jacketSize;
	}

    /**
     * Gets pants size.
     *
     * @return the pants size
     */
    public CommonCode getPantsSize() {
		return pantsSize;
	}

    /**
     * Sets pants size.
     *
     * @param pantsSize the pants size
     */
    public void setPantsSize(CommonCode pantsSize) {
		this.pantsSize = pantsSize;
	}

    /**
     * Gets exercise count.
     *
     * @return the exercise count
     */
    public Integer getExerciseCount() {
		return exerciseCount;
	}

    /**
     * Sets exercise count.
     *
     * @param exerciseCount the exercise count
     */
    public void setExerciseCount(Integer exerciseCount) {
		this.exerciseCount = exerciseCount;
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

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return email.equals(user.email);

    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", activated='" + activated + '\'' +
                ", langKey='" + langKey + '\'' +
                ", activationKey='" + activationKey + '\'' +
                "}";
    }

    /**
     * Update.
     *
     * @param name           the name
     * @param phoneNumber    the phone number
     * @param zipcode        the zipcode
     * @param address1       the address 1
     * @param address2       the address 2
     * @param age            the age
     * @param gender         the gender
     * @param favorSite      the favor site
     * @param favorCategory  the favor category
     * @param jacketSize     the jacket size
     * @param pantsSize      the pants size
     * @param exerciseCount  the exercise count
     * @param registerStatus the register status
     */
    public void update(String name, String phoneNumber, String zipcode, String address1, String address2, int age, String gender,
			Zip favorSite, CommonCode favorCategory, CommonCode jacketSize, CommonCode pantsSize,
			Integer exerciseCount, RegisterStatus registerStatus) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.zipcode = zipcode;
		this.address1 = address1;
		this.address2 = address2;
		this.age = age;
		this.gender = gender;
		this.favorSite = favorSite;
		this.favorCategory = favorCategory;
		this.jacketSize = jacketSize;
		this.pantsSize = pantsSize;
		this.exerciseCount = exerciseCount;
		this.registerStatus = registerStatus;
	}
}
