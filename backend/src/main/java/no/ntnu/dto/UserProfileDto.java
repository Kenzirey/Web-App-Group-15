package no.ntnu.dto;

import java.util.Set;
import java.util.stream.Collectors;
import no.ntnu.database.model.Role;

/**
 * Data Transfer Object for the collection of user profile information.
 */
public class UserProfileDto {

    private String username;
    private Set<String> roles;
    private boolean isTwoFactorEnabled;
    private Long id;

    /**
     * Constructs a new user profile data transfer object with the specified details.
     *
     * @param id                    The unique id of the user.
     * @param username              The name of the user.
     * @param roles                 The roles assigned to the user.
     * @param isTwoFactorEnabled    Boolean of whether two factor is enabled.
     */
    public UserProfileDto(Long id, String username,
                          Set<Role> roles, boolean isTwoFactorEnabled) {
        this.id = id;
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