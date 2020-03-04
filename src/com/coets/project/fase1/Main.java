package com.coets.project.fase1;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {

        // Create rockets
        Coet coet1 =null, coet2 =null;
        try {
            coet1 = new Coet("x", 3);
            coet2 = new Coet("LDSFJA32", 6);
        } catch (Exception e) {
            System.out.println("Error: el codi de coet introduït té més de 8 caràcters.");
            exit(1);
        }

        // Show rocket data
        System.out.println("Dades del coet 1:");
        System.out.println("Codi: " + coet1.getCodi());
        System.out.println("Número de propulsors: " + coet1.getNumPropulsors());

        System.out.println("Dades del coet 2:");
        System.out.println("Codi: " + coet2.getCodi());
        System.out.println("Número de propulsors: " + coet2.getNumPropulsors());

    }
}
