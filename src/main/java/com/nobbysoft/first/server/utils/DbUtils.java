package com.nobbysoft.first.server.utils;

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
				return true;
			} 

		}
		LOGGER.info("table "+tableName+" does not exist.");
		return false;
	}

	public static final boolean doesViewExist(Connection con, String tableName) throws SQLException {
		DatabaseMetaData dbmd = con.getMetaData();
		try (ResultSet rs = dbmd.getTables( null, null, tableName.toUpperCase(),
				new String[]{"VIEW"});) {
			if(rs.next()){				
				return true;
			}

		}
		LOGGER.info("view "+tableName+" does not exist.");
		return false;
	}
	
	public static final boolean doesTableColumnExist(Connection con, String tableName, String columnName) throws SQLException {
		DatabaseMetaData dbmd = con.getMetaData();
		try (ResultSet rs = dbmd.getTables( null, null, tableName.toUpperCase(),
				new String[]{"TABLE"});) {
			if(rs.next()){
				try(ResultSet rsc = dbmd.getColumns(null,null,tableName.toUpperCase(),columnName.toUpperCase())){
					if(rsc.next()){						
						return true;
					}
				} 
			}

		}
		LOGGER.info("table.column "+tableName+"."+columnName+" does not exist.");
		return false;
	}

	public static final boolean isTableColumnFK(Connection con, String tableName, String columnName, String otherTable)
			throws SQLException {
		DatabaseMetaData dbmd = con.getMetaData();
		
		
		
		try (ResultSet rs = dbmd.getImportedKeys( null, null, tableName.toUpperCase());) {
			while(rs.next()){
				String fkc = rs.getString("FKCOLUMN_NAME");
				String fkt = rs.getString("FKTABLE_NAME");
				String pkc = rs.getString("PKCOLUMN_NAME"); // not used..
				String pkt = rs.getString("PKTABLE_NAME");
				if(tableName.equalsIgnoreCase(fkt) &&
					columnName.equalsIgnoreCase(fkc) &&
					otherTable.equalsIgnoreCase(pkt) ){
						//LOGGER.info("table.column "+tableName+"."+columnName+" is a FK");
						return true;
					}					
				} 
			}
		LOGGER.info("table.column "+tableName+"."+columnName+" is NOT a FK");
		return false;
	}	
	
	
}
