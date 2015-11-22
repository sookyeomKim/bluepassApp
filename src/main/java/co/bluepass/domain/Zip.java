package co.bluepass.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type Zip.
 */
@Entity
@Table(name = "ZIP")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Zip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Pattern(regexp = "^([0-9]{3}-[0-9]{3})$")
    @Column(name = "zipcode", nullable = false)
    private String zipcode;

    @NotNull
    @Column(name = "sido", nullable = false)
    private String sido;

    @NotNull
    @Column(name = "gugun", nullable = true)
    private String gugun;

    @NotNull
    @Column(name = "dong", nullable = false)
    private String dong;

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
     * Gets sido.
     *
     * @return the sido
     */
    public String getSido() {
        return sido;
    }

    /**
     * Sets sido.
     *
     * @param sido the sido
     */
    public void setSido(String sido) {
        this.sido = sido;
    }

    /**
     * Gets gugun.
     *
     * @return the gugun
     */
    public String getGugun() {
        return gugun;
    }

    /**
     * Sets gugun.
     *
     * @param gugun the gugun
     */
    public void setGugun(String gugun) {
        this.gugun = gugun;
    }

    /**
     * Gets dong.
     *
     * @return the dong
     */
    public String getDong() {
        return dong;
    }

    /**
     * Sets dong.
     *
     * @param dong the dong
     */
    public void setDong(String dong) {
        this.dong = dong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Zip zip = (Zip) o;

        return Objects.equals(id, zip.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Zip{" +
                "id=" + id +
                ", zipcode='" + zipcode + "'" +
                ", sido='" + sido + "'" +
                ", gugun='" + gugun + "'" +
                ", dong='" + dong + "'" +
                '}';
    }
}
