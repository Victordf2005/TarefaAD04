
package programacion;

import clases.BaseDatos;
import clases.Cliente;
import clases.HibernateUtil;
import java.util.List;
import java.util.Scanner;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author Víctor Díaz
 */

public class XestionClientes {
    
    // Método para pedir datos do novo cliente
    
    public static void engadirCliente() {
        
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+             ENGADIR CLIENTE               +");        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n\n");
        
        //Pedimos datos do cliente
        String nome = "";
        
        while (nome.equals("")) {
            System.out.println("Teclea o nome do/da cliente/a ('K' para cancelar).\n");
            nome = teclado.nextLine();
        }
        
        if (!nome.equalsIgnoreCase("K")) {
            
            String apelidos = "";
            
            while (apelidos.equals("")) {                
                System.out.println("Teclea os apelidos ('K' para cancelar).\n");
                apelidos = teclado.nextLine();
            }
            
            if (!apelidos.equalsIgnoreCase("K")) {

                String email = "/";

                while (email.equals("/")) {                
                    System.out.println("Teclea o email ('K' para cancelar).\n");
                    email = teclado.nextLine();
                }
                    if (!email.equalsIgnoreCase("K")) {
                        //Pedimos confirmación para engadilo/a
                        System.out.printf("Engadimos %s %s, con email %s, como cliente (S/N)? ", nome, apelidos, email);

                        if (teclado.nextLine().equalsIgnoreCase("S")) {
                            BaseDatos.engadirCliente(nome, apelidos, email);
                        } else {
                            avisarNonEngadido();
                        }
                    } else {
                        avisarNonEngadido();
                    }
      
            } else {
                avisarNonEngadido();
            }
        } else {
            avisarNonEngadido();
        }
     }
    
    private static void avisarNonEngadido() {
        System.out.println(">>>>> O/A cliente/a NON foi engadido/a.");
    }    
    
    // Método que amosa a listaxe de cliente e pide o código do cliente a eliminar
    
    public static void eliminarCliente() {
    
        Scanner teclado = new Scanner(System.in);
                
        System.out.println("+-------------------------------------------+");
        System.out.println("|            ELIMINAR CLIENTE               |");        
        System.out.println("+-------------------------------------------+\n\n");
        
        listarClientes(false);
        
        System.out.println("Teclea o código do/da cliente/a a eliminar (tecleando un código fóra da listaxe ou negativo cancelamos o proceso). ");

        String id = "";
        
        while (!Comuns.eNumerico(id)) {
            id = teclado.nextLine();
        }

        try {
        
            // Buscamos o cliente a eliminar
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            Query consulta = sesion.createQuery("select cl from Clientes cl where cl.id=:i");
            consulta.setParameter("i", Integer.parseInt(id));            
            List<Cliente> clientes = consulta.getResultList();
            
            // Comprobamos se obtemos algún rexistro            
            if (clientes.size()>0) {
                
                System.out.printf("Desexas eliminar o/a cliente/a %s %s (S/N)?", clientes.get(0).getNome(), clientes.get(0).getApelidos());
  
                if (teclado.nextLine().equalsIgnoreCase("S")) {
                    // eliminamos o cliente                   
                    BaseDatos.eliminarCliente(id);
                } else {
                    System.out.println(">>>>> O/A cliente/a non foi eliminado/a.");
                }
                
            }else {                
                System.out.println(">>>>> Non existe ningún/ha cliente/a con código " + id);                
            }
                        
        } catch (Exception erro) {
            System.out.println("Erro buscando o/a cliente a eliminar: " + erro.getMessage());
        }
        
    }
    
    public static void listarClientes(Boolean verCabeceira) {

            
        System.out.println("+===========================================+");
        System.out.println("|             LISTAR CLIENTES               |");        
        System.out.println("+===========================================+\n\n");

        
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            Query consulta = sesion.createQuery("select cl from Cliente cl");
            
            List<Cliente> clientes = consulta.getResultList();
            
            System.out.printf("%9s%2s%-30s%2s%-25s%2s%-20s","CODIGO","","NOME","", "APELIDOS", "", "EMAIL");
            
            for (Cliente cli:clientes) {
                System.out.println("");
                System.out.printf("%9s%2s%-30s%2s%-25s%2s%-20s",cli.getId(), "", cli.getNome(),"", cli.getApelidos(), "", cli.getEmail());
            }

            System.out.println(programacion.Main.FINLISTAXE);
        }
        
        catch(Exception erro){
            System.err.println("Erro listando clientes: " + erro.getMessage());
        }
        
    }
    
        
}
