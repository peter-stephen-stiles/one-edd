package com.nobbysoft.first.common.entities.staticdto.attributes;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.staticdto.AbilityScoreI;
import com.nobbysoft.first.common.utils.SU;

public class Intelligence implements AbilityScoreI,Comparable<Intelligence>, Serializable, DataDTOInterface<Integer>{

	private int abilityScore; // 0 - 18
	private int possibleAdditionalLanguages; // 0- 7
	private int chanceToKnowSpell; // 0 > 95 in 5s
	private int minSpellsPerLevel; // 0 - 10
	private int maxSpellsPerLevel; // 0 - 18 - ALL
	

	public Intelligence() { 
	}
	

	@Override
	public Integer getKey() {
		return abilityScore;
	}

	@Override
	public String getDescription() {
		return Integer.toString(abilityScore);
	}
	
	@Override
	public int getAbilityScore() {
		return abilityScore;
	}

	@Override
	public void setAbilityScore(int abilityScore) {
		this.abilityScore=abilityScore;		
	}


	public int getPossibleAdditionalLanguages() {
		return possibleAdditionalLanguages;
	}


	public void setPossibleAdditionalLanguages(int possibleAdditionalLanguages) {
		this.possibleAdditionalLanguages = possibleAdditionalLanguages;
	}


	public int getChanceToKnowSpell() {
		return chanceToKnowSpell;
	}


	public void setChanceToKnowSpell(int chanceToKnowSpell) {
		this.chanceToKnowSpell = chanceToKnowSpell;
	}


	public int getMinSpellsPerLevel() {
		return minSpellsPerLevel;
	}


	public void setMinSpellsPerLevel(int minSpellsPerLevel) {
		this.minSpellsPerLevel = minSpellsPerLevel;
	}


	public int getMaxSpellsPerLevel() {
		return maxSpellsPerLevel;
	}


	public void setMaxSpellsPerLevel(int maxSpellsPerLevel) {
		this.maxSpellsPerLevel = maxSpellsPerLevel;
	}

	

	@Override
	public Object[] getAsRow() {
		return new Object[] {this,abilityScore, 
			SU.a(possibleAdditionalLanguages),
			SU.p(chanceToKnowSpell),
			minSpellsPerLevel,
			maxSpellsPerLevel
			};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"INT",
				"Add Lang.",
				"MU: % Know Spell",
				"MU: Min Spell/Lvl",
				"MU: Max Spell/Lvl"};
	}
	@Override
	public Integer[] getColumnWidths() {
		// TODO Auto-generated method stub
		return new Integer[] {50,70,120,120,120};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		// TODO Auto-generated method stub
		return new String[] {null,null,null,null,null,};
	}


	@Override
	public int compareTo(Intelligence o) {
		if(abilityScore>o.abilityScore) {
			return 1;
		}
		if(o.abilityScore>abilityScore) {
			return -1;
		}
		return 0;
	}
	
}
