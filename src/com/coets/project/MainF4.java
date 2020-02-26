package com.coets.project;

import static java.lang.System.exit;

public class MainF4 {

    public static void main(String[] args) {

        // Create rockets
        CoetF4 coet1=null;
        CoetF4 coet2=null;
        try {
            coet1 = new CoetF4("32WESSDS", 3, new int[]{5, 7, 25});
            coet2 = new CoetF4("LDSFJA32", 6, new int[]{30, 40, 50, 50, 30, 10});
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exit(1);
        }

        // Show rocket data
        System.out.println("Dades del coet 1:");
        System.out.println("Codi: " + coet1.getCodi());
        System.out.println("Número de propulsors: " + coet1.getNumPropulsors());
        int[] potencies=coet1.getPotenciaMaxima();
        for (int i=0;i<potencies.length;i++) {
            System.out.println("Potència màxima propulsor " + (i+1) + ": " + potencies[i]);
        }

        System.out.println("Dades del coet 2:");
        System.out.println("Codi: " + coet2.getCodi());
        System.out.println("Número de propulsors: " + coet2.getNumPropulsors());
        potencies=coet2.getPotenciaMaxima();
        for (int i=0;i<potencies.length;i++) {
            System.out.println("Potència màxima propulsor " + (i+1) + ": " + potencies[i]);
        }

        // Power increase
        try {
            coet1.increasePower(new int[]{15, 10, 5});
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

        while (coet1.isChangingSpeed());
        coet1.accelerate(0, 500);

        // Wait until the engines have stopped increasing power and show final power
        while (coet1.isChangingSpeed());
        System.out.println("Current power after acceleration: " + coet1.getCurrentPower());
        // Test of over available power acceleration
        coet2.accelerate(0,5000);
    }
}
