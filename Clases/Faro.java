/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpparque;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Fabio
 */

public class Faro {

    private Semaphore escaleraCaracol;
    private Semaphore ToboganA;
    private Semaphore ToboganB;

    public Faro(int n) {
        this.escaleraCaracol = new Semaphore(n, true);
        this.ToboganA = new Semaphore(1, true);
        this.ToboganB = new Semaphore(1, true);
    }

    public void subirEscalera(Persona unaP) {
        //El metodo sube la escalera caracol controlando la cantidad de personas que suben simultaneamente
        Random r = new Random();
        try {
            escaleraCaracol.acquire(1);
            System.out.println(unaP.getNombre() + " Esta Subiendo la escalera Caracol");
            Thread.sleep(1000 * (r.nextInt(4) + 1));
            System.out.println(unaP.getNombre() + " LLego a la cima");
            escaleraCaracol.release();
        } catch (Exception ex) {
        }
    }

    public void bajarPorElTobogan(Persona unaP) {
        //El metodo se encarga de controlar que en cada tobogan bajen de a uno.Como asi tambien un administrador
        //decide quien baja por que tobogan.
        Random r = new Random();
        int t = r.nextInt(2);
        try {
            System.out.println("El administrador decide que " +unaP.getNombre() + " va por el tobogan >> " + t);
            if (t == 0) {
                ToboganA.acquire(1);
                System.out.println(unaP.getNombre() + " Baja Por el Tobogan << " + t);
                Thread.sleep(1000 * (r.nextInt(4) + 1));
                System.out.println(unaP.getNombre() + " Ya Bajo < " );
                ToboganA.release();
            } else {
                ToboganB.acquire(1);
                System.out.println(unaP.getNombre() + " Baja Por el Tobogan << " + t);
                Thread.sleep(1000 * (r.nextInt(4) + 1));
                System.out.println(unaP.getNombre() + " Ya Bajo < " );
                ToboganB.release();
            }
        } catch (Exception ex) {
        }

    }

}
