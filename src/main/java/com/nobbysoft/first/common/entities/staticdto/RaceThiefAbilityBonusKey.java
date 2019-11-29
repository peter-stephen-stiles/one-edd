package com.nobbysoft.first.common.entities.staticdto;

public class RaceThiefAbilityBonusKey implements Comparable<RaceThiefAbilityBonusKey> {


	private String raceId;
	private String thiefAbilityType;// FK to TheifAbilityType	
	public RaceThiefAbilityBonusKey(String raceId, String thiefAbilityType ) {
		super();
		this.raceId = raceId;
		this.thiefAbilityType = thiefAbilityType;
	}
	public RaceThiefAbilityBonusKey() { 
	}
	
	protected String compareKeyValue() {
		return raceId+"|"+thiefAbilityType;
	}
	
	@Override
	public int compareTo(RaceThiefAbilityBonusKey o) { 
		return compareKeyValue().compareTo(o.compareKeyValue());
	}
	public String getRaceId() {
		return raceId;
	}
	public void setRaceId(String raceId) {
		this.raceId = raceId;
	}
	public String getThiefAbilityType() {
		return thiefAbilityType;
	}
	public void setThiefAbilityType(String thiefAbilityType) {
		this.thiefAbilityType = thiefAbilityType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((thiefAbilityType == null) ? 0 : thiefAbilityType.hashCode());
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
		RaceThiefAbilityBonusKey other = (RaceThiefAbilityBonusKey) obj;
		if (thiefAbilityType == null) {
			if (other.thiefAbilityType != null)
				return false;
		} else if (!thiefAbilityType.equals(other.thiefAbilityType))
			return false;
		if (raceId == null) {
			if (other.raceId != null)
				return false;
		} else if (!raceId.equals(other.raceId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("RaceClassLimitKey [raceId=%s, thiefAbilityType=%s]", raceId, thiefAbilityType);
	}

}
