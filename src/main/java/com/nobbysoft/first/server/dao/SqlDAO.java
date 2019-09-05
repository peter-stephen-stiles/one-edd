package com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nobbysoft.first.common.utils.ResultSetListener;

public class SqlDAO {

	public SqlDAO() { 
	}
	

	public void runSqlUpdate(Connection con,String sql) throws SQLException{

		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.executeUpdate();
		}
	}
	
	public void runSql(Connection con,String sql,ResultSetListener listener) throws SQLException{
		
		try(PreparedStatement ps = con.prepareStatement(sql)){
			
			try(ResultSet rs = ps.executeQuery()){
				
				ResultSetMetaData rsmd = rs.getMetaData();
				listener.haveTheMetaData(rsmd);
				
				List<String> labels = new ArrayList<>();
				for(int i=0,n=rsmd.getColumnCount();i<n;i++) {
					labels.add(rsmd.getColumnLabel(i+1));
				}
				listener.haveTheColumnLabels(labels.toArray(new String[labels.size()]));
				
				int row=0;
				while(rs.next()) {

					List<Object> data = new ArrayList<>();
					for(int i=0,n=rsmd.getColumnCount();i<n;i++) {
						Object o = rs.getObject(i+1);
						data.add(o);
					}
					listener.haveARow(row, data.toArray());
					row++;
				}
				
			}
			
		} finally {
			listener.finished();
		}
		
	}

}
