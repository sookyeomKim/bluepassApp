package co.bluepass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The type Feature.
 */
@Entity
@Table(name = "CLUB_FEATURE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Feature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Club club;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_name", referencedColumnName = "name")
    private CommonCode code;

    /**
     * Instantiates a new Feature.
     *
     * @param club       the club
     * @param commonCode the common code
     */
    public Feature(Club club, CommonCode commonCode) {
		this.club = club;
		this.code = commonCode;
	}

    /**
     * Instantiates a new Feature.
     */
    public Feature() {
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
     * Gets club.
     *
     * @return the club
     */
    public Club getClub() {
        return club;
    }

    /**
     * Sets club.
     *
     * @param club the club
     */
    public void setClub(Club club) {
        this.club = club;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public CommonCode getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param commonCode the common code
     */
    public void setCode(CommonCode commonCode) {
        this.code = commonCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Feature feature = (Feature) o;

        return Objects.equals(id, feature.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Feature{" +
                "club id =" + club.getId() +
                "commonCode id =" + code.getId() +
                '}';
    }
}
