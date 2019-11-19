package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;

public class TurnUndead  implements Comparable<TurnUndead>, Serializable, DataDTOInterface<TurnUndeadKey>{

	public TurnUndead() { 
	}
	
	private int effectiveClericLevelFrom;	
	private int undeadType; //FK
	private int effectiveClericLevelTo;
	private int rollRequired; //-1=D, 0=T, 1-20=RR,21=IMPO
	private int numberAffectedFrom;
	private int numberAffectedTo;
	
	public int getEffectiveClericLevelFrom() {
		return effectiveClericLevelFrom;
	}
	public void setEffectiveClericLevelFrom(int effectiveClericLevelFrom) {
		this.effectiveClericLevelFrom = effectiveClericLevelFrom;
	}
	public int getUndeadType() {
		return undeadType;
	}
	public void setUndeadType(int undeadType) {
		this.undeadType = undeadType;
	}
	public int getEffectiveClericLevelTo() {
		return effectiveClericLevelTo;
	}
	public void setEffectiveClericLevelTo(int effectiveClericLevelTo) {
		this.effectiveClericLevelTo = effectiveClericLevelTo;
	}
	public int getRollRequired() {
		return rollRequired;
	}
	public void setRollRequired(int rollRequired) {
		this.rollRequired = rollRequired;
	}
	public int getNumberAffectedFrom() {
		return numberAffectedFrom;
	}
	public void setNumberAffectedFrom(int numberAffectedFrom) {
		this.numberAffectedFrom = numberAffectedFrom;
	}
	public int getNumberAffectedTo() {
		return numberAffectedTo;
	}
	public void setNumberAffectedTo(int numberAffectedTo) {
		this.numberAffectedTo = numberAffectedTo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + effectiveClericLevelFrom;
		result = prime * result + effectiveClericLevelTo;
		result = prime * result + numberAffectedFrom;
		result = prime * result + numberAffectedTo;
		result = prime * result + rollRequired;
		result = prime * result + undeadType;
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
		TurnUndead other = (TurnUndead) obj;
		if (effectiveClericLevelFrom != other.effectiveClericLevelFrom)
			return false;
		if (effectiveClericLevelTo != other.effectiveClericLevelTo)
			return false;
		if (numberAffectedFrom != other.numberAffectedFrom)
			return false;
		if (numberAffectedTo != other.numberAffectedTo)
			return false;
		if (rollRequired != other.rollRequired)
			return false;
		if (undeadType != other.undeadType)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TurnUndead [effectiveClericLevelFrom=" + effectiveClericLevelFrom + ", undeadType=" + undeadType
				+ ", effectiveClericLevelTo=" + effectiveClericLevelTo + ", rollRequired=" + rollRequired
				+ ", numberAffectedFrom=" + numberAffectedFrom + ", numberAffectedTo=" + numberAffectedTo + "]";
	}
	@Override
	public TurnUndeadKey getKey() { 
		return new TurnUndeadKey(effectiveClericLevelFrom,undeadType); 
	}
	@Override
	public String getDescription() { 
		return ""+effectiveClericLevelFrom+"-"+effectiveClericLevelFrom + " " + undeadType + " = " +rollRequired;
	}
	
	
	public String getLevelRange() {
		if(effectiveClericLevelFrom==effectiveClericLevelTo) {
			return ""+effectiveClericLevelFrom;
		}
		if(effectiveClericLevelTo==99) {
			return ""+effectiveClericLevelFrom+"+";
		}
		return ""+effectiveClericLevelFrom+"-"+effectiveClericLevelTo;
	}
	
	
	public String getRollRequiredString() {
		if(rollRequired<0) {
			return "D";
		} else if (rollRequired==0) {
			return "T";
		} else if (rollRequired>20) {
			return "--";
		} else {
			return ""+rollRequired;
		}
	}
	
	@Override
	public Object[] getAsRow() {

		return new Object[] {this,getLevelRange(),undeadType, getRollRequiredString()};
	}
	@Override
	public String[] getRowHeadings() {

		return new String[] {"Level","Type","Roll Required"};
	}
	@Override
	public Integer[] getColumnWidths() {

		return new Integer[] {00,150,-1};
	}
	@Override
	public String[] getColumnCodedListTypes() {

		return new String[] {null,Constants.CLI_UNDEAD,null}; // need to use new CLI type!!
	}
	@Override
	public int compareTo(TurnUndead o) {
		int ret = getKey().compareTo(o.getKey());
		if(ret==0) {
			ret = toString().compareTo(o.toString());
		}
		return ret;
	}

}
