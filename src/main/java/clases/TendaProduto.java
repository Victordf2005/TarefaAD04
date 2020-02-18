/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Víctor Díaz
 */

@Entity
@Table(name="TendaProdutos")

public class TendaProduto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="stock")
    private int stock;
    
    @ManyToOne
    @JoinColumn(name="tenda")
    private Tenda tenda;
    
    @ManyToOne
    @JoinColumn(name="produto")
    private Produto produto;
    
    public TendaProduto(){}

    public TendaProduto(Tenda tenda, Produto produto, int stock) {
        this.tenda = tenda;
        this.produto = produto;
        this.stock = stock;
    }

    public Tenda getTenda() {
        return tenda;
    }

    public void setTenda(Tenda tenda) {
        this.tenda = tenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
