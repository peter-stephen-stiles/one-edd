package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.server.utils.DbUtils;

public class DAOUtils {
	

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	private DAOUtils() { 
	}

	public static final  void createInts(Connection con, String tableName, String[] newInts) throws SQLException {
		String sql;
		String dataType = "INTEGER";
		for (String column : newInts) {
			if (!DbUtils.doesTableColumnExist(con, tableName, column)) {
				sql = "ALTER TABLE " + tableName + " add column " + column + " " + dataType;
				try (PreparedStatement ps = con.prepareStatement(sql);) {
					ps.execute();
				}
			}
		}
	}

	public static final  void createBooleans(Connection con, 
			String tableName, String[] newBoos) throws SQLException {
		String sql;
		String dataType = "BOOLEAN";
		for (String column : newBoos) {
			if (!DbUtils.doesTableColumnExist(con, tableName, column)) {
				sql = "ALTER TABLE " + tableName + " add column " + column + " " + dataType;
				try (PreparedStatement ps = con.prepareStatement(sql);) {
					ps.execute();
				}
			}
		}
	}

	public static final void createStrings(Connection con, String tableName, VC[] newStrings)
			throws SQLException {
		String sql;
		{
		String dataType = "VARCHAR";
		for (int i=0,n=newStrings.length;i<n;i++) {
			VC vc = newStrings[i];
			String column = vc.getName();
			int len = vc.getLength();
			boolean nullable = vc.isNullable();
			if (!DbUtils.doesTableColumnExist(con, tableName, column)) {
				sql = "ALTER TABLE " + tableName +  " add column " + column + " " + dataType+"("+len+")";
				if(!nullable) {
					sql  = sql + " NOT NULL DEFAULT 'unknown'";
				}
				try (PreparedStatement ps = con.prepareStatement(sql);) {
					ps.execute();
				}

			}
		}
		
}
	}
	

	public static final void createDecimals(Connection con, String tableName, DECIMAL[] newDecimals)
			throws SQLException {
		String sql;
		{
		String dataType = "DECIMAL";
		for (int i=0,n=newDecimals.length;i<n;i++) {
			DECIMAL vc = newDecimals[i];
			String column = vc.getName();
			int len = vc.getLength();
			int dec = vc.getDecimalPlaces();
			boolean nullable = vc.isNullable();
			if (!DbUtils.doesTableColumnExist(con, tableName, column)) {
				sql = "ALTER TABLE " + tableName +  " add column " + column + " ";
				sql = sql + dataType+"("+len;
				if(dec>0) {
					sql = sql + ","+dec;
				}
				sql = sql +")";
				if(!nullable) {
					sql  = sql + " NOT NULL DEFAULT 0";
				}
				try (PreparedStatement ps = con.prepareStatement(sql);) {
					ps.execute();
				} catch (Exception ex) {
					LOGGER.info("Error running\n"+sql);
					throw ex;
				}

			}
		}
		
}
	}
	
	/**
	 * "use one that take VC[] instead"
	 */
	@Deprecated  
	public static final void createStrings(Connection con, String tableName, String[] newStrings, int[] stringLengths)
			throws SQLException {
		String sql;
		{
		String dataType = "VARCHAR";
		for (int i=0,n=newStrings.length;i<n;i++) {
			String column = newStrings[i];
			int len = stringLengths[i];
			if (!DbUtils.doesTableColumnExist(con, tableName, column)) {
				sql = "ALTER TABLE " + tableName + 
						" add column " + column + " " + dataType+"("+len+")";
				try (PreparedStatement ps = con.prepareStatement(sql);) {
					ps.execute();
				}
			}
		}
		
}
	}
	
}
