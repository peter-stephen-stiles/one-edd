package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;

import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpell;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpellKey;

public interface CharacterClassSpellService extends DataServiceI<CharacterClassSpell,CharacterClassSpellKey>{
 
	public int getMaxSpellLevelInTable(String characterClassId) throws SQLException;
}
