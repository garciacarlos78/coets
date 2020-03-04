package com.coets.project.fase3;

import static java.lang.System.exit;

public class MainF3 {

    public static void main(String[] args) {

        // Create rockets
        CoetF3 coet1=null, coet2=null;
        try {
            coet1 = new CoetF3("32WESSDS", 3, new int[]{10, 30, 80});
            coet2 = new CoetF3("LDSFJA32", 6, new int[]{30, 40, 50, 50, 30, 10});
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exit(1);
        }

        // Show rocket data
        System.out.println("Dades del coet 1:");
        System.out.println("Codi: " + coet1.getCodi());
        System.out.println("N�mero de propulsors: " + coet1.getNumPropulsors());
        int[] potencies=coet1.getPotenciaMaxima();
        for (int i=0;i<potencies.length;i++) {
            System.out.println("Pot�ncia m�xima propulsor " + (i+1) + ": " + potencies[i]);
        }

        System.out.println("Dades del coet 2:");
        System.out.println("Codi: " + coet2.getCodi());
        System.out.println("N�mero de propulsors: " + coet2.getNumPropulsors());
        potencies=coet2.getPotenciaMaxima();
        for (int i=0;i<potencies.length;i++) {
            System.out.println("Pot�ncia m�xima propulsor " + (i+1) + ": " + potencies[i]);
        }

        // Power increase
        try {
            coet1.accelerate(new int[]{15, 10, 5});
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Power decrease
        try {
            // Wait until the rocket is not being accelerated neither braked
            while (coet1.isChangingSpeed()) {
                System.out.println("Waiting... Some of the engines is changing its power...");
                Thread.sleep(1000);
            }
            coet1.brake(new int[]{1,0,2});
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
