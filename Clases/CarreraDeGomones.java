/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpparque;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabio
 */
public class CarreraDeGomones {

    private int solo, duo, capacidad, posicion, compañero, bicis;
    private boolean esperandoDuo = false;
    private Condition espera, duos, colaBici, camioneta, esperaBolsos;
    private Lock lock;
    private CyclicBarrier GomonesListos, tren;

    private Bolsa bolsas[];

    public CarreraDeGomones(int solo, int duo, int cantGomonesLargada, Bolsa Colbolsas[]) {
        this.solo = solo;
        this.duo = duo;
        this.capacidad = cantGomonesLargada;
        this.posicion = 0;
        this.lock = new ReentrantLock();
        this.espera = lock.newCondition();
        this.duos = lock.newCondition();
        this.colaBici = lock.newCondition();
        this.camioneta = lock.newCondition();
        this.esperaBolsos = lock.newCondition();
        this.GomonesListos = new CyclicBarrier(cantGomonesLargada);
        this.tren = new CyclicBarrier(15);//El enunciado dice que como maximo debend e haber 15 Personas en el Tren

        this.bolsas = Colbolsas;

    }

    public void empiezaActividad(Persona unaP) throws InterruptedException {
        lock.lock();
        Random tiempo = new Random();

        //Si True Voy en Bici de lo contrario voy en el tren.
        if (unaP.getBici()) {
            while (bicis > 0) {
                //Si no hay bicis disponibles me duermo y espero en la cola Hasta que halla una disponible.
                colaBici.await();
            }
            //Tomo la bici
            bicis--;
            System.out.println(unaP.getNombre() + " Tomo la Bicicleta " + bicis + " Y se dirije a los GOMONES");
            Thread.sleep(tiempo.nextInt(5) * 1000); //Simulo el uso de la bicicleta.

        } else {
            lock.unlock();
            System.out.println(unaP.getNombre() + "Subio al Tren");
            try {
                tren.await(20, TimeUnit.SECONDS);
            } catch (InterruptedException | BrokenBarrierException x) {
            } catch (TimeoutException ex) {
                tren.reset();
            }

        }

    }

    public void llegarAlaActividad(Persona unaP) {
        //LLega a la actividad para comenzar a subirse a los gomones
        lock.lock();
        if (unaP.getBici()) {
            bicis++;//Devuelve la Bici y Magicamente vuelve al Comienzo transportada para que otra persona la utilize xD
            System.out.println(unaP.getNombre() + "LLego a la Actividad en Bici");
        } else {
            System.out.println(unaP.getNombre() + "Llego a la Actividad En Tren");
        }
        lock.unlock();
    }

    public synchronized void guardarCosas(Persona unaP) {
        //Guarda sus cosas que no deceen mojar en las bolsas asignadas.
        Random num = new Random();
        int numero;
        lock.lock();
        boolean asignado = false;
        while (asignado) {
            numero = num.nextInt(bolsas.length);
            if (bolsas[numero].getEstado()) {
                asignado = false;
                unaP.setLLave(numero);
                System.out.println("La Bolsa n° " + numero + " fue Asignada a " + unaP.getNombre());
            }
        }
        lock.unlock();
    }

    public void subirGomon(Persona unP) throws InterruptedException {

        lock.lock();
        if (!unP.getDuo()) { //Si no Tiene Duo Intento tomar un Gomon Individual de lo contrario un Gomon Duo.
            while (!(solo > 0)) { //Si no hay Gomones Disponibles los Duermo en la Cola de espera, en cuando hallan 
                //Gomones disponibles ignoro el While o salgo del while depende del caso.
                System.out.println(unP.getNombre() + "Esta esperando que se Desocupe un Gomon Individual");
                espera.await();
            }
            solo--;
            System.out.println(unP.getNombre() + "Ya se subio al gomon Individual");
        } else { //Intento tomar un Gomon Duo
            //Si no hay Gomones Disponibles los Duermo en la Cola de espera, en cuando hallan 
            //Gomones disponibles ignoro el While o salgo del while depende del caso.
            while (!(duo > 0)) {
                espera.await();
            }
            //En caso de que Hallan Gomones Disponibles pero no sea el Compañero del ultimo que tomo un Gomon Duo
            //Me duermo y espero que llegue el compañero del Ultimo Gomon Duo Tomado.
            while (esperandoDuo && (compañero != unP.getNombre())) {
                espera.await();
            }

            if (esperandoDuo) {//Si True es xq mi compañero ya subio al gomon y va a subirse el que lo invoca.
                System.out.println(compañero + "llego y se sube al Gomon DUO " + duo);
                duo--;
                compañero = -99;
                esperandoDuo = false;
            } else {
                //Si quiero un Gomon Duo y soy el primero Pido el Gomon y me duermo Hasta que mi compañero llegue.
                System.out.println(unP.getNombre() + " Se Subio y espera al Compañero " + compañero + " que suba al Gomon DUO" + duo);
                compañero = unP.getAcompañante();
                unP.setPuedoBajar(false);
                duos.await();
            }
        }

        lock.unlock();
    }

    public void bajar(Persona unaP) throws InterruptedException {
        Random r = new Random();
        //Este metodo Simula la Carrera de los Gomones
        if (unaP.getPuedoBajar()) {
            try {
                GomonesListos.await(20, TimeUnit.SECONDS); //Invoco a la Barrera que unan vez que llegen los n Gomones Larga.
                if (unaP.getDuo()) {
                    System.out.println(unaP.getNombre() + " y " + unaP.getAcompañante() + "Empezaron a Bajar en el Gomon DUO");
                } else {
                    System.out.println(unaP.getNombre() + "Empezo a Bajar con el Gomon");
                }
                Thread.sleep(r.nextInt(4) * 1000); //Simulo la bajada del Gomon
            } catch (InterruptedException | BrokenBarrierException x) {
            } catch (TimeoutException ex) {
                GomonesListos.reset();
            }
        }

    }

    public void meta(Persona unaP) throws InterruptedException {
        //Este metodo se encarga de recibir los gomones que bajaron y asignarle la posicion que logro cada uno.
        //Posicion esta inicializado en 0.
        lock.lock();
        if (!unaP.getDuo()) {
            posicion++;
            System.out.println(unaP.getNombre() + " y " + unaP.getAcompañante() + " Llegaron a la Meta en la posicion " + posicion);
            solo++;
        } else {
            posicion++;
            System.out.println(unaP.getNombre() + "Llego a la Meta en la posicion " + posicion);
            duo++;
            duos.notifyAll(); //Despierto a mi Duo ya que llegue al final de la carrera.
        }
        espera.notifyAll();// Despierto a todos los que estaban esperando por un Gomon ya sea DUO o Individual
        if (posicion == 1) {//Cuando llega el Primer Gomon a la Meta despierto a la camioneta para que cargue y traiga las bolsas respectivas
            camioneta.notifyAll();
        }
        //Verifico que cuando llegen todos los gomones al final reseteo la posicion en 0 para la proxima carrera
        if (posicion == capacidad) {
            posicion = 0;
        }
        esperaBolsos.await(); //Espero a que lleguen los Bolsos
        lock.unlock();
    }

    public void llevarBolsas() throws InterruptedException {

        lock.lock();
        Random r = new Random();
        System.out.println("La camioneta ya llego al parque y esta lista");//La Camioneta esta Lista y Se duerme Hasta, Cuando llegan el primero a la meta la Camioneta Carga las bolsas y las lleva.
        camioneta.await();
        System.out.println("LA CAMIONETA CARGA LAS BOLSAS");
        Thread.sleep(r.nextInt(4) * 1000);//Simula que carga las cosas a la Camioneta
        System.out.println("LA CAMIONETA TRAE LAS BOLSAS");
        Thread.sleep(r.nextInt(5) * 1000);//Simula que lleva las cosas a la Meta
        System.out.println("LA CAMIONETA ENTREGA LAS BOLSAS");
        esperaBolsos.notifyAll();//Desperto a los que esperan el bolso y les digo que ya llegaron los bolsos 
        lock.unlock();
    }

    public void retirarCosas(Persona unaP) {
        //Cada Persona luego de Terminar la carrera retira sus cosas que guardo en las bolsas.
        lock.lock();
        System.out.println(unaP.getNombre() + " Retira Sus pertenencias y devuelvc la LLAVE n° " + unaP.getLlave());
        bolsas[unaP.getLlave()].setEstado(false);//Cambio el estado de la Bolsa de Ocupado A Libre
        lock.unlock();
    }
}
