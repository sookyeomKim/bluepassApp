package co.bluepass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.bluepass.domain.Action;
import co.bluepass.domain.ActionImage;
import co.bluepass.domain.Image;

/**
 * The interface Action image repository.
 */
public interface ActionImageRepository extends JpaRepository<ActionImage,Long> {

    /**
     * Find by action list.
     *
     * @param action the action
     * @return the list
     */
    List<ActionImage> findByAction(Action action);

    /**
     * Find by action and image action image.
     *
     * @param action the action
     * @param image  the image
     * @return the action image
     */
    ActionImage findByActionAndImage(Action action, Image image);

}
