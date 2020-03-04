package com.coets.project.fase2;

import java.util.ArrayList;
import java.util.List;

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
    
    public int[] getPower() {
    	
    	int[] powerArray = new int[thrusters.size()];
    	for (int i=0;i<powerArray.length;i++) powerArray[i]=thrusters.get(i).getMaxPower();
    	return powerArray;
    	
    }
}