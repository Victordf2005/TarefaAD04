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

public class ConfiguracionHibernate implements Serializable {
    
    private  String driver = "";
    private  String dialect = "";
    private  String HBM2DDL_AUTO = "";
    private  Boolean SHOW_SQL = false;
    
    public ConfiguracionHibernate() {}
    
    public ConfiguracionHibernate(String driver, String dialect, String HBM2DDL_AUTO, Boolean SHOW_SQL) {
        this.driver = driver;
        this.dialect = dialect;
        this.HBM2DDL_AUTO = HBM2DDL_AUTO;
        this.SHOW_SQL = SHOW_SQL;
    }

    public  String getDriver() {
        return this.driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public  String getDialect() {
        return this.dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public  String getHBM2DDL_AUTO() {
        return this.HBM2DDL_AUTO;
    }

    public void setHBM2DDL_AUTO(String HBM2DDL_AUTO) {
        this.HBM2DDL_AUTO = HBM2DDL_AUTO;
    }

    public  Boolean getSHOW_SQL() {
        return this.SHOW_SQL;
    }

    public void setSHOW_SQL(Boolean SHOW_SQL) {
        this.SHOW_SQL = SHOW_SQL;
    }
    
    
}
