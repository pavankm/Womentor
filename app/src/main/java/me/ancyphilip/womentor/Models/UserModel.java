package me.ancyphilip.womentor.Models;

import java.util.List;

public class UserModel {
//
//    public UserModel(String name, String phone, String email) {
//        this.name = name;
//        this.phone = phone;
//        this.email = email;
//    }

    public UserModel() {
    }

    public UserModel(String name, String phone, String email, String profileImageUrl, String location,
                     String jobTitle, String company, String bio, String facebookUsername, String twitterHandle, String linkedinUsername,
                     List<String> skills) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.location = location;
        this.jobTitle = jobTitle;
        this.company = company;
        this.bio = bio;
        this.facebookUsername = facebookUsername;
        this.twitterHandle = twitterHandle;
        this.linkedinUsername = linkedinUsername;
        this.skills = skills;
    }


    private String name;

    private String phone;

    private String email;
    private String profileImageUrl;
    private String location;
    private String jobTitle;
    private String company;
    private String bio;
    private String facebookUsername;
    private String twitterHandle;
    private String linkedinUsername;
    private List<String> skills;

    private String userId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFacebookUsername() {
        return facebookUsername;
    }

    public void setFacebookUsername(String facebookUsername) {
        this.facebookUsername = facebookUsername;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getLinkedinUsername() {
        return linkedinUsername;
    }

    public void setLinkedinUsername(String linkedinUsername) {
        this.linkedinUsername = linkedinUsername;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
