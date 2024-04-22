package no.ntnu.dto;



public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String role;
    private boolean twoFactorEnabled;


    public UserRegistrationDto() {

    }

    public UserRegistrationDto(String name, String email, String password, String role, boolean twoFactorEnabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public String GetName() {
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
