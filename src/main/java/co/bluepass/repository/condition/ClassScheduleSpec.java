package co.bluepass.repository.condition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
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
import co.bluepass.domain.ActionSchedule;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.Instructor;
import co.bluepass.domain.Reservation;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.User;

/**
 * The type Class schedule spec.
 */
public class ClassScheduleSpec {

    /**
     * Start date specification.
     *
     * @param date the date
     * @return the specification
     */
    public static Specification<ClassSchedule> startDate(final String date) {
        return new Specification<ClassSchedule>() {
            @Override
            public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	DateTime startTime = DateTime.now();
            	if(StringUtils.isNotEmpty(date)) {
            		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMMdd");
            		startTime = formatter.parseDateTime(date);
            	}
                return cb.greaterThanOrEqualTo(root.<DateTime>get("startTime"), startTime);
            }
        };
    }

    /**
     * End date specification.
     *
     * @param date the date
     * @return the specification
     */
    public static Specification<ClassSchedule> endDate(final String date) {
    	return new Specification<ClassSchedule>() {
    		@Override
    		public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			DateTime endTime = DateTime.now().plusDays(7);
    			if(StringUtils.isNotEmpty(date)) {
    				DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMMdd");
    				endTime = formatter.parseDateTime(date);
    			}
    			endTime = endTime.withTime(23, 59, 59, 999);
    			return cb.lessThanOrEqualTo(root.<DateTime>get("startTime"), endTime);
    		}
    	};
    }

    /**
     * Start time specification.
     *
     * @param time the time
     * @return the specification
     */
    public static Specification<ClassSchedule> startTime(final String time) {
    	return new Specification<ClassSchedule>() {
    		@Override
    		public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				return cb.greaterThanOrEqualTo(cb.function("TIME_FORMAT", String.class,
						root.<DateTime> get("startTime"), cb.literal("%H%i")), time);
    		}
    	};
    }

    /**
     * End time specification.
     *
     * @param time the time
     * @return the specification
     */
    public static Specification<ClassSchedule> endTime(final String time) {
    	return new Specification<ClassSchedule>() {
    		@Override
    		public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    			return cb.lessThanOrEqualTo(cb.function("TIME_FORMAT", String.class,
    					root.<DateTime> get("startTime"), cb.literal("%H%i")), time);
    		}
    	};
    }

    /**
     * Club specification.
     *
     * @param club the club
     * @return the specification
     */
    public static Specification<ClassSchedule> club(final Club club) {
    	return new Specification<ClassSchedule>() {
    		@Override
    		public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			return cb.equal(root.get("club"), club);
    		}
    	};
    }

    /**
     * Action specification.
     *
     * @param action the action
     * @return the specification
     */
    public static Specification<ClassSchedule> action(final Action action) {
    	return new Specification<ClassSchedule>() {
    		@Override
    		public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			return cb.equal(root.get("action"), action);
    		}
    	};
    }

    /**
     * Instructor specification.
     *
     * @param instructor the instructor
     * @return the specification
     */
    public static Specification<ClassSchedule> instructor(final Instructor instructor) {
    	return new Specification<ClassSchedule>() {
    		@Override
    		public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			return cb.equal(root.get("instructor"), instructor);
    		}
    	};
    }

    /**
     * User specification.
     *
     * @param user the user
     * @return the specification
     */
    public static Specification<ClassSchedule> user(final User user) {
		return new Specification<ClassSchedule>() {
            @Override
            public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("user"), user);
            }
        };
	}

    /**
     * Category specification.
     *
     * @param category the category
     * @return the specification
     */
    public static Specification<ClassSchedule> category(final CommonCode category) {
		return new Specification<ClassSchedule>() {
            @Override
            public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("category"), category);
            }
        };
	}

    /**
     * Address specification.
     *
     * @param address1 the address 1
     * @param address2 the address 2
     * @return the specification
     */
    public static Specification<ClassSchedule> address(final String address1, final String address2) {
		return new Specification<ClassSchedule>() {
            @Override
            public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	Subquery<Club> sq = query.subquery(Club.class);
                Root<Club> club = sq.from(Club.class);
                //Join<ClassSchedule, Action> sqAction = classSchedule.join("id");
                sq.select(club)
                	.where(
                			cb.like(club.<String>get("address1"), "%" + address1),
                			cb.and(cb.like(club.<String>get("address2"), address2 + "%"))
                	);
                return cb.in(root.get("club")).value(sq);
            }
        };
	}

    /**
     * Old address specification.
     *
     * @param oldAddress the old address
     * @return the specification
     */
    public static Specification<ClassSchedule> oldAddress(final String oldAddress) {
		return new Specification<ClassSchedule>() {
			@Override
			public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Subquery<Club> sq = query.subquery(Club.class);
				Root<Club> club = sq.from(Club.class);
				sq.select(club)
				.where(
						cb.like(club.<String>get("oldAddress"), "%" + oldAddress + "%")
						);
				return cb.in(root.get("club")).value(sq);
			}
		};
	}

    /**
     * Reservatated specification.
     *
     * @return the specification
     */
    public static Specification<ClassSchedule> reservatated() {
		return new Specification<ClassSchedule>() {
			@Override
			public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Subquery<ClassSchedule> sq = query.subquery(ClassSchedule.class);
				Root<Reservation> reservation = sq.from(Reservation.class);
				//Join<Reservation, ClassSchedule> sqReservation = reservation.join("classSchedule");
				sq.distinct(true).select(reservation.<ClassSchedule>get("classSchedule"))
					.where(cb.notEqual(reservation.get("canceled"), true));

				return cb.in(root).value(sq);
			}
		};
	}

    /**
     * Enabled specification.
     *
     * @param enable the enable
     * @return the specification
     */
    public static Specification<ClassSchedule> enabled(final Boolean enable) {
		return new Specification<ClassSchedule>() {
			@Override
			public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				if(enable){
					//and
				}else{
					//ac = false || (ac = true & cs = false)
				}

				Subquery<ActionSchedule> sq = query.subquery(ActionSchedule.class);
				Root<ActionSchedule> actionSchedule = sq.from(ActionSchedule.class);
				sq.select(actionSchedule)
					.where(
							cb.equal(actionSchedule.get("enable"), enable)
					);

				return cb.in(root.get("actionSchedule")).value(sq);
			}
		};
	}

    /**
     * Used specification.
     *
     * @param used the used
     * @return the specification
     */
    public static Specification<ClassSchedule> used(final boolean used) {
		return new Specification<ClassSchedule>() {
            @Override
            public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	/*if(used){
            		return cb.and(
	            		cb.greaterThan(root.<Integer>get("reservationCount"), 0),
	            		cb.equal(root.<Integer>get("remindCount"), 0)
            		);
            	}else{
            		return cb.and(
        				cb.greaterThan(root.<Integer>get("reservationCount"), 0),
        				cb.greaterThan(root.<Integer>get("remindCount"), 0)
        			);

            	}*/

            	Subquery<ClassSchedule> sq = query.subquery(ClassSchedule.class);
				Root<Reservation> reservation = sq.from(Reservation.class);

				if(used){
					sq.distinct(true).select(reservation.<ClassSchedule>get("classSchedule"))
					.where(cb.isNotNull(reservation.get("used")));

				}else{
					sq.distinct(true).select(reservation.<ClassSchedule>get("classSchedule"))
					.where(cb.isNull(reservation.get("used")));

				}

				return cb.in(root).value(sq);
            }
        };
	}

    /**
     * Finished specification.
     *
     * @param finished the finished
     * @return the specification
     */
    public static Specification<ClassSchedule> finished(final Boolean finished) {
		return new Specification<ClassSchedule>() {
			@Override
			public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("finished"), finished);
			}
		};
	}

}
