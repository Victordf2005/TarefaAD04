/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;



/**
 *
 * @author Víctor Díaz
 */

public class HibernateUtil {
    
    private static SessionFactory sesionFactory = null;
    private static Configuracion configuracion = null;
    
    // Método que devolve a sesión para operar coa base de datos    
    public static SessionFactory getSessionFactory(){
     
        // Creamos a sesión se non está configurada
        if(sesionFactory == null) {
            
            if (configuracion == null) {
                cargarConfiguracion();
                
            }
        
            try {
                Configuration conf = new Configuration();
                
                Properties propiedades = new Properties();
                
                //Engadimos as propiedades
                Properties settings = new Properties();
                
                //Indicamos o conector da base de datos que vamos a usar
                settings.put(Environment.DRIVER,configuracion.getHiber().getDriver());
                
                //Indicamos a localización da base de datos que vamos a utilizar
                settings.put(Environment.URL,configuracion.getConexion().getURL());
                
                //Indicamos o usuario da base de datos con cal nos vamos conectar e o seu contrasinal
                settings.put(Environment.USER,configuracion.getConexion().getUser());
                settings.put(Environment.PASS,configuracion.getConexion().getPassword());
                
                //Indicamos o dialecto que ten que usar Hibernate 
                settings.put(Environment.DIALECT,configuracion.getHiber().getDialect());
                
                //Indicamos que se as táboas todas se borren e se volvan crear
                settings.put(Environment.HBM2DDL_AUTO, configuracion.getHiber().getHBM2DDL_AUTO());
                
                //Indicamos que se mostre as operacións SQL que Hibernate leva a cabo
                settings.put(Environment.SHOW_SQL, configuracion.getHiber().getSHOW_SQL());
                conf.setProperties(settings);
                
                //Engaidmos aquelas clases nas que queremos facer persistencia
                conf.addAnnotatedClass(Produto.class);
                conf.addAnnotatedClass(Tenda.class);
                conf.addAnnotatedClass(TendaProduto.class);
                conf.addAnnotatedClass(TendaEmpregado.class);
                conf.addAnnotatedClass(Cliente.class);
                conf.addAnnotatedClass(Empregado.class);
                conf.addAnnotatedClass(Provincia.class);
                
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
                sesionFactory = conf.buildSessionFactory(serviceRegistry);
                
            }
            catch (HibernateException erro) {
                erro.printStackTrace();
            }
        }
        
        return sesionFactory;
    }
    
    private static void cargarConfiguracion() {
                
        //cargar datos do arquivo JSON
        File arquivo = new File("src/main/java/datosJson/config.json");
        
        if (arquivo.exists()) {
            
            //Cargamos os datos almacenados no arquivo json
            try {

                //Fluxo e buffer de entrada para o arquivo
                FileReader fluxoDatos = new FileReader(arquivo);
                BufferedReader entrada = new BufferedReader(fluxoDatos);

                //Lemos o arquivo liña a liña
                StringBuilder jsonBuilder = new StringBuilder();
                String linea;

                while ((linea = entrada.readLine()) != null) {
                    jsonBuilder.append(linea).append("\n");
                }

                //pechamos o arquivo
                entrada.close();

                //construimos o string con todas as liñas lidas
                String json = jsonBuilder.toString();

                //Pasamos o json á clase correspondente
                Gson gson = new Gson();
                configuracion = gson.fromJson(json, Configuracion.class);
                
            } catch (IOException erro) {
                System.out.println("Erro cargando configuración HibernateUtil:\n" + erro.getMessage());
            }
        }
           
    }
    
}
