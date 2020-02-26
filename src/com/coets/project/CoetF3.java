package com.coets.project;

public class CoetF3 {

    private String codi;
    private int numPropulsors;
    private int[] potenciaMaxima;
    private int[] potenciaActual;

    // We cannot send another accelerate/brake instruction while it's still acclerating/braking
    // We have a boolean for each engine indicating its status
    private boolean[] changingSpeed;

    public CoetF3(String codi, int numPropulsors, int[] potenciaMaxima) throws Exception {

        // Check number of characters of codi
        if (codi.length()>8) throw new Exception("Error: el codi del coet no pot tenir més de 8 caracters.");

        // Check if the array potenciaMaxima contains the as many values as engines has the rocket
        if (potenciaMaxima.length!=numPropulsors) throw new Exception("Error: la potència inicial indicada no " +
                "coincideix amb el nombre de motors del coet.");

        // Initialize members
        this.codi = codi;
        this.numPropulsors = numPropulsors;
        this.potenciaMaxima = potenciaMaxima;
        this.potenciaActual = new int[numPropulsors];
        this.changingSpeed = new boolean[numPropulsors];
        // Initialize current power to 0 and engine status to false (not changing speed)
        for(int i=0;i<numPropulsors;i++) {
            potenciaActual[i]=0;
            changingSpeed[i]=false;
        }
    }

    public String getCodi() {
        return codi;
    }

    public int getNumPropulsors() {
        return numPropulsors;
    }

    public int[] getPotenciaMaxima() {
        return potenciaMaxima;
    }

    public void accelerate (int[] powers) throws Exception {

        // Check if the engine is being accelerated/braked
        if (isChangingSpeed()) throw new Exception("Error: en aquests moments no és possible accelerar. \nAl menys un" +
                " dels motors està en procès d'acceleració o frenada.");

        // Check that int[] has the same number of elements as engines has the rocket
        if (powers.length!=numPropulsors) throw new Exception("Error: el nombre de potències objectiu indicades no " +
                "coincideix amb el nombre de propulsors del coet.");

        // Check that there is no any power below the current power
        for (int i=0;i<numPropulsors;i++) {
            if (powers[i]<potenciaActual[i]) throw new Exception("Error: s'ha indicat una potència inferior a l'actual" +
                    " al propulsor " + i);
        }

        // Create a new thread for each engine to increase the current power
        for (int i=0;i<numPropulsors;i++) {
            new accelerateThread(i, powers[i]).start();
            // We indicate that the rocket engine is changing its power
            changingSpeed[i]=true;
        }
    }

    // Return true if the rocket is accelerating/braking, false otherwise
    public boolean isChangingSpeed() {
        for (int i=0;i<numPropulsors;i++) {
            if (changingSpeed[i]==true) return true;
        }
        return false;
    }

    private class accelerateThread extends Thread {

        private int goalPower;
        private int engineNumber;

        public accelerateThread(int engineNumber, int goalPower) {
            this.goalPower = goalPower;
            this.engineNumber = engineNumber;
        }

        @Override
        public void run() {
            System.out.println("Accelerating engine number " + engineNumber +". Current power: "
                    + potenciaActual[engineNumber] + ". Maximum power: " + potenciaMaxima[engineNumber] +
                    ". Goal power: " + goalPower);

            while (potenciaActual[engineNumber]<goalPower && potenciaActual[engineNumber]<potenciaMaxima[engineNumber])
            {
                System.out.println("Increasing power... Engine " + engineNumber + ". Current power: " +
                        ++potenciaActual[engineNumber]);
            }

            if (goalPower>potenciaMaxima[engineNumber]) System.out.println("The engine " + engineNumber +
                    " has reached its maximum power ("+ potenciaMaxima[engineNumber] + ").");
            else System.out.println("Engine " + engineNumber + ": Goal power achieved!!!");

            // We indicate that this engine has finished accelerating
            changingSpeed[engineNumber]=false;
        }
    }

    public void brake (int[] powers) throws Exception {

        // Check if the engine is being accelerated/braked
        if (isChangingSpeed()) throw new Exception("Error: en aquests moments no és possible frenar. \nAl menys un" +
                " dels motors està en procès d'acceleració o frenada.");

        // Check that int[] has the same number of elements as engines has the rocket
        if (powers.length!=numPropulsors) throw new Exception("Error: el nombre de potències objectiu indicades no " +
                "coincideix amb el nombre de propulsors del coet.");

        // Check that there is no any indicated power above the current power
        for (int i=0;i<numPropulsors;i++) {
            if (powers[i]>potenciaActual[i]) throw new Exception("Error: s'ha indicat una potència superior a l'actual" +
                    " al propulsor " + i);
        }

        // Create a new thread for each engine to increase the current power
        for (int i=0;i<numPropulsors;i++) {
            new brakeThread(i, powers[i]).start();
            // We indicate that the rocket engine is changing its power
            changingSpeed[i]=true;
        }
    }

    private class brakeThread extends Thread {

        private int goalPower;
        private int engineNumber;

        public brakeThread(int engineNumber, int goalPower) {
            this.goalPower = goalPower;
            this.engineNumber = engineNumber;
        }

        @Override
        public void run() {
            System.out.println("Braking engine number " + engineNumber + " Current power: " +
                    potenciaActual[engineNumber] +" Goal power: " + goalPower);

            while (potenciaActual[engineNumber]>goalPower) {
                System.out.println("Decreasing power... Engine " + engineNumber + ". Current power: " + --potenciaActual[engineNumber]);
            }

            System.out.println("Engine " + engineNumber + ": Goal power achieved!!!");

            // We indicate that this engine has finished braking
            changingSpeed[engineNumber]=false;
        }
    }
}
