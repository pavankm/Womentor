package me.ancyphilip.womentor;

/**
 * Created by ancyphilip on 2/24/18.
 */

public class cards {

    private String userId;
    private String name;
    public cards(String userId, String name){
        this.userId = userId;
        this.name =name;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId (String userId){
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
