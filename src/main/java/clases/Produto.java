package clases;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Víctor Díaz
 */

@Entity
@Table(name="Produtos")

public class Produto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="nome")
    private String nome;
    @Column(name="descricion")
    private String descricion;
    @Column(name="prezo")    
    private double prezo;
    
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private Set<TendaProduto> tendaProdutos;
    
    //Construtor por defecto
    public Produto(){}
    
    //Contrutor con parámetros
    public Produto(String pNome, String pDescricion, double pPrezo){
        this.nome = pNome;
        this.descricion = pDescricion;
        this.prezo = pPrezo;     
        this.tendaProdutos = new HashSet<>();
    }

    //setters
    public void setId(int parametro) {this.id = parametro;}
    public void setNome(String parametro) {this.nome= parametro;}
    public void setDescricion(String parametro) {this.nome = parametro;}    
    public void setPrezo(float parametro) {this.prezo = parametro;}
    
    //getters
    public int getId() {return this.id;}
    public String getNome() {return this.nome;}
    public String getDescricion() {return this.descricion;}
    public double getPrezo() {return this.prezo;}
    
}