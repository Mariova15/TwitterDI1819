/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import twitter4j.auth.AccessToken;

/**
 *
 * @author Mario
 */
public class User {

    private String screeName, accessToken, accessTokenSecret;
    private long id;

    public User(String accessToken, String accessTokenSecret) {
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }

    public User(String screeName, String accessToken, String accessTokenSecret, long id) {
        this.screeName = screeName;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
        this.id = id;
    }
        
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
        
    public String getScreeName() {
        return screeName;
    }

    public void setScreeName(String screeName) {
        this.screeName = screeName;
    }
        
    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public AccessToken generarAccessToken(){    
        AccessToken token;        
        return token = new AccessToken(accessToken , accessTokenSecret);       
    }
    
}
