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
		//accelerateTests(coet1);

		// Brake rocket
		//brakeTests(coet1);

		// Speed change tests
		speedTests();
    }

	private static void speedTests() {

		try {
			// Create a rocket
			Coet coet = new Coet("SPEED!", new int[]{10, 20, 30});

			// Accelerate from 0 to 100
			coet.accelerate(0, 600);

			// Wait until rocket is not modifying speed
			Thread.sleep(1000);
			while (coet.isChangingPower()) {}

			// Brake from 100 to 0
			coet.accelerate(100, 0);

			// Wait until rocket is not modifying speed
			Thread.sleep(1000);
			while (coet.isChangingPower()) {}

			// Accelerate to an unafordable speed
			coet.accelerate(0,1000);

			// Wait until rocket is not modifying speed
			Thread.sleep(1000);
			while (coet.isChangingPower()) {}

			// Put the rocket to half-throttle
			coet.accelerar(new int[] {5, 10, 15});

			// Wait until rocket is not modifying speed
			Thread.sleep(1000);
			while (coet.isChangingPower()) {}

			// Brake to an unafordable speed
			coet.accelerate(5000,0);
			
			// Make the rocket throttle some thrusters and braking others
			// 1. Put the throttles unbalanced
			coet.frenar(new int[] {10, 5, 0});
			
			// Wait until rocket is not modifying speed
			Thread.sleep(1000);
			while (coet.isChangingPower()) {}

			// 2. Order a speed change
			coet.accelerate(50,  500);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void accelerateTests(Coet coet) {

		// Testing 1: target array differ in number of thrusters
		try {
			System.out.println("========================================================================================");
			System.out.println("Accelerate: Testing target array with bad number of thrusters...");
			coet.accelerar(new int[] {2, 5});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Testing 2: target array exceeds rocket maximum power
		try {
			System.out.println("========================================================================================");
			System.out.println("Accelerate: Testing target array exceeding total rocket power...");
			coet.accelerar(new int[] {10, 30, 81});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Testing 3: target array exceeds rocket maximum power
		try {
			System.out.println("========================================================================================");
			System.out.println("Accelerate: Testing target array with less total power than current power...");
			coet.accelerar(new int[] {0, 0, 0});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Testing 4: target array exceeds thruster maximum power
		try {
			System.out.println("========================================================================================");
			System.out.println("Accelerate: Testing target array exceeding thruster power...");
			coet.accelerar(new int[] {11, 10, 81});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Testing 5: target array contains a negative power
		try {
			System.out.println("========================================================================================");
			System.out.println("Accelerate: Testing target array with negative power...");
			coet.accelerar(new int[] {5, -1, 75});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Testing 7: target array is correct
		try {
			System.out.println("========================================================================================");
			System.out.println("Accelerate: Testing acceleration with correct array...");
			coet.accelerar(new int[] {5, 15, 40});
		} catch (Exception e){
			System.out.println(e.getMessage());
		}

		// Testing 8: an acceleration order is sent while the rocket is still accelerating
		try {
			System.out.println("========================================================================================");
			System.out.println("Accelerate: Testing change speed while still changing former speed...");
			coet.accelerar(new int[] {10, 15, 40});
		} catch (Exception e){
			System.out.println(e.getMessage());
		}

		// Wait until rocket is not accelerating
		while (coet.isChangingPower()){}

		// Testing 9: an acceleration order is sent with less total power than current power
		try {
			System.out.println("========================================================================================");
			System.out.println("Accelerate: Testing acceleration order with less total power than current power...");
			coet.accelerar(new int[] {5, 15, 30});
		} catch (Exception e){
			System.out.println(e.getMessage());
		}

	}

	private static void brakeTests(Coet coet) {

    	// Common tests are ignored as they use the same function as accelerate method

		// Testing 1: target array exceeds rocket current power
		try {
			System.out.println("========================================================================================");
			System.out.println("Brake: Testing target array with more total power than current power...");
			coet.frenar(new int[] {10, 15, 50});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Testing 2: target array is correct
		try {
			System.out.println("========================================================================================");
			System.out.println("Brake: Testing brake with correct array...");
			coet.frenar(new int[] {0, 12, 20});
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
}