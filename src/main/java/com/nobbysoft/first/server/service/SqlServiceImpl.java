package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.derby.client.am.SqlException;

import com.nobbysoft.first.common.entities.meta.DTOColumn;
import com.nobbysoft.first.common.entities.meta.DTOConstraint;
import com.nobbysoft.first.common.entities.meta.DTOIndex;
import com.nobbysoft.first.common.entities.meta.DTOTable;
import com.nobbysoft.first.common.servicei.SqlService;
import com.nobbysoft.first.common.utils.ResultSetListener;
import com.nobbysoft.first.common.utils.ReturnValue;
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
			try {
			 dao.runSql(con, sql, listener);
			} finally {
			con.rollback();
			}
			}
		

	}

	
	public void runUpdate(String sql) throws SQLException{
		try(Connection con = cm.getConnection()){
			try {							
			 dao.runSqlUpdate(con, sql);
			 con.commit();
			} finally {
			con.rollback();
			}
			}
	}



	@Override
	public List<DTOColumn> metaDataColumns(String catalog, String schema, String tableName)
			throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.metaDataColumns(con, catalog, schema, tableName);
			} finally {
			con.rollback();
			}
			}
		
	}


	

	@Override
	public List<DTOTable> metaDataTables(String catalog, String schema,String tableNameFilter) throws SQLException {
		List<DTOTable> list = null;
		try(Connection con = cm.getConnection()){
			try {
			list=dao.metaDataTables(con, catalog, schema,tableNameFilter);

			} finally {
			con.rollback();
			}
		}
		return list;
	}
	

	@Override
	public List<DTOTable> metaDataTables(String tableNameFilter) throws SQLException {
		List<DTOTable> list = null;
		try(Connection con = cm.getConnection()){
			try {
			list=dao.metaDataTables(con, tableNameFilter);

			} finally {
			con.rollback();
			}
		}
		return list;
	}

	@Override
	public List<DTOIndex> metaDataIndexes(String catalog, String schema, String tableName) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.metaDataIndexes(con, catalog, schema, tableName);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<DTOConstraint> metaDataConstraints(String catalog, String schema, String tableName) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.metaDataConstraints(con, catalog, schema, tableName);
			} finally {
			con.rollback();
			}
			}
	}
	
	

	public List<String> metaCatalogs() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.metaCatalogs(con);
			} finally {
			con.rollback();
			}
			}
	}
	public List<String> metaSchema() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.metaSchema(con);
			} finally {
			con.rollback();
			}
			}
	}
	
	public ReturnValue<String> export (String catalog,String schema,String fileName) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.export(con,catalog,schema,fileName);
			} finally {
			con.rollback();
			}
			}
	}
	
	
}
