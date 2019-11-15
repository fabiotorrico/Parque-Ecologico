/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpparque;

import java.util.Random;
import tpparque.Librerias.TecladoIn;

/**
 *
 * @author Fabio
 */
public class TPPARQUE {

    /**
     *Autor TORRICO FABIO FAI-1927
     */
    public static void main(String[] args) {
        int duo, solo, cantTope, cantEscalera,capacidadRes, cantSolo, cantDuo,personas=0;
        Random random = new Random();
        System.out.println("Cargar Datos de La Carrera De Gomones");
        System.out.println("Ingrese la Cantidad de Gomones Individuales");
        solo = TecladoIn.readLineInt();
        System.out.println("Ingrese la Cantidad de Gomones DUO");
        duo = TecladoIn.readLineInt();
        System.out.println("Ingrese la Cantidad de Gomones que participan de la carrera");
        cantTope = TecladoIn.readLineInt();
        System.out.println("-------------------------------o-------------------------------");
        System.out.println("Cargar Datos del Faro");
        System.out.println("Ingrese la capacidad de la Escalera Caracol del Faro");
        cantEscalera = TecladoIn.readLineInt();

        System.out.println("-------------------------------o-------------------------------");
        System.out.println("Ingrese Datos De Cada Restaurante");
        Restaurante restaurantes[] = new Restaurante[3];
        for (int i = 0; i < 3; i++) {
            System.out.println("Ingrese la capacidad del Restaurante " + i + " <");
            capacidadRes = TecladoIn.readLineInt();
            restaurantes[i] = new Restaurante(i, capacidadRes);
        }
        System.out.println("-------------------------------o-------------------------------");
        System.out.println("Ingrese La cantidad de Personas que van a usar Gomon Solos");
        cantSolo = TecladoIn.readLineInt();
        System.out.println("-------------------------------o-------------------------------");
        System.out.println("Ingrese Un numero PAR de La cantidad de Personas que van a usar Gomon DUO");
        cantDuo = TecladoIn.readLineInt();
        Bolsa colBolsas[] = new Bolsa[cantDuo + cantSolo];
        for (int i = 0; i < cantDuo + cantSolo; i++) {
            colBolsas[i] = new Bolsa(i, false);
        }

        System.out.println("-------------------------------o-------------------------------");
        Faro unFaro = new Faro(cantEscalera);
        CarreraDeGomones unaCa = new CarreraDeGomones(solo, duo, cantTope, colBolsas);
        Parque parque = new Parque(unaCa, unFaro, restaurantes);

        Persona personasSolo[] = new Persona[cantSolo];
        for (int i = 0; i < cantSolo; i++) {
            personasSolo[i] = new Persona(personas, i, false, random.nextBoolean(), parque);
            personas++;
        }

        Persona personasDUO[] = new Persona[cantDuo];
        for (int i = 0; i < cantDuo; i++) {
            int x;
            if (i % 2 == 0) {
                personasDUO[i] = new Persona(personas, personas + 1, true, random.nextBoolean(), parque);
            } else {
                personasDUO[i] = new Persona(personas, personas - 1, true, random.nextBoolean(), parque);

            }
            personas++;
        }

        System.out.println("-------------------------------o-------------------------------");

        Hora hora = new Hora(parque);
        hora.start();
        for (int i = 0; i < cantDuo; i++) {
            personasDUO[i].start();
        }
        for (int i = 0; i < cantSolo; i++) {
            personasSolo[i].start();
        }
        Camioneta camioneta = new Camioneta("LA HILUX", parque);
        camioneta.start();
    }

}
