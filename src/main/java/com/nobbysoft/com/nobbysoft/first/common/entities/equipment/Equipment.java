package com.nobbysoft.com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;

public class Equipment  implements Serializable{
	
private String name;
private HANDS requiresHands=HANDS.NONE;
private int capacityGP=0;
private int encumberanceGP=0;

public String getName() {
	return name;
}

public Equipment(String name, int capacityGP, int encumberanceGP, HANDS requiresHands) {
	super();
	this.name = name;
	this.capacityGP = capacityGP;
	this.encumberanceGP = encumberanceGP;
	this.requiresHands = requiresHands;
}

public void setName(String name) {
	this.name = name;
}

public HANDS getRequiresHands() {
	return requiresHands;
}

public void setRequiresHands(HANDS requiresHands) {
	this.requiresHands = requiresHands;
}

public int getCapacityGP() {
	return capacityGP;
}

public void setCapacityGP(int capacityGP) {
	this.capacityGP = capacityGP;
}

public int getEncumberanceGP() {
	return encumberanceGP;
}

public void setEncumberanceGP(int encumberanceGP) {
	this.encumberanceGP = encumberanceGP;
}

public enum WHERE{HAND_R,HAND_L,HEAD,NECK,TORSO,WAIST,ARM_R,ARM_L,LEGS,FOOT_R,FOOT_L,PACK,OTHER_OR_NOT};

public enum HANDS{NONE,SINGLE_HANDED,DOUBLE_HANDED};


}
