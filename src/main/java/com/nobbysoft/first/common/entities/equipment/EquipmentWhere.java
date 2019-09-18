package com.nobbysoft.first.common.entities.equipment;

import com.nobbysoft.first.common.utils.SU;

public enum EquipmentWhere{
	HAND_R,
	HAND_L,
	HEAD,
	NECK,
	TORSO,
	WAIST,
	ARM_R,
	ARM_L,
	LEGS,
	FOOT_R,
	FOOT_L,
	PACK,
	OTHER;
	
	private String description;
	public String getDescription() {
		return description;
	}
	EquipmentWhere(){
		description = name().replace("_", " ");	
		description=SU.proper(description);		
	}
	
	public String getDesc() {
		return description;
	}
	}