package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;

import com.nobbysoft.first.common.entities.staticdto.CharacterClassLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassLevelKey;

public interface CharacterClassLevelService extends DataServiceI<CharacterClassLevel,CharacterClassLevelKey>{
 
	public int getMaxLevelLevelInTable(String characterClassId) throws SQLException;
	 
	public int getMaxAllowedLevel(int pcId, String characterClassId) throws SQLException; 
	
	public CharacterClassLevel getThisLevel(String characterClassId, int level) throws SQLException;
	

	public CharacterClassLevel getNameLevel(String characterClassId) throws SQLException;
	
}
