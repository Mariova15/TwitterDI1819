/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author alumnop
 */
public class Usuario implements Serializable {

    private String screenName, name;
    private List<Usuario> listaFollowers;
    private List<Usuario> listaFollows;

    public Usuario(String screenName, String name) {
        this.screenName = screenName;
        this.name = name;
    }

    public Usuario(String screenName, List<Usuario> listaFollowers, List<Usuario> listaFollows) {
        this.screenName = screenName;
        this.listaFollowers = listaFollowers;
        this.listaFollows = listaFollows;
    }

    public Usuario() {
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Usuario> getListaFollowers() {
        return listaFollowers;
    }

    public void setListaFollowers(List<Usuario> listaFollowers) {
        this.listaFollowers = listaFollowers;
    }

    public List<Usuario> getListaFollows() {
        return listaFollows;
    }

    public void setListaFollows(List<Usuario> listaFollows) {
        this.listaFollows = listaFollows;
    }

    @Override
    public String toString() {
        return "Usuario{ screenName=" + screenName + ", name=" + name + '}';
    }

}
