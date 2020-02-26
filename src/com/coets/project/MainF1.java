package com.coets.project;

import static java.lang.System.exit;

public class MainF1 {

    public static void main(String[] args) {

        // Create rockets
        CoetF1 coetF11 =null, coetF12 =null;
        try {
            coetF11 = new CoetF1("x", 3);
            coetF12 = new CoetF1("LDSFJA32", 6);
        } catch (Exception e) {
            System.out.println("Error: el codi de coet introduït té més de 8 caràcters.");
            exit(1);
        }

        // Show rocket data
        System.out.println("Dades del coet 1:");
        System.out.println("Codi: " + coetF11.getCodi());
        System.out.println("Número de propulsors: " + coetF11.getNumPropulsors());

        System.out.println("Dades del coet 2:");
        System.out.println("Codi: " + coetF12.getCodi());
        System.out.println("Número de propulsors: " + coetF12.getNumPropulsors());

    }
}
