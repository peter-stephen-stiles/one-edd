package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpell;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpellKey;
import com.nobbysoft.first.common.servicei.CharacterClassSpellService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.CharacterClassSpellDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class CharacterClassSpellServiceImpl implements CharacterClassSpellService {

	private final ConnectionManager cm;
	private final CharacterClassSpellDAO dao;
	
	public CharacterClassSpellServiceImpl() {
		cm = new ConnectionManager();
		dao = new CharacterClassSpellDAO();
	}

	@Override
	public void createTable() throws SQLException {
		try(Connection con = cm.getConnection()){
		 dao.createTable(con);
		}
	}

	@Override
	public CharacterClassSpell get(CharacterClassSpellKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.get(con,key);
			}
	}

	@Override
	public void insert(CharacterClassSpell value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.insert(con,value);
			 con.commit();
			}
	}

	@Override
	public List<CharacterClassSpell> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getList(con);
			}
	}

	@Override
	public List<CharacterClassSpell> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getFilteredList(con,filter);
			}
	}

	@Override
	public void delete(CharacterClassSpell value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.delete(con,value);
			 con.commit();
			}
	}

	@Override
	public void update(CharacterClassSpell value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.update(con,value);
			 con.commit();
			}
	}

	@Override
	public List<CodedListItem<CharacterClassSpellKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			 return dao.getAsCodedList(con);
			}
	}

 
}
