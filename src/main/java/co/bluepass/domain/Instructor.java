package co.bluepass.domain;

import co.bluepass.web.rest.jsonview.Views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The type Instructor.
 */
@Entity
@Table(name = "INSTRUCTOR")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class Instructor implements Serializable {

	@JsonView(Views.ClassScheduleSummary.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@JsonView(Views.ClassScheduleSummary.class)
    @NotNull
    @Size(max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Image photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Club club;

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
     * Gets photo.
     *
     * @return the photo
     */
    public Image getPhoto() {
		return photo;
	}

    /**
     * Sets photo.
     *
     * @param photo the photo
     */
    public void setPhoto(Image photo) {
		this.photo = photo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Instructor instructor = (Instructor) o;

        return Objects.equals(id, instructor.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                '}';
    }
}
