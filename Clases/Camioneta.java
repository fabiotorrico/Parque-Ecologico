/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpparque;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabio
 */
public class Camioneta extends Thread{
    private String nombre;
    private CarreraDeGomones unaCarrera;
    private Parque parque;
    public Camioneta(String nombre, Parque parque) {
        //Constructor.
        this.nombre = nombre;
        this.parque=parque;
    }

    @Override
    public void run() {
        try {
            //La unica accion de la Camioneta es Traer las Cosas de Las Personas que realizan la Carrera De gomones
            
            while(parque.abierto()){
            unaCarrera.llevarBolsas();
            }
            } catch (InterruptedException ex) {
            System.out.println("Se rompe aka");
        }
                 
               
            }
        
    }
    
    

