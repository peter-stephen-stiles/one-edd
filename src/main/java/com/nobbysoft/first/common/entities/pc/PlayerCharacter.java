package com.nobbysoft.first.common.entities.pc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.staticdto.Alignment;
import com.nobbysoft.first.common.entities.staticdto.ClassType;
import com.nobbysoft.first.common.entities.staticdto.Gender;

@SuppressWarnings("serial")
public class PlayerCharacter implements Serializable,DataDTOInterface<Integer> {

	private int pcId=-1;// auto allocate an int please
	
	private String characterName;

	private String playerName;
	private Gender gender;
	private Alignment alignment;
	private String raceId;
	private ClassType classType; 
	
	private int exceptionalStrength=0;
	
	public int getExceptionalStrength() {
		return exceptionalStrength;
	}

	public void setExceptionalStrength(int exceptionalStrength) {
		this.exceptionalStrength = exceptionalStrength;
	}





	private int attrStr;
	private int attrInt;
	private int attrWis;
	private int attrDex;
	private int attrCon;
	private int attrChr;
	
	private int initialCon;
	
	private String firstClass=null;
	private String secondClass=null;
	private String thirdClass=null;
	
	private int firstClassLevel=0;
	private int secondClassLevel=0;
	private int thirdClassLevel=0;

	private int firstClassExperience=0;
	private int secondClassExperience=0;
	private int thirdClassExperience=0;
	

	private int firstClassHp=0;
	private int secondClassHp=0;
	private int thirdClassHp=0;
	
	
	public int getFirstClassHp() {
		return firstClassHp;
	}

	public void setFirstClassHp(int firstClassHp) {
		this.firstClassHp = firstClassHp;
	}

	public int getSecondClassHp() {
		return secondClassHp;
	}

	public void setSecondClassHp(int secondClassHp) {
		this.secondClassHp = secondClassHp;
	}

	public int getThirdClassHp() {
		return thirdClassHp;
	}

	public void setThirdClassHp(int thirdClassHp) {
		this.thirdClassHp = thirdClassHp;
	}

	public List<Integer> getHpList(){
		List<Integer> cp = new ArrayList<>();
		cp.add(firstClassHp);
		if(secondClassHp>0) {
			cp.add(secondClassHp);
			if(thirdClassHp>0) {
				cp.add(thirdClassHp);
			}
		}		
		return cp;
	}



	
	public int getHp() {
		return firstClassHp+
				secondClassHp+
				thirdClassHp;
	}
	
	public int getExperience() {
		return firstClassExperience
				+secondClassExperience+
				thirdClassExperience;
	}
	
	public List<Integer> getExperienceList(){
		List<Integer> cp = new ArrayList<>();
		cp.add(firstClassExperience);
		if(secondClassExperience>0) {
			cp.add(secondClassExperience);
			if(thirdClassExperience>0) {
				cp.add(thirdClassExperience);
			}
		}
		
		return cp;
	}
	public List<String> getClasses(){
		List<String> classes = new ArrayList<>();
		classes.add(firstClass);
		if(secondClass!=null) {
			classes.add(secondClass);
			if(thirdClass!=null) {
				classes.add(thirdClass);
			}
		}
		
		return classes;
	}
	public int[] getAtts() {
		return new int[] {
				attrStr,attrInt,attrWis,attrDex,attrCon,attrChr}
		; 
	}
 
	
	public PlayerCharacter(){
		
	}
	
	public void setAtts(int[] atts) {
		attrStr=atts[0];
		attrInt=atts[1];
		attrWis=atts[2];
		attrDex=atts[3];
		attrCon=atts[4];
		attrChr=atts[5];

	}
	
	public int getAttrStr() {
		return attrStr;
	}


	public void setAttrStr(int attrStr) {
		this.attrStr = attrStr;
	}


	public int getAttrInt() {
		return attrInt;
	}


	public void setAttrInt(int attrInt) {
		this.attrInt = attrInt;
	}


	public int getAttrWis() {
		return attrWis;
	}


	public void setAttrWis(int attrWis) {
		this.attrWis = attrWis;
	}


	public int getAttrDex() {
		return attrDex;
	}


	public void setAttrDex(int attrDex) {
		this.attrDex = attrDex;
	}


	public int getAttrCon() {
		return attrCon;
	}


	public void setAttrCon(int attrCon) {
		this.attrCon = attrCon;
	}


	public int getAttrChr() {
		return attrChr;
	}


	public void setAttrChr(int attrChr) {
		this.attrChr = attrChr;
	}


	public String getFirstClass() {
		return firstClass;
	}


	public void setFirstClass(String firstClass) {
		this.firstClass = firstClass;
	}


	public String getSecondClass() {
		return secondClass;
	}


	public void setSecondClass(String secondClass) {
		this.secondClass = secondClass;
	}


	public String getThirdClass() {
		return thirdClass;
	}


	public void setThirdClass(String thirdClass) {
		this.thirdClass = thirdClass;
	}


	public int getFirstClassLevel() {
		return firstClassLevel;
	}


	public void setFirstClassLevel(int firstClassLevel) {
		this.firstClassLevel = firstClassLevel;
	}


	public int getSecondClassLevel() {
		return secondClassLevel;
	}


	public void setSecondClassLevel(int secondClassLevel) {
		this.secondClassLevel = secondClassLevel;
	}


	public int getThirdClassLevel() {
		return thirdClassLevel;
	}


	public void setThirdClassLevel(int thirdClassLevel) {
		this.thirdClassLevel = thirdClassLevel;
	}


	public int getFirstClassExperience() {
		return firstClassExperience;
	}


	public void setFirstClassExperience(int firstClassExperience) {
		this.firstClassExperience = firstClassExperience;
	}


	public int getSecondClassExperience() {
		return secondClassExperience;
	}


	public void setSecondClassExperience(int secondClassExperience) {
		this.secondClassExperience = secondClassExperience;
	}


	public int getThirdClassExperience() {
		return thirdClassExperience;
	}


	public void setThirdClassExperience(int thirdClassExperience) {
		this.thirdClassExperience = thirdClassExperience;
	}

	public void setCharacterName(String name) {
		this.characterName = name;
	}
	public void setRaceId(String race) {
		this.raceId = race;
	}
	
	public Alignment getAlignment() {
		return alignment;
	}
	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
	public ClassType getClassType() {
		return classType;
	}
	public void setClassType(ClassType classType) {
		this.classType = classType;
	}
 
	public String getCharacterName() {
		return characterName;
	}
	public String getRaceId() {
		return raceId;
	}
 
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

 
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getPcId() {
		return pcId;
	}

	public void setPcId(int pcId) {
		this.pcId = pcId;
	}

	@Override
	public Integer getKey() {

		return pcId;
	}

	@Override
	public String getDescription() {
		return characterName;
	}

	@Override
	public Object[] getAsRow() { 
		return new Object[] {this,pcId,characterName, raceId,
				firstClass,
				gender.name(),alignment.name()};
	}

	
	
	@Override
	public String[] getRowHeadings() { 
		return new String[] {"Id","Character Name","Race","Class","Gender","Alignment"
				
		};
	}

	@Override
	public Integer[] getColumnWidths() { 
		return new Integer[] {120,300,150,200,100,150};
	}

	@Override
	public String[] getColumnCodedListTypes() { 
		return new String[] {null,null,Constants.CLI_RACE,Constants.CLI_CLASS,Constants.CLI_GENDER,Constants.CLI_ALIGNMENT};
	}





	public int getInitialCon() {
		return initialCon;
	}





	public void setInitialCon(int initialCon) {
		this.initialCon = initialCon;
	}
}
