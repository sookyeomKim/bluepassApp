package co.bluepass.repository;

import co.bluepass.domain.CommonCode;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * The interface Common code repository.
 */
public interface CommonCodeRepository extends JpaRepository<CommonCode,Long> {

    /**
     * Find by name common code.
     *
     * @param name the name
     * @return the common code
     */
    CommonCode findByName(String name);

    /**
     * Find by name in list.
     *
     * @param features the features
     * @return the list
     */
    List<CommonCode> findByNameIn(String[] features);

    /**
     * Find one by name common code.
     *
     * @param name the name
     * @return the common code
     */
    CommonCode findOneByName(String name);

    /**
     * Find by parent and option 1 common code.
     *
     * @param parent  the parent
     * @param option1 the option 1
     * @return the common code
     */
    CommonCode findByParentAndOption1(CommonCode parent, String option1);

}
