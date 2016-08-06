package io.egreen.apistudio.bootstrap.auth;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dewmal on 7/19/16.
 */
public class User implements Principal {
    private String id, firstName, lastName, login, email, password;
    private List<String> role;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRole() {
        if (role == null) {
            role = new ArrayList<>();
        }
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    @Override
    public String getName() {
        return this.firstName + " " + this.lastName;
    }
}