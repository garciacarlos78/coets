package com.coets.project;

public class CoetF2 {

    private String codi;
    private int numPropulsors;
    private int[] potencia;

    public CoetF2(String codi, int numPropulsors, int[] potencia) throws Exception {

        // Check number of character of codi
        if (codi.length()>8) throw new Exception();

        // Check we added a potencia for each propulsor
        if (potencia.length!=numPropulsors) throw new Exception();

        this.codi = codi;
        this.numPropulsors = numPropulsors;
        this.potencia = potencia;
    }

    public String getCodi() {
        return codi;
    }

    public int getNumPropulsors() {
        return numPropulsors;
    }

    public int[] getPotencia() {
        return potencia;
    }
}
