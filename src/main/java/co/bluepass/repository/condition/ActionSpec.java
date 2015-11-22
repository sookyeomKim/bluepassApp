package co.bluepass.repository.condition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import co.bluepass.domain.Action;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.User;

/**
 * The type Action spec.
 */
public class ActionSpec {

    /**
     * Club specification.
     *
     * @param club the club
     * @return the specification
     */
    public static Specification<Action> club(final Club club) {
        return new Specification<Action>() {
            @Override
            public Predicate toPredicate(Root<Action> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
    public static Specification<Action> user(final User user) {
		return new Specification<Action>() {
            @Override
            public Predicate toPredicate(Root<Action> root,
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
    public static Specification<Action> action(final Action action) {
		return new Specification<Action>() {
            @Override
            public Predicate toPredicate(Root<Action> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	Subquery<ClassSchedule> sq = query.subquery(ClassSchedule.class);
                Root<ClassSchedule> classSchedule = sq.from(ClassSchedule.class);
                //Join<ClassSchedule, Action> sqAction = classSchedule.join("id");
                sq.select(classSchedule).where(cb.equal(classSchedule.get("action"), action));

                return cb.in(root.get("classSchedule")).value(sq);
            }
        };
	}

    /**
     * Category specification.
     *
     * @param category the category
     * @return the specification
     */
    public static Specification<Action> category(final CommonCode category) {
		return new Specification<Action>() {
            @Override
            public Predicate toPredicate(Root<Action> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
    public static Specification<Action> address(final String address1, final String address2) {
		return new Specification<Action>() {
            @Override
            public Predicate toPredicate(Root<Action> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
    public static Specification<Action> oldAddress(final String oldAddress) {
		return new Specification<Action>() {
			@Override
			public Predicate toPredicate(Root<Action> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Subquery<Club> sq = query.subquery(Club.class);
				Root<Club> club = sq.from(Club.class);
				sq.select(club)
				.where(
						cb.equal(club.<String>get("oldAddress"), oldAddress)
						);
				return cb.in(root.get("club")).value(sq);
			}
		};
	}



}
