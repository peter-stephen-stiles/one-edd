package com.nobbysoft.com.nobbysoft.first.common.servicei;

import java.sql.SQLException;

import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;

public interface PlayerCharacterService extends DataServiceI<PlayerCharacter,Integer>{
 
	public int getNextId() throws SQLException;
	
}
