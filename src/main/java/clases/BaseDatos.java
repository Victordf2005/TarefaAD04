package clases;

import java.util.List;
import java.util.Scanner;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import programacion.Comuns;

/**
 *
 * @author Víctor Díaz
 */
public class BaseDatos {
        
    /*
    Engadir nova tenda á base de datos
    */
    
    public static void engadirTenda(String nome, String codProvincia, String cidade) {
        
        Provincia prov = getProvincia(codProvincia);
                
        Tenda t = new Tenda(nome, cidade, prov);
                
        Transaction tran = null;
        Provincia provincia = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            tran = sesion.beginTransaction();
            
            sesion.save(t);
            
            tran.commit();
            
            sesion.close();
            
            System.out.println("Tenda gravada correctamente.");
            
        } catch (Exception erro) {
            System.out.println("Erro gravando datos da nova tenda: " + erro.getMessage());
        }
    }
    
    
    public static void eliminarTenda(String id) {
             
        Transaction tran = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select te from Tenda te where te.id=:i");
            consulta.setParameter("i", Integer.parseInt(id));
            List<Tenda> tendas = consulta.getResultList();
            
            if (tendas.size() >0) {
            
                tran = sesion.beginTransaction();

                sesion.delete(tendas.get(0));

                tran.commit();

                sesion.close();

                System.out.println("Tenda eliminada correctamente.");
            }
            
        } catch (Exception erro) {
            System.out.println("Erro tratando de eliminar a tenda: " + erro.getMessage());
        }
    }
    
    public static void listarTendas(Boolean verCabeceira) {

        if (verCabeceira) {
            
            System.out.println("+===========================================+");
            System.out.println("|              LISTAR TENDAS                |");        
            System.out.println("+===========================================+\n\n");

        } else {            
            System.out.println("Listaxe de tendas da franquicia:\n");
        }        
        
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select te from Tenda te");
            List<Tenda> tendas = consulta.getResultList();
                        
            System.out.printf("\n%9s%2s%-30s%2s%-25s%2s%-20s","CODIGO","","NOME","", "CIDADE", "", "PROVINCIA");
            
            for (Tenda te:tendas) {
                System.out.println("");
                System.out.printf("%9s%2s%-30s%2s%-25s%2s%-20s",te.getId(), "", te.getNome(),"", te.getCidade(), "", te.getProvincia().getNome());
            }
            
            System.out.println(programacion.Main.FINLISTAXE);
        }
        catch(Exception erro){
            System.err.println("Erro listando tendas: " + erro.getMessage());
        }
    }
    
    
    public static void engadirProduto(String nome, String descricion, double prezo) {
           
        Produto p = new Produto(nome, descricion, prezo);
                
        Transaction tran = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            tran = sesion.beginTransaction();
            
            sesion.save(p);
            
            tran.commit();
            
            sesion.close();
            
            System.out.println("Produto gravado correctamente.");
            
        } catch (Exception erro) {
            System.out.println("Erro gravando datos do novo produto: " + erro.getMessage());
        }
    }
    
    public static void eliminarProduto(String id) {
        
        Transaction tran = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select pr from Produto pr where pr.id=:i");
            consulta.setParameter("i", Integer.parseInt(id));
            List<Produto> produtos = consulta.getResultList();
            
            if (produtos.size() >0) {
            
                tran = sesion.beginTransaction();

                sesion.delete(produtos.get(0));

                tran.commit();

                sesion.close();

                System.out.println("Produto eliminado correctamente.");
            }
            
        } catch (Exception erro) {
            System.out.println("Erro tratando de eliminar o produto: " + erro.getMessage());
        }
    }
    
    public static void eliminarProdutoTenda(String id) {
        
        Transaction tran = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select tp from TendaProduto tp where tp.id=:i");
            consulta.setParameter("i", Integer.parseInt(id));
            List<TendaProduto> TendaProdutos = consulta.getResultList();
            
            if (TendaProdutos.size() >0) {
            
                tran = sesion.beginTransaction();

                sesion.delete(TendaProdutos.get(0));

                tran.commit();

                sesion.close();

                System.out.println("Produto eliminado correctamente.");
            }
            
        } catch (Exception erro) {
            System.out.println("Erro tratando de eliminar o produto: " + erro.getMessage());
        }
    }
    
    public static void listarProdutosTenda(Boolean verCabeceira) {
            
        Scanner teclado = new Scanner(System.in);

        if (verCabeceira) {
            System.out.println("+===========================================+");
            System.out.println("|    LISTAR PRODUTOS DUNHA TENDA            |");        
            System.out.println("+===========================================+\n\n");
        }
        
        BaseDatos.listarTendas(false);
        
        System.out.println("Teclea o código do/da tenda/a para listar os seus produtos (tecleando un código fóra da listaxe ou negativo cancelamos o proceso). ");

        String id = "";
        
        while (!Comuns.eNumerico(id)) {
            id = teclado.nextLine();
        }
        
        if (existeTenda(id)) {
            
            Tenda t = getTenda(id);
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select tp from TendaProduto tp where tenda=:i");
            consulta.setParameter("i", t);
            List<TendaProduto> tendaProdutos = consulta.getResultList();

            try {

                System.out.println("\nListaxe de produtos da tenda ");

                System.out.printf("\n%9s%2s%9s%2s%-30s%2s%7s%2s%9s","CODIGO_ID","","COD.PROD.","","PRODUTO","", "PREZO", "", "STOCK");

                for (TendaProduto tp:tendaProdutos) {
                    System.out.println("");
                    System.out.printf("%9s%2s%9s%2s%-30s%2s%7.2f%2s%9s",tp.getId(),"", tp.getProduto().getId(), "", tp.getProduto().getNome(),"", tp.getProduto().getPrezo(), "", tp.getStock());
                }
                System.out.println(programacion.Main.FINLISTAXE);
            }
            catch(Exception erro){
                System.err.println("Erro listando tendas: " + erro.getMessage());
            }

        } else {
            System.out.println("Non existe a tenda co código " + id);
        }
    }
    
    public static void listarProdutoTenda(String _produto, String _tenda) {

        Produto p = getProduto(_produto);
        Tenda t = getTenda(_tenda);
        
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select tp from TendaProduto tp where tp.produto=:p and tp.tenda=:t");
            consulta.setParameter("p", p);
            consulta.setParameter("t", t);
            List<TendaProduto> tendaProdutos = consulta.getResultList();
            
            System.out.printf("\n%9s%2s%9s%2s%-30s%2s%9s","CODIGO_ID","","COD.PROD.","","PRODUTO", "", "STOCK");

            for (TendaProduto tp:tendaProdutos){
                System.out.println("");
                System.out.printf("%9s%2s%9s%2s%-30s%2s%9s",tp.getId(),"", tp.getProduto().getId(), "", tp.getProduto().getNome(), "", tp.getStock());
            }
            
            System.out.println(programacion.Main.FINLISTAXE);
        }
        catch(Exception erro) {
            System.out.println("Erro listando stock: " + erro.getMessage());
        }
    }
        
    public static void engadirProdutoTenda(String _produto, String _tenda, String stock) {
        
        Produto p = getProduto(_produto);
        Tenda t = getTenda(_tenda);
        
        TendaProduto tp = new TendaProduto(t, p, Integer.parseInt(stock));        
                
        Transaction tran = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            tran = sesion.beginTransaction();
            
            sesion.save(tp);
            
            tran.commit();
            
            sesion.close();
            
            System.out.println("O produto " + p.getNome() + " foi gravado correctamente na tenda " + t.getNome() );
            
        } catch (Exception erro) {
            System.out.println("Erro gravando o produto na tenda " + erro.getMessage());
        }
    }
    
    public static void actualizarStock(String id, String stock) {
    
        Transaction tran = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select tp from TendaProduto tp where tp.id=:i");
            consulta.setParameter("i", Integer.parseInt(id));
            List<TendaProduto> TendaProdutos = consulta.getResultList();
            
            if (TendaProdutos.size() >0) {
            
                tran = sesion.beginTransaction();
                TendaProdutos.get(0).setStock(Integer.parseInt(stock));
                
                sesion.update(TendaProdutos.get(0));

                tran.commit();

                sesion.close();

                System.out.println("Actualizado correctamente o stock.");
            }
            
        } catch (Exception erro) {
            System.out.println("Erro tratando de actualizar o stock do produto selecionado: " + erro.getMessage());
        }
    }
    
    
    public static void engadirEmpregado(String nome, String apelidos) {
           
        Empregado em = new Empregado(nome, apelidos);
                
        Transaction tran = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            tran = sesion.beginTransaction();
            
            sesion.save(em);
            
            tran.commit();
            
            sesion.close();
            
            System.out.println("Empregado gravado correctamente.");
            
        } catch (Exception erro) {
            System.out.println("Erro gravando datos do/a novo/a empregado/a: " + erro.getMessage());
        }
    }
        
    public static void eliminarEmpregado(String id) {
        
        Transaction tran = null;
        
        System.out.println(id);
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select em from Empregado em where em.id=:i");
            consulta.setParameter("i", Integer.parseInt(id));
            List<Empregado> empregados = consulta.getResultList();
            
            if (empregados.size() >0) {
            
                tran = sesion.beginTransaction();

                sesion.delete(empregados.get(0));

                tran.commit();

                sesion.close();

                System.out.println("Empregado/a eliminado/a correctamente.");
            }
            
        } catch (Exception erro) {
            System.out.println("Erro tratando de eliminar o/a empregado/a: " + erro.getMessage());
        }
    }
        
    public static void listarEmpregados(Boolean verCabeceira) {

        if (verCabeceira) {
            
            System.out.println("+===========================================+");
            System.out.println("|        LISTAXE DE EMPREGADOS/AS           |");        
            System.out.println("+===========================================+\n\n");

        } else {            
            System.out.println("Listaxe de empregados/as da franquicia:\n");
        }        
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            Query consulta = sesion.createQuery("select em from Empregado em");
            List<Empregado> empregados = consulta.getResultList();
                        
            System.out.printf("\n%9s%2s%-20s%2s%-30s","CODIGO","","NOME","", "APELIDOS");
                        
            for(Empregado em:empregados){
                System.out.println("");
                System.out.printf("%9s%2s%-20s%2s%-30s",em.getId(), "", em.getNome(),"", em.getApelidos());
            }
            
            System.out.println("");
        }
        catch(Exception erro){
            System.err.println("Erro listando empregados/as: " + erro.getMessage());
        }
    }
    
    public static void rexistrarHorasEmpregado(String _empregado, String _tenda, String horas) {

        Empregado e = getEmpregado(_empregado);
        Tenda t = getTenda(_tenda);
        
        TendaEmpregado te = new TendaEmpregado(t, e, Integer.parseInt(horas));        
                
        Transaction tran = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            tran = sesion.beginTransaction();
            
            sesion.save(te);
            
            tran.commit();
            
            sesion.close();
            
            System.out.println("Rexistradas correctamente as horas do empregado " + e.getNome() + " " + e.getApelidos() + " na tenda " + t.getNome() );
            
        } catch (Exception erro) {
            System.out.println("Erro rexistrando as horas traballadas por este/a empregado/a na tenda " + erro.getMessage());
        }           
    }
    
    public static void verHorasTraballadas(String _empregado, String _tenda) {
                
        try {           
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();

            Query consulta = sesion.createQuery("select te from TendaEmpregado te where te.empregado=:e and te.tenda=:t");
            consulta.setParameter("e", getEmpregado(_empregado));
            consulta.setParameter("t", getTenda(_tenda));
            
            List<TendaEmpregado> tendaEmpregados = consulta.getResultList();
            
            System.out.printf("\n%-50s%-50s%7s","EMPREGADO/A","TENDA", "HORAS");
                        
            for (TendaEmpregado te:tendaEmpregados) {
                System.out.println("");
                System.out.printf("%-50s%-50s%7s",te.getEmpregado().getNome() + " " + te.getEmpregado().getApelidos(), te.getTenda().getNome() + " (" + te.getTenda().getCidade() + ")", te.getHorasSemanais());
            }
            System.out.println("");
        }
        catch(Exception erro){
            System.err.println("Erro listando as horas de traballo: " + erro.getMessage());
        }
    }
    
    
    public static void engadirCliente(String nome, String apelidos, String email) {
           
        Cliente cl = new Cliente(nome, apelidos, email);
                
        Transaction tran = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            tran = sesion.beginTransaction();
            
            sesion.save(cl);
            
            tran.commit();
            
            sesion.close();
            
            System.out.println("Cliente/a gravado/a correctamente.");
            
        } catch (Exception erro) {
            System.out.println("Erro gravando datos do/a novo/a cliente/a: " + erro.getMessage());
        }
    }
        
    public static void eliminarCliente(String id) {
        
        Transaction tran = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select cl from Cliente cl where cl.id=:i");
            consulta.setParameter("i", Integer.parseInt(id));
            List<Cliente> clientes = consulta.getResultList();
            
            if (clientes.size() >0) {
            
                tran = sesion.beginTransaction();

                sesion.delete(clientes.get(0));

                tran.commit();

                sesion.close();

                System.out.println("Cliente/a eliminado/a correctamente.");
            }
            
        } catch (Exception erro) {
            System.out.println("Erro tratando de eliminar o/a cliente/a: " + erro.getMessage());
        }
    }
    
    public static void listarProvincias() {
                
        System.out.println("Listaxe de provincias:\n");
        
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();

            Query consulta = sesion.createQuery("select pr from Provincia pr");
            List<Provincia> provincias = consulta.getResultList();

            System.out.printf("\n%3s%2s%-30s","COD","","PROVINCIA");

            for (Provincia pr: provincias){
                System.out.println("");
                System.out.printf("%3s%2s%-30s",pr.getId(), "", pr.getNome());
            }
            
            System.out.println("");
        }
        
        catch(Exception erro){

            System.err.println("Erro listando provincias: " + erro.getMessage());
        }
    }
    
    
    private static boolean existe(String id, String taboa ) {
        
        Boolean retorno = false;

        String HQL = "select t from " + taboa + " t where t.id=:i";
                
        try {
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery(HQL);
            if (taboa.equals("Provincia")) {
                consulta.setParameter("i", id);                
            }else {
                consulta.setParameter("i", Integer.parseInt(id));
            }
            List<Object> resultado = consulta.getResultList();
        
            if (resultado.size() > 0) {
                retorno = true;
            }
        } catch (Exception erro) {
            System.out.println(erro.getMessage());
        }
        
        return retorno;
    }

    public static boolean existeProduto(String id){
        return existe(id, "Produto");
    }
    
    public static boolean existeTenda(String id) {
        return existe(id, "Tenda");
    }
    
    
    public static boolean existeEmpregado(String id){
        return existe(id, "Empregado");
    }
    
    public static boolean existeProvincia(String id){
        return existe(id, "Provincia");
    }
    
    public static boolean existeProdutoTenda(String id) {
        return existe(id, "TendaProduto");
    }
    
    public static boolean existeProdutoTenda(String _produto, String _tenda) {
        
        Produto p = getProduto(_produto);
        Tenda t = getTenda(_tenda);
        
        Boolean retorno = false;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select tp from TendaProduto tp where tp.produto=:p and tp.tenda=:t");
            consulta.setParameter("p", p);
            consulta.setParameter("t", t);
            
            List<TendaProduto> tendaProdutos = consulta.getResultList();
            
            if (tendaProdutos.size() > 0) {
                retorno = true;
            }
        } catch (Exception erro) {
            System.out.println(erro.getMessage());
        }
        
        return retorno;
    }
    
    public static void gravarProvincias(Provincias provincias) {
        
        Transaction tran = null;
        Provincia provincia = null;
                
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            tran = sesion.beginTransaction();
            
            for (int i = 0; i<provincias.getProvincias().size(); i++) {
                
                provincia = provincias.getProvincias().get(i);
                
                sesion.saveOrUpdate(provincia);
            }
            
            tran.commit();
            
            sesion.close();
            
            System.out.println("Gravadas ou actualizadas as provincias na base de datos.");
            
        } catch (Exception erro) {
            System.out.println("Erro engadindo as provincias á base de datos: " + erro.getMessage());
        }
                
    }
    
    private static Provincia getProvincia(String codProvincia) {
    
        Provincia prov = null;
             
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select pr from Provincia pr where pr.id=:p");
            consulta.setParameter("p", codProvincia);            
            List<Provincia> provincias = consulta.getResultList();
            
            if (provincias.size() > 0) {
                prov = provincias.get(0);
            }
        } catch (Exception erro) {
            System.out.println(erro.getMessage());
        }
        
        return prov;
        
    }
    
    
    private static Produto getProduto(String codigo) {
    
        Produto p = null;
             
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select pr from Produto pr where pr.id=:p");
            consulta.setParameter("p", Integer.parseInt(codigo));            
            List<Produto> produtos = consulta.getResultList();
            
            if (produtos.size() > 0) {
                p = produtos.get(0);
            }
        } catch (Exception erro) {
            System.out.println(erro.getMessage());
        }
        
        return p;
        
    }
    
    
    private static Tenda getTenda(String codigo) {
    
        Tenda t = null;
             
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select te from Tenda te where te.id=:t");
            consulta.setParameter("t", Integer.parseInt(codigo));            
            List<Tenda> tendas = consulta.getResultList();
            
            if (tendas.size() > 0) {
                t = tendas.get(0);
            }
        } catch (Exception erro) {
            System.out.println(erro.getMessage());
        }
        
        return t;
        
    }
    
    
    private static Empregado getEmpregado(String codigo) {
    
        Empregado e = null;
             
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            Query consulta = sesion.createQuery("select em from Empregado em where em.id=:e");
            consulta.setParameter("e", Integer.parseInt(codigo));            
            List<Empregado> empregados = consulta.getResultList();
            
            if (empregados.size() > 0) {
                e = empregados.get(0);
            }
        } catch (Exception erro) {
            System.out.println(erro.getMessage());
        }
        
        return e;
        
    }
}