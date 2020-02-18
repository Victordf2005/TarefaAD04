
package programacion;

import clases.BaseDatos;
import clases.Empregado;
import clases.HibernateUtil;
import clases.Provincias;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author Víctor Díaz
 */
public class XestionEmpregados {
    
    public static void engadirEmpregado() {
        
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+             ENGADIR EMPREGADO             +");        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n\n");
        
        //Pedimos datos do empregado
        String nome = "";
        
        while (nome.equals("")) {
            System.out.println("Teclea o nome do/da empregado/a ('K' para cancelar).\n");
            nome = teclado.nextLine();
        }
        
        if (!nome.equalsIgnoreCase("K")) {
            
            String apelidos = "";
            
            while (apelidos.equals("")) {                
                System.out.println("Teclea os apelidos ('K' para cancelar).\n");
                apelidos = teclado.nextLine();
            }
            
            if (!apelidos.equalsIgnoreCase("K")) {

                //Pedimos confirmación para engadilo/a
                System.out.printf("Engadimos %s %s como empregado (S/N)? ", nome, apelidos);

                if (teclado.nextLine().equalsIgnoreCase("S")) {
                    
                    BaseDatos.engadirEmpregado(nome, apelidos);
                } else {
                    avisarNonEngadidoE();
                }
      
            } else {
                avisarNonEngadidoE();
            }
        } else {
            avisarNonEngadidoE();
        }
     }
    
    public static void eliminarEmpregado() {
    
        Scanner teclado = new Scanner(System.in);
                
        System.out.println("+-------------------------------------------+");
        System.out.println("|            ELIMINAR EMPREGADO             |");        
        System.out.println("+-------------------------------------------+\n\n");
        
        BaseDatos.listarEmpregados(false);
        
        System.out.println("Teclea o código do/da empregado/a a eliminar (tecleando un código fóra da listaxe ou negativo cancelamos o proceso). ");

        String id = "";
        
        while (!Comuns.eNumerico(id)) {
            id = teclado.nextLine();
        }

        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            Query consulta = sesion.createQuery("select em from Empregado em where em.id=:i");
            consulta.setParameter("i", Integer.parseInt(id));
            List<Empregado> empregados = consulta.getResultList();

            if (empregados.size() > 0) {
                
                System.out.printf("Desexas eliminar o/a empregado/a %s %s (*) (S/N)?", empregados.get(0).getNome(), empregados.get(0).getApelidos());
                System.out.println("\n(*) Tamén serán eliminados os rexistros horarios en cada tenda.");

                if (teclado.nextLine().equalsIgnoreCase("S")) {
                    // eliminamos o empregado                   
                    BaseDatos.eliminarEmpregado(id);
                } else {
                    System.out.println(">>>>> O/A empregado/a non foi eliminado/a.");
                }

            } else {                
                System.out.println(">>>>> Non existe ningún/ha empregado/a con código " + id);
            }
            
        } catch (Exception erro) {
            System.out.println("Erro buscando o/a empregado a eliminar: " + erro.getMessage());
        }
        
    }
    
    public static void rexistroHorasTraballadas() {
            
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+ REXISTRO DE HORAS TRABALLADAS NUNHA TENDA +");        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n\n");
        
        String empregado = seleccionarEmpregado();
        
        if (!empregado.equalsIgnoreCase("K")) {
            
            String tenda = seleccionarTenda();
            
            if (!tenda.equalsIgnoreCase("K")) {  

                String horas = "";

                while (horas.equalsIgnoreCase("")) {
                    System.out.println("Teclea o número de horas traballadas ('K' para cancelar).");
                    horas = teclado.nextLine();                                    
                }

                if (Comuns.eNumerico(horas)) {

                    //Pedimos confirmación para engadila
                    System.out.printf("Rexistramos %s horas do empregado %s na tenda %s (S/N)? ", horas, empregado, tenda);

                    if (teclado.nextLine().equalsIgnoreCase("S")) {
                        //Engadimos o novo produto
                        BaseDatos.rexistrarHorasEmpregado(empregado, tenda, horas);
                    } else {
                        avisarNonEngadidoH(1);
                    }
                } else {
                    avisarNonEngadidoH(6);
                }
            }
        }
    }

    public static void verHorasTraballadas() {
         
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+     VER HORAS TRABALLADAS NUNHA TENDA     +");        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n\n");
        
        String empregado = seleccionarEmpregado();
        
        if (!empregado.equalsIgnoreCase("K")) {
            
            String tenda = seleccionarTenda();
            
            if (!tenda.equalsIgnoreCase("K")) {            
                
                BaseDatos.verHorasTraballadas(empregado, tenda);
            }
        }
    
    }
    
    
    private static String seleccionarEmpregado() {
    
        Scanner teclado = new Scanner(System.in);
        
        BaseDatos.listarEmpregados(false);
        
        String empregado = "";
        
        while (empregado.equals("")) {
            System.out.println("Teclea o código do enpregado que traballou na tenda ('K' para cancelar).\n");
            empregado = teclado.nextLine();
        }
        
        if (!empregado.equalsIgnoreCase("K")) {
            
            if (Comuns.eNumerico(empregado)) {
                
                if (BaseDatos.existeEmpregado(empregado)){
                    
                } else {
                    empregado="K";
                    avisarNonEngadidoH(3);
                }
            } else {
                empregado="K";
                avisarNonEngadidoH(2);
            }
        } else {
            avisarNonEngadidoH(1);
        }
        
        return empregado;
    }
    
    private static String seleccionarTenda() {
    
        
        Scanner teclado = new Scanner(System.in);

        BaseDatos.listarTendas(false);

        String tenda = "";

        while (tenda.equals("")) {
            System.out.println("Teclea o código da tenda na traballou o empregado ('K' para cancelar).\n");
            tenda = teclado.nextLine();
        }

        if (!tenda.equalsIgnoreCase("K")) {

            if (Comuns.eNumerico(tenda)) {
                
                if (BaseDatos.existeTenda(tenda)) {
                    
                } else {
                    tenda ="K";
                    avisarNonEngadidoH(5);
                }
            } else {
                tenda="K";
                avisarNonEngadidoH(4);
            }
        } else {
            avisarNonEngadidoH(1);
        }
        
        return tenda;
    }
    
    
    private static void avisarNonEngadidoE() {        
        System.out.println("Operación cancelada por vostede.");                
        System.out.println(">>>>> O/A empregado/a NON foi engadido/a.");
    } 
    
    private static void avisarNonEngadidoH(int mensaxe) {
        
        switch (mensaxe) {
            case 1:{
                System.out.println("Operación cancelada por vostede.");
                break;
            }
            case 2:{
                System.out.println("O código do empregado debe ser numérico.");
                break;
            }
            case 3: {
                System.out.println("Código de empregado inexistente.");
                break;
            }
            case 4: {
                System.out.println("O código da tenda debe ser numérico.");
                break;
            }
            case 5: {
                System.out.println("O código da tenda non existe.");
                break;
            }
            case 6: {
                System.out.println("As horas traballadas deben ser numéricas.");
                break;
            }
        }
        System.out.println(">>>>> NON se rexistraron as horas traballadas.");
    } 
    
    
}
