package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;

import com.nobbysoft.first.common.entities.staticdto.CharacterClassLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassLevelKey;

public interface CharacterClassLevelService extends DataServiceI<CharacterClassLevel,CharacterClassLevelKey>{
 
	public int getMaxLevelLevelInTable(String characterClassId) throws SQLException;
	 
	 
}
