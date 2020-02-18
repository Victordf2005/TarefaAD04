package clases;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Víctor Díaz
 */

@Entity
@Table(name="Tendas")

public class Tenda  implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="nome")
    private String nome = "";
    @Column(name="cidade")
    private String cidade = "";
    
    @ManyToOne
    @JoinColumn(name="provincia")
    private Provincia provincia;
    
    @OneToMany(mappedBy = "tenda", cascade = CascadeType.ALL)
    private Set<TendaProduto> tendaProdutos;
    
    @OneToMany(mappedBy = "tenda", cascade = CascadeType.ALL)
    private Set<TendaEmpregado> tendaEmpregados;
    
    //construtor por defecto
    public Tenda(){
    }
    
    //construtor con parámetros
    public Tenda(String pNome, String pCidade, Provincia provincia) {
        this.nome = pNome;
        this.cidade = pCidade;
        this.provincia = provincia;
        this.tendaProdutos = new HashSet<>();
        this.tendaEmpregados = new HashSet<>();        
    }
    
    
    //setters
    public void setNome(String parametro) {this.nome = parametro;}
    public void setCidade(String parametro)  {this.cidade = parametro;}
    
    //getters
    public int getId() {return this.id;}
    public String getNome() {return this.nome;}
    public String getCidade() {return this.cidade;}
    public Provincia getProvincia() {return this.provincia;}
    
    public void engadirProduto(TendaProduto tendaProduto) {
        this.tendaProdutos.add(tendaProduto);
    }
    
    public void engadirEmpregado(TendaEmpregado tendaEmpregado) {
        this.tendaEmpregados.add(tendaEmpregado);
    }
    
}
