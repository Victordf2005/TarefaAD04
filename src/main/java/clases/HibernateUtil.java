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
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


/**
 *
 * @author clatvdf
 */
public class HibernateUtil {
    
    private static SessionFactory sesionFactory = null;
    private static Configuracion configuracion = null;
    
    // Método que devolve a sesión para operar coa base de datos    
    public static SessionFactory getSessionFactory(){
     
        // Creamos a sesión se non está configurada
        if(sesionFactory == null) {
            
            if (configuracion == null) {
                cargarConfiguracion(configuracion);
            }
        
            try {
                Configuration conf = new Configuration();
                
                Properties propiedades = new Properties();
                
                /*propiedades.put(Environment.DRIVER,configuracion.getHiber().getDriver());
                                
                propiedades.put(Environment.URL,configuracion.getConexion().getURL());
                
                propiedades.put(Environment.USER,configuracion.getConexion().getUser());
                propiedades.put(Environment.PASS,configuracion.getConexion().getPassword());
                
                propiedades.put(Environment.DIALECT,configuracion.getHiber().getDialect());
                
                propiedades.put(Environment.HBM2DDL_AUTO,configuracion.getHiber().getHBM2DDL_AUTO());
                
                propiedades.put(Environment.SHOW_SQL,configuracion.getHiber().getSHOW_SQL());*/
                
                /*propiedades.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");                
                propiedades.put(Environment.URL,"jdbc:mysql://192.168.56.101:3036/hibernate");
                propiedades.put(Environment.USER,"userhibernate");
                propiedades.put(Environment.PASS,"abc123.");                
                propiedades.put(Environment.DIALECT,"org.hibernate.dialect.MySQL5Dialect");                
                propiedades.put(Environment.HBM2DDL_AUTO,"update");                
                propiedades.put(Environment.SHOW_SQL,true);
                
                conf.setProperties(propiedades);
                
                conf.addAnnotatedClass(Produto.class);
                conf.addAnnotatedClass(Tenda.class);
                conf.addAnnotatedClass(TendaProduto.class);
                conf.addAnnotatedClass(Cliente.class);
                conf.addAnnotatedClass(Empregado.class);
                conf.addAnnotatedClass(Provincia.class);
                conf.addAnnotatedClass(TendaEmpregado.class);
                
                
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
                sesionFactory = conf.buildSessionFactory(serviceRegistry);*/
                
                
                //Engadimos as propiedades
                Properties settings = new Properties();
                
                //Indicamos o conector da base de datos que vamos a usar
                settings.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
                
                //Indicamos a localización da base de datos que vamos a utilizar
                settings.put(Environment.URL,"jdbc:mysql://192.168.56.101:3306/hibernate");
                
                //Indicamos o usuario da base de datos con cal nos vamos conectar e o seu contrasinal
                settings.put(Environment.USER,"userhibernate");
                settings.put(Environment.PASS,"abc123.");
                
                //Indicamos o dialecto que ten que usar Hibernate 
                settings.put(Environment.DIALECT,"org.hibernate.dialect.MySQL5Dialect");
                
                //Indicamos que se as táboas todas se borren e se volvan crear
                settings.put(Environment.HBM2DDL_AUTO, "update");
                
                //Indicamos que se mostre as operacións SQL que Hibernate leva a cabo
                settings.put(Environment.SHOW_SQL, "true");
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
    
    private static void cargarConfiguracion(Configuracion configuracion) {
                
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
                System.out.println("Erro cargando listaxe de provincias:\n" + erro.getMessage());
            }
        }
           
    }
    
}
