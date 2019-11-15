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
public class Restaurante {

    private int capacidad;
    private int nombre;

    public Restaurante(int capacidad, int nombre) {
        this.capacidad = capacidad;
        this.nombre = nombre;
    }

    public synchronized void comer(Persona unaP) throws InterruptedException {

        if (unaP.getPase()<3) {
            if (unaP.getRestaurante() != nombre) { //Verifico que no halla comido en este restaurante
            while (!(capacidad > 0)) {  //Si el Restaurante esta lleno espero en la fila Hasta poder entrar
                System.out.println(unaP.getNombre() + " Espera en la Fila del restaurante " + nombre);
                wait();
            }
            capacidad--;
            System.out.println(unaP.getNombre() + " Entro al Restaurante " + nombre);

            if (unaP.getRestaurante() == -1) {//Si es -1 quiere decir que no inreso a ningun restaurante
                System.out.println(unaP.getNombre() + "Esta Almorzando");
            } else {
                System.out.println(unaP.getNombre() + "Esta Merendando");
            }
            unaP.setRestaurante(nombre);//Le seteo el resturante para que le sepa a que restaurante ya asistio.
            unaP.setComio(true);//Le aviso que ya comio asi no busca otro Restaurante
            unaP.usoPase();
            }
        } else {
            System.out.println(unaP.getNombre()+" Ya uso sus 2 pases Libre a los Restaurantes");
        }

    }

}
