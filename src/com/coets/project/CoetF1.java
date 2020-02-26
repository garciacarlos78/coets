package com.coets.project;

public class CoetF1 {

    private String codi;
    private int numPropulsors;

    public CoetF1(String codi, int numPropulsors) throws Exception {

        // Check number of character of codi
        if (codi.length()>8) throw new Exception();

        this.codi = codi;
        this.numPropulsors = numPropulsors;
    }

    public String getCodi() {
        return codi;
    }

    public int getNumPropulsors() {
        return numPropulsors;
    }
}
