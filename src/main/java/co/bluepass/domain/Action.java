package co.bluepass.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import co.bluepass.web.rest.jsonview.JsonViews;
import co.bluepass.web.rest.jsonview.Views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * The type Action.
 */
@Entity
@Table(name = "ACTION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Action implements Serializable {

	@JsonView(Views.SummaryBase.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonView(Views.SummaryBase.class)
	@NotNull
	@Size(max = 100)
	@Column(name = "title", length = 100, nullable = false)
	private String title;

	@Size(max = 500)
	@Column(name = "description", length = 150)
	private String description;

	@JsonView(Views.ActionSummary.class)
	@Size(max = 40)
	@Column(name = "short_description", length = 40)
	private String shortDescription;

	@JsonView(Views.ActionSummary.class)
	@ManyToOne
	private CommonCode category;

	@JsonView(Views.ActionSummary.class)
	@ManyToMany(targetEntity = ActionImage.class, mappedBy = "action", fetch = FetchType.EAGER)
	private List<Image> images;

	@JsonView(Views.ActionSummary.class)
	@ManyToOne
	private Club club;

	@ManyToOne
	private Zip zip;

	@JsonView(Views.ActionSummary.class)
    @Column(name = "use_limit_type")
    private String useLimitType;

	@JsonView(Views.ActionSummary.class)
    @Column(name = "use_limit_value")
    private Integer useLimitValue;

	@ManyToOne
	private User creator;

	@JsonView(Views.ActionSummary.class)
    @Column(name = "movie_ids")
    private String movieIds;

    /**
     * Instantiates a new Action.
     */
    public Action() {
	}

    /**
     * Instantiates a new Action.
     *
     * @param title            the title
     * @param description      the description
     * @param shortDescription the short description
     * @param category         the category
     * @param club             the club
     * @param zip              the zip
     * @param useLimitType     the use limit type
     * @param useLimitValue    the use limit value
     * @param movieIds         the movie ids
     * @param creator          the creator
     */
    public Action(String title, String description, String shortDescription,
			CommonCode category, Club club, Zip zip,
			String useLimitType, Integer useLimitValue, String movieIds,
			User creator) {
		super();
		this.title = title;
		this.description = description;
		this.shortDescription = shortDescription;
		this.category = category;
		this.club = club;
		this.zip = zip;
		this.useLimitType = useLimitType;
		this.useLimitValue = useLimitValue;
		this.movieIds = movieIds;
		this.creator = creator;
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
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
		return title;
	}

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
		this.title = title;
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
     * Gets short description.
     *
     * @return the short description
     */
    public String getShortDescription() {
		return shortDescription;
	}

    /**
     * Sets short description.
     *
     * @param shortDescription the short description
     */
    public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
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
     * Gets images.
     *
     * @return the images
     */
    public List<Image> getImages() {
		return images;
	}

    /**
     * Sets images.
     *
     * @param images the images
     */
    public void setImages(List<Image> images) {
		this.images = images;
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
     * Gets zip.
     *
     * @return the zip
     */
    public Zip getZip() {
		return zip;
	}

    /**
     * Sets zip.
     *
     * @param zip the zip
     */
    public void setZip(Zip zip) {
		this.zip = zip;
	}

    /**
     * Gets use limit type.
     *
     * @return the use limit type
     */
    public String getUseLimitType() {
		return useLimitType;
	}

    /**
     * Sets use limit type.
     *
     * @param useLimitType the use limit type
     */
    public void setUseLimitType(String useLimitType) {
		this.useLimitType = useLimitType;
	}

    /**
     * Gets use limit value.
     *
     * @return the use limit value
     */
    public Integer getUseLimitValue() {
		return useLimitValue;
	}

    /**
     * Sets use limit value.
     *
     * @param useLimitValue the use limit value
     */
    public void setUseLimitValue(Integer useLimitValue) {
		this.useLimitValue = useLimitValue;
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

	/*@JsonView(Views.ActionSummary.class)
	@JsonProperty("images")
	public Image[] getImagesArr() {
	    return images.toArray(new Image[images.size()]);
	}*/

    /**
     * Gets movie ids.
     *
     * @return the movie ids
     */
    public String getMovieIds() {
		return movieIds;
	}

    /**
     * Sets movie ids.
     *
     * @param movieIds the movie ids
     */
    public void setMovieIds(String movieIds) {
		this.movieIds = movieIds;
	}

    /**
     * Update.
     *
     * @param title            the title
     * @param description      the description
     * @param shortDescription the short description
     * @param category         the category
     * @param club             the club
     * @param useLimitType     the use limit type
     * @param useLimitValue    the use limit value
     * @param movieIds         the movie ids
     * @param creator          the creator
     */
    public void update(String title, String description, String shortDescription,
			CommonCode category, Club club, String useLimitType, Integer useLimitValue,
			String movieIds, User creator) {
		this.title = title;
		this.description = description;
		this.shortDescription = shortDescription;
		this.category = category;
		this.club = club;
		this.useLimitType = useLimitType;
		this.useLimitValue = useLimitValue;
		this.movieIds = movieIds;
		this.creator = creator;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Action action = (Action) o;

        return Objects.equals(id, action.id);

    }

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Action [id=" + id + ", title=" + title + ", description=" + description + ", shortDescription="
				+ shortDescription + ", category=" + category + ", images=" + images + ", club=" + club + ", zip=" + zip
				+ ", useLimitType=" + useLimitType + ", useLimitValue=" + useLimitValue + ", creator=" + creator
				+ ", movieIds=" + movieIds + "]";
	}
}
