package com.nobbysoft.first.common.entities.equipment;

import com.nobbysoft.first.common.utils.SU;

public enum EquipmentHands{NONE,SINGLE_HANDED,DOUBLE_HANDED;
	private String description;
	public String getDescription() {
		return description;
	}
	EquipmentHands(){
		description = name().replace("_", " ");	
		description=SU.proper(description);		
	}

}