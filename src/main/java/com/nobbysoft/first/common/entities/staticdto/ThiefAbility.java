package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class ThiefAbility implements Serializable, DataDTOInterface<ThiefAbilityKey>  {

	
	
	private int thiefLevel;
	private String thiefAbilityType;// FK to TheifAbilityType	
	private double percentageChance;
	
	public ThiefAbility() { 
	}

	public int getThiefLevel() {
		return thiefLevel;
	}

	public void setThiefLevel(int thiefLevel) {
		this.thiefLevel = thiefLevel;
	}

	public String getThiefAbilityType() {
		return thiefAbilityType;
	}

	public void setThiefAbilityType(String thiefAbilityType) {
		this.thiefAbilityType = thiefAbilityType;
	}

	public double getPercentageChance() {
		return percentageChance;
	}

	public void setPercentageChance(double percentageChance) {
		this.percentageChance = percentageChance;
	}

	@Override
	public String toString() {
		return "ThiefAbility [thiefLevel=" + thiefLevel + ", thiefAbilityType=" + thiefAbilityType
				+ ", percentageChance=" + percentageChance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(percentageChance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((thiefAbilityType == null) ? 0 : thiefAbilityType.hashCode());
		result = prime * result + thiefLevel;
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
		ThiefAbility other = (ThiefAbility) obj;
		if (Double.doubleToLongBits(percentageChance) != Double.doubleToLongBits(other.percentageChance))
			return false;
		if (thiefAbilityType == null) {
			if (other.thiefAbilityType != null)
				return false;
		} else if (!thiefAbilityType.equals(other.thiefAbilityType))
			return false;
		if (thiefLevel != other.thiefLevel)
			return false;
		return true;
	}

	
	public String getPercentageChanceString() {
		String ret1 = String.format("%.1f", percentageChance);		
		if(ret1.endsWith(".0")) {
			String ret0 = String.format("%.0f", percentageChance);
			return ret0 + "%";
		} else {		
			return ret1 + "%";
		}
	}
	
	@Override
	public ThiefAbilityKey getKey() { 
		return new ThiefAbilityKey (thiefLevel, thiefAbilityType);
	}

	@Override
	public String getDescription() { 
		return ""+thiefLevel+":"+thiefAbilityType+"="+getPercentageChanceString();
	}

	@Override
	public Object[] getAsRow() {
		return new Object[] {this,thiefLevel,thiefAbilityType,getPercentageChanceString()};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"Thief Level","Ability","%age Chance"};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {100,200,100};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {null,Constants.CLI_THIEF_ABILITY,null};
	}

	
	
}
