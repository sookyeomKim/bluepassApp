package co.bluepass.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
//import org.codehaus.jackson.annotate.JsonIgnoreProperties;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import co.bluepass.web.rest.jsonview.Views;

/**
 * The type Image.
 */
@Entity
@Table(name = "IMAGE")
@NamedQueries({
    @NamedQuery(name = "images", query = "select i from Image i order by i.id")
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@JsonIgnoreProperties({"id","thumbnailFilename","newFilename","contentType","dateCreated","lastUpdated"})
public class Image implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView(Views.ActionSummary.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@JsonView(Views.ActionSummary.class)
    private String name;

    @JsonView(Views.ActionSummary.class)
    private String thumbnailFilename;

    private String newFilename;

    @JsonView(Views.ActionSummary.class)
    private String contentType;

    @Column(name = "size_")
    private Long size;

    private Long thumbnailSize;

    @Transient
    private String url;

    @Transient
    private String thumbnailUrl;

    @Transient
    private String deleteUrl;

    @Transient
    private String deleteType;


    /**
     * Gets url.
     *
     * @return the url
     */
    @JsonView(Views.ActionSummary.class)
    @JsonProperty("url")
	public String getUrl() {
		if(url == null || url.length() <= 0){
			url = "/image/picture/" + getId();
		}
		return url;
	}

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
		this.url = url;
	}

    /**
     * Gets thumbnail url.
     *
     * @return the thumbnail url
     */
    @JsonView(Views.ActionSummary.class)
    @JsonProperty("thumbnailUrl")
	public String getThumbnailUrl() {
		if(thumbnailUrl == null || thumbnailUrl.length() <= 0){
			thumbnailUrl = "/image/thumbnail/" + getId();
		}
		return thumbnailUrl;
	}

    /**
     * Sets thumbnail url.
     *
     * @param thumbnailUrl the thumbnail url
     */
    public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

    /**
     * Gets delete url.
     *
     * @return the delete url
     */
    public String getDeleteUrl() {
		return deleteUrl;
	}

    /**
     * Sets delete url.
     *
     * @param deleteUrl the delete url
     */
    public void setDeleteUrl(String deleteUrl) {
		if(deleteUrl == null || deleteUrl.length() <= 0){
			deleteUrl = "/image/delete/" + getId();
		}
		this.deleteUrl = deleteUrl;
	}

    /**
     * Gets delete type.
     *
     * @return the delete type
     */
    public String getDeleteType() {
		return deleteType;
	}

    /**
     * Sets delete type.
     *
     * @param deleteType the delete type
     */
    public void setDeleteType(String deleteType) {
		if(deleteType == null || deleteType.length() <= 0){
			deleteType = "DELETE";
		}
		this.deleteType = deleteType;
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
     * Gets thumbnail filename.
     *
     * @return the thumbnail filename
     */
    public String getThumbnailFilename() {
		return thumbnailFilename;
	}

    /**
     * Gets new filename.
     *
     * @return the new filename
     */
    public String getNewFilename() {
		return newFilename;
	}

    /**
     * Gets content type.
     *
     * @return the content type
     */
    public String getContentType() {
		return contentType;
	}

    /**
     * Gets size.
     *
     * @return the size
     */
    public Long getSize() {
		return size;
	}

    /**
     * Gets thumbnail size.
     *
     * @return the thumbnail size
     */
    public Long getThumbnailSize() {
		return thumbnailSize;
	}

    /**
     * Instantiates a new Image.
     */
    public Image() {}

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
		return id;
	}

    /**
     * Gets builder.
     *
     * @param image the image
     * @return the builder
     */
    public static Builder getBuilder(Image image) {
		return new Builder(image);
	}

    /**
     * Gets builder.
     *
     * @param name the name
     * @return the builder
     */
    public static Builder getBuilder(String name) {
    	return new Builder(name);
    }

    /**
     * The type Builder.
     */
    public static class Builder {

    	private Image built;

        /**
         * Instantiates a new Builder.
         *
         * @param name the name
         */
        public Builder(String name){
    		built = new Image();
    		built.name = name;
    	}

        /**
         * Instantiates a new Builder.
         *
         * @param image the image
         */
        public Builder(Image image){
    		if(image == null){
    			built = new Image();
    		}else{
    			built = image;
    		}
    	}

        /**
         * Build image.
         *
         * @return the image
         */
        public Image build(){
    		return built;
    	}

        /**
         * Thumbnail filename builder.
         *
         * @param thumbnailFilename the thumbnail filename
         * @return the builder
         */
        public Builder thumbnailFilename(String thumbnailFilename) {
    		built.thumbnailFilename = thumbnailFilename;
    		return this;
    	}

        /**
         * New filename builder.
         *
         * @param newFilename the new filename
         * @return the builder
         */
        public Builder newFilename(String newFilename) {
    		built.newFilename = newFilename;
    		return this;
    	}

        /**
         * Content type builder.
         *
         * @param contentType the content type
         * @return the builder
         */
        public Builder contentType(String contentType) {
    		built.contentType = contentType;
    		return this;
    	}

        /**
         * Size builder.
         *
         * @param size the size
         * @return the builder
         */
        public Builder size(Long size) {
    		built.size = size;
    		return this;
    	}

        /**
         * Thumbnail size builder.
         *
         * @param thumbnailSize the thumbnail size
         * @return the builder
         */
        public Builder thumbnailSize(Long thumbnailSize) {
    		built.thumbnailSize = thumbnailSize;
    		return this;
    	}

        /**
         * Url builder.
         *
         * @param url the url
         * @return the builder
         */
        public Builder url(String url) {
    		built.url = url;
    		return this;
    	}

        /**
         * Thumbnail url builder.
         *
         * @param thumbnailUrl the thumbnail url
         * @return the builder
         */
        public Builder thumbnailUrl(String thumbnailUrl) {
    		built.thumbnailUrl = thumbnailUrl;
    		return this;
    	}

        /**
         * Delete url builder.
         *
         * @param deleteUrl the delete url
         * @return the builder
         */
        public Builder deleteUrl(String deleteUrl) {
    		built.deleteUrl = deleteUrl;
    		return this;
    	}

        /**
         * Delete type builder.
         *
         * @param deleteType the delete type
         * @return the builder
         */
        public Builder deleteType(String deleteType) {
    		built.deleteType = deleteType;
    		return this;
    	}
    }

}
