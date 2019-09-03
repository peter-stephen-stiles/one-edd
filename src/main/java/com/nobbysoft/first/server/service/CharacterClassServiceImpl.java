package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.CharacterClassDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class CharacterClassServiceImpl implements CharacterClassService {

	private final ConnectionManager cm;
	private final CharacterClassDAO dao;
	
	public CharacterClassServiceImpl() {
		cm = new ConnectionManager();
		dao = new CharacterClassDAO();
	}

	@Override
	public void createTable() throws SQLException {
		try(Connection con = cm.getConnection()){
		 dao.createTable(con);
		}
	}

	@Override
	public CharacterClass get(String key) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.get(con,key);
			}
	}

	@Override
	public void insert(CharacterClass value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.insert(con,value);
			 con.commit();
			}
	}

	@Override
	public List<CharacterClass> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getList(con);
			}
	}

	@Override
	public List<CharacterClass> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getFilteredList(con,filter);
			}
	}

	@Override
	public void delete(CharacterClass value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.delete(con,value);
			 con.commit();
			}
	}

	@Override
	public void update(CharacterClass value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.update(con,value);
			 con.commit();
			}
	}

	@Override
	public List<CodedListItem<String>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			 return dao.getAsCodedList(con);
			}
	}


	public List<CodedListItem<String>>  getSpellClassesAsCodedList(){
		try(Connection con = cm.getConnection()){
			 return dao.getSpellClassesAsCodedList(con);
			} catch (SQLException e) {
				throw new IllegalStateException("Trouble with getting spell classes",e);
			}
	}
	
}
