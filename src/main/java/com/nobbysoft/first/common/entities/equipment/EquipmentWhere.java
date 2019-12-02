package com.nobbysoft.first.common.entities.equipment;

import com.nobbysoft.first.common.utils.SU;

public enum EquipmentWhere{
	HAND_R("Hand, Right"),
	HAND_L("Hand, Left"),
	HEAD("Head"),
	NECK("Neck"),
	TORSO("Torso"),
	WAIST("Waist"),
	ARM_R("Arm, Right"),
	ARM_L("Arm, Left"),
	LEGS("Legs"),
	FOOT_R("Foot, Right"),
	FOOT_L("Foot, Left"),
	PACK ("Pack"),
	OTHER("Other");
	
	private String description;
	public String getDescription() {
		return description;
	}
	EquipmentWhere(String description){
		
		this.description=description;		
	}
	
	public String getDesc() {
		return description;
	}
	}