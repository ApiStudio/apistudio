package io.egreen.apistudio.bootstrap.filter;
//
//
//import com.myapp.security.MyApplicationSecurityContext;
//import com.myapp.bean.User;

import io.egreen.apistudio.bootstrap.auth.AuthenticationThirdParty;
import io.egreen.apistudio.bootstrap.auth.BasicAuth;
import io.egreen.apistudio.bootstrap.auth.User;
import io.egreen.apistudio.bootstrap.context.ApplicationSecurityContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Jersey HTTP Basic Auth filter
 *
 * @author Deisss (LGPLv3)
 */
@Priority(1)
@Provider
public class AuthFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
            .entity("You cannot access this resource").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
            .entity("Access blocked for all users !!").build();

    @Context
    private ResourceInfo resourceInfo;


    @Inject
    private AuthenticationThirdParty authenticationThirdParty;


    /**
     * Apply the filter : check input request, validate or not with user auth
     *
     * @param containerRequest The request from Tomcat server
     */
    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
//containerRequest.

        Method resourceMethod = resourceInfo.getResourceMethod();
//        LOGGER.info(resourceMethod.isAnnotationPresent(RolesAllowed.class));
        if (resourceMethod.isAnnotationPresent(PermitAll.class)) {

        } else if (resourceMethod.isAnnotationPresent(DenyAll.class)) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        } else if (resourceMethod.isAnnotationPresent(RolesAllowed.class)) {
            //GET, POST, PUT, DELETE, ...
            String method = containerRequest.getMethod();
            // myresource/get/56bCA for example
            String path = containerRequest.getUriInfo().getPath(true);
//            LOGGER.info(path);
            //We do allow wadl to be retrieve
            if (method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))) {
                return;
            }

            //Get the authentication passed in HTTP headers parameters
            String auth = containerRequest.getHeaderString("authorization");
            //If the user does not have the right (does not provide any HTTP Basic Auth)
            if (auth == null) {
                throw new WebApplicationException(Status.UNAUTHORIZED);
            }

            //lap : loginAndPassword
            String[] lap = BasicAuth.decode(auth);
//        LOGGER.info(lap);

            //If login or password fail
            if (lap == null || lap.length != 2) {
                throw new WebApplicationException(Status.UNAUTHORIZED);
            }
//        LOGGER.info(lap[0] + " " + lap[1]);
            RolesAllowed rolesAnnotation = resourceMethod.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
            //DO YOUR DATABASE CHECK HERE (replace that line behind)...
            User authenticationResult = authenticationThirdParty.authentication(lap[0], lap[1], rolesSet);

            //Our system refuse login and password
            if (authenticationResult == null) {
                throw new WebApplicationException(Status.UNAUTHORIZED);
            }

            // We configure your Security Context here
            String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
            containerRequest.setSecurityContext(new ApplicationSecurityContext(authenticationResult, scheme));
        }
        //TODO : HERE YOU SHOULD ADD PARAMETER TO REQUEST, TO REMEMBER USER ON YOUR REST SERVICE...
    }

}
