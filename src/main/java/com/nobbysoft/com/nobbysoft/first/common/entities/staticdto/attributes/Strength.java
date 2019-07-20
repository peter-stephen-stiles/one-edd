package com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.attributes;

import java.io.Serializable;

import com.nobbysoft.com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.AbilityScoreI;

@SuppressWarnings("serial")
public class Strength  implements AbilityScoreI,Comparable<Strength>, Serializable, DataDTOInterface<StrengthKey>{

	public Strength() {		
	}
	@Override
	public String toString() {
		return "Strength [abilityScore=" + abilityScore + ", exceptionalStrength=" + exceptionalStrength
				+ ", exceptionalStrengthTo=" + exceptionalStrengthTo + ", hitProbability=" + hitProbability
				+ ", damageAdjustment=" + damageAdjustment + ", weightAllowance=" + weightAllowance + ", openDoors="
				+ openDoors + ", openMagicalDoors=" + openMagicalDoors + ", bendBarsLiftGates=" + bendBarsLiftGates
				+ "]";
	}

	private int abilityScore;
	private int exceptionalStrength;
	private int exceptionalStrengthTo;
	private int hitProbability;
	private int damageAdjustment;
	private int weightAllowance;
	private int openDoors;
	private int openMagicalDoors;
	private int bendBarsLiftGates;
	
	public int getExceptionalStrength() {
		return exceptionalStrength;
	}
	public void setExceptionalStrength(int exceptionalStrength) {
		this.exceptionalStrength = exceptionalStrength;
	}
	public int getHitProbability() {
		return hitProbability;
	}
	public void setHitProbability(int hitProbability) {
		this.hitProbability = hitProbability;
	}
	public int getDamageAdjustment() {
		return damageAdjustment;
	}
	public void setDamageAdjustment(int damageAdjustment) {
		this.damageAdjustment = damageAdjustment;
	}
	public int getWeightAllowance() {
		return weightAllowance;
	}
	public void setWeightAllowance(int weightAllowance) {
		this.weightAllowance = weightAllowance;
	}
	public int getOpenDoors() {
		return openDoors;
	}
	public void setOpenDoors(int openDoors) {
		this.openDoors = openDoors;
	}
	public int getOpenMagicalDoors() {
		return openMagicalDoors;
	}
	public void setOpenMagicalDoors(int openMagicalDoors) {
		this.openMagicalDoors = openMagicalDoors;
	}
	public int getBendBarsLiftGates() {
		return bendBarsLiftGates;
	}
	public void setBendBarsLiftGates(int bendBarsLiftGates) {
		this.bendBarsLiftGates = bendBarsLiftGates;
	}
	@Override
	public StrengthKey getKey() { 
		return new StrengthKey (abilityScore,exceptionalStrength);
	}
	@Override
	public String getDescription() {
		if(abilityScore==18) {
			if(exceptionalStrength==100) {
				return ""+abilityScore+"/00";
			} else if(exceptionalStrength<1) {
				return ""+abilityScore;
			} else {
				return ""+abilityScore+"/"+exceptionalStrength;
			}
		}
		return ""+abilityScore;
	}
	@Override
	public Object[] getAsRow() {
		// TODO Auto-generated method stub
		return new Object[] {this,abilityScore,exceptionalStrength,exceptionalStrengthTo,hitProbability,damageAdjustment,weightAllowance,openDoors,openMagicalDoors,bendBarsLiftGates};
	}
	@Override
	public String[] getRowHeadings() {
		// TODO Auto-generated method stub
		return new String[] {"Ability","%","-%","To hit","Dam","Weight","Open","Op Mag","BB/LG"};
	}
	@Override
	public Integer[] getColumnWidths() {
		// TODO Auto-generated method stub
		return new  Integer[] {80,50,50,50,50,50,50,50,50};
	}
	@Override
	public String[] getColumnCodedListTypes() {
		// TODO Auto-generated method stub
		return new String[] {};
	}
 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + abilityScore;
		result = prime * result + bendBarsLiftGates;
		result = prime * result + damageAdjustment;
		result = prime * result + exceptionalStrength;
		result = prime * result + exceptionalStrengthTo;
		result = prime * result + hitProbability;
		result = prime * result + openDoors;
		result = prime * result + openMagicalDoors;
		result = prime * result + weightAllowance;
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
		Strength other = (Strength) obj;
		if (abilityScore != other.abilityScore)
			return false;
		if (bendBarsLiftGates != other.bendBarsLiftGates)
			return false;
		if (damageAdjustment != other.damageAdjustment)
			return false;
		if (exceptionalStrength != other.exceptionalStrength)
			return false;
		if (exceptionalStrengthTo != other.exceptionalStrengthTo)
			return false;
		if (hitProbability != other.hitProbability)
			return false;
		if (openDoors != other.openDoors)
			return false;
		if (openMagicalDoors != other.openMagicalDoors)
			return false;
		if (weightAllowance != other.weightAllowance)
			return false;
		return true;
	}
	@Override
	public int getAbilityScore() {
		// TODO Auto-generated method stub
		return abilityScore;
	}
	@Override
	public void setAbilityScore(int abilityScore) {
		this.abilityScore=abilityScore;
	}
	@Override
	public int compareTo(Strength o) {
		if(o==null) {
			return 1;
		}
		int ret = getKey().compareTo(o.getKey());
		if(ret==0) {
		ret = toString().compareTo(o.toString());
		}
		return ret;
	}
	public int getExceptionalStrengthTo() {
		return exceptionalStrengthTo;
	}
	public void setExceptionalStrengthTo(int exceptionalStrengthTo) {
		this.exceptionalStrengthTo = exceptionalStrengthTo;
	}

}
