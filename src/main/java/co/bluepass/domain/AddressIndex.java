package co.bluepass.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * The type Address index.
 */
@Entity
@Table(name = "ADDRESS_INDEX")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AddressIndex implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "old_address")
    private String oldAddress;

    /**
     * Instantiates a new Address index.
     *
     * @param oldAddress the old address
     */
    public AddressIndex(String oldAddress) {
    	this.oldAddress = oldAddress;
	}

    /**
     * Instantiates a new Address index.
     */
    public AddressIndex() {
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
     * Gets old address.
     *
     * @return the old address
     */
    public String getOldAddress() {
        return oldAddress;
    }

    /**
     * Sets old address.
     *
     * @param oldAddress the old address
     */
    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressIndex addressIndex = (AddressIndex) o;

        return Objects.equals(id, addressIndex.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AddressIndex{" +
                "id=" + id +
                ", oldAddress='" + oldAddress + "'" +
                '}';
    }
}
