package co.bluepass.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import co.bluepass.web.rest.jsonview.Views;

/**
 * The type Common code.
 */
@Entity
@Table(name = "COMMON_CODE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommonCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.ActionSummary.class)
    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @JsonView(Views.ActionSummary.class)
    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "description")
    private String description;

    @Column(name = "option1")
    private String option1;

    @Column(name = "option2")
    private String option2;

    @Column(name = "option3")
    private String option3;

    @ManyToOne
    private CommonCode parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CommonCode> children;

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
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
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
     * Gets option 1.
     *
     * @return the option 1
     */
    public String getOption1() {
        return option1;
    }

    /**
     * Sets option 1.
     *
     * @param option1 the option 1
     */
    public void setOption1(String option1) {
        this.option1 = option1;
    }

    /**
     * Gets option 2.
     *
     * @return the option 2
     */
    public String getOption2() {
        return option2;
    }

    /**
     * Sets option 2.
     *
     * @param option2 the option 2
     */
    public void setOption2(String option2) {
        this.option2 = option2;
    }

    /**
     * Gets option 3.
     *
     * @return the option 3
     */
    public String getOption3() {
        return option3;
    }

    /**
     * Sets option 3.
     *
     * @param option3 the option 3
     */
    public void setOption3(String option3) {
        this.option3 = option3;
    }

    /**
     * Gets parent.
     *
     * @return the parent
     */
    public CommonCode getParent() {
        return parent;
    }

    /**
     * Sets parent.
     *
     * @param commonCode the common code
     */
    public void setParent(CommonCode commonCode) {
        this.parent = commonCode;
    }

    /**
     * Gets children.
     *
     * @return the children
     */
    public Set<CommonCode> getChildren() {
		return children;
	}

    /**
     * Sets children.
     *
     * @param children the children
     */
    public void setChildren(Set<CommonCode> children) {
		this.children = children;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommonCode commonCode = (CommonCode) o;

        return Objects.equals(id, commonCode.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CommonCode{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", value='" + value + "'" +
                ", description='" + description + "'" +
                ", option1='" + option1 + "'" +
                ", option2='" + option2 + "'" +
                ", option3='" + option3 + "'" +
                '}';
    }
}
