package com.nobbysoft.first.common.entities.pc;

public class PlayerCharacterLevel {

	private String thisClass=null;
	
	private int thisClassLevel=0;

	private int thisClassExperience=0;

	private int thisClassHp=0;

	public PlayerCharacterLevel() { 
	}
	
	public PlayerCharacterLevel(String thisClass, int thisClassLevel, int thisClassExperience, int thisClassHp) {
		super();
		this.thisClass = thisClass;
		this.thisClassLevel = thisClassLevel;
		this.thisClassExperience = thisClassExperience;
		this.thisClassHp = thisClassHp;
	}



	public String getThisClass() {
		return thisClass;
	}

	public void setThisClass(String thisClass) {
		this.thisClass = thisClass;
	}

	public int getThisClassLevel() {
		return thisClassLevel;
	}

	public void setThisClassLevel(int thisClassLevel) {
		this.thisClassLevel = thisClassLevel;
	}

	public int getThisClassExperience() {
		return thisClassExperience;
	}

	public void setThisClassExperience(int thisClassExperience) {
		this.thisClassExperience = thisClassExperience;
	}

	public int getThisClassHp() {
		return thisClassHp;
	}

	public void setThisClassHp(int thisClassHp) {
		this.thisClassHp = thisClassHp;
	}

}
