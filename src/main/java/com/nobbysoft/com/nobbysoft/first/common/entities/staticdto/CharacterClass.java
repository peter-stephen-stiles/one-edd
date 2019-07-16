package com.nobbysoft.com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.com.nobbysoft.first.common.entities.DataDTOInterface;

public class CharacterClass implements Serializable, DataDTOInterface<String> {
	private String classId; 
	private String name; 
	private int hitDice;
	private int hitDiceAtFirstLevel=1;
	private int maxHdLevel;
	private boolean masterSpellClass;
	private int hpAfterNameLevel;
	
	private String parentClassId; 
	
	private int minStr;
	private int minInt;
	private int minWis;
	private int minDex;
	private int minCon;
	private int minChr;
	private int proficienciesAtFirstLevel;
	

	private boolean highConBonus;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + (highConBonus ? 1231 : 1237);
		result = prime * result + hitDice;
		result = prime * result + hitDiceAtFirstLevel;
		result = prime * result + hpAfterNameLevel;
		result = prime * result + (masterSpellClass ? 1231 : 1237);
		result = prime * result + maxHdLevel;
		result = prime * result + minChr;
		result = prime * result + minCon;
		result = prime * result + minDex;
		result = prime * result + minInt;
		result = prime * result + minStr;
		result = prime * result + minWis;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + newProficiencyEveryXLevels;
		result = prime * result + nonProficiencyPenalty;
		result = prime * result + ((parentClassId == null) ? 0 : parentClassId.hashCode());
		result = prime * result + prValueForXpBonus;
		result = prime * result + primeRequisite1;
		result = prime * result + primeRequisite2;
		result = prime * result + primeRequisite3;
		result = prime * result + proficienciesAtFirstLevel;
		result = prime * result + xpBonusPercent;
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
		CharacterClass other = (CharacterClass) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (highConBonus != other.highConBonus)
			return false;
		if (hitDice != other.hitDice)
			return false;
		if (hitDiceAtFirstLevel != other.hitDiceAtFirstLevel)
			return false;
		if (hpAfterNameLevel != other.hpAfterNameLevel)
			return false;
		if (masterSpellClass != other.masterSpellClass)
			return false;
		if (maxHdLevel != other.maxHdLevel)
			return false;
		if (minChr != other.minChr)
			return false;
		if (minCon != other.minCon)
			return false;
		if (minDex != other.minDex)
			return false;
		if (minInt != other.minInt)
			return false;
		if (minStr != other.minStr)
			return false;
		if (minWis != other.minWis)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (newProficiencyEveryXLevels != other.newProficiencyEveryXLevels)
			return false;
		if (nonProficiencyPenalty != other.nonProficiencyPenalty)
			return false;
		if (parentClassId == null) {
			if (other.parentClassId != null)
				return false;
		} else if (!parentClassId.equals(other.parentClassId))
			return false;
		if (prValueForXpBonus != other.prValueForXpBonus)
			return false;
		if (primeRequisite1 != other.primeRequisite1)
			return false;
		if (primeRequisite2 != other.primeRequisite2)
			return false;
		if (primeRequisite3 != other.primeRequisite3)
			return false;
		if (proficienciesAtFirstLevel != other.proficienciesAtFirstLevel)
			return false;
		if (xpBonusPercent != other.xpBonusPercent)
			return false;
		return true;
	}

	private int newProficiencyEveryXLevels;
	private int nonProficiencyPenalty;
	
	
	public int getProficienciesAtFirstLevel() {
		return proficienciesAtFirstLevel;
	}

	public void setProficienciesAtFirstLevel(int proficienciesAtFirstLevel) {
		this.proficienciesAtFirstLevel = proficienciesAtFirstLevel;
	}

	public int getNonProficiencyPenalty() {
		return nonProficiencyPenalty;
	}

	public void setNonProficiencyPenalty(int nonProficiencyPenalty) {
		this.nonProficiencyPenalty = nonProficiencyPenalty;
	}

	public int getNewProficiencyEveryXLevels() {
		return newProficiencyEveryXLevels;
	}

	public void setNewProficiencyEveryXLevels(int newProficiencyEveryXLevels) {
		this.newProficiencyEveryXLevels = newProficiencyEveryXLevels;
	}

	
	
	public int[] getMinimums() {
		return new int[] {
				minStr,
				minInt,
				minWis,
				minDex,
				minCon,
				minChr
		};
	}
	
	private int primeRequisite1;
	private int primeRequisite2;
	private int primeRequisite3;
	private int xpBonusPercent;
	private int prValueForXpBonus;
	

	public Object[] getAsRow(){
		return new Object[]{this,classId,name,parentClassId};
	}
	private static final String[] h =new String[]{"Id","Name", "Parent"};
	public String[] getRowHeadings(){
		return h;
	}

	public String[] getColumnCodedListTypes() {
		return new String[] {null,null,Constants.CLI_CLASS};
	}

	private static final Integer[] w =new Integer[]{120,-1,120};
	public Integer[] getColumnWidths(){
		return w;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getKey() { 
		return classId;
	}

	@Override
	public String getDescription() { 
		return name;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getHitDice() {
		return hitDice;
	}

	public void setHitDice(int hitDice) {
		this.hitDice = hitDice;
	}

	public int getHitDiceAtFirstLevel() {
		return hitDiceAtFirstLevel;
	}

	public void setHitDiceAtFirstLevel(int hitDiceAtFirstLevel) {
		this.hitDiceAtFirstLevel = hitDiceAtFirstLevel;
	}

	public int getMaxHdLevel() {
		return maxHdLevel;
	}

	public void setMaxHdLevel(int maxHdLevel) {
		this.maxHdLevel = maxHdLevel;
	}

	public boolean isMasterSpellClass() {
		return masterSpellClass;
	}

	public void setMasterSpellClass(boolean masterSpellClass) {
		this.masterSpellClass = masterSpellClass;
	}

	public String getParentClassId() {
		return parentClassId;
	}

	public void setParentClassId(String parentClassId) {
		this.parentClassId = parentClassId;
	}

	public int getHpAfterNameLevel() {
		return hpAfterNameLevel;
	}

	public void setHpAfterNameLevel(int hpAfterNameLevel) {
		this.hpAfterNameLevel = hpAfterNameLevel;
	}

	public int getMinStr() {
		return minStr;
	}

	public void setMinStr(int minStr) {
		this.minStr = minStr;
	}

	public int getMinInt() {
		return minInt;
	}

	public void setMinInt(int minInt) {
		this.minInt = minInt;
	}

	public int getMinWis() {
		return minWis;
	}

	public void setMinWis(int minWis) {
		this.minWis = minWis;
	}

	public int getMinDex() {
		return minDex;
	}

	public void setMinDex(int minDex) {
		this.minDex = minDex;
	}

	public int getMinCon() {
		return minCon;
	}

	public void setMinCon(int minCon) {
		this.minCon = minCon;
	}

	public int getMinChr() {
		return minChr;
	}

	public void setMinChr(int minChr) {
		this.minChr = minChr;
	}

	public int getPrimeRequisite1() {
		return primeRequisite1;
	}

	public void setPrimeRequisite1(int primeRequisite1) {
		this.primeRequisite1 = primeRequisite1;
	}

	public int getPrimeRequisite2() {
		return primeRequisite2;
	}

	public void setPrimeRequisite2(int primeRequisite2) {
		this.primeRequisite2 = primeRequisite2;
	}

	public int getXpBonusPercent() {
		return xpBonusPercent;
	}

	public void setXpBonusPercent(int xpBonusPercent) {
		this.xpBonusPercent = xpBonusPercent;
	}

	public int getPrValueForXpBonus() {
		return prValueForXpBonus;
	}

	public void setPrValueForXpBonus(int prValueForXpBonus) {
		this.prValueForXpBonus = prValueForXpBonus;
	}

	public int getPrimeRequisite3() {
		return primeRequisite3;
	}

	public void setPrimeRequisite3(int primeRequisite3) {
		this.primeRequisite3 = primeRequisite3;
	}
	
     @Override
     public String toString() {
    	 return name;
     }

	public boolean isHighConBonus() {
		return highConBonus;
	}

	public void setHighConBonus(boolean highConBonus) {
		this.highConBonus = highConBonus;
	}
}
