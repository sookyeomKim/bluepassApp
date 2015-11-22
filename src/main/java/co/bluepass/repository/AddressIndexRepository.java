package co.bluepass.repository;

import co.bluepass.domain.AddressIndex;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * The interface Address index repository.
 */
public interface AddressIndexRepository extends JpaRepository<AddressIndex,Long> {

}
