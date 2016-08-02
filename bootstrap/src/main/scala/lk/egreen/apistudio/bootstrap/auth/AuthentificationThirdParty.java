package lk.egreen.apistudio.bootstrap.auth;

import javax.inject.Singleton;

/**
 * Created by dewmal on 7/19/16.
 */
@Singleton
public class AuthentificationThirdParty {







    public User authentification(String username, String password) {
        User user = new User();
        user.setFirstName(username);
        user.setPassword(password);
        user.getRole().add("ADMIN");
        return user;
    }
}
