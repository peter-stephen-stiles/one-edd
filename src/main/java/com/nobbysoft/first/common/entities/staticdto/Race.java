package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;

public class Race  implements Comparable<Race>, Serializable, DataDTOInterface<String>{
	
private String raceId;
private String name;
private boolean hasMagicDefenceBonus;

@Override
public String toString() {
	return name;
}


private int minMaleStr;
private int minMaleInt;
private int minMaleWis;
private int minMaleDex;
private int minMaleCon;
private int minMaleChr;
private int minFemaleStr;
private int minFemaleInt;
private int minFemaleWis;
private int minFemaleDex;
private int minFemaleCon;
private int minFemaleChr;
private int maxMaleStr;
private int maxMaleInt;
private int maxMaleWis;
private int maxMaleDex;
private int maxMaleCon;
private int maxMaleChr;
private int maxFemaleStr;
private int maxFemaleInt;
private int maxFemaleWis;
private int maxFemaleDex;
private int maxFemaleCon;
private int maxFemaleChr;

private int bonusStr;
private int bonusInt;
private int bonusWis;
private int bonusDex;
private int bonusCon;
private int bonusChr;

private boolean multiClassable=false;


public boolean isMultiClassable() {
	return multiClassable;
}

public void setMultiClassable(boolean multiClassable) {
	this.multiClassable = multiClassable;
}

public int[] getFemaleMaximums() {
	return new int[] {
			maxFemaleStr,
			maxFemaleInt,
			maxFemaleWis,
			maxFemaleDex,
			maxFemaleCon,
			maxFemaleChr
	};
}

public int[] getFemaleMinimums() {
	return new int[] {
			minFemaleStr,
			minFemaleInt,
			minFemaleWis,
			minFemaleDex,
			minFemaleCon,
			minFemaleChr

	};
}


public int[] getMaleMaximums() {
	return new int[] {
			maxMaleStr,
			maxMaleInt,
			maxMaleWis,
			maxMaleDex,
			maxMaleCon,
			maxMaleChr
	};
}

public int[] getMaleMinimums() {
	return new int[] {
			minMaleStr,
			minMaleInt,
			minMaleWis,
			minMaleDex,
			minMaleCon,
			minMaleChr

	};
}

public Object[] getAsRow(){
	return new Object[]{this,raceId,name};
}
private static final String[] h =new String[]{"Id","Name"};
public String[] getRowHeadings(){
	return h;
}
private static final Integer[] w =new Integer[]{120,-1};
public Integer[] getColumnWidths(){
	return w;
}

public String[] getColumnCodedListTypes() {
	return new String[0];
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public boolean isHasMagicDefenceBonus() {
	return hasMagicDefenceBonus;
}
public void setHasMagicDefenceBonus(boolean hasMagicDefenceBonus) {
	this.hasMagicDefenceBonus = hasMagicDefenceBonus;
}
 
@Override
public String getKey() {
	return raceId;
}
@Override
public String getDescription() {
	return name;
}

public String getRaceId() {
	return raceId;
}

public void setRaceId(String raceId) {
	this.raceId = raceId;
}

public int getMinMaleStr() {
	return minMaleStr;
}

public void setMinMaleStr(int minMaleStr) {
	this.minMaleStr = minMaleStr;
}

public int getMinMaleInt() {
	return minMaleInt;
}

public void setMinMaleInt(int minMaleInt) {
	this.minMaleInt = minMaleInt;
}

public int getMinMaleWis() {
	return minMaleWis;
}

public void setMinMaleWis(int minMaleWis) {
	this.minMaleWis = minMaleWis;
}

public int getMinMaleDex() {
	return minMaleDex;
}

public void setMinMaleDex(int minMaleDex) {
	this.minMaleDex = minMaleDex;
}

public int getMinMaleCon() {
	return minMaleCon;
}

public void setMinMaleCon(int minMaleCon) {
	this.minMaleCon = minMaleCon;
}

public int getMinMaleChr() {
	return minMaleChr;
}

public void setMinMaleChr(int minMaleChr) {
	this.minMaleChr = minMaleChr;
}

public int getMinFemaleStr() {
	return minFemaleStr;
}

public void setMinFemaleStr(int minFemaleStr) {
	this.minFemaleStr = minFemaleStr;
}

public int getMinFemaleInt() {
	return minFemaleInt;
}

public void setMinFemaleInt(int minFemaleInt) {
	this.minFemaleInt = minFemaleInt;
}

public int getMinFemaleWis() {
	return minFemaleWis;
}

public void setMinFemaleWis(int minFemaleWis) {
	this.minFemaleWis = minFemaleWis;
}

public int getMinFemaleDex() {
	return minFemaleDex;
}

public void setMinFemaleDex(int minFemaleDex) {
	this.minFemaleDex = minFemaleDex;
}

public int getMinFemaleCon() {
	return minFemaleCon;
}

public void setMinFemaleCon(int minFemaleCon) {
	this.minFemaleCon = minFemaleCon;
}

public int getMinFemaleChr() {
	return minFemaleChr;
}

public void setMinFemaleChr(int minFemaleChr) {
	this.minFemaleChr = minFemaleChr;
}

public int getMaxMaleStr() {
	return maxMaleStr;
}

public void setMaxMaleStr(int maxMaleStr) {
	this.maxMaleStr = maxMaleStr;
}

public int getMaxMaleInt() {
	return maxMaleInt;
}

public void setMaxMaleInt(int maxMaleInt) {
	this.maxMaleInt = maxMaleInt;
}

public int getMaxMaleWis() {
	return maxMaleWis;
}

public void setMaxMaleWis(int maxMaleWis) {
	this.maxMaleWis = maxMaleWis;
}

public int getMaxMaleDex() {
	return maxMaleDex;
}

public void setMaxMaleDex(int maxMaleDex) {
	this.maxMaleDex = maxMaleDex;
}

public int getMaxMaleCon() {
	return maxMaleCon;
}

public void setMaxMaleCon(int maxMaleCon) {
	this.maxMaleCon = maxMaleCon;
}

public int getMaxMaleChr() {
	return maxMaleChr;
}

public void setMaxMaleChr(int maxMaleChr) {
	this.maxMaleChr = maxMaleChr;
}

public int getMaxFemaleStr() {
	return maxFemaleStr;
}

public void setMaxFemaleStr(int maxFemaleStr) {
	this.maxFemaleStr = maxFemaleStr;
}

public int getMaxFemaleInt() {
	return maxFemaleInt;
}

public void setMaxFemaleInt(int maxFemaleInt) {
	this.maxFemaleInt = maxFemaleInt;
}

public int getMaxFemaleWis() {
	return maxFemaleWis;
}

public void setMaxFemaleWis(int maxFemaleWis) {
	this.maxFemaleWis = maxFemaleWis;
}

public int getMaxFemaleDex() {
	return maxFemaleDex;
}

public void setMaxFemaleDex(int maxFemaleDex) {
	this.maxFemaleDex = maxFemaleDex;
}

public int getMaxFemaleCon() {
	return maxFemaleCon;
}

public void setMaxFemaleCon(int maxFemaleCon) {
	this.maxFemaleCon = maxFemaleCon;
}

public int getMaxFemaleChr() {
	return maxFemaleChr;
}

public void setMaxFemaleChr(int maxFemaleChr) {
	this.maxFemaleChr = maxFemaleChr;
}

public int getBonusStr() {
	return bonusStr;
}

public void setBonusStr(int bonusStr) {
	this.bonusStr = bonusStr;
}

public int getBonusInt() {
	return bonusInt;
}

public void setBonusInt(int bonusInt) {
	this.bonusInt = bonusInt;
}

public int getBonusWis() {
	return bonusWis;
}

public void setBonusWis(int bonusWis) {
	this.bonusWis = bonusWis;
}

public int getBonusDex() {
	return bonusDex;
}

public void setBonusDex(int bonusDex) {
	this.bonusDex = bonusDex;
}

public int getBonusCon() {
	return bonusCon;
}

public void setBonusCon(int bonusCon) {
	this.bonusCon = bonusCon;
}

public int getBonusChr() {
	return bonusChr;
}

public void setBonusChr(int bonusChr) {
	this.bonusChr = bonusChr;
}

public int[] getBonuses() {
	return new int[] {
			bonusStr,
			bonusInt,
			bonusWis,
			bonusDex,
			bonusCon,
			bonusChr,
	};
}

@Override
public int compareTo(Race o) {
	
	int r=  name.compareTo(o.getName());
	if(r==0) {
		r = raceId.compareTo(o.getRaceId());
	}
	return r;
	}

}
