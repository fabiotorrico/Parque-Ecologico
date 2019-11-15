/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpparque;

import java.util.Random;

/**
 *
 * @author Fabio
 */
public class Parque {

    private boolean estaAbierto;
    private CarreraDeGomones unaCa;
    private Faro unFaro;
    private Restaurante[] restaurantes;

    public Parque( CarreraDeGomones unaCa, Faro unFaro, Restaurante[] restaurantes) {
        //Constructor
        this.estaAbierto = true;
        this.unaCa = unaCa;
        this.unFaro = unFaro;
        this.restaurantes = restaurantes;
    }

    public boolean abierto() {
      
        return estaAbierto;
    }

    public void setAbierto(double hora) {
        //El modulo se escarga de verificar si el Parque puede estar abierto o no segun la hora.
        if (hora < 18.01) {
            estaAbierto = true;
        } else {
            estaAbierto = false;
        }
    }

    public void actividadGomones(Persona unaP) {
        //El modulo Realiza la Carrera de Gomones.
        try {
            if (estaAbierto) {// Si el Parque aun Esta Abierto Realizo la Actividad.
                unaCa.empiezaActividad(unaP);
                unaCa.llegarAlaActividad(unaP);
                unaCa.guardarCosas(unaP);
                unaCa.subirGomon(unaP);
                unaCa.bajar(unaP);
                unaCa.meta(unaP);
                unaCa.retirarCosas(unaP);
            } else {
                System.out.println(unaP.getNombre() + " No pudo Hacer La Carrera de Gomones por que El Parque Ya cerro");
            }

        } catch (Exception e) {
        }

    }

    public void actividadFaro(Persona unaP) {
        //El modulo se Encarga de que la Persona suba al Faro.
        try {
            if (estaAbierto) {// Si el Parque aun Esta Abierto Realizo la Actividad.
            unFaro.subirEscalera(unaP);
            unFaro.bajarPorElTobogan(unaP);   
            }else {
            System.out.println(unaP.getNombre() + " No pudo Subir al FARO por que el esta Parque CERR0");      
            }
            
        } catch (Exception e) {
        }

    }

    public void actividadComer(Persona unaP) {
        //El modulo se escarga de que la Persona valla a (almorzar o Merendar) en alguno de los restaurantes del Parque.
        try {
            if (estaAbierto) {// Si el Parque aun Esta Abierto Realizo la Actividad.
                while (!unaP.getComio()&&estaAbierto) {
                    Random r = new Random();
                    restaurantes[r.nextInt(restaurantes.length) + 1].comer(unaP);
                }
                if (!estaAbierto) {
                System.out.println(unaP.getNombre() + " No pudo Ingresar al Restaurante por que El Parque Ya cerro");
                }
            } else {
                System.out.println(unaP.getNombre() + " No pudo Ingresar al Restaurante por que El Parque Ya cerro");
            }

        } catch (Exception e) {
        }

    }
    
    public void entrarParque(Persona unaP){
        while (estaAbierto) {  // Si el Parque aun Esta Abierto Realizo la Actividades Aleatoriamente.          
           Random r= new Random();
           switch(r.nextInt(3)){
               case 0:
                   actividadComer(unaP);
                   break;
               case 1:
                   actividadFaro(unaP);
                   break;
               case 3: 
                   actividadGomones(unaP);
                   break;   
           }
        }
    
    }
}
