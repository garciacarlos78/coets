package com.coets.project.fase2;

import static java.lang.System.exit;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // Create rockets
        Coet coet1 =null, coet2 =null;
        try {
            coet1 = new Coet("32WESSDS", new int[] {10, 30, 80});
            coet2 = new Coet("LDSFJA32", new int[] {30, 40, 50, 50, 30, 10});
        } catch (Exception e) {
            System.out.println("Error: el codi de coet introduït té més de 8 caràcters.");
            exit(1);
        }

        // Show rocket data
        System.out.println(coet1.getCodi() + ": " + Arrays.toString(coet1.getPower()));
        System.out.println(coet2.getCodi() + ": " + Arrays.toString(coet2.getPower()));
    }
}
