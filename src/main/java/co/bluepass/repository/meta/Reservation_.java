package co.bluepass.repository.meta;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.Club;
import co.bluepass.domain.Reservation;
import co.bluepass.domain.User;

/**
 * The type Reservation.
 */
@StaticMetamodel(Reservation_.class)
public class Reservation_ {
    /**
     * The constant user.
     */
    public static volatile SingularAttribute<Reservation, User> user;
    /**
     * The constant club.
     */
    public static volatile SingularAttribute<Reservation, Club> club;
    /**
     * The constant classSchedule.
     */
    public static volatile SingularAttribute<Reservation, ClassSchedule> classSchedule;
}
