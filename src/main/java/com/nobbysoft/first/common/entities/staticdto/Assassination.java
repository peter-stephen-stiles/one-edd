package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.utils.SU;

@SuppressWarnings("serial")
public class Assassination  implements Comparable<Assassination>, Serializable, DataDTOInterface<AssassinationKey> {

	public Assassination() { 
	}
	
	
	private int assassinLevel;
	private int victimLevelTo;
	private int victimLevelFrom;
	
	private int percentageChance;

	@Override
	public AssassinationKey getKey() { 
		return new AssassinationKey (assassinLevel,victimLevelFrom);
	}

	
	
	@Override
	public String getDescription() {
		return ""+assassinLevel+" vs "+victimLevelFrom+"-"+victimLevelTo+" = "+percentageChance+"%";
		
	}

	public String getVictimLevel() {
		if(victimLevelTo==99) {
			return ""+victimLevelFrom+"+";
		} else {
			return ""+victimLevelFrom+"-"+victimLevelTo;
		}
	}
	
	@Override
	public Object[] getAsRow() {
		return new Object[] {this,assassinLevel,getVictimLevel(),SU.p(percentageChance, "--")};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"Assassin level","Victim level","%age chance"};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new  Integer[] {120,120,-1};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {null,null,null};
	}

	@Override
	public int compareTo(Assassination o) {
		int ret = getKey().compareTo(o.getKey());
		if(ret==0) {
			long rel = percentageChance - o.getPercentageChance();
			ret = (int)Math.signum(rel);
		}
		return 0;
	}

	public int getAssassinLevel() {
		return assassinLevel;
	}

	public void setAssassinLevel(int assassinLevel) {
		this.assassinLevel = assassinLevel;
	}

	public int getVictimLevelTo() {
		return victimLevelTo;
	}

	public void setVictimLevelTo(int victimLevel) {
		this.victimLevelTo = victimLevel;
	}

	public int getPercentageChance() {
		return percentageChance;
	}

	public void setPercentageChance(int percentageChance) {
		this.percentageChance = percentageChance;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + assassinLevel;
		result = prime * result + percentageChance;
		result = prime * result + victimLevelFrom;
		result = prime * result + victimLevelTo;
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
		Assassination other = (Assassination) obj;
		if (assassinLevel != other.assassinLevel)
			return false;
		if (percentageChance != other.percentageChance)
			return false;
		if (victimLevelFrom != other.victimLevelFrom)
			return false;
		if (victimLevelTo != other.victimLevelTo)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Assassination [assassinLevel=" + assassinLevel + ", victimLevelTo=" + victimLevelTo
				+ ", victimLevelFrom=" + victimLevelFrom + ", percentageChance=" + percentageChance + "]";
	}



	public int getVictimLevelFrom() {
		return victimLevelFrom;
	}



	public void setVictimLevelFrom(int victimLevelFrom) {
		this.victimLevelFrom = victimLevelFrom;
	}

 

}
