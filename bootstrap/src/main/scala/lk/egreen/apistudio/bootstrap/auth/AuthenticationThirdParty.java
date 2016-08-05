package lk.egreen.apistudio.bootstrap.auth;

import javax.inject.Singleton;

/**
 * Created by dewmal on 7/19/16.
 */
@Singleton
public class AuthenticationThirdParty {


    public User authentication(String username, String password) {
        User user = new User();
        user.setFirstName(username);
        user.setPassword(password);
        user.getRole().add("ADMIN");
        return user;
    }
}
