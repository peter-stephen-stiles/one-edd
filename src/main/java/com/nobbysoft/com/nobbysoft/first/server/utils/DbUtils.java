package com.nobbysoft.com.nobbysoft.first.server.utils;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum DbUtils {
INSTANCE;

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	private DbUtils() {
		// TODO Auto-generated constructor stub
	}
	public static final boolean doesTableExist(Connection con, String tableName) throws SQLException {
		DatabaseMetaData dbmd = con.getMetaData();
		try (ResultSet rs = dbmd.getTables( null, null, tableName.toUpperCase(),
				new String[]{"TABLE"});) {
			if(rs.next()){
				LOGGER.info("table "+tableName+" already exists; good.");
				return true;
			}

		}
		return false;
	}

	public static final boolean doesViewExist(Connection con, String tableName) throws SQLException {
		DatabaseMetaData dbmd = con.getMetaData();
		try (ResultSet rs = dbmd.getTables( null, null, tableName.toUpperCase(),
				new String[]{"VIEW"});) {
			if(rs.next()){
				LOGGER.info("view "+tableName+" already exists; good.");
				return true;
			}

		}
		return false;
	}
	
	public static final boolean doesTableColumnExist(Connection con, String tableName, String columnName) throws SQLException {
		DatabaseMetaData dbmd = con.getMetaData();
		try (ResultSet rs = dbmd.getTables( null, null, tableName.toUpperCase(),
				new String[]{"TABLE"});) {
			if(rs.next()){
				//LOGGER.info("table "+tableName+" already exists;");
				try(ResultSet rsc = dbmd.getColumns(null,null,tableName.toUpperCase(),columnName.toUpperCase())){
					if(rsc.next()){
						LOGGER.info("table.column "+tableName+"."+columnName+" already exists;");
						return true;
					}
				} 
			}

		}
		return false;
	}

}
