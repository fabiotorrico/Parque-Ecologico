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
public class Persona extends Thread{
    private int nombre,acompañante,numeroLlave,restaurante,paseRestaurante;
    private boolean duo,puedoBajar,bici,comio;
    private Parque parque;

    public Persona(int nombre, int acompañante, boolean duo, boolean bici,Parque parquex) {
        this.nombre = nombre;
        this.acompañante = acompañante;
        this.duo = duo;
        this.puedoBajar = true;
        this.bici=bici;
        this.restaurante=-1;
        this.comio=false;
        this.paseRestaurante=0;
        this.parque=parquex;
    }

    public int getNombre() {
        return nombre;
    }
    public int getAcompañante() {
        return acompañante;
    }
    public boolean getDuo() {
        return duo;
    }
    public int getLlave(){
    return numeroLlave;
    }
    public boolean getPuedoBajar() {
        return puedoBajar;
    }
    public int getRestaurante(){
    return restaurante;
    }
    public boolean getComio(){
    return comio;
    }
    public int getPase(){
    return paseRestaurante;}
    public void usoPase(){
    paseRestaurante++;
    }
    public void setComio(boolean x){
    this.comio=x;
    }    
    public void setRestaurante(int x){
    this.restaurante=x;
    }
    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
    public void setAcompañante(int acompañante) {
        this.acompañante = acompañante;
    }
    public void setDuo(boolean duo) {
        this.duo = duo;
    }
    public void setPuedoBajar(boolean puedoBajar) {
        this.puedoBajar = puedoBajar;
    }
    public void setLLave(int llave){
        this.numeroLlave=llave;
    }
    public int getNumeroLlave() {
        return numeroLlave;
    }
    public boolean getBici() {
        return bici;
    }

    @Override
    public void run() {
        parque.entrarParque(this);
    }
    
    
}
