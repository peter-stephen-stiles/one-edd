package com.nobbysoft.com.nobbysoft.first.common.entities.staticdto;

public class RaceClassLimitKey implements Comparable<RaceClassLimitKey> {


	private String raceId;
	private String classId;
	public RaceClassLimitKey(String raceId, String classId ) {
		super();
		this.raceId = raceId;
		this.classId = classId;
	}
	public RaceClassLimitKey() { 
	}
	
	protected String compareKeyValue() {
		return raceId+"|"+classId;
	}
	
	@Override
	public int compareTo(RaceClassLimitKey o) { 
		return compareKeyValue().compareTo(o.compareKeyValue());
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
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
		RaceClassLimitKey other = (RaceClassLimitKey) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
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
		return String.format("RaceClassLimitKey [raceId=%s, classId=%s]", raceId, classId);
	}

}
