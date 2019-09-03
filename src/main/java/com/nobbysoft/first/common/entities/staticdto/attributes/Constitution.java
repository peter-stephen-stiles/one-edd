package com.nobbysoft.first.common.entities.staticdto.attributes;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.staticdto.AbilityScoreI;

public class Constitution  implements AbilityScoreI,Comparable<Constitution>, Serializable, DataDTOInterface<Integer>{

	private int abilityScore;
	
	public int getHitPointAdjustment() {
		return hitPointAdjustment;
	}

	public void setHitPointAdjustment(int hitPointAdjustment) {
		this.hitPointAdjustment = hitPointAdjustment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + abilityScore;
		result = prime * result + hitPointAdjustment;
		result = prime * result + hitPointAdjustmentHigh;
		result = prime * result + resurrectionSurvival;
		result = prime * result + systemShockSurvival;
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
		Constitution other = (Constitution) obj;
		if (abilityScore != other.abilityScore)
			return false;
		if (hitPointAdjustment != other.hitPointAdjustment)
			return false;
		if (hitPointAdjustmentHigh != other.hitPointAdjustmentHigh)
			return false;
		if (resurrectionSurvival != other.resurrectionSurvival)
			return false;
		if (systemShockSurvival != other.systemShockSurvival)
			return false;
		return true;
	}

	public int getHitPointAdjustmentHigh() {
		return hitPointAdjustmentHigh;
	}

	public void setHitPointAdjustmentHigh(int hitPointAdjustmentHigh) {
		this.hitPointAdjustmentHigh = hitPointAdjustmentHigh;
	}

	public int getSystemShockSurvival() {
		return systemShockSurvival;
	}

	public void setSystemShockSurvival(int systemShockSurvival) {
		this.systemShockSurvival = systemShockSurvival;
	}

	public int getResurrectionSurvival() {
		return resurrectionSurvival;
	}

	public void setResurrectionSurvival(int resurrectionSurvival) {
		this.resurrectionSurvival = resurrectionSurvival;
	}

	private int hitPointAdjustment;
	private int hitPointAdjustmentHigh;
	private int systemShockSurvival;
	private int resurrectionSurvival;
	
	public Constitution() { 
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
		return new Object[] {this,abilityScore, hitPointAdjustment,hitPointAdjustmentHigh,systemShockSurvival,resurrectionSurvival};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"Con","Hp Adj","(fighters)","System Shock Survival","Resurrection Survival"};
	}

	@Override
	public Integer[] getColumnWidths() {
		// TODO Auto-generated method stub
		return new Integer[] {50,70,70,100,100};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		// TODO Auto-generated method stub
		return new String[] {null,null,null,null,null,};
	}

	@Override
	public int compareTo(Constitution o) {
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

}
