package lk.egreen.apistudio.bootstrap.filter;
//
//
//import com.myapp.security.MyApplicationSecurityContext;
//import com.myapp.bean.User;

import lk.egreen.apistudio.bootstrap.auth.AuthentificationThirdParty;
import lk.egreen.apistudio.bootstrap.auth.BasicAuth;
import lk.egreen.apistudio.bootstrap.auth.User;
import lk.egreen.apistudio.bootstrap.context.ApplicationSecurityContext;
import lk.egreen.apistudio.bootstrap.externalsource.database.CassandraDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

/**
 * Jersey HTTP Basic Auth filter
 *
 * @author Deisss (LGPLv3)
 */
@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);




    @Inject
    private AuthentificationThirdParty authentificationThirdParty;


    /**
     * Apply the filter : check input request, validate or not with user auth
     *
     * @param containerRequest The request from Tomcat server
     */
    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {


        //GET, POST, PUT, DELETE, ...
        String method = containerRequest.getMethod();
        // myresource/get/56bCA for example
        String path = containerRequest.getUriInfo().getPath(true);
//        LOGGER.info(path);
        //We do allow wadl to be retrieve
        if (method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))) {
            return;
        }

        //Get the authentification passed in HTTP headers parameters
        String auth = containerRequest.getHeaderString("authorization");
//        LOGGER.info(auth);
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

        //DO YOUR DATABASE CHECK HERE (replace that line behind)...
        User authentificationResult = authentificationThirdParty.authentification(lap[0], lap[1]);

        //Our system refuse login and password
        if (authentificationResult == null) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        // We configure your Security Context here
        String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
        containerRequest.setSecurityContext(new ApplicationSecurityContext(authentificationResult, scheme));

        //TODO : HERE YOU SHOULD ADD PARAMETER TO REQUEST, TO REMEMBER USER ON YOUR REST SERVICE...
    }
}
