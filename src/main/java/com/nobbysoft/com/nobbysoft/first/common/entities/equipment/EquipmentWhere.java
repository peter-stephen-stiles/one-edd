package com.nobbysoft.com.nobbysoft.first.common.entities.equipment;

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
	
	public String getDesc() {
		return name();
	}
	}