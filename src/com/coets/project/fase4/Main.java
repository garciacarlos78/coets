package com.coets.project.fase4;

import java.util.Arrays;

import static java.lang.System.exit;

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
        System.out.println(coet1.getCodi() + ": " + Arrays.toString(coet1.getMaxPowerArray()));
        System.out.println(coet2.getCodi() + ": " + Arrays.toString(coet2.getMaxPowerArray()));
        
        // Accelerate rocket
		accelerateTests(coet1);


    }

	private static void accelerateTests(Coet coet1) {

		// Testing 1: target array differ in number of thrusters
		try {
			System.out.println("========================================================================================");
			System.out.println("Testing target array with bad number of thrusters...");
			coet1.accelerar(new int[] {2, 5});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Testing 2: target array exceeds rocket maximum power
		try {
			System.out.println("========================================================================================");
			System.out.println("Testing target array exceeding total rocket power...");
			coet1.accelerar(new int[] {10, 30, 81});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Testing 3: target array exceeds rocket maximum power
		try {
			System.out.println("========================================================================================");
			System.out.println("Testing target array with less total power than current power...");
			coet1.accelerar(new int[] {0, 0, 0});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Testing 4: target array exceeds thruster maximum power
		try {
			System.out.println("========================================================================================");
			System.out.println("Testing target array exceeding thruster power...");
			coet1.accelerar(new int[] {11, 10, 81});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Testing 5: target array contains a negative power
		try {
			System.out.println("========================================================================================");
			System.out.println("Testing target array with negative power...");
			coet1.accelerar(new int[] {5, -1, 75});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Testing 6: target array is correct
		try {
			System.out.println("========================================================================================");
			System.out.println("Testing acceleration with correct array...");
			coet1.accelerar(new int[] {5, 15, 40});
		} catch (Exception e){
			System.out.println(e.getMessage());
			exit(1);
		}

		// Testing 7: an acceleration order is sent while the rocket is still accelerating
		try {
			System.out.println("========================================================================================");
			System.out.println("Testing change speed while still changing former speed...");
			coet1.accelerar(new int[] {10, 15, 40});
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
    }
}






























