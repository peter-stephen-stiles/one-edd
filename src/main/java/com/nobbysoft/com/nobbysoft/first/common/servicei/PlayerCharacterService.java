package com.nobbysoft.com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.com.nobbysoft.first.common.views.ViewPlayerCharacter;

public interface PlayerCharacterService extends DataServiceI<PlayerCharacter,Integer>{
 
	public int getNextId() throws SQLException;
	public List<ViewPlayerCharacter> getViewList(String filter) throws SQLException;
	
}
