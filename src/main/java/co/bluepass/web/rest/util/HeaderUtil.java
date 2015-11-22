package co.bluepass.web.rest.util;

import org.springframework.http.HttpHeaders;

/**
 * The type Header util.
 */
public class HeaderUtil {

    /**
     * Create alert http headers.
     *
     * @param message the message
     * @param param   the param
     * @return the http headers
     */
    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-petizenApp-alert", message);
        headers.add("X-petizenApp-params", param);
        return headers;
    }

    /**
     * Create entity creation alert http headers.
     *
     * @param entityName the entity name
     * @param param      the param
     * @return the http headers
     */
    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("petizenApp." + entityName + ".created", param);
    }

    /**
     * Create entity update alert http headers.
     *
     * @param entityName the entity name
     * @param param      the param
     * @return the http headers
     */
    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("petizenApp." + entityName + ".updated", param);
    }

    /**
     * Create entity deletion alert http headers.
     *
     * @param entityName the entity name
     * @param param      the param
     * @return the http headers
     */
    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("petizenApp." + entityName + ".deleted", param);
    }

}
