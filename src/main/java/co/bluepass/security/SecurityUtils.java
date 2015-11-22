package co.bluepass.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import co.bluepass.domain.User;

import java.util.Collection;

/**
 * The type Security utils.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Gets current login.
     *
     * @return the current login
     */
    public static String getCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails springSecurityUser = null;
        String userName = null;
        if(authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    /**
     * Is authenticated boolean.
     *
     * @return the boolean
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Is user in role boolean.
     *
     * @param role the role
     * @return the boolean
     */
    public static boolean isUserInRole(String role) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if(authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                return springSecurityUser.getAuthorities().contains(new SimpleGrantedAuthority(role));
            }
        }
        return false;
    }


    /**
     * Gets current user details.
     *
     * @return the current user details
     */
    public static UserDetails getCurrentUserDetails() {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	UserDetails userDetails = null;
    	if("anonymousUser".equals(principal)) {
    		principal = null;
    	} else if(principal instanceof UserDetails) {
    		userDetails = (UserDetails) principal;
    	}
    	return userDetails;
    }

}
