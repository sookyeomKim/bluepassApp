package co.bluepass.security;

import org.springframework.security.core.AuthenticationException;

/**
 * The type User not activated exception.
 */
public class UserNotActivatedException extends AuthenticationException {

    /**
     * Instantiates a new User not activated exception.
     *
     * @param message the message
     */
    public UserNotActivatedException(String message) {
        super(message);
    }

    /**
     * Instantiates a new User not activated exception.
     *
     * @param message the message
     * @param t       the t
     */
    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
