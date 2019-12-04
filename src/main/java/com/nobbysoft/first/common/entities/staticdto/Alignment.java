package com.nobbysoft.first.common.entities.staticdto;

import com.nobbysoft.first.common.utils.SU;

public enum Alignment {
 LAWFUL_GOOD(0),
 LAWFUL_NEUTRAL(1),
 LAWFUL_EVIL(2),
 NEUTRAL_GOOD(3),
 NEUTRAL(4),
 NEUTRAL_EVIL(5),
 CHAOTIC_GOOD(6),
 CHAOTIC_NEUTRAL(7),
 CHAOTIC_EVIL(8);

	
	public int getIndex() {
		return index;
	}
	
	final private int index;
	final private String description;
	public String getDescription() {
		return description;
	}
	Alignment(int index){
		this.index=index;		
		description=SU.proper(name().replace("_", " "));		
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
