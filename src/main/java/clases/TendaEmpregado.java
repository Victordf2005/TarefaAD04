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
@Table(name="TendaEmpregados")

public class TendaEmpregado implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="horasSemanais")
    private int horasSemanais;
    
    @ManyToOne
    @JoinColumn(name="tenda")
    private Tenda tenda;
    
    @ManyToOne
    @JoinColumn(name="empregado")
    private Empregado empregado;

    public TendaEmpregado() {
    }

    public TendaEmpregado(Tenda tenda, Empregado empregado, int horasSemanais) {
        this.tenda = tenda;
        this.empregado = empregado;
        this.horasSemanais = horasSemanais;
    }

    public Tenda getTenda() {
        return tenda;
    }

    public void setTenda(Tenda tenda) {
        this.tenda = tenda;
    }

    public Empregado getEmpregado() {
        return empregado;
    }

    public void setEmpregado(Empregado empregado) {
        this.empregado = empregado;
    }


    public int getHorasSemanais() {
        return horasSemanais;
    }

    public void setHorasSemanais(int horasSemanais) {
        this.horasSemanais = horasSemanais;
    }
    
    
}
