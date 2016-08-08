package io.egreen.apistudio.bootstrap.auth;

import javax.inject.Singleton;
import java.util.Set;

/**
 * Created by dewmal on 7/19/16.
 */
@Singleton
public class AuthenticationThirdParty {


    public User authentication(String username, String password, Set<String> rolesSet) {
        User user = new User();
        user.setFirstName(username);
        user.setPassword(password);
        user.getRole().add("ADMIN");

        for (String allowedUserRoles : user.getRole()) {
            if (rolesSet.contains(allowedUserRoles)) {
                return user;
            }
        }
        //Step 2. Verify user role

        return null;
    }
}
