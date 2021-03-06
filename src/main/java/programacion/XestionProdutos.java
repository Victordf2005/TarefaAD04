
package programacion;

import clases.BaseDatos;
import clases.Cliente;
import clases.HibernateUtil;
import clases.InformeProdutos;
import clases.InformeStock;
import clases.InformeTendas;
import clases.Produto;
import clases.Tenda;
import clases.TendaProduto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author Víctor Díaz
 */

public class XestionProdutos {
    
    // Método que pide datos do novo produto a engadir
    public static void engadirProduto() {
        
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+             ENGADIR PRODUTO               +");        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n\n");
        
        // Pedimos o nome do produto
        String nome = "";
        
        while (nome.equals("")){
            System.out.println("Teclea o nome do produto ('K' para cancelar).\n");
            nome = teclado.nextLine();
        }
        
        if (!nome.equalsIgnoreCase("K")) {
            
            //Pedimos datos do produto
            String descricion = "";

            while (descricion.equals("")) {
                System.out.println("Teclea a descrición do produto ('K' para cancelar).\n");
                descricion = teclado.nextLine();
            }

            if (!descricion.equalsIgnoreCase("K")) {

                String prezo = "";

                while (prezo.equals("") || !Comuns.eNumerico(prezo)) {                
                    System.out.println("Teclea o prezo ('0' para cancelar).\n");
                    prezo = teclado.nextLine();
                }

                if (Float.parseFloat(prezo) != 0.0) {

                    //Pedimos confirmación para engadila
                    System.out.printf("Engadimos o produto %s (%s) cun prezo de %s € (S/N)? ", nome, descricion, prezo);

                    if (teclado.nextLine().equalsIgnoreCase("S")) {
                        //Engadimos o novo produto
                        BaseDatos.engadirProduto(nome, descricion, Double.parseDouble(prezo));
                    } else {
                        avisarNonEngadido(1);
                    }

                } else {
                    avisarNonEngadido(1);
                }
            } else {
                avisarNonEngadido(1);
            }
        } else {
            avisarNonEngadido(1);
        }
     }
    
    // Método que permite engadir un produto a unha tenda
    public static void engadirProdutoTenda() {
    
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+      ENGADIR PRODUTO A UNHA TENDA         +");        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n\n");
        
        String produto = seleccionarProduto();
        
        if (!produto.equalsIgnoreCase("K")) {
            
            String tenda = seleccionarTenda();
            
            if (!tenda.equalsIgnoreCase("K")) {
            
                if (!BaseDatos.existeProdutoTenda(produto, tenda)) {
                    
                    String stock = "";
                    
                    while (stock.equals("")) {                
                        System.out.println("Teclea o stock ('K' para cancelar).\n");
                        stock = teclado.nextLine();
                    }

                    if (!stock.equalsIgnoreCase("K")) {
                        
                        if (Comuns.eNumerico(stock)) {

                            //Pedimos confirmación para engadilo
                            System.out.printf("Engadimos o produto %s á tenda %s cun stock de %s unidades (S/N)? ", produto, tenda, stock);

                            if (teclado.nextLine().equalsIgnoreCase("S")) {
                                //Engadimos o novo produto
                                BaseDatos.engadirProdutoTenda(produto, tenda, stock);
                            } else {
                                avisarNonEngadido(1);
                            }
                        }else{
                            avisarNonEngadido(6);
                        }

                    } else {
                        avisarNonEngadido(1);
                    }
                } else {
                    avisarNonEngadido(7);
                }
            }
        }        
     }
    
    // Método que permite seleccionar un produto da listaxe que amosa
    private static String seleccionarProduto() {
    
        Scanner teclado = new Scanner(System.in);
        
        listarProdutos(false);
        
        String produto = "";
        
        while (produto.equals("")) {
            System.out.println("Teclea o código do produto a engadir á tenda ('K' para cancelar).\n");
            produto = teclado.nextLine();
        }
        
        if (!produto.equalsIgnoreCase("K")) {
            
            if (Comuns.eNumerico(produto)) {
                
                if (BaseDatos.existeProduto(produto)){
                    
                } else {
                    produto="K";
                    avisarNonEngadido(3);
                }
            } else {
                produto="K";
                avisarNonEngadido(2);
            }
        } else {
            avisarNonEngadido(1);
        }
        
        return produto;
    }
    
    // Método que permite seleccionar unha tenda da listaxe que amosa
    private static String seleccionarTenda() {
            
        Scanner teclado = new Scanner(System.in);

        BaseDatos.listarTendas(false);

        String tenda = "";

        while (tenda.equals("")) {
            System.out.println("Teclea o código da tenda na que engadir o produto ('K' para cancelar).\n");
            tenda = teclado.nextLine();
        }

        if (!tenda.equalsIgnoreCase("K")) {

            if (Comuns.eNumerico(tenda)) {
                
                if (BaseDatos.existeTenda(tenda)) {
                    
                } else {
                    tenda ="K";
                    avisarNonEngadido(5);
                }
            } else {
                tenda="K";
                avisarNonEngadido(4);
            }
        } else {
            avisarNonEngadido(1);
        }
        
        return tenda;
    }
           
    // Método para eliminar un produto a través do seu id
    public static void eliminarProduto() {
    
        Scanner teclado = new Scanner(System.in);
                
        System.out.println("+-------------------------------------------+");
        System.out.println("|            ELIMINAR PRODUTO               |");        
        System.out.println("+-------------------------------------------+\n\n");
        
        listarProdutos(false);
                
        System.out.println("Teclea o nº do produto a eliminar (tecleando un nº fóra da lista ou negativo cancelamos o proceso). ");

        String id = "";
        
        while (!Comuns.eNumerico(id)) {
            id = teclado.nextLine();
        }

        try {
            
            // Buscamos o produto a eliminar
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            Query consulta = sesion.createQuery("select pr from Produto pr where pr.id=:i");
            consulta.setParameter("i", Integer.parseInt(id));
            List<Produto> produtos = consulta.getResultList();
            
            // Comprobamos se obtemos algún rexistro
                                    
            if (produtos.size() > 0) {
                System.out.printf("Desexas eliminar o produto %s da franquicia (*) (S/N)?", produtos.get(0).getNome());
                System.out.println("\n(*) Tamén será eliminado este produto en cada tenda.");

                if (teclado.nextLine().equalsIgnoreCase("S")) {
                    // eliminamos o produto                   
                    BaseDatos.eliminarProduto(id);
                } else {
                    System.out.println(">>>>> O produto non foi eliminado.");
                }

            } else {                
                System.out.println(">>>>> Non existe ningún produto con código " + id);
            }
            
        } catch (Exception erro) {
            System.out.println("Erro buscando o produto a eliminar: " + erro.getMessage());
        }
        
    }
    
    public static void listarProdutos(Boolean verCabeceira) {

        if (verCabeceira) {
            
            System.out.println("+===========================================+");
            System.out.println("|        LISTAXE DE PRODUTOS                |");        
            System.out.println("+===========================================+\n\n");

        } else {        
            System.out.println("Listaxe de produtos da franquicia:\n");
        }
                
        try {           
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            Query consulta = sesion.createQuery("select pr from Produto pr");
            List<Produto> produtos = consulta.getResultList();
            
            System.out.printf("\n%9s%2s%-30s%2s%-100s%9s","CODIGO","","NOME","", "DESCRICION", "PREZO");

            for(Produto pr:produtos) {
                System.out.println("");
                System.out.printf("%9s%2s%-30s%2s%-100s%2s%7.2f",pr.getId(),"", pr.getNome(),"",pr.getDescricion(),"",pr.getPrezo());
            }
            System.out.println("");
        }
        catch(Exception erro){
            System.err.println("Erro listando produtos: " + erro.getMessage());
        }
    }
    
    // Método para eliminar un produto dunha tenda
    public static void eliminarProdutoTenda(){
                
        Scanner teclado = new Scanner(System.in);
                
        System.out.println("+-------------------------------------------+");
        System.out.println("|       ELIMINAR PRODUTO DUNHA TENDA        |");        
        System.out.println("+-------------------------------------------+\n\n");
        
        BaseDatos.listarProdutosTenda(false);
                
        System.out.println("Teclea o CODIGO_ID do produto a eliminar (tecleando un nº fóra da lista ou negativo cancelamos o proceso). ");
                
        String id = "";
        
        while (!Comuns.eNumerico(id)) {
            id = teclado.nextLine();
        }

        try {
            // Buscamos o produto a eliminar
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            Query consulta = sesion.createQuery("select tp from TendaProduto tp where tp.id=:i");
            consulta.setParameter("i",Integer.parseInt(id));
            List<TendaProduto> tendaProdutos = consulta.getResultList();

            // Comprobamos se obtemos algún rexistro
            
            if (tendaProdutos.size() > 0) {
                System.out.printf("Desexas eliminar o produto con código %s (%s), con %s unidades de stock, da tenda %s, de %s (S/N)?",
                        tendaProdutos.get(0).getId(), tendaProdutos.get(0).getProduto().getNome(),
                        tendaProdutos.get(0).getStock(),
                        tendaProdutos.get(0).getTenda().getNome(), tendaProdutos.get(0).getTenda().getCidade());

                if (teclado.nextLine().equalsIgnoreCase("S")) {
                    // eliminamos o produto                   
                    BaseDatos.eliminarProdutoTenda(id);
                } else {
                    System.out.println(">>>>> O produto non foi eliminado.");
                }

            } else {                
                System.out.println(">>>>> Non existe ningún rexistro de produtos da tenda con CODIGO_ID " + id);
            }
            
        } catch (Exception erro) {
            System.out.println("Erro buscando o produto a eliminar: " + erro.getMessage());
        }
        
    }
    
    
    public static void verStockProdutoTenda(){
                
        Scanner teclado = new Scanner(System.in);
                
        System.out.println("+-------------------------------------------+");
        System.out.println("|   VER STOCK DUN PRODUTO DUNHA TENDA       |");        
        System.out.println("+-------------------------------------------+\n\n");
        
        String produto = seleccionarProduto();
        
        if (!produto.equalsIgnoreCase("K")) {
            
            String tenda = seleccionarTenda();
            
            if (!tenda.equalsIgnoreCase("K")) {
                
                BaseDatos.listarProdutoTenda(produto, tenda);
                
            }
        }       
    }
    
    // Método que xera un informe en formato json con todos os produtos de todas as tendas
    public static void xerarInformeStockCompleto(){
        
        try {
            
            File arquivo = new File("src/main/java/datosJson/informeStock.json");
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            // Buscamos só as tendas que teñan produtos
            Query consulta = sesion.createQuery("select tp.tenda from TendaProduto tp group by tp.tenda");
            List<Tenda> tendasConProdutos = consulta.getResultList();
            
            ArrayList<InformeTendas> informeTendas = new ArrayList<InformeTendas>();
            
            for (Tenda tcp:tendasConProdutos) {
                
                ArrayList<InformeProdutos> produtosTenda = new ArrayList<InformeProdutos>();
                
                try {
                    
                    // Por cada tenda, buscamos os seus produtos
                    
                    Session sesion2 = HibernateUtil.getSessionFactory().openSession();
                    Query consulta2 = sesion.createQuery("select tp from TendaProduto tp where tp.tenda=:t order by produto");
                    consulta2.setParameter("t", tcp);
                    List<TendaProduto> tendaProdutos = consulta2.getResultList();
                    
                    for (TendaProduto tp:tendaProdutos) {
                                                                        
                        InformeProdutos ip = new InformeProdutos(tp.getProduto().getId(),
                                tp.getProduto().getNome(),
                                tp.getProduto().getDescricion(),
                                tp.getProduto().getPrezo(),
                                tp.getStock());
                    
                        // Engadimos o produto á listaxe de produtos da tenda
                        produtosTenda.add(ip);
                    }
                    
                    if (produtosTenda.size() > 0) {
                        // Engadimos a listaxe de produtos da tenda (se ten) ao informe
                        InformeTendas it = new InformeTendas(tcp.getId(), tcp.getNome(), tcp.getCidade(), tcp.getProvincia().getNome(), produtosTenda);
                        informeTendas.add(it);
                    }
                }

                catch (Exception erro) {
                    System.out.println("Erro buscando produtos da tenda.");
                }
            }
            
            InformeStock is = new InformeStock(informeTendas);
            
            // Gardamos o informe en formato json
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(is);
                        
            try {
                FileWriter fluxoSaida = new FileWriter(arquivo);
                BufferedWriter saida = new BufferedWriter(fluxoSaida);
                saida.write(json);
                saida.close();
                
                System.out.println("\n>>>>>> INFORME XERADO CORRECTAMENTE");
                
            }
            catch (IOException erro) {
                System.out.println("Erro gardando o informe json: " + erro.getMessage());
            }
            
        }
        
        catch (Exception erro) {
            System.out.println("Erro xerando o informe: " + erro.getMessage());
        }
        
                    
                
    }
    
    // Método para actualizar o stock dun produto nunha tenda
    public static void actualizarStockProdutoTenda() {
    
        Scanner teclado = new Scanner(System.in);        
           
        System.out.println("+-------------------------------------------+");
        System.out.println("| ACTUALIZAR STOCK DUN PRODUTO DUNHA TENDA  |");        
        System.out.println("+-------------------------------------------+\n\n");
        
        BaseDatos.listarProdutosTenda(false);
        
        String id = "";
        
        while (id.equals("")) {
            System.out.println("Teclea o CODIGO_ID do rexistro de produtos da tenda a actualizarlle o stock ('K' para cancelar).");
            id = teclado.nextLine();
        }
        
        if (BaseDatos.existeProdutoTenda(id)) {
            if (!id.equalsIgnoreCase("K")) {

                String stock = "";

                while (stock.equals("")) {                
                    System.out.println("Teclea o novo stock ('K' para cancelar).\n");
                    stock = teclado.nextLine();
                }

                if (!stock.equalsIgnoreCase("K")) {

                    if (Comuns.eNumerico(stock)) {

                        //Pedimos confirmación para engadila
                        System.out.printf("Actualizamos o stock do rexistro de produtos da tenda con CODIGO_ID %s a %s unidades (S/N)? ", id, stock);

                        if (teclado.nextLine().equalsIgnoreCase("S")) {
                            //Engadimos o novo produto
                            BaseDatos.actualizarStock(id, stock);
                        } else {
                            avisarNonActualizado(1);
                        }

                    } else {
                        avisarNonActualizado(2);
                    }

                } else {
                    avisarNonActualizado(1);                
                }

            } else {
                avisarNonActualizado(1);
            }
        }else {
            avisarNonActualizado(3);            
        }
        
    }
        
    private static void avisarNonEngadido(int mensaxe) {
        
        switch (mensaxe) {
            case 1:{
                System.out.println("Operación cancelada por vostede.");
                break;
            }
            case 2:{
                System.out.println("O código do produto debe ser numérico.");
                break;
            }
            case 3: {
                System.out.println("Código de produto inexistente.");
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
                System.out.println("O stock debe ser numérico. Deixar a 0 se non hai.");
                break;
            }
            case 7: {
                System.out.println("Este produto xa está de alta nesta tenda.");
                break;
            }
            case 8:{
                System.out.println("O prezo do produto debe ser numérico.");
                break;
            }        
        }
        System.out.println(">>>>> O produto NON foi engadido.");
    } 
    
       
    private static void avisarNonActualizado(int mensaxe) {
        
        switch (mensaxe) {
            case 1:{
                System.out.println("Operación cancelada por vostede.");
                break;
            }
            case 2:{
                System.out.println("O stock do produto debe ser numérico.");
                break;
            }
            case 3: {
                System.out.println("O código introducido non corresponde con ningún rexistro.");
                break;
            }
        }
        System.out.println(">>>>> O stock NON foi actualizado.");
    } 
    
}
