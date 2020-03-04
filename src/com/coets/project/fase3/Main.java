package com.coets.project.fase3;

import static java.lang.System.exit;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // Create rockets
        Coet coet1 =null, coet2 =null;
        try {
            coet1 = new Coet("32WESSDS", new int[] {1000, 3000, 8000});
            coet2 = new Coet("LDSFJA32", new int[] {30, 40, 50, 50, 30, 10});
        } catch (Exception e) {
            System.out.println("Error: el codi de coet introduït té més de 8 caràcters.");
            exit(1);
        }

        // Show rocket data
        System.out.println(coet1.getCodi() + ": " + Arrays.toString(coet1.getMaxPowerArray()));
        System.out.println(coet2.getCodi() + ": " + Arrays.toString(coet2.getMaxPowerArray()));
        
        // Accelerate rocket
        
        try {
        	// Testing 1: target array differ in number of thrusters
        	System.out.println("========================================================================================");
        	System.out.println("Testing target array with bad number of thrusters...");
        	coet1.accelerar(new int[] {2, 5});
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        
        try {
        	// Testing 2: target array exceeds rocket maximum power
        	System.out.println("========================================================================================");
        	System.out.println("Testing target array exceeding total rocket power...");
        	coet1.accelerar(new int[] {10, 30, 81});
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        
        try {
        	// Testing 3: target array exceeds rocket maximum power
        	System.out.println("========================================================================================");
        	System.out.println("Testing target array with less total power than current power...");
        	coet1.accelerar(new int[] {0, 0, 0});
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }

        try {
        	// Testing 4: target array exceeds thruster maximum power
        	System.out.println("========================================================================================");
        	System.out.println("Testing target array exceeding thruster power...");
        	coet1.accelerar(new int[] {11, 10, 81});
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        
        try {
        	// Testing 5: target array contains a negative power
        	System.out.println("========================================================================================");
        	System.out.println("Testing target array with negative power...");
        	coet1.accelerar(new int[] {5, -1, 75});
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }

        
        System.out.println("========================================================================================");
    	System.out.println("Testing acceleration with correct array...");
    	try {
    		coet1.accelerar(new int[] {500, 1500, 4000});
    		coet1.accelerar(new int[] {600, 2000, 5000});
    		coet1.accelerar(new int[] {700, 2500, 6000});
    		coet1.accelerar(new int[] {800, 3000, 7000});
    	} catch (Exception e){
    		System.out.println(e.getMessage());
    		exit(1);
    	}
    	
        System.out.println("========================================================================================");
    	System.out.println("Testing change speed while still changing former speed...");
    	try {
    		coet1.accelerar(new int[] {10, 15, 40});
    	} catch (Exception e){
    		System.out.println(e.getMessage());
    	}

    	
    }
}






























