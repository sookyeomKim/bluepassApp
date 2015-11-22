package co.bluepass.repository;

import co.bluepass.domain.Zip;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * The interface Zip repository.
 */
public interface ZipRepository extends JpaRepository<Zip,Long> {

    /**
     * Find by dong list.
     *
     * @param dong the dong
     * @return the list
     */
    List<Zip> findByDong(String dong);

    /**
     * Find by dong like list.
     *
     * @param dong the dong
     * @return the list
     */
    List<Zip> findByDongLike(String dong);

    /**
     * Find by zipcode zip.
     *
     * @param zipcode the zipcode
     * @return the zip
     */
    Zip findByZipcode(String zipcode);


}
