package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PlayerCharacterDetailI<E> {
	
	public void deleteForPC(Connection con, int  pcId) throws SQLException;
	public List<E> getForPC(Connection con, int  pcId) throws SQLException;
	
}
