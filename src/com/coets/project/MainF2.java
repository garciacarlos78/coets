package com.coets.project;

import static java.lang.System.exit;

public class MainF2 {

    public static void main(String[] args) {

        // Create rockets
        CoetF2 coet1=null, coet2=null;
        try {
            coet1 = new CoetF2("32WESSDS", 3, new int[]{10, 30, 80});
            coet2 = new CoetF2("LDSFJA32", 6, new int[]{30, 40, 50, 50, 30, 10});
        } catch (Exception e) {
            System.out.println("Error: el codi de coet introdu�t t� m�s de 8 car�cters" +
                    "o el n�mero de pot�ncies introdu�des no coincideix amb el n�mero de propulsors.");
            exit(1);
        }

        // Show rocket data
        System.out.println("Dades del coet 1:");
        System.out.println("Codi: " + coet1.getCodi());
        System.out.println("N�mero de propulsors: " + coet1.getNumPropulsors());
        int[] potencies=coet1.getPotencia();
        for (int i=0;i<potencies.length;i++) {
            System.out.println("Pot�ncia propulsor " + (i+1) + ": " + potencies[i]);
        }

        System.out.println("Dades del coet 2:");
        System.out.println("Codi: " + coet2.getCodi());
        System.out.println("N�mero de propulsors: " + coet2.getNumPropulsors());
        potencies=coet2.getPotencia();
        for (int i=0;i<potencies.length;i++) {
            System.out.println("Pot�ncia propulsor " + (i+1) + ": " + potencies[i]);
        }

    }
}
