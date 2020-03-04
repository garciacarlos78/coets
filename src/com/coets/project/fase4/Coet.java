package com.coets.project.fase4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Coet {

    private String codi;
    private List<Thruster> thrusters;

    public Coet(String codi, int[] thrustersPower) throws Exception {

        // Check number of character of codi
        if (codi.length()>8) throw new Exception();

        this.codi = codi;
        this.thrusters = new ArrayList<>(3);
        for (int i=0;i<thrustersPower.length;i++) thrusters.add(new Thruster(thrustersPower[i]));
    }

    public String getCodi() {
        return codi;
    }

    public int getNumPropulsors() {
        return thrusters.size();
    }
    
    // Fase3 refactor: change name getPower to getMaxPowerArray
    public int[] getMaxPowerArray() {    	
    	int[] powerArray = new int[thrusters.size()];
    	for (int i=0;i<powerArray.length;i++) powerArray[i]=thrusters.get(i).getMaxPower();
    	return powerArray;    	
    }
    
    // Fase3 add: get total max power
    public int getMaxPower() {
    	return Arrays.stream(getMaxPowerArray()).sum();
    }
    
    
    // Fase3 add: get the current power
    private int getCurrentPower() {
    	int currentPower = 0;
    	for (int i=0;i<thrusters.size();i++) currentPower+=thrusters.get(i).getCurrentPower();
    	return currentPower;
    }
    
    public void accelerar(int[] targetPower) throws Exception {
    	
    	// Common checks: checks needed when accelerating and when braking
    	commonChecks(targetPower);    	
    	
    	// Check if target power is greater than current power. If not greater, throw exception.
    	int totalTargetPower = IntStream.of(targetPower).sum();
    	if (totalTargetPower<=getCurrentPower())
    		throw new Exception("Error: La potència total ha de ser superior a la potència actual del coet."
    				+ "\nPotència demanada: " + totalTargetPower + " - Potència actual: " + getCurrentPower());
    	
    	System.out.println("Accelerant coet " + this.codi + "...");
    	
    	// Else, modify power of each thruster
    	for (int i=0;i<targetPower.length;i++) {
//			System.out.println("*************** Sending acceleration order to thruster #" + i + " ******************");
			thrusters.get(i).changePower(targetPower[i], i);
//			System.out.println("*************** Sent acceleration order to thruster #" + i + " ******************");
		}
    };
    
	public void frenar(int[] targetPower) throws Exception {

    	// Common checks: checks needed when accelerating and when braking
    	commonChecks(targetPower);    	
    	
    	// Check if target power is lower than current power. If not lower, throw exception.
    	int totalTargetPower = IntStream.of(targetPower).sum();
    	if (totalTargetPower>=getCurrentPower())
    		throw new Exception("Error: La potència total ha de ser inferior a la potència actual del coet."
    				+ "\nPotència demanada: " + totalTargetPower + " - Potència actual: " + getCurrentPower());
    	
    	System.out.println("Frenant coet " + this.codi + "...");
    	
    	// Else, modify power of each thruster
    	for (int i=0;i<targetPower.length;i++) {
   			thrusters.get(i).changePower(targetPower[i], i);
    	}    	
	};
    
    private void commonChecks(int[] targetPower) throws Exception {
    	
    	// Check if provided power array has the same number of elements as thrusters has the rocket
    	if (targetPower.length!=thrusters.size()) 
    		throw new Exception("Les potències indicades no coincideixen amb el nombre de propulsors del coet."
    				+ "\nNombre de potències indicades: " + targetPower.length
    				+ "\nNombre de propulsors del coet: " + thrusters.size());
    	
    	// Check if target power is greater than maximum power. If greater, throw exception.
    	int totalTargetPower = IntStream.of(targetPower).sum();
    	if (totalTargetPower>getMaxPower())
    		throw new Exception("Error: La potència total demanada supera la potència màxima del coet."
    				+ "\nPotència demanada: " + totalTargetPower + " - Potència màxima: " + getMaxPower());
    	
    	// "Iterable" checks:
    	//	-There is no negative target power
    	// 	-There is no thruster accelerating nor braking
    	// 	-There is no individual target power greater than maximum thruster power
    	StringBuilder sb = new StringBuilder("Error: la potència objectiu d'un o més propulsors supera la seva potència màxima.");
		boolean proceed = true;
    	for (int i=0;i<targetPower.length;i++) {
    		if (targetPower[i]<0) throw new Exception("Error: No pot haver-hi potències negatives.");
    		if (thrusters.get(i).changingPower) throw new Exception("Error: El coet està actualment accelerant o frenant, has d'esperar que s'aturi.");
    		if (targetPower[i]>thrusters.get(i).getMaxPower()) {
    			sb.append("\nPropulsor " + (i+1) + " - Potència màxima: " + thrusters.get(i).getMaxPower() +
    					" - Potència objectiu: " + targetPower[i]);
    			proceed = false;
    		}
    			    		    	}
    	if (!proceed) throw new Exception(sb.toString());

	}


}








































