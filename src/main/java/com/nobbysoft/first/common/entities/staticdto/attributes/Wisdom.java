package com.nobbysoft.first.common.entities.staticdto.attributes;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.staticdto.AbilityScoreI;
import com.nobbysoft.first.common.utils.SU;

public class Wisdom  implements AbilityScoreI,Comparable<Wisdom>, Serializable, DataDTOInterface<Integer>{

	private int abilityScore;
	
	private int divineSpellChanceFailure;
	private int divineSpellBonusSpellLevel;
	private int divineMaxSpellLevel;
	
	private int magicalAttackAdjustment;
	

	
	public Wisdom() { 
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
	public Object[] getAsRow() {
		return new Object[] {this,
				abilityScore, 
				getMagicalAttackAdjustmentString(),
				getSpellBonusString(),
				SU.p(divineSpellChanceFailure), 
				getSpellMaxSpellLevelString()};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"WIS",
				"Mag Att Adj",
				"Spell Bonus (lvl)",
				"Spell Failure %",
				"Max Spell Lvl"};
	}

	public String getMagicalAttackAdjustmentString() {
		if(magicalAttackAdjustment==0) {
			return "none";
		} else {
			return ""+magicalAttackAdjustment;
		}
	}
	
	
	public String getSpellMaxSpellLevelString() {
		if(divineMaxSpellLevel==0) {
			return "";
		} else {
			return ""+divineMaxSpellLevel;
		}
	}
	
 
	
	public String getSpellBonusString() {
		if (divineSpellBonusSpellLevel<1) {
			return "";
		}else {
			return "+1 lvl "+divineSpellBonusSpellLevel+" spell";
		}
	}
	
	@Override
	public Integer[] getColumnWidths() {
		// TODO Auto-generated method stub
		return new Integer[] {50,100,100,100,100};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		// TODO Auto-generated method stub
		return new String[] {null,null,null,null,null,};
	}

	@Override
	public int compareTo(Wisdom o) {
		if(abilityScore>o.abilityScore) {
			return 1;
		}
		if(o.abilityScore>abilityScore) {
			return -1;
		}
		return 0;
	}

	@Override
	public int getAbilityScore() {
		return abilityScore;
	}

	@Override
	public void setAbilityScore(int abilityScore) {
		this.abilityScore=abilityScore;		
	}

	public int getDivineSpellChanceFailure() {
		return divineSpellChanceFailure;
	}

	public void setDivineSpellChanceFailure(int divineSpellChanceFailure) {
		this.divineSpellChanceFailure = divineSpellChanceFailure;
	}

	public int getDivineSpellBonusSpellLevel() {
		return divineSpellBonusSpellLevel;
	}

	public void setDivineSpellBonusSpellLevel(int divineSpellBonusSpellLevel) {
		this.divineSpellBonusSpellLevel = divineSpellBonusSpellLevel;
	}

	public int getDivineMaxSpellLevel() {
		return divineMaxSpellLevel;
	}

	public void setDivineMaxSpellLevel(int divineMaxSpellLevel) {
		this.divineMaxSpellLevel = divineMaxSpellLevel;
	}

	public void setMagicalAttackAdjustment(int magicalAttackAdjustment) {
		this.magicalAttackAdjustment = magicalAttackAdjustment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + abilityScore;
		result = prime * result + divineMaxSpellLevel;
		result = prime * result + divineSpellBonusSpellLevel;
		result = prime * result + divineSpellChanceFailure;
		result = prime * result + magicalAttackAdjustment;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wisdom other = (Wisdom) obj;
		if (abilityScore != other.abilityScore)
			return false;
		if (divineMaxSpellLevel != other.divineMaxSpellLevel)
			return false;
		if (divineSpellBonusSpellLevel != other.divineSpellBonusSpellLevel)
			return false;
		if (divineSpellChanceFailure != other.divineSpellChanceFailure)
			return false;
		if (magicalAttackAdjustment != other.magicalAttackAdjustment)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Wisdom [abilityScore=" + abilityScore + ", divineSpellChanceFailure=" + divineSpellChanceFailure
				+ ", divineSpellBonusSpellLevel=" + divineSpellBonusSpellLevel + ", divineMaxSpellLevel="
				+ divineMaxSpellLevel + ", magicalAttackAdjustment=" + magicalAttackAdjustment + "]";
	}

	public int getMagicalAttackAdjustment() {
		return magicalAttackAdjustment;
	}

}
