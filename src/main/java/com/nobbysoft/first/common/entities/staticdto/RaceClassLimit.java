package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class RaceClassLimit  implements Serializable, DataDTOInterface<RaceClassLimitKey> {

	
	public RaceClassLimit(  ) {
		super(); 
	}
	
	public RaceClassLimit(String raceId, String classId ) {
		super();
		this.raceId = raceId;
		this.classId = classId;
	}
	
	
	private String raceId;
	private String classId;
	private int maxLevel;
	// for now, just text :}
	private String limitingFactors;
	
	private int limitingAttribute; // str,con etc
	private boolean npcClassOnly;
	private int maxLevelPrEq17 ;
	private int maxLevelPrLt17;


	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public String getLimitingFactors() {
		return limitingFactors;
	}
	public void setLimitingFactors(String limitingFactors) {
		this.limitingFactors = limitingFactors;
	}
	public String getRaceId() {
		return raceId;
	}
	public void setRaceId(String raceId) {
		this.raceId = raceId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	@Override
	public RaceClassLimitKey getKey() { 
		return new RaceClassLimitKey(raceId,classId);
	}
	@Override
	public String getDescription() { 
		return raceId+","+classId;
	}
	@Override
	public Object[] getAsRow() { 
		return new Object[] {this,raceId,classId,maxLevel,  getLimitingAttributeString(),npcClassOnly};
	}
	@Override
	public String[] getRowHeadings() { 
		return new String[] {"Race","Class","Max level","Limiting attribute","NPC only?"};
	}
	@Override
	public Integer[] getColumnWidths() { 
		return new Integer[] {150,150,100,200,110};
	}
	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {Constants.CLI_RACE,Constants.CLI_CLASS,Constants.CLI_RACE_CLASS_MAX_LEVEL,null};
	}

	public boolean isNpcClassOnly() {
		return npcClassOnly;
	}

	public void setNpcClassOnly(boolean npcClassOnly) {
		this.npcClassOnly = npcClassOnly;
	}

	public int getMaxLevelPrEq17() {
		return maxLevelPrEq17;
	}

	public void setMaxLevelPrEq17(int maxLevelPrEq17) {
		this.maxLevelPrEq17 = maxLevelPrEq17;
	}

	public int getMaxLevelPrLt17() {
		return maxLevelPrLt17;
	}

	public void setMaxLevelPrLt17(int maxLevelPrLt17) {
		this.maxLevelPrLt17 = maxLevelPrLt17;
	}

	@Override
	public String toString() {
		return "RaceClassLimit [raceId=" + raceId + ", classId=" + classId + ", maxLevel=" + maxLevel
				+ ", limitingFactors=" + limitingFactors + ", limitingAttribute=" + limitingAttribute
				+ ", npcClassOnly=" + npcClassOnly + ", maxLevelPrEq17=" + maxLevelPrEq17 + ", maxLevelPrLt17="
				+ maxLevelPrLt17 + "]";
	}

	public int getLimitingAttribute() {
		return limitingAttribute;
	}

	public void setLimitingAttribute(int limitingAttribute) {
		this.limitingAttribute = limitingAttribute;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + limitingAttribute;
		result = prime * result + ((limitingFactors == null) ? 0 : limitingFactors.hashCode());
		result = prime * result + maxLevel;
		result = prime * result + maxLevelPrEq17;
		result = prime * result + maxLevelPrLt17;
		result = prime * result + (npcClassOnly ? 1231 : 1237);
		result = prime * result + ((raceId == null) ? 0 : raceId.hashCode());
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
		RaceClassLimit other = (RaceClassLimit) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (limitingAttribute != other.limitingAttribute)
			return false;
		if (limitingFactors == null) {
			if (other.limitingFactors != null)
				return false;
		} else if (!limitingFactors.equals(other.limitingFactors))
			return false;
		if (maxLevel != other.maxLevel)
			return false;
		if (maxLevelPrEq17 != other.maxLevelPrEq17)
			return false;
		if (maxLevelPrLt17 != other.maxLevelPrLt17)
			return false;
		if (npcClassOnly != other.npcClassOnly)
			return false;
		if (raceId == null) {
			if (other.raceId != null)
				return false;
		} else if (!raceId.equals(other.raceId))
			return false;
		return true;
	}
	
	public String getLimitingAttributeString() {
		if(limitingAttribute<1||limitingAttribute>6) {
			return "";
		}
		return Attribute.fromIndex(limitingAttribute).getDescription();
	}
	
	
}
