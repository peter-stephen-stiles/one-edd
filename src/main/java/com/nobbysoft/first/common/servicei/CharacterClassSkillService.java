package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.CharacterClassSkill;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSkillKey;
import com.nobbysoft.first.common.utils.ReturnValue;

public interface CharacterClassSkillService extends DataServiceI<CharacterClassSkill,CharacterClassSkillKey>{
 
	public ReturnValue<Integer>  copyFrom(String fromClassId, String toClassId) throws SQLException;
	
	public ReturnValue<List<CharacterClassSkill>> getSkillForClassLevel(String classId,int level)throws SQLException;
	
}
