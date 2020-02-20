package com.nobbysoft.first.common.entities.equipment;

public enum EquipmentWhere{
	HEAD("Head",1),
	NECK("Neck",2),
	SHOULDERS("Shoulders",4),
	ARM_R("Arm, Right",5),
	ARM_L("Arm, Left",6),
	HAND_R("Hand, Right",7),
	HAND_L("Hand, Left",8),
	FINGERS_R ("Fingers (Right Hand)",9),
	FINGERS_L ("Fingers (Left Hand)",10),
	TORSO("Torso",11),
	WAIST("Waist",12),
	LEGS("Legs",13),
	FEET("Feet",14),
	PACK ("Pack",16),
	OTHER("Other",17);
	
	private int index;
	private String description;
	public String getDescription() {
		return description;
	}
	EquipmentWhere(String description, int index){
		this.index=index;
		this.description=description;		
	}
	public int getIndex() {
		return index;
	}
	public String getDesc() {
		return description;
	}
	}