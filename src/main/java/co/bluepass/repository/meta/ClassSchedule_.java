package co.bluepass.repository.meta;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.bluepass.domain.Action;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.Club;

/**
 * The type Class schedule.
 */
@StaticMetamodel(ClassSchedule.class)
public class ClassSchedule_ {
    /**
     * The constant club.
     */
    public static volatile SingularAttribute<ClassSchedule, Club> club;
    /**
     * The constant action.
     */
    public static volatile SingularAttribute<ClassSchedule, Action> action;
}
