package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.nobbysoft.first.common.servicei.SqlService;
import com.nobbysoft.first.common.utils.ResultSetListener;
import com.nobbysoft.first.server.dao.SqlDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class SqlServiceImpl implements SqlService {

	private final ConnectionManager cm;
	private final SqlDAO dao;
	public SqlServiceImpl() {

		cm = new ConnectionManager();
		 dao = new SqlDAO();
	}

	@Override
	public void runSql(String sql, ResultSetListener listener) throws SQLException {
		
		try(Connection con = cm.getConnection()){
			 dao.runSql(con, sql, listener);
			}
		

	}

}
