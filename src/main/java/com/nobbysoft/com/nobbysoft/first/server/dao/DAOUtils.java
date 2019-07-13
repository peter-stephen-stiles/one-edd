package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.nobbysoft.com.nobbysoft.first.server.utils.DbUtils;

public class DAOUtils {

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
