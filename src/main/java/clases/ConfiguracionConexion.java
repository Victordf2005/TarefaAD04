/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;

/**
 *
 * @author Víctor Díaz
 */

public class ConfiguracionConexion implements Serializable {
    
    private String address="";
    private String port="";
    private String name="";
    private String user="";
    private String password="";
    
    public ConfiguracionConexion(){}
    
    public ConfiguracionConexion(String _address, String _port, String _name, String _user, String _password) {
        this.address = _address;
        this.port = _port;
        this.name = _name;
        this.user = _user;
        this.password = _password;
    }
        
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getURL() {
        return "jdbc:mysql://"
                + this.address + ":"
                + this.port + "/"
                + this.name;
    }
}
