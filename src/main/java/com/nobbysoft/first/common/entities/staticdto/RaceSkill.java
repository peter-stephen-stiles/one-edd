package com.nobbysoft.first.common.entities.staticdto;


import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class RaceSkill implements Serializable, DataDTOInterface<RaceSkillKey> {

	public RaceSkill() {
		// TODO Auto-generated constructor stub
	}


	private String raceId;
	private String skillId;
	private String skillName;
	private String skillDefinition;

	@Override
	public RaceSkillKey getKey() {
		return new RaceSkillKey(raceId,skillId);
	}

	@Override
	public String getDescription() {
		return raceId+ " "+ skillName;
	}

	@Override
	public Object[] getAsRow() { 
		return new Object[] {this,raceId,skillId,skillName};
	}

	@Override
	public String[] getRowHeadings() { 
		return new String[] {"Race","Skill","Name"};
	}

	@Override
	public Integer[] getColumnWidths() { 
		return new Integer[] {150,150,-1};
	}

	@Override
	public String[] getColumnCodedListTypes() { 
		return new String[] {Constants.CLI_RACE,null,null};
	}

	public String getRaceId() {
		return raceId;
	}

	public void setRaceId(String raceId) {
		this.raceId = raceId;
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

}
