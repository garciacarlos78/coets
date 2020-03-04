package com.coets.project.fase3;

public class Thruster {
	
	private int maxPower;
	private int currentPower;

	public Thruster(int maxPower) {
		super();
		this.maxPower = maxPower;
		this.currentPower=0;
	}

	public int getMaxPower() {
		return maxPower;
	}
	
	public int getCurrentPower() { return currentPower;}
}
