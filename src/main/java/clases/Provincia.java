package clases;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



/**
 *
 * @author Víctor Díaz
 */

@Entity
@Table(name="Provincias")

public class Provincia implements Comparable<Provincia>, Serializable {
   
    @Id
    @Column(name="id")
    private String id;
    @Column(name="nome")
    private String nome;
    
    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL)
    private Set<Tenda> tendas;
    
    //Construtor por defecto
    public Provincia(){
        this.id ="00";
        this.nome= new String("");
    }
    
    //Contrutor con parámetros
    public Provincia(String pId, String pDescricion){
        this.id = pId;
        this.nome = new String(pDescricion);
    }
    
    //setters
    public void setId(String parametro) {this.id = parametro;}    
    public void setNome(String parametro) {this.nome = parametro;}    
    
    //getters
    public String getId() {return this.id;}    
    public String getNome() {return this.nome;}

    @Override
    public int compareTo(Provincia prv) {
        return id.compareTo(prv.getId());
    }
}
