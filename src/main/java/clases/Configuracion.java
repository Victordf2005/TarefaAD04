/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author clatvdf
 */
public class Configuracion {
    
    private  ConfiguracionConexion dbConnection = null;
    private  ConfiguracionHibernate hibernate = null;

    public Configuracion() {
    }

    
    public Configuracion(ConfiguracionConexion conexion, ConfiguracionHibernate hiber) {
        this.dbConnection = conexion;
        this.hibernate = hiber;
    }

    public  ConfiguracionConexion getConexion() {
        return dbConnection;
    }

    public  void setConexion(ConfiguracionConexion conexion) {
        this.dbConnection = conexion;
    }

    public  ConfiguracionHibernate getHiber() {
        return hibernate;
    }

    public  void setHiber(ConfiguracionHibernate hiber) {
        this.hibernate = hiber;
    }
    
}
