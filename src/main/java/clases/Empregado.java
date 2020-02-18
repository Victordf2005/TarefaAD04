package clases;

import java.io.Serializable;
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
@Table(name="Empregado")

public class Empregado  implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="nome")    
    private String nome;
    @Column(name="apelidos")
    private String apelidos;
        
    @OneToMany(mappedBy = "empregado",cascade=CascadeType.ALL)
    private Set<TendaEmpregado> tendaEmpregados;
    
    //Construtor por defecto
    public Empregado(){}
    
    //Construtor con parámetros
    public Empregado(String pNome, String pApelidos) {
        this.nome = pNome;
        this.apelidos = pApelidos;
    }
    
    //setters
    public void setNome(String parametro) {this.nome = parametro;}
    public void setApelidos(String parametro) {this.apelidos = parametro;}
    
    //getters
    public int getId() {return this.id;}
    public String getNome() {return this.nome;}
    public String getApelidos() {return this.apelidos;}
    
}

