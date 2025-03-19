package es.us.dp1.lx_xy_24_25.your_game_name.auth.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class SignupRequest {

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;
    
    private String firstName;

    
    private String lastName;

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}