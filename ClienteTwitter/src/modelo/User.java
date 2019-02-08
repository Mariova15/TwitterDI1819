/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author alumnop
 */
public class User implements Serializable{
    private String tokens,sceenName,name;

    public User(String tokens, String sceenName, String name) {
        this.tokens = tokens;
        this.sceenName = sceenName;
        this.name = name;
    }

    public User() {
    }

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    public String getSceenName() {
        return sceenName;
    }

    public void setSceenName(String sceenName) {
        this.sceenName = sceenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{ sceenName=" + sceenName + ", name=" + name + '}';
    }

    
}
