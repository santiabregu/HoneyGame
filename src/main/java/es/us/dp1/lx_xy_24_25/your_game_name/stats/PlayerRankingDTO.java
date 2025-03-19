package es.us.dp1.lx_xy_24_25.your_game_name.stats;

public class PlayerRankingDTO {
    private String username;
    private String profilePhoto; // Ruta al avatar del jugador
    private Integer totalPoints;

    // Constructor
    public PlayerRankingDTO(String username, String profilePhoto, Integer totalPoints) {
        this.username = username;
        this.profilePhoto = profilePhoto;
        this.totalPoints = totalPoints;
    }

    // Getters y Setters
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

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
    
}
