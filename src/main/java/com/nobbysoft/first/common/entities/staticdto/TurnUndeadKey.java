package com.nobbysoft.first.common.entities.staticdto;

public class TurnUndeadKey  implements Comparable<TurnUndeadKey>{

	public TurnUndeadKey() {
	}
	
	private int effectiveClericLevelFrom;	
	private int undeadType; //FK
	
	public int getEffectiveClericLevelFrom() {
		return effectiveClericLevelFrom;
	}
	public TurnUndeadKey(int effectiveClericLevelFrom, int undeadType) {
		super();
		this.effectiveClericLevelFrom = effectiveClericLevelFrom;
		this.undeadType = undeadType;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + effectiveClericLevelFrom;
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
		TurnUndeadKey other = (TurnUndeadKey) obj;
		if (effectiveClericLevelFrom != other.effectiveClericLevelFrom)
			return false;
		if (undeadType != other.undeadType)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TurnUndeadKey [effectiveClericLevelFrom=" + effectiveClericLevelFrom + ", undeadType=" + undeadType
				+ "]";
	}
	
 
	
	@Override
	public int compareTo(TurnUndeadKey o) {
		int ret=0;
		long rel = effectiveClericLevelFrom - o.getEffectiveClericLevelFrom();
		ret = (int)Math.signum(rel);
		if(ret==0) {
			rel = undeadType - o.getUndeadType();
		}
		ret = (int)Math.signum(rel);
		return ret;
	}

}
