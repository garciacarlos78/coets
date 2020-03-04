package com.coets.project.fase4;

import static java.lang.System.exit;

public class CoetF4 {

    private String codi;
    private int numPropulsors;
    private int[] potenciaMaxima;
    private int[] potenciaActual;

    // We cannot send another accelerate/brake instruction while it's still acclerating/braking
    // We have a boolean for each engine indicating its status
    private boolean[] changingSpeed;

    public CoetF4(String codi, int numPropulsors, int[] potenciaMaxima) throws Exception {

        // Check number of characters of codi
        if (codi.length()>8) throw new Exception("Error: el codi del coet no pot tenir m�s de 8 caracters.");

        // Check if the array potenciaMaxima contains the as many values as engines has the rocket
        if (potenciaMaxima.length!=numPropulsors) throw new Exception("Error: la pot�ncia inicial indicada no " +
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

    public void increasePower(int[] powers) throws Exception {

        // Check if the engine is being accelerated/braked
        if (isChangingSpeed()) throw new Exception("Error: en aquests moments no �s possible accelerar. \nAl menys un" +
                " dels motors est� en proc�s d'acceleraci� o frenada.");

        // Check that int[] has the same number of elements as engines has the rocket
        if (powers.length!=numPropulsors) throw new Exception("Error: el nombre de pot�ncies objectiu indicades no " +
                "coincideix amb el nombre de propulsors del coet.");

        // Check that there is no any power below the current power
        for (int i=0;i<numPropulsors;i++) {
            if (powers[i]<potenciaActual[i]) throw new Exception("Error: s'ha indicat una pot�ncia inferior a l'actual" +
                    " al propulsor " + i);
        }

        // Create a new thread for each engine to increase the current power
        for (int i=0;i<numPropulsors;i++) {
            new accelerateThread(i, powers[i]).start();
            // We indicate that the rocket engine is changing its power
            changingSpeed[i]=true;
        }
    }

    public void brake (int[] powers) throws Exception {

        // Check if the engine is being accelerated/braked
        if (isChangingSpeed()) throw new Exception("Error: en aquests moments no �s possible frenar. \nAl menys un" +
                " dels motors est� en proc�s d'acceleraci� o frenada.");

        // Check that int[] has the same number of elements as engines has the rocket
        if (powers.length!=numPropulsors) throw new Exception("Error: el nombre de pot�ncies objectiu indicades no " +
                "coincideix amb el nombre de propulsors del coet.");

        // Check that there is no any indicated power above the current power
        for (int i=0;i<numPropulsors;i++) {
            if (powers[i]>potenciaActual[i]) throw new Exception("Error: s'ha indicat una pot�ncia superior a l'actual" +
                    " al propulsor " + i);
        }

        // Create a new thread for each engine to increase the current power
        for (int i=0;i<numPropulsors;i++) {
            new brakeThread(i, powers[i]).start();
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

    // Apply power increase to change the current speed to the target speed
    public void accelerate (int currentSpeed, int targetSpeed){

        // Check if the target speed is higher than the current speed
        if (targetSpeed<=currentSpeed) {
            System.out.println("La velocitat final indicada ha de ser superior a l'actual");
            exit(0);
        }

        // Calculate available power
        int availablePower=0;
        for (int i=0;i<numPropulsors;i++) {
            availablePower+=potenciaMaxima[i]-potenciaActual[i];
        }

        // Calculate necessary power
        // Assumption: PT is the power increase necessary to add to the current power
        // PT = ((v-vo)/100)�
        double necessaryPower = Math.pow((targetSpeed-currentSpeed)/100,2);

        // Testing purposes
        System.out.println("V0: " + currentSpeed + ", Vf: " + targetSpeed);
        System.out.println("Necessary total power (increase): " + necessaryPower);
        System.out.println("Available power: " + availablePower);

        // Check if we have enough power
        if ((int) necessaryPower+1 > availablePower) {
            System.out.println("El coet no disposa de la pot�ncia necess�ria per a assolir aquesta velocitat.");
            exit(0);
        }

        // Make a copy of the potenciaActual array
        int[] targetPower=potenciaActual.clone();

        // Calculate final distribution of the power amongst the engines
        // Distribution pattern: KISS
        int distributedPower=0;
        int engineNumber=0;
        while (distributedPower < necessaryPower) {
            if (targetPower[engineNumber]<potenciaMaxima[engineNumber]) {
                targetPower[engineNumber]++;
                distributedPower++;
            }
            engineNumber++;
            engineNumber%=numPropulsors;
        }

        // Show current power distribution and target power distribution
        System.out.println("Current power distribution: " + showIntArray(potenciaActual));
        System.out.println("Target power distribution: " + showIntArray(targetPower));

        // Increase power to get the target power distribution
        try {
            this.increasePower(targetPower);
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }

    public String getCurrentPower() {
        return showIntArray(potenciaActual);
    }

    private String showIntArray(int[] intArray) {
        StringBuilder sb = new StringBuilder("[");
        for (int i=0;i<numPropulsors;i++) {
            sb.append(intArray[i]);
            if (i<numPropulsors-1) sb.append(" - ");
        }
        sb.append("]");
        return sb.toString();
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
