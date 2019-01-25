/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Mario
 */
public class User {

    private String screeName, accessToken, accessTokenSecret;

    public User(String screeName, String accessToken, String accessTokenSecret) {
        this.screeName = screeName;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }

    public String getScreeName() {
        return screeName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }
        
}
