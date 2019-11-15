/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpparque;

/**
 *
 * @author Fabio
 */
public class Bolsa {
    private int numeroDeBolsa;
    private boolean estado;

    public Bolsa(int numero, boolean estado) {
        this.numeroDeBolsa = numero;
        this.estado = estado;
    }

    public int getNumeroDeBolsa() {
        return numeroDeBolsa;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setNumeroDeBolsa(int numeroDeBolsa) {
        this.numeroDeBolsa = numeroDeBolsa;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
       
}
