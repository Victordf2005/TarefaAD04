/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Víctor Díaz
 */

public class Configuracion {
    
    private  ConfiguracionConexion dbConnection = null;
    private  ConfiguracionHibernate hibernate = null;

    public Configuracion() {
    }

    
    public Configuracion(ConfiguracionConexion dbConnection, ConfiguracionHibernate hibernate) {
        this.dbConnection = dbConnection;
        this.hibernate = hibernate;
    }

    public  ConfiguracionConexion getConexion() {
        return dbConnection;
    }

    public  void setConexion(ConfiguracionConexion dbConnection) {
        this.dbConnection = dbConnection;
    }

    public  ConfiguracionHibernate getHiber() {
        return hibernate;
    }

    public  void setHiber(ConfiguracionHibernate hibernate) {
        this.hibernate = hibernate;
    }
    
}
