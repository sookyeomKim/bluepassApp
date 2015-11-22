package co.bluepass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import co.bluepass.web.rest.jsonview.Views;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The type Action image.
 */
@Entity
@Table(name = "ACTION_IMAGE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActionImage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@JsonIgnore
    @ManyToOne
    private Action action;

	@JsonView(Views.ActionSummary.class)
    @ManyToOne(cascade = {CascadeType.REMOVE})
    private Image image;

    /**
     * Instantiates a new Action image.
     *
     * @param action the action
     * @param image  the image
     */
    public ActionImage(Action action, Image image) {
		this.action = action;
		this.image = image;
	}

    /**
     * Instantiates a new Action image.
     */
    public ActionImage() {
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
     * Gets action.
     *
     * @return the action
     */
    public Action getAction() {
		return action;
	}

    /**
     * Sets action.
     *
     * @param action the action
     */
    public void setAction(Action action) {
		this.action = action;
	}

    /**
     * Gets image.
     *
     * @return the image
     */
    public Image getImage() {
		return image;
	}

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
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
		ActionImage other = (ActionImage) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ActionImage [id=" + id + ", action=" + action.getId() + ", image="
				+ image.getId() + "]";
	}
}
