package co.bluepass.web.rest.jsonview;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * The interface Json views.
 */
public @interface JsonViews {
    /**
     * Value json view [ ].
     *
     * @return the json view [ ]
     */
    JsonView[] value();
}
