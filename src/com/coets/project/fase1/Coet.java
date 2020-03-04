package com.coets.project.fase1;

import java.util.ArrayList;
import java.util.List;

public class Coet {

    private String codi;
    private List<Thruster> thrusters;

    public Coet(String codi, int numPropulsors) throws Exception {

        // Check number of character of codi
        if (codi.length()>8) throw new Exception();

        this.codi = codi;
        this.thrusters = new ArrayList<>(3);
        for (int i=0;i<numPropulsors;i++) thrusters.add(new Thruster());
    }

    public String getCodi() {
        return codi;
    }

    public int getNumPropulsors() {
        return thrusters.size();
    }
}