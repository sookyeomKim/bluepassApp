package co.bluepass.repository;

import co.bluepass.domain.PartnerRequest;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * The interface Partner request repository.
 */
public interface PartnerRequestRepository extends JpaRepository<PartnerRequest,Long> {

}
