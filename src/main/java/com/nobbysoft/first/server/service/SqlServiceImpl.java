package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nobbysoft.first.common.entities.meta.DTOColumn;
import com.nobbysoft.first.common.entities.meta.DTOConstraint;
import com.nobbysoft.first.common.entities.meta.DTOIndex;
import com.nobbysoft.first.common.entities.meta.DTOTable;
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

	
	public void runUpdate(String sql) throws SQLException{
		try(Connection con = cm.getConnection()){
			 dao.runSqlUpdate(con, sql);
			}
	}



	@Override
	public List<DTOColumn> metaDataColumns(String catalog, String schema, String tableName)
			throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.metaDataColumns(con, catalog, schema, tableName);
			}
		
	}
 

	@Override
	public List<DTOTable> metaDataTables(String tableNameFilter) throws SQLException {
		List<DTOTable> list = null;
		try(Connection con = cm.getConnection()){
			
			list=dao.metaDataTables(con, tableNameFilter);
			
		}
		return list;
	}

	@Override
	public List<DTOIndex> metaDataIndexes(String catalog, String schema, String tableName) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.metaDataIndexes(con, catalog, schema, tableName);
			}
	}

	@Override
	public List<DTOConstraint> metaDataConstraints(String catalog, String schema, String tableName) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.metaDataConstraints(con, catalog, schema, tableName);
			}
	}
}
