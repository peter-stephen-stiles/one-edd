package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.RaceThiefAbilityBonus;
import com.nobbysoft.first.common.entities.staticdto.RaceThiefAbilityBonusKey;

public interface RaceThiefAbilityBonusService  extends DataServiceI<RaceThiefAbilityBonus,RaceThiefAbilityBonusKey>{

	public List<RaceThiefAbilityBonus> getBonusesForRace(String raceId) throws SQLException ;
	
}
