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

public class InformeProdutos {
 
    private int CodProduto;
    private String NomeProduto;
    private String DescricionProduto;
    private double PrezoProduto;
    private int StockEnTenda;

    public InformeProdutos() {
    }

    public InformeProdutos(int CodProduto, String NomeProduto, String DescricionProduto, double PrezoProduto, int StockEnTenda) {
        this.CodProduto = CodProduto;
        this.NomeProduto = NomeProduto;
        this.DescricionProduto = DescricionProduto;
        this.PrezoProduto = PrezoProduto;
        this.StockEnTenda = StockEnTenda;
    }

    
    
}
