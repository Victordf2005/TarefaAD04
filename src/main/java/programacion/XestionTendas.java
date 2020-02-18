package programacion;


import clases.BaseDatos;
import clases.HibernateUtil;
import clases.Tenda;
import java.util.List;
import java.util.Scanner;
import javax.persistence.Query;
import org.hibernate.Session;



/**
 *
 * @author Víctor Díaz
 */

public class XestionTendas {
    
    // Método que pide os datos da nova tenda a engadir
    public static void engadirTenda() {
        
        Scanner teclado = new Scanner(System.in);
                
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+             ENGADIR TENDA                 +");        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n\n");
        
        //Pedimos datos da tenda
        String tendaNome = "";
        
        while (tendaNome.equals("")) {
            System.out.println("Teclea o nome da tenda ('K' para cancelar).\n");
            tendaNome = teclado.nextLine();
        }
        
        if (!tendaNome.equalsIgnoreCase("K")) {
            
            String codProv = seleccionarProvincia();

            if (!codProv.equalsIgnoreCase("K")) {

                String tendaCidade = "";

                while (tendaCidade.equals("")) {
                    System.out.println("Teclea o nome da cidade na que se ubica ('K' para cancelar.\n");
                    tendaCidade = teclado.nextLine();
                }                

                if (!tendaNome.equalsIgnoreCase("K")) {

                    //Pedimos confirmación para engadila
                    System.out.printf("Engadimos a tenda %s, sita en %s, á franquicia (S/N)? ", tendaNome, tendaCidade);

                    if (teclado.nextLine().equalsIgnoreCase("S")) {
                        //Engadimos a nova tenda
                        BaseDatos.engadirTenda(tendaNome, codProv, tendaCidade);
                    } else {
                        avisarNonEngadida();
                    }
                } else {
                    avisarNonEngadida();
                }
            } else {
                avisarNonEngadida();
            }
        } else {
            avisarNonEngadida();
        }
 
    }
    
    // Método que pide o Id da tenda a eliminar
    public static void eliminarTenda() {
    
        Scanner teclado = new Scanner(System.in);
                
        System.out.println("+-------------------------------------------+");
        System.out.println("|            ELIMINAR TENDA                 |");        
        System.out.println("+-------------------------------------------+\n\n");
        
        BaseDatos.listarTendas(false);

        System.out.println("Teclea o código do/da tenda/a a eliminar (tecleando un código fóra da listaxe ou negativo cancelamos o proceso). ");

        String id = "";
        
        while (!Comuns.eNumerico(id)) {
            id = teclado.nextLine();
        }

        try {
            
            // Buscamos a tenda a eliminar
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            Query consulta = sesion.createQuery("select te from Tenda te where te.id=:i");
            consulta.setParameter("i", Integer.parseInt(id));
            List<Tenda> tendas = consulta.getResultList();
                        
            // Comprobamos que obtemos algún rexistro
            
            if (tendas.size() > 0) {
                
                System.out.printf("Desexas eliminar a tenda %s de %s (*) (S/N)?", tendas.get(0).getNome(), tendas.get(0).getCidade());
                System.out.println("\n(*) Tamén serán eliminados os rexistros de produtos e horas de traballadores desa tenda.");

                if (teclado.nextLine().equalsIgnoreCase("S")) {
                    // eliminamos a tenda
                    BaseDatos.eliminarTenda(id);
                } else {
                    System.out.println(">>>>> A tenda non foi eliminada.");
                }

            } else {                
                System.out.println(">>>>> Non existe ningunha tenda con código " + id);
            }
            
        } catch (Exception erro) {
            System.out.println("Erro buscando a tenda a eliminar: " + erro.getMessage());
        }
        
    }
        
    private static void avisarNonEngadida() {
        System.out.println(">>>>> A tenda non foi engadida.");
    }
    
    // Método que permite seleccionar a provincia na que radica a tenda
    private static String seleccionarProvincia() {
        
        Scanner teclado = new Scanner(System.in);

        BaseDatos.listarProvincias();
        String provincia = "";
        
        while (provincia.equals("")) {
            System.out.println("Teclea o código da provincia ('K' para cancelar).\n");
            provincia = teclado.nextLine();
        }
        
        if (!provincia.equalsIgnoreCase("K")) {
            
            if (Comuns.eNumerico(provincia)) {
                
                if (BaseDatos.existeProvincia(provincia)){
                    
                } else {
                    provincia="K";
                    System.out.println("Código de provincia inexistente.");
                }
            } else {
                provincia="K";
                System.out.println("O código da provincia debe ser numérico.");
            }
        } else {
            System.out.println("Operación cancelada por vostede.");
        }
        
        return provincia;
        
    }
    
    
}
