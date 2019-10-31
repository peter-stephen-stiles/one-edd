package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.pc.PlayerCharacterHp;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHpKey;
import com.nobbysoft.first.common.views.ViewPlayerCharacterHp; 

public interface PlayerCharacterHpService extends DataServiceI<PlayerCharacterHp,PlayerCharacterHpKey>{
// 
	
	public List<ViewPlayerCharacterHp> getViewForPC(int pcId) throws SQLException;
	public List<PlayerCharacterHp> getForPC(int pcId) throws SQLException;
	
	public void addAll(List<PlayerCharacterHp> list) throws SQLException;

	
}
