package co.bluepass.repository;

import co.bluepass.domain.User;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find one by activation key user.
     *
     * @param activationKey the activation key
     * @return the user
     */
    User findOneByActivationKey(String activationKey);

    /**
     * Find all by activated is false and created date before list.
     *
     * @param dateTime the date time
     * @return the list
     */
    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(DateTime dateTime);

    /**
     * Find one by reset key user.
     *
     * @param resetKey the reset key
     * @return the user
     */
    User findOneByResetKey(String resetKey);

    /**
     * Find one by email user.
     *
     * @param email the email
     * @return the user
     */
    User findOneByEmail(String email);

}
