/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.ArrayList;


/**
 *
 * @author Víctor Díaz
 */

public class InformeTendas {
    
    private int CodTenda;
    private String NomeTenda;
    private String CidadeTenda;
    private String ProvinciaTenda;
    private ArrayList<InformeProdutos> Produtos;

    public InformeTendas() {
    }

    public InformeTendas(int CodTenda, String NomeTenda, String CidadeTenda, String ProvinciaTenda, ArrayList<InformeProdutos> Produtos) {
        this.CodTenda = CodTenda;
        this.NomeTenda = NomeTenda;
        this.CidadeTenda = CidadeTenda;
        this.ProvinciaTenda = ProvinciaTenda;
        this.Produtos = Produtos;
    }


    
    
}
