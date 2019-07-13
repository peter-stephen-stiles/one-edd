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
 
}
