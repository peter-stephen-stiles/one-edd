package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;

import com.nobbysoft.first.common.entities.staticdto.attributes.*;

public interface StrengthService  extends DataServiceI<Strength,StrengthKey>{
	
	public Strength getStrengthFor(int strength, int exceptional) throws SQLException;

}
