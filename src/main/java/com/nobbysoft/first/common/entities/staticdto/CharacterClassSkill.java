package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class CharacterClassSkill implements Serializable, DataDTOInterface<CharacterClassSkillKey> {


	private String classId;
	private String skillId;
	private int fromLevel;
	private String skillName;
	private String skillDefinition;
	
	public CharacterClassSkill() {
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillDefinition() {
		return skillDefinition;
	}

	public void setSkillDefinition(String skillDefinition) {
		this.skillDefinition = skillDefinition;
	}


	@Override
	public CharacterClassSkillKey getKey() {
		return new CharacterClassSkillKey (classId,skillId);
	}

	@Override
	public String getDescription() {

		return classId+ " "+ fromLevel+" "+skillName;
	}

	@Override
	public Object[] getAsRow() {
		return new Object[]{this,classId,fromLevel,skillId,skillName,skillDefinition};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"","Class","Level","Skill","Name","Def"};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {0,100,80,150,1000};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {Constants.CLI_CLASS,null,null,null,null};
	}

	public int getFromLevel() {
		return fromLevel;
	}

	public void setFromLevel(int fromLevel) {
		this.fromLevel = fromLevel;
	}

}
