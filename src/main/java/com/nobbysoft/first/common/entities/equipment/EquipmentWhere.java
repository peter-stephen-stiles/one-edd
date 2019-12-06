package com.nobbysoft.first.common.entities.equipment;

public enum EquipmentWhere{
	HAND_R("Hand, Right",6),
	HAND_L("Hand, Left",7),
	HEAD("Head",1),
	NECK("Neck",2),
	TORSO("Torso",3),
	WAIST("Waist",8),
	ARM_R("Arm, Right",4),
	ARM_L("Arm, Left",5),
	LEGS("Legs",9),
	FOOT_R("Foot, Right",10),
	FOOT_L("Foot, Left",11),
	PACK ("Pack",12),
	OTHER("Other",13);
	
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