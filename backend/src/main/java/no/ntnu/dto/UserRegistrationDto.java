package no.ntnu.dto;


/**
 * Represents the data transfer object (DTO) for user registration.
 */
public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String role;
    private boolean twoFactorEnabled;


    /**
     * Default no-argument constructor.
     */
    public UserRegistrationDto() {

    }

    /**
     * Constructs a DTO of user registration with specified details.
     *
     * @param name              The name of the user.
     * @param email             The email address of the user.
     * @param password          The password of the user.
     * @param role              The user's role.
     * @param twoFactorEnabled  Boolean value whether two factor is enabled.
     */
    public UserRegistrationDto(String name, String email,
                               String password, String role,
                               boolean twoFactorEnabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

}
