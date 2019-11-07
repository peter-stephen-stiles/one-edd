package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.SavingThrow;
import com.nobbysoft.first.common.entities.staticdto.SavingThrowKey;
import com.nobbysoft.first.common.utils.ReturnValue;

public interface SavingThrowService  extends DataServiceI<SavingThrow,SavingThrowKey>{

	public List<SavingThrow> getSavesForClassLevel(String classId, int level) throws SQLException;
	public ReturnValue<Integer> copyFrom(String fromClassId, String toClassId) throws SQLException;
}
