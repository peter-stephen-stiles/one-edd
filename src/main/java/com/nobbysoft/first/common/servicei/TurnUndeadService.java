package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.*;

public interface TurnUndeadService extends DataServiceI<TurnUndead,TurnUndeadKey>{

	public List<TurnUndead> getListForClericLevel(int level)throws SQLException ;
	
}
