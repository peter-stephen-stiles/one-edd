package com.nobbysoft.com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.com.nobbysoft.first.common.servicei.PlayerCharacterService;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.common.views.ViewPlayerCharacter;
import com.nobbysoft.com.nobbysoft.first.server.dao.PlayerCharacterDAO;
import com.nobbysoft.com.nobbysoft.first.server.dao.PlayerCharacterSpellDAO;
import com.nobbysoft.com.nobbysoft.first.server.utils.ConnectionManager;

public class PlayerCharacterServiceImpl implements PlayerCharacterService {

	private final ConnectionManager cm;
	private final PlayerCharacterDAO dao;
	private final PlayerCharacterSpellDAO spellDao;
	
	public PlayerCharacterServiceImpl() {
		cm = new ConnectionManager();
		dao = new PlayerCharacterDAO();
		spellDao = new PlayerCharacterSpellDAO();
	}

	@Override
	public void createTable() throws SQLException {
		try(Connection con = cm.getConnection()){
		 dao.createTable(con);
		}
	}

	@Override
	public PlayerCharacter get(Integer key) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.get(con,key);
			}
	}

	@Override
	public void insert(PlayerCharacter value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.insert(con,value);
			 con.commit();
			}
	}

	@Override
	public List<PlayerCharacter> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getList(con);
			}
	}

	@Override
	public List<PlayerCharacter> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getFilteredList(con,filter);
			}
	}

	
	@Override

	public List<ViewPlayerCharacter> getViewList(String filter) throws SQLException{
		try(Connection con = cm.getConnection()){
			return dao.getViewList(con,filter);
			}
	}
	
	@Override
	public void delete(PlayerCharacter value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.delete(con,value);
			 //
			 spellDao.deleteForPC(con, value.getPcId());
			 //
			 con.commit();
			}
	}

	@Override
	public void update(PlayerCharacter value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.update(con,value);
			 con.commit();
			}
	}

	@Override
	public List<CodedListItem<Integer>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			 return dao.getAsCodedList(con);
			}
	}

	public int getNextId() throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getNextid(con);
		}
	}
	
}
