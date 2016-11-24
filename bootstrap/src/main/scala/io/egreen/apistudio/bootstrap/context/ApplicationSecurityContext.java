package io.egreen.apistudio.bootstrap.context;

import io.egreen.apistudio.bootstrap.auth.User;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 *
 * Manage
 *
 * Created by dewmal on 7/19/16.
 */
public class ApplicationSecurityContext implements SecurityContext {
    private User user;
    private String scheme;

    public ApplicationSecurityContext(User user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {return this.user;}

    @Override
    public boolean isUserInRole(String s) {
        if (user.getRole() != null) {
            return user.getRole().contains(s);
        }
        return false;
    }

    @Override
    public boolean isSecure() {return "https".equals(this.scheme);}

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}