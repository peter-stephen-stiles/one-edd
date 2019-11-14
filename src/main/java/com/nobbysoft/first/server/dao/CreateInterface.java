package com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface CreateInterface {
	

	public void createTable(Connection con) throws SQLException;
	public default void createConstraints(Connection con) throws SQLException{
		
	};

}
