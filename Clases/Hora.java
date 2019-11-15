/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpparque;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabio
 */
public class Hora extends Thread{
    
  private double hora;
  private Parque parque;

    public Hora( Parque parque) {
        this.hora = 9.00;
        this.parque = parque;
    }
    
    public void run(){
        Random r=new Random();
            try {
                while (parque.abierto()) { 
                Thread.sleep(r.nextInt(5)*1000);
                hora=hora+0.30;
                parque.setAbierto(hora);
                    System.out.println("La Hora es >>| "+hora+" |<<");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Hora.class.getName()).log(Level.SEVERE, null, ex);
            
            }
    }
    
  
  
    
    
    
}
