package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;

public class CharacterClassSpell implements Serializable, DataDTOInterface<CharacterClassSpellKey> {

	
	private String classId;
	private String spellClassId;
	private int level;

	private int level1Spells;
	private int level2Spells;
	private int level3Spells;
	private int level4Spells;
	private int level5Spells;
	private int level6Spells;
	private int level7Spells;
	private int level8Spells;
	private int level9Spells;
	
	
	public CharacterClassSpell() {
	}


	@Override
	public CharacterClassSpellKey getKey() {
		return new CharacterClassSpellKey(classId,spellClassId,level) ;
	}


	@Override
	public String getDescription() {
		return ""+classId+":"+spellClassId+":"+level;
	}


	@Override
	public Object[] getAsRow() {
		// TODO Auto-generated method stub
		return new Object[] {this,
				classId,
				spellClassId,
				level,
				level1Spells,
				level2Spells,
				level3Spells,
				level4Spells,
				level5Spells,
				level6Spells,
				level7Spells,
				level8Spells,
				level9Spells};
	}


	@Override
	public String[] getRowHeadings() {
		return new String[] {"","Class","Spell class","Level",
				"Lvl 1 Spells",
				"Lvl 2 Spells",
				"Lvl 3 Spells",
				"Lvl 4 Spells",
				"Lvl 5 Spells",
				"Lvl 6 Spells",
				"Lvl 7 Spells",
				"Lvl 8 Spells",
				"Lvl 9 Spells"
		};
	}


	@Override
	public Integer[] getColumnWidths() {
		// TODO Auto-generated method stub
		return new Integer[] {0,100,100,
				50,
				50,
				50,
				50,
				50,
				50,
				50,
				50,
				50,
				50,
				};
	}

	

	@Override
	public String[] getColumnCodedListTypes() {
		// TODO Auto-generated method stub
		return new String[] {Constants.CLI_CLASS,Constants.CLI_CLASS,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,};
	}


	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}


	public String getSpellClassId() {
		return spellClassId;
	}


	public void setSpellClassId(String spellClassId) {
		this.spellClassId = spellClassId;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public Map<Integer,Integer> spellsByLevel (){
		Map<Integer,Integer> map = new HashMap<>();

		map.put(1, level1Spells);
		map.put(2, level2Spells);
		map.put(3, level3Spells);
		map.put(4, level4Spells);
		map.put(5, level5Spells);
		map.put(6, level6Spells);
		map.put(7, level7Spells);
		map.put(8, level8Spells);
		map.put(9, level9Spells);
		return map;
	}
	
	public void setLevelXSpell(int level, int spells) {
		if(level==1) {
			level1Spells = spells;
		}else if(level==2) {
			level2Spells = spells;
		}else if(level==3) {
			level3Spells = spells;
		}else if(level==4) {
			level4Spells = spells;
		}else if(level==5) {
			level5Spells = spells;
		}else if(level==6) {
			level6Spells = spells;
		}else if(level==7) {
			level7Spells = spells;
		}else if(level==8) {
			level8Spells = spells;
		}else if(level==9) {
			level9Spells = spells;
		}else {
		throw new IllegalStateException("No such spell level as "+level);	
		}
	}
	
	public int getLevel1Spells() {
		return level1Spells;
	}


	public void setLevel1Spells(int level1Spells) {
		this.level1Spells = level1Spells;
	}


	public int getLevel2Spells() {
		return level2Spells;
	}


	public void setLevel2Spells(int level2Spells) {
		this.level2Spells = level2Spells;
	}


	public int getLevel3Spells() {
		return level3Spells;
	}


	public void setLevel3Spells(int level3Spells) {
		this.level3Spells = level3Spells;
	}


	public int getLevel4Spells() {
		return level4Spells;
	}


	public void setLevel4Spells(int level4Spells) {
		this.level4Spells = level4Spells;
	}


	public int getLevel5Spells() {
		return level5Spells;
	}


	public void setLevel5Spells(int level5Spells) {
		this.level5Spells = level5Spells;
	}


	public int getLevel6Spells() {
		return level6Spells;
	}


	public void setLevel6Spells(int level6Spells) {
		this.level6Spells = level6Spells;
	}


	public int getLevel7Spells() {
		return level7Spells;
	}


	public void setLevel7Spells(int level7Spells) {
		this.level7Spells = level7Spells;
	}


	public int getLevel8Spells() {
		return level8Spells;
	}


	public void setLevel8Spells(int level8Spells) {
		this.level8Spells = level8Spells;
	}


	public int getLevel9Spells() {
		return level9Spells;
	}


	public void setLevel9Spells(int level9Spells) {
		this.level9Spells = level9Spells;
	}

}
