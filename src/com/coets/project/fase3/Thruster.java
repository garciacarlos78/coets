package com.coets.project.fase3;

public class Thruster {

	private int maxPower;
	private int currentPower;

	// It allows us to know if the thruster is currently changing its power
	boolean changingPower = false;

	public Thruster(int maxPower) {
		super();
		this.maxPower = maxPower;
		this.currentPower=0;
	}

	public int getMaxPower() {
		return maxPower;
	}

	public int getCurrentPower() { return currentPower;}

	public void changePower(int targetPower, int thrusterNumber) {

		// Check if targetPower is different from current power
		if (targetPower==currentPower) return;
		else if (targetPower>currentPower) new accelerateThread(targetPower, thrusterNumber).start();
		else new Thread(new brakeThread(targetPower, thrusterNumber)).start();
//		System.out.println("*************** RETURNING FROM changePower Thruster #" + thrusterNumber + " ******************");
	}

	private class accelerateThread extends Thread {

		private int targetPower;
		private int thrusterNumber;

		public accelerateThread(int targetPower, int thrusterNumber) {
			this.targetPower = targetPower;
			this.thrusterNumber = thrusterNumber;
		}

		@Override
		public void run() {

			// Mark the thruster as changing power
			changingPower = true;

			System.out.println("Accelerating engine number " + thrusterNumber +"... Current power: "
					+ currentPower + " - Maximum power: " + maxPower +
					" - Target power: " + targetPower);

			while (currentPower<targetPower)
			{
				System.out.println("Increasing power... Engine " + thrusterNumber + " - Current power: " +
						++currentPower);
			}

			System.out.println("Engine " + thrusterNumber + ": Target power achieved!!!");

			// We indicate that this engine has finished accelerating
			changingPower=false;
		}
	}

	private class brakeThread implements Runnable {

		private int targetPower;
		private int thrusterNumber;

		public brakeThread(int targetPower, int thrusterNumber) {
			this.targetPower = targetPower;
			this.thrusterNumber = thrusterNumber;
		}

		@Override
		public void run() {

			// Mark the thruster as changing power
			changingPower = true;

			System.out.println("Braking engine number " + thrusterNumber +"... Current power: "
					+ currentPower + " - Maximum power: " + maxPower +
					" - Goal power: " + targetPower);

			while (currentPower>targetPower)
			{
				System.out.println("Decreasing power... Engine " + thrusterNumber + " - Current power: " +
						--currentPower);
			}

			System.out.println("Engine " + thrusterNumber + ": Target power achieved!!!");

			// We indicate that this engine has finished accelerating
			changingPower=false;
		}
	}
}