package com.coets.project.fase3;

import java.util.ArrayList;
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
    
    // Fase3 refactor: change name getPower to getMaxPower
    public int[] getMaxPower() {    	
    	int[] powerArray = new int[thrusters.size()];
    	for (int i=0;i<powerArray.length;i++) powerArray[i]=thrusters.get(i).getMaxPower();
    	return powerArray;    	
    }
    
    // Fase3 add: get the current power
    private int getCurrentPower() {
    	int currentPower = 0;
    	for (int i=0;i<thrusters.size();i++) currentPower+=thrusters.get(i).getCurrentPower();
    	return currentPower;
    }
    
    public void accelerar(int[] targetPower) throws Exception {
    	
    	// Check if provided power array has the same number of elements as thrusters has the rocket
    	if (targetPower.length!=thrusters.size()) 
    		throw new Exception("Les potències indicades no coincideixen amb el nombre de propulsors del coet."
    				+ "\nNombre de potències indicades: " + targetPower.length
    				+ "\nNombre de propulsors del coet: " + thrusters.size());
    	
    	// Check if target power is greater than current power
    	int totalTargetPower = IntStream.of(targetPower).sum();
    	
    	// If not greater, throw exception
    	if (totalTargetPower<=getCurrentPower()) 
    		throw new Exception("La potència total ha de superior a la potència actual del coet.");
    	
    	// Else, modify power of each thruster
    	for (int i=0;i<targetPower.length)
    	
    };
    public void frenar(int[] targetPower) {};
}