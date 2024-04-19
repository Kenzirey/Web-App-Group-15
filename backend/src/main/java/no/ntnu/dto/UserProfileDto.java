package no.ntnu.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import no.ntnu.database.entities.Role;



/**
 * Data Transfer Object for user profile information.
 */
public class UserProfileDto {

    private String username;
    private Set<String> roles;
    private boolean isTwoFactorEnabled;

    public UserProfileDto(String username, Set<Role> roles, boolean isTwoFactorEnabled) {
        this.username = username;
        this.roles = roles.stream().map(Role::getName).collect(Collectors.toSet());
        this.isTwoFactorEnabled = isTwoFactorEnabled;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean isTwoFactorEnabled() {
        return isTwoFactorEnabled;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        isTwoFactorEnabled = twoFactorEnabled;
    }
}