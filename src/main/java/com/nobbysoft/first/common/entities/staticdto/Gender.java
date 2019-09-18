package com.nobbysoft.first.common.entities.staticdto;

import com.nobbysoft.first.common.utils.SU;

public enum Gender {
MALE,
FEMALE;
	private String description;
	public String getDescription() {
		return description;
	}
	Gender(){
		description = name().replace("_", " ");	
		description=SU.proper(description);		
	}
}
