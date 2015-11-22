package co.bluepass.web.rest.dto;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.Image;

/**
 * The type Action dto.
 */
public class ActionDTO {

	private Long id;

	@NotNull
	@Size(max = 100)
	private String title;

	@Size(max = 500)
	private String description;

	@Size(max = 40)
	private String shortDescription;

	private CommonCode category;

	private Long[] imageIds;

	@NotNull
	private Long clubId;

	private String useLimitType;

	private Integer useLimitValue;

	private String movieIds;

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
     * Get image ids long [ ].
     *
     * @return the long [ ]
     */
    public Long[] getImageIds() {
		return imageIds;
	}

    /**
     * Sets image ids.
     *
     * @param imageIds the image ids
     */
    public void setImageIds(Long[] imageIds) {
		this.imageIds = imageIds;
	}

    /**
     * Gets club id.
     *
     * @return the club id
     */
    public Long getClubId() {
		return clubId;
	}

    /**
     * Sets club id.
     *
     * @param clubId the club id
     */
    public void setClubId(Long clubId) {
		this.clubId = clubId;
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

	@Override
	public String toString() {
		return "ActionDTO [id=" + id + ", title=" + title + ", description=" + description + ", shortDescription="
				+ shortDescription + ", category=" + category + ", imageIds=" + Arrays.toString(imageIds) + ", clubId="
				+ clubId + ", useLimitType=" + useLimitType + ", useLimitValue=" + useLimitValue + ", movieIds="
				+ movieIds + "]";
	}

}
