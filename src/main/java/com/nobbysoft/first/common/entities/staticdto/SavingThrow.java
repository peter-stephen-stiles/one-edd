package com.nobbysoft.first.common.entities.staticdto;

import com.nobbysoft.first.common.utils.SU;

public enum SavingThrow {
PARALYSATION_POISON_OR_DEATH_MAGIC(false),
PETRIFICATION_OR_POLYMORPH(false),
ROD_STAFF_OR_WAND(true),
BREATH_WEAPON(false),
SPELL(true);
	
	private String description;
	public String getDescription() {
		return description;
	}
	
	
	private final boolean racialBonusApplies;
	private SavingThrow(boolean racialBonusApplies){
		this.racialBonusApplies=racialBonusApplies;
		description = name().replace("_", " ");	
		description=SU.proper(description);
	}
	public boolean isRacialBonusApplies() {
		return racialBonusApplies;
	}
}
