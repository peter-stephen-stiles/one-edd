package com.nobbysoft.first.common.entities.staticdto.attributes;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.staticdto.AbilityScoreI;
import com.nobbysoft.first.common.utils.SU;

@SuppressWarnings("serial")
public class Charisma implements AbilityScoreI,Comparable<Charisma>, Serializable, DataDTOInterface<Integer>{

	private int abilityScore;
	 
	private int maxHenchmen;
	private int loyaltyBase;
	private int reactionAdjustment;
	
	public Charisma() { 
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
				maxHenchmen,
				SU.ap(loyaltyBase,"normal"),
				SU.ap(reactionAdjustment,"normal")};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"CHR","Max Henchmen","Loyalty Base","Reaction Adjustment"};
	}

 
 
	
	@Override
	public Integer[] getColumnWidths() {
		// TODO Auto-generated method stub
		return new Integer[] {50,70,100,100};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		// TODO Auto-generated method stub
		return new String[] {null,null,null,null};
	}

	@Override
	public int compareTo(Charisma o) {
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

	public int getMaxHenchmen() {
		return maxHenchmen;
	}

	public void setMaxHenchmen(int maxHenchmen) {
		this.maxHenchmen = maxHenchmen;
	}

	public int getLoyaltyBase() {
		return loyaltyBase;
	}

	public void setLoyaltyBase(int loyaltyBase) {
		this.loyaltyBase = loyaltyBase;
	}

	public int getReactionAdjustment() {
		return reactionAdjustment;
	}

	public void setReactionAdjustment(int reactionAdjustment) {
		this.reactionAdjustment = reactionAdjustment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + abilityScore;
		result = prime * result + loyaltyBase;
		result = prime * result + maxHenchmen;
		result = prime * result + reactionAdjustment;
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
		Charisma other = (Charisma) obj;
		if (abilityScore != other.abilityScore)
			return false;
		if (loyaltyBase != other.loyaltyBase)
			return false;
		if (maxHenchmen != other.maxHenchmen)
			return false;
		if (reactionAdjustment != other.reactionAdjustment)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Charisma [abilityScore=" + abilityScore + ", maxHenchmen=" + maxHenchmen + ", loyaltyBase="
				+ loyaltyBase + ", reactionAdjustment=" + reactionAdjustment + "]";
	}


}
