package com.nobbysoft.first.common.entities.staticdto.attributes;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.staticdto.AbilityScoreI;

public class Dexterity  implements AbilityScoreI,Comparable<Dexterity>, Serializable, DataDTOInterface<Integer>{

	private int abilityScore;
	
	private int reactionAttackAdjustment;
	private int defensiveAdjustment;
	
	private int pickPockets;
	private int openLocks;
	private int locateRemoveTraps;
	private int moveSilently;
	private int hideInShadows; 
	
	public Dexterity() { 
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
		return new Object[] {this,abilityScore, 
				reactionAttackAdjustment,
				defensiveAdjustment,
				pickPockets,
				openLocks,
				locateRemoveTraps,
				moveSilently,
				hideInShadows};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"DEX","React/att adj","Def adj)","Pick pockets",
				"Open locks","Loc/rem traps","Move silent","Hide in shadows"};
	}

	@Override
	public Integer[] getColumnWidths() {
		// TODO Auto-generated method stub
		return new Integer[] {50,50,50,50,50,50,50,50};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		// TODO Auto-generated method stub
		return new String[] {null,null,null,null,null,null,null,null,};
	}

	@Override
	public int compareTo(Dexterity o) {
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

	public int getReactionAttackAdjustment() {
		return reactionAttackAdjustment;
	}

	public void setReactionAttackAdjustment(int reactionAttackAdjustment) {
		this.reactionAttackAdjustment = reactionAttackAdjustment;
	}

	public int getDefensiveAdjustment() {
		return defensiveAdjustment;
	}

	public void setDefensiveAdjustment(int defensiveAdjustment) {
		this.defensiveAdjustment = defensiveAdjustment;
	}

	public int getPickPockets() {
		return pickPockets;
	}

	public void setPickPockets(int pickPockets) {
		this.pickPockets = pickPockets;
	}

	public int getOpenLocks() {
		return openLocks;
	}

	public void setOpenLocks(int openLocks) {
		this.openLocks = openLocks;
	}

	public int getLocateRemoveTraps() {
		return locateRemoveTraps;
	}

	public void setLocateRemoveTraps(int locateRemoveTraps) {
		this.locateRemoveTraps = locateRemoveTraps;
	}

	public int getMoveSilently() {
		return moveSilently;
	}

	public void setMoveSilently(int moveSilently) {
		this.moveSilently = moveSilently;
	}

	public int getHideInShadows() {
		return hideInShadows;
	}

	public void setHideInShadows(int hideInShadows) {
		this.hideInShadows = hideInShadows;
	}

	@Override
	public String toString() {
		return "Dexterity [abilityScore=" + abilityScore + ", reactionAttackAdjustment=" + reactionAttackAdjustment
				+ ", defensiveAdjustment=" + defensiveAdjustment + ", pickPockets=" + pickPockets + ", openLocks="
				+ openLocks + ", locateRemoveTraps=" + locateRemoveTraps + ", moveSilently=" + moveSilently
				+ ", hideInShadows=" + hideInShadows + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + abilityScore;
		result = prime * result + defensiveAdjustment;
		result = prime * result + hideInShadows;
		result = prime * result + locateRemoveTraps;
		result = prime * result + moveSilently;
		result = prime * result + openLocks;
		result = prime * result + pickPockets;
		result = prime * result + reactionAttackAdjustment;
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
		Dexterity other = (Dexterity) obj;
		if (abilityScore != other.abilityScore)
			return false;
		if (defensiveAdjustment != other.defensiveAdjustment)
			return false;
		if (hideInShadows != other.hideInShadows)
			return false;
		if (locateRemoveTraps != other.locateRemoveTraps)
			return false;
		if (moveSilently != other.moveSilently)
			return false;
		if (openLocks != other.openLocks)
			return false;
		if (pickPockets != other.pickPockets)
			return false;
		if (reactionAttackAdjustment != other.reactionAttackAdjustment)
			return false;
		return true;
	}

}
