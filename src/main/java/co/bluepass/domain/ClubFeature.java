package co.bluepass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The type Club feature.
 */
@Entity
@Table(name = "CLUB_FEATURE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClubFeature implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@JsonIgnore
    @ManyToOne
    private Club club;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    private Feature feature;

    /**
     * Instantiates a new Club feature.
     *
     * @param action  the action
     * @param feature the feature
     */
    public ClubFeature(Club action, Feature feature) {
		this.club = action;
		this.feature = feature;
	}

    /**
     * Instantiates a new Club feature.
     */
    public ClubFeature() {
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
     * @param action the action
     */
    public void setClub(Club action) {
		this.club = action;
	}

    /**
     * Gets feature.
     *
     * @return the feature
     */
    public Feature getFeature() {
		return feature;
	}

    /**
     * Sets feature.
     *
     * @param image the image
     */
    public void setFeature(Feature image) {
		this.feature = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((club == null) ? 0 : club.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((feature == null) ? 0 : feature.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClubFeature other = (ClubFeature) obj;
		if (club == null) {
			if (other.club != null)
				return false;
		} else if (!club.equals(other.club))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (feature == null) {
			if (other.feature != null)
				return false;
		} else if (!feature.equals(other.feature))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClubFeature [id=" + id + ", action=" + club.getId() + ", image="
				+ feature.getId() + "]";
	}
}
