package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.RaceSkill;
import com.nobbysoft.first.common.entities.staticdto.RaceSkillKey;

public interface RaceSkillService  extends DataServiceI<RaceSkill,RaceSkillKey>{

	public List<RaceSkill> getSkillsForRace(String raceId) throws SQLException;
	
}
