package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;

import com.nobbysoft.first.common.utils.ResultSetListener;

public interface SqlService {

	public void runSql(String sql,ResultSetListener listener) throws SQLException;
	public void runUpdate(String sql) throws SQLException;
	
}
