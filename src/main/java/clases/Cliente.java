package clases;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="Clientes")

public class Cliente  implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="nome")
    private String nome;
    @Column(name="apelidos")
    private String apelidos;
    @Column(name="email")
    private String email;
    
    //construtor por defecto
    public Cliente() {}
    
    //construtor con parámetros
    public Cliente(String pNome, String pApelidos, String pEmail) {
        this.nome = pNome;
        this.apelidos = pApelidos;
        this.email = pEmail;
    }
    
    //setters
    public void setNome(String parametro) {this.nome = parametro;}
    public void setApelidos(String parametro) {this.apelidos = parametro;}
    public void setEmail(String parametro) {this.email = parametro;}
    
    //getters
    public int getId() {return this.id;}
    public String getNome() {return this.nome;}
    public String getApelidos() {return this.apelidos;}
    public String getEmail() {return this.email;}
        
}
