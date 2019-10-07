package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpell;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpellKey;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.views.ViewPlayerCharacterSpell; 

public interface PlayerCharacterSpellService extends DataServiceI<PlayerCharacterSpell,PlayerCharacterSpellKey>{
// 
//	public int getNextId() throws SQLException;
	
	public List<ViewPlayerCharacterSpell> getViewForPC(int pcId) throws SQLException;
	public List<PlayerCharacterSpell> getForPC(int pcId) throws SQLException;
	
	public List<Spell> getViewNotForPC(int pcId, int level, String spellClassId,String filterName) throws SQLException;

	
}
