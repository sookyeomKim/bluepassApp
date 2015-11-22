package co.bluepass.repository.condition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.jpa.domain.Specification;

import co.bluepass.domain.Action;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.Club;
import co.bluepass.domain.Reservation;
import co.bluepass.domain.User;

/**
 * The type Reservation spec.
 */
public class ReservationSpec {

    /*public static Specification<Reservation> titleLike(final Long clubId) {
        return new Specification<Reservation>() {
            @Override
            public Predicate toPredicate(Root<Reservation> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get(""), "%" + clubId + "%");
            }
        };
    }*/

    /**
     * Club specification.
     *
     * @param club the club
     * @return the specification
     */
    public static Specification<Reservation> club(final Club club) {
        return new Specification<Reservation>() {
            @Override
            public Predicate toPredicate(Root<Reservation> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("club"), club);
            }
        };
    }

    /**
     * User specification.
     *
     * @param user the user
     * @return the specification
     */
    public static Specification<Reservation> user(final User user) {
		return new Specification<Reservation>() {
            @Override
            public Predicate toPredicate(Root<Reservation> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("user"), user);
            }
        };
	}

    /**
     * Action specification.
     *
     * @param action the action
     * @return the specification
     */
    public static Specification<Reservation> action(final Action action) {
		return new Specification<Reservation>() {
            @Override
            public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	Subquery<ClassSchedule> sq = query.subquery(ClassSchedule.class);
                Root<ClassSchedule> classSchedule = sq.from(ClassSchedule.class);
                //Join<ClassSchedule, Reservation> sqReservation = classSchedule.join("id");
                sq.select(classSchedule).where(cb.equal(classSchedule.get("action"), action));

                return cb.in(root.get("classSchedule")).value(sq);
            }
        };
	}

    /**
     * Year month specification.
     *
     * @param yearMonth the year month
     * @return the specification
     */
    public static Specification<Reservation> yearMonth(final String yearMonth) {
        return new Specification<Reservation>() {
            @Override
    		public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				return cb.equal(cb.function("TIME_FORMAT", String.class,
						root.<DateTime> get("startTime"), cb.literal("%y%m")), yearMonth);
    		}
        };
    }

    /**
     * Crash time specification.
     *
     * @param column    the column
     * @param startTime the start time
     * @param endTime   the end time
     * @return the specification
     */
    public static Specification<Reservation> crashTime(final String column, final DateTime startTime, final DateTime endTime) {
		return new Specification<Reservation>() {
            @Override
    		public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				return cb.between(root.<DateTime>get(column), startTime, endTime);
    		}
        };
	}

    /**
     * Canceled specification.
     *
     * @param canceled the canceled
     * @return the specification
     */
    public static Specification<Reservation> canceled(final Boolean canceled) {
		return new Specification<Reservation>() {
            @Override
            public Predicate toPredicate(Root<Reservation> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("canceled"), canceled);
            }
        };
	}

    /**
     * Used specification.
     *
     * @param used the used
     * @return the specification
     */
    public static Specification<Reservation> used(final Boolean used) {
		return new Specification<Reservation>() {
			@Override
			public Predicate toPredicate(Root<Reservation> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("used"), used);
			}
		};
	}

    /**
     * Used is null specification.
     *
     * @return the specification
     */
    public static Specification<Reservation> usedIsNull() {
		return new Specification<Reservation>() {
			@Override
			public Predicate toPredicate(Root<Reservation> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.isNull(root.get("used"));
			}
		};
	}



}
