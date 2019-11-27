package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.CharacterClassToHit;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassToHitKey;
import com.nobbysoft.first.common.utils.ReturnValue;

public interface CharacterClassToHitService extends DataServiceI<CharacterClassToHit,CharacterClassToHitKey>{
 
	public ReturnValue<Integer>  copyFrom(String fromClassId, String toClassId) throws SQLException;
	
	public ReturnValue<CharacterClassToHit> getToHitForClassLevel(String classId,int level)throws SQLException;
	
}
