package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpell;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpellKey;

public interface CharacterClassSpellService extends DataServiceI<CharacterClassSpell,CharacterClassSpellKey>{
 
	public int getMaxSpellLevelInTable(String characterClassId) throws SQLException;
	
	public List<String> getSpellClassesForClasses(List<String> classIds) throws SQLException;
	
	public List<CharacterClassSpell> getAllowedSpells( int pcId) throws SQLException;
}
