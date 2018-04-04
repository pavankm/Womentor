package me.ancyphilip.womentor.Chat;

/**
 * Created by ancyphilip on 2/25/18.
 */

public class ChatObject {


    private String message;
    private Boolean currentUser;
    private String createdBy;
    private String profileImageUrl;


    public ChatObject(String message, Boolean currentUser, String createdBy, String profileImageUrl) {
        this.message = message;
        this.currentUser = currentUser;
        this.createdBy = createdBy;
        this.profileImageUrl = profileImageUrl;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Boolean currentUser) {
        this.currentUser = currentUser;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
