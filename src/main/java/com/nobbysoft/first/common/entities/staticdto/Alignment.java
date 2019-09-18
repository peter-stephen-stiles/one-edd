package com.nobbysoft.first.common.entities.staticdto;

import com.nobbysoft.first.common.utils.SU;

public enum Alignment {
 LAWFUL_GOOD,
 LAWFUL_NEUTRAL,
 LAWFUL_EVIL,
 NEUTRAL_GOOD,
 NEUTRAL,
 NEUTRAL_EVIL,
 CHAOTIC_GOOD,
 CHAOTIC_NEUTRAL,
 CHAOTIC_EVIL;

	private String description;
	public String getDescription() {
		return description;
	}
	Alignment(){
		description = name().replace("_", " ");	
		description=SU.proper(description);		
	}
	
	public boolean isGood() {
		return this.name().indexOf("GOOD")>=0;
	}
	public boolean isEvil() {
		return this.name().indexOf("EVIL")>=0;
	}
	
	public boolean isLawful() {
		return this.name().indexOf("LAWFUL")>=0;
	}
	public boolean isChaotic() {
		return this.name().indexOf("CHAOTIC")>=0;
	}
}
