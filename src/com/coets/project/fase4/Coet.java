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
    		throw new Exception("Error: La pot�ncia total ha de ser superior a la pot�ncia actual del coet."
    				+ "\nPot�ncia demanada: " + totalTargetPower + " - Pot�ncia actual: " + getCurrentPower());
    	
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
    	
    	// Check if target power is higher than current power. If higher, throw exception.
    	int totalTargetPower = IntStream.of(targetPower).sum();
    	if (totalTargetPower>=getCurrentPower())
    		throw new Exception("Error: La pot�ncia total ha de ser inferior a la pot�ncia actual del coet."
    				+ "\nPot�ncia demanada: " + totalTargetPower + " - Pot�ncia actual: " + getCurrentPower());
    	
    	System.out.println("Frenant coet " + this.codi + "...");
    	
    	// Else, modify power of each thruster
    	for (int i=0;i<targetPower.length;i++) {
   			thrusters.get(i).changePower(targetPower[i], i);
    	}    	
	};

	public boolean isChangingPower() {
		for (Thruster t: this.thrusters) {
			if (t.isChangingPower()) return true;
		}
		return false;
	}
    
    private void commonChecks(int[] targetPower) throws Exception {
    	
    	// Check if provided power array has the same number of elements as thrusters has the rocket
    	if (targetPower.length!=thrusters.size()) 
    		throw new Exception("Les pot�ncies indicades no coincideixen amb el nombre de propulsors del coet."
    				+ "\nNombre de pot�ncies indicades: " + targetPower.length
    				+ "\nNombre de propulsors del coet: " + thrusters.size());
    	
    	// Check if target power is greater than maximum power. If greater, throw exception.
    	int totalTargetPower = IntStream.of(targetPower).sum();
    	if (totalTargetPower>getMaxPower())
    		throw new Exception("Error: La pot�ncia total demanada supera la pot�ncia m�xima del coet."
    				+ "\nPot�ncia demanada: " + totalTargetPower + " - Pot�ncia m�xima: " + getMaxPower());
    	
    	// "Iterable" checks:
    	//	-There is no negative target power
    	// 	-There is no thruster accelerating nor braking
    	// 	-There is no individual target power greater than maximum thruster power
    	StringBuilder sb = new StringBuilder("Error: la pot�ncia objectiu d'un o m�s propulsors supera la seva pot�ncia m�xima.");
		boolean proceed = true;
    	for (int i=0;i<targetPower.length;i++) {
    		if (targetPower[i]<0) throw new Exception("Error: No pot haver-hi pot�ncies negatives.");
    		if (thrusters.get(i).changingPower) throw new Exception("Error: El coet est� actualment accelerant o frenant, has d'esperar que s'aturi.");
    		if (targetPower[i]>thrusters.get(i).getMaxPower()) {
    			sb.append("\nPropulsor " + (i+1) + " - Pot�ncia m�xima: " + thrusters.get(i).getMaxPower() +
    					" - Pot�ncia objectiu: " + targetPower[i]);
    			proceed = false;
    		}
    			    		    	}
    	if (!proceed) throw new Exception(sb.toString());

	}

	// Apply power increase to change the current speed to the target speed
	public void accelerate (int currentSpeed, int targetSpeed){

		// Just in case it is only possible to accelerate
		// Check if the target speed is higher than the current speed
		/*if (targetSpeed<=currentSpeed) {
			System.out.println("La velocitat final indicada ha de ser superior a l'actual");
			return;
		}*/

		if (currentSpeed==targetSpeed) return;

		// Calculate if we have to increase or decrease power
		boolean accelerate=true;
		if (currentSpeed>targetSpeed) accelerate=false;

		// Calculate available power to increase/decrease
		int availablePower;
		if (accelerate) availablePower=getMaxPower()-getCurrentPower();
		else availablePower=getCurrentPower();

		// Calculate necessary power
		// Assumption: PT is the power necessary to add/sub to the current power
		// PT = ((v-vo)/100)^2
		// As we cannot apply decimal powers to the thrusters, we get the nearest int
		int necessaryPower = (int)Math.round(Math.pow((targetSpeed-currentSpeed)/100,2));

		// Testing purposes
		System.out.println("V0: " + currentSpeed + ", Vf: " + targetSpeed);
		System.out.println("Necessary total power (increase): " + necessaryPower);
		System.out.println("Available power: " + availablePower);

		// Check if we have enough power
		if ((int)(necessaryPower-1)+1 > availablePower) {
			if (accelerate) {
				System.out.println("El coet no disposa de la pot?ncia necess?ria per a assolir aquesta velocitat.");
				System.out.println("La velocitat màxima assolible actualment és " + getMaxSpeed(currentSpeed, availablePower));
			} else {
				System.out.println("El coet no es pot frenar actualment fins a la velocitat indicada");
				System.out.println("La velocitat mínima assolible actualment és " + getMinSpeed(currentSpeed, availablePower));
			}
			return;
		}

		// Get the target power distribution amongst the thrusters
		// Policy: have all of the thrusters working at the same percentage of its maximum power
		int[] targetPower=distributePower(necessaryPower);

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

	private int[] distributePower(int necessaryPower) {

		// Save as int to avoid multiple callings to the same function
		int maxPower= getMaxPower();

		// Step 1: get an array with the percentage of the total power of each thruster
		double[] powerPercentage = new double[thrusters.size()];
		for (int i=0;i<thrusters.size();i++) {
			powerPercentage[i]=thrusters.get(i).getMaxPower()/maxPower;
		}

		// Step 2: get the target power of each thruster according to its percentage
		// Steps 1 and 2 could have be done in the same loop, done this way for clarity
		int[] result = new int[thrusters.size()];
		for (int i=0;i<thrusters.size();i++) {
			result[i]=(int)Math.round(necessaryPower*powerPercentage[i]);
		}

		// Step 3: ensure that the result array sums exactly the necessary power
		int totalPower = IntStream.of(result).sum();
		int difference = necessaryPower-totalPower;

		// If the difference is positive, we apply that difference to the most powerful thruster
		if (difference>0) {
			int strongestThruster = getStrongest(powerPercentage);
			if (thrusters.get(strongestThruster).getAvailablePower()>=difference) {
				result[strongestThruster]+=difference;
				difference=0;
			} else {
				// apply the disposable power
			}
		}

		// Otherwise, we free the weakest thruster


		eturn new int[0];
	}

	private int getMaxSpeed(int currentSpeed, int availablePower) {
		return currentSpeed + (int)(100*Math.sqrt(availablePower));
	}

	private int getMinSpeed(int currentSpeed, int availablePower) {
		return currentSpeed - (int)(100*Math.sqrt(availablePower));
	}


	private int getAvailableAccelPower() {
		return 0;
	}


}








































