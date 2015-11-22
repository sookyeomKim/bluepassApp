package co.bluepass.repository.condition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * The interface Condition spec.
 *
 * @param <T> the type parameter
 */
public interface ConditionSpec<T> {
    /**
     * To predicate predicate.
     *
     * @param root  the root
     * @param query the query
     * @param cb    the cb
     * @param id    the id
     * @return the predicate
     */
    Predicate toPredicate(Root<T> root, CriteriaQuery<T> query, CriteriaBuilder cb, Long id);
}
