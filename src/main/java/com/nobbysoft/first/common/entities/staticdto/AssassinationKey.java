package com.nobbysoft.first.common.entities.staticdto;

public class AssassinationKey  implements Comparable<AssassinationKey>{

	public AssassinationKey() { 
	}

	private int assassinLevel;
	private int victimLevelFrom;
	
	
	public int getAssassinLevel() {
		return assassinLevel;
	}
	public void setAssassinLevel(int assassinLevel) {
		this.assassinLevel = assassinLevel;
	}
	public int getVictimLevelFrom() {
		return victimLevelFrom;
	}
	public void setVictimLevelFrom(int victimLevel) {
		this.victimLevelFrom = victimLevel;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + assassinLevel;
		result = prime * result + victimLevelFrom;
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
		AssassinationKey other = (AssassinationKey) obj;
		if (assassinLevel != other.assassinLevel)
			return false;
		if (victimLevelFrom != other.victimLevelFrom)
			return false;
		return true;
	}
	public AssassinationKey(int assassinLevel, int victimLevel) {
		super();
		this.assassinLevel = assassinLevel;
		this.victimLevelFrom = victimLevel;
	}
	@Override
	public String toString() {
		return "AssassinationKey [assassinLevel=" + assassinLevel + ", victimLevelFrom=" + victimLevelFrom + "]";
	}
	@Override
	public int compareTo(AssassinationKey o) {
		int ret=0;
		long rel = assassinLevel - o.getAssassinLevel();
		ret = (int)Math.signum(rel);
		if(ret==0) {
			rel = victimLevelFrom - o.getVictimLevelFrom();
		}
		ret = (int)Math.signum(rel);
		return ret;
	}
	
	
}
