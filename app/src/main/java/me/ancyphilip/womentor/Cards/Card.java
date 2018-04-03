package me.ancyphilip.womentor.Cards;

import java.util.List;

/**
 * Created by ancyphilip on 2/24/18.
 */

public class Card {

    private String userId;
    private String name;
    private String profileImageUrl;

    private String location;
    private String jobTitle;
    private String company;
    private List<String> skills;


    public Card(String userId, String name, String profileImageUrl, String location, String jobTitle,
                String company, List<String> skills) {
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.location = location;
        this.jobTitle = jobTitle;
        this.company = company;
        this.skills = skills;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
