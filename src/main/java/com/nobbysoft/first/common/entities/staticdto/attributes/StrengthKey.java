package com.nobbysoft.first.common.entities.staticdto.attributes;

public class StrengthKey implements Comparable<StrengthKey> {

	private int abilityScore;
	private int exceptionalStrength;
	
	
	public void setAbilityScore(int abilityScore) {
		this.abilityScore = abilityScore;
	}

	public StrengthKey(int abilityScore,int exceptionalStrength) {
		this.abilityScore=abilityScore;
		this.exceptionalStrength=exceptionalStrength;
	}
	
	public StrengthKey() {
		
	}

	@Override
	public String toString() {
		return "StrengthKey [abilityScore=" + abilityScore + "/" + exceptionalStrength + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + abilityScore;
		result = prime * result + exceptionalStrength;
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
		StrengthKey other = (StrengthKey) obj;
		if (abilityScore != other.abilityScore)
			return false;
		if (exceptionalStrength != other.exceptionalStrength)
			return false;
		return true;
	}

	@Override
	public int compareTo(StrengthKey o) {
		if(o==null) {
			return -1;
		}
		if(abilityScore>o.abilityScore) {
			return 1;
		}
		if(abilityScore<o.abilityScore) {
			return -1;
		}
		if(exceptionalStrength>o.exceptionalStrength) {
			return 1;
		}
		if(exceptionalStrength<o.exceptionalStrength) {
			return -1;
		}
		return 0;
	}

	public int getExceptionalStrength() {
		return exceptionalStrength;
	}

	public void setExceptionalStrength(int exceptionalStrength) {
		this.exceptionalStrength = exceptionalStrength;
	}

	public int getAbilityScore() {
		return abilityScore;
	}

}
