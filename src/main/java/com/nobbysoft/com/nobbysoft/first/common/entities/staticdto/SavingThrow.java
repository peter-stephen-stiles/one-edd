package com.nobbysoft.com.nobbysoft.first.common.entities.staticdto;

public enum SavingThrow {
PARALYSATION_POISON_OR_DEATH_MAGIC(false),
PETRIFICATION_OR_POLYMORPH(false),
ROD_STAFF_OR_WAND(true),
BREATH_WEAPON(false),
SPELL(true);
	private final boolean racialBonusApplies;
	private SavingThrow(boolean racialBonusApplies){
		this.racialBonusApplies=racialBonusApplies;
	}
	public boolean isRacialBonusApplies() {
		return racialBonusApplies;
	}
}
