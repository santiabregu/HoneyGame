package es.us.dp1.lx_xy_24_25.your_game_name.player;

public class PlayerDTO {
    private String firstname;
    private String lastname;
    private String username;
    private String profilePhoto;

    public PlayerDTO(){}

    public PlayerDTO(String firstname, String lastname, String username, String profilePhoto) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.profilePhoto = profilePhoto;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}