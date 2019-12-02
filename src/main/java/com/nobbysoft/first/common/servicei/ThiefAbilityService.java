package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.ThiefAbility;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbilityKey;

public interface ThiefAbilityService  extends DataServiceI<ThiefAbility,ThiefAbilityKey>{
	
	public List<ThiefAbility> getListForThiefLevel(int level) throws SQLException;

}
