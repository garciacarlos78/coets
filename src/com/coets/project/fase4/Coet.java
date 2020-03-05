package com.coets.project.fase4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.System.exit;

public class Coet {

    private String codi;
    private List<Thruster> thrusters;

    public Coet(String codi, int[] thrustersPower) throws Exception {

        // Check number of character of codi
        if (codi.length() > 8) throw new Exception();

        this.codi = codi;
        this.thrusters = new ArrayList<>(3);
        for (int i = 0; i < thrustersPower.length; i++) thrusters.add(new Thruster(thrustersPower[i]));
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
        for (int i = 0; i < powerArray.length; i++) powerArray[i] = thrusters.get(i).getMaxPower();
        return powerArray;
    }

    public int[] getCurrentPowerArray() {
        int[] powerArray = new int[thrusters.size()];
        for (int i = 0; i < powerArray.length; i++) powerArray[i] = thrusters.get(i).getCurrentPower();
        return powerArray;
    }

    // Fase3 add: get total max power
    public int getMaxPower() {
        return Arrays.stream(getMaxPowerArray()).sum();
    }

    // Fase3 add: get the current power
    private int getCurrentPower() {
        int currentPower = 0;
        for (int i = 0; i < thrusters.size(); i++) currentPower += thrusters.get(i).getCurrentPower();
        return currentPower;
    }

    public void accelerar(int[] targetPower) throws Exception {

        // Common checks: checks needed when accelerating and when braking
        commonChecks(targetPower);

        // Check if target power is greater than current power. If not greater, throw exception.
        int totalTargetPower = IntStream.of(targetPower).sum();
        if (totalTargetPower <= getCurrentPower())
            throw new Exception("Error: La potència total ha de ser superior a la potència actual del coet."
                    + "\npotència demanada: " + totalTargetPower + " - potència actual: " + getCurrentPower());

        System.out.println("Accelerant coet " + this.codi + "...");

        // Else, modify power of each thruster
        for (int i = 0; i < targetPower.length; i++) {
//			System.out.println("*************** Sending acceleration order to thruster #" + i + " ******************");
            thrusters.get(i).changePower(targetPower[i], i);
//			System.out.println("*************** Sent acceleration order to thruster #" + i + " ******************");
        }
    }

    ;

    public void frenar(int[] targetPower) throws Exception {

        // Common checks: checks needed when accelerating and when braking
        commonChecks(targetPower);

        // Check if target power is higher than current power. If higher, throw exception.
        int totalTargetPower = IntStream.of(targetPower).sum();
        if (totalTargetPower >= getCurrentPower())
            throw new Exception("Error: La potència total ha de ser inferior a la potència actual del coet."
                    + "\npotència demanada: " + totalTargetPower + " - potència actual: " + getCurrentPower());

        System.out.println("Frenant coet " + this.codi + "...");

        // Else, modify power of each thruster
        for (int i = 0; i < targetPower.length; i++) {
            thrusters.get(i).changePower(targetPower[i], i);
        }
    }

    ;

    public boolean isChangingPower() {
        for (Thruster t : this.thrusters) {
            if (t.isChangingPower()) return true;
        }
        return false;
    }

    private void commonChecks(int[] targetPower) throws Exception {

        // Check if provided power array has the same number of elements as thrusters has the rocket
        if (targetPower.length != thrusters.size())
            throw new Exception("Les potències indicades no coincideixen amb el nombre de propulsors del coet."
                    + "\nNombre de potències indicades: " + targetPower.length
                    + "\nNombre de propulsors del coet: " + thrusters.size());

        // Check if target power is greater than maximum power. If greater, throw exception.
        int totalTargetPower = IntStream.of(targetPower).sum();
        if (totalTargetPower > getMaxPower())
            throw new Exception("Error: La potència total demanada supera la potència màxima del coet."
                    + "\npotència demanada: " + totalTargetPower + " - potència màxima: " + getMaxPower());

        // "Iterable" checks:
        //	-There is no negative target power
        // 	-There is no thruster accelerating nor braking
        // 	-There is no individual target power greater than maximum thruster power
        StringBuilder sb = new StringBuilder("Error: la potència objectiu d'un o màs propulsors supera la seva potència màxima.");
        boolean proceed = true;
        for (int i = 0; i < targetPower.length; i++) {
            if (targetPower[i] < 0) throw new Exception("Error: No pot haver-hi potències negatives.");
            if (thrusters.get(i).changingPower)
                throw new Exception("Error: El coet està actualment accelerant o frenant, has d'esperar que s'aturi.");
            if (targetPower[i] > thrusters.get(i).getMaxPower()) {
                sb.append("\nPropulsor " + (i + 1) + " - potència màxima: " + thrusters.get(i).getMaxPower() +
                        " - potència objectiu: " + targetPower[i]);
                proceed = false;
            }
        }
        if (!proceed) throw new Exception(sb.toString());

    }

    // Apply power increase/decrease to change the current speed to the target speed
    public void accelerate(int currentSpeed, int targetSpeed) throws Exception {

        // Just in case it is only possible to accelerate
        // Check if the target speed is higher than the current speed
		/*if (targetSpeed<=currentSpeed) {
			System.out.println("La velocitat final indicada ha de ser superior a l'actual");
			return;
		}*/

        if (currentSpeed == targetSpeed) return;

        // Ensure the rocket is not changing its power
        if (isChangingPower()) throw new Exception("Error: El cohet està modificant la seva potència.");

        // Calculate if we have to increase or decrease power
        boolean accelerate = true;
        if (currentSpeed > targetSpeed) accelerate = false;

        // Calculate available power to increase/decrease
        int availablePower;
        if (accelerate) availablePower = getMaxPower() - getCurrentPower();
        else availablePower = getCurrentPower();

        // Calculate necessary power
        // Assumption: PT is the power necessary to add/sub to the current power
        // PT = ((v-vo)/100)^2
        // As we cannot apply decimal powers to the thrusters, we get the nearest int
        int necessaryPower = (int) Math.round(Math.pow((targetSpeed - currentSpeed) / 100, 2));

        // Testing purposes
        System.out.println("===================== Speed change ordered ======================");
        System.out.println("V0: " + currentSpeed + ", Vf: " + targetSpeed);
        System.out.println("Necessary total power (increase/decrease): " + necessaryPower);
        System.out.println("Available power: " + availablePower);

        // Check if we have enough power
        if ((int) (necessaryPower - 1) + 1 > availablePower) {
            if (accelerate) {
                System.out.println("El coet no disposa de la potència necess?ria per a assolir aquesta velocitat.");
                System.out.println("La velocitat màxima assolible actualment és " + getMaxSpeed(currentSpeed, availablePower));
            } else {
                System.out.println("El coet no es pot frenar actualment fins a la velocitat indicada");
                System.out.println("La velocitat mínima assolible actualment és " + getMinSpeed(currentSpeed, availablePower));
            }
            return;
        }

        // Get the target power distribution amongst the thrusters
        // Policy: have all of the thrusters working at the same percentage of its maximum power
        int[] targetPower = distributePower(necessaryPower);

        // Show current power distribution and target power distribution
        System.out.println("Current power distribution: " + Arrays.toString(getCurrentPowerArray()));
        System.out.println("Target power distribution: " + Arrays.toString(targetPower));

        // Send target power array to the rocket
        if (accelerate) accelerar(targetPower);
        else frenar(targetPower);
    }

    private int[] distributePower(int necessaryPower) {

        // Save as int to avoid multiple callings to the same function
        int maxPower = getMaxPower();

        // Step 1: get an array with the percentage of the total power of each thruster
        double[] powerPercentage = new double[thrusters.size()];
        for (int i = 0; i < thrusters.size(); i++) {
            powerPercentage[i] = (double) thrusters.get(i).getMaxPower() / maxPower;
        }

        // Step 2: get the target power of each thruster according to its percentage
        // Steps 1 and 2 could have be done in the same loop, done this way for clarity
        int[] result = new int[thrusters.size()];
        for (int i = 0; i < thrusters.size(); i++) {
            result[i] = (int) Math.round(necessaryPower * powerPercentage[i]);
        }

        // Step 3: ensure that the result array sums exactly the necessary power
        int totalPower = IntStream.of(result).sum();
        int difference = necessaryPower - totalPower;

        // If the difference is positive, we apply that difference to the most powerful thruster
        // We iterate in case with the most powerful thruster is not enough
        while (difference > 0) {
            int strongestThruster = getStrongest(powerPercentage);
            if (thrusters.get(strongestThruster).getAvailablePower() >= difference) {
                result[strongestThruster] += difference;
                difference = 0;
            } else {
                // apply the disposable power to current thruster and get next strongest
                difference -= thrusters.get(strongestThruster).getAvailablePower();
                result[strongestThruster] = thrusters.get(strongestThruster).getMaxPower();
                // put its percentage to -1 to avoid getting selected again as strongest thruster
                powerPercentage[strongestThruster] = -1;
            }
        }

        // If the difference is negative (we have applied more power than needed), we free the weakest thruster
        while (difference < 0) {
            int weakestThruster = getWeakest(powerPercentage);
            if (thrusters.get(weakestThruster).getCurrentPower() >= difference) {
                result[weakestThruster] -= difference;
                difference = 0;
            } else {
                // free the disposable power to current thruster and get next weakest
                difference += thrusters.get(weakestThruster).getCurrentPower();
                result[weakestThruster] = 0;
                // put its percentage to 2 to avoid getting selected again as strongest thruster
                powerPercentage[weakestThruster] = 2;
            }
        }

        // At this point, we have in result[] the target power distribution to achieve the target speed
        return result;
    }

    private int getWeakest(double[] powerPercentage) {

        int weakest = 0;
        double power = powerPercentage[0];

        for (int i = 1; i < powerPercentage.length; i++) {
            if (powerPercentage[i] < power) {
                weakest = i;
                power = powerPercentage[i];
            }
        }
        return weakest;
    }

    private int getStrongest(double[] powerPercentage) {

        int strongest = 0;
        double power = powerPercentage[0];

        for (int i = 1; i < powerPercentage.length; i++) {
            if (powerPercentage[i] > power) {
                strongest = i;
                power = powerPercentage[i];
            }
        }
        return strongest;
    }

    private int getMaxSpeed(int currentSpeed, int availablePower) {
        return currentSpeed + (int) (100 * Math.sqrt(availablePower));
    }

    private int getMinSpeed(int currentSpeed, int availablePower) {
        return currentSpeed - (int) (100 * Math.sqrt(availablePower));
    }


    private int getAvailableAccelPower() {
        return 0;
    }


}








































