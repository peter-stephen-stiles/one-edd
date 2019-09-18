package com.nobbysoft.first.common.entities.staticdto;

import com.nobbysoft.first.common.utils.SU;

public enum ClassType {
SINGLE,
MULTIPLE,
DUAL;
private String description;
public String getDescription() {
	return description;
}
ClassType(){
	description = name().replace("_", " ");	
	description=SU.proper(description);		
}
}
