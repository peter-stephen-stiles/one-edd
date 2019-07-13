package com.nobbysoft.com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.com.nobbysoft.first.common.servicei.SpellService;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.server.dao.SpellDAO;
import com.nobbysoft.com.nobbysoft.first.server.utils.ConnectionManager;

public class SpellServiceImpl implements SpellService {

	private final ConnectionManager cm;
	private final SpellDAO dao;
	
	
	public SpellServiceImpl() {
		cm = new ConnectionManager();
		dao = new SpellDAO();
	}
	@Override
	public void createTable() throws SQLException {
		try(Connection con = cm.getConnection()){
		 dao.createTable(con);
		}
	}

	@Override
	public Spell get(String key) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.get(con,key);
			}
	}

	@Override
	public void insert(Spell value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.insert(con,value);
			 con.commit();
			}
	}

	@Override
	public List<Spell> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getList(con);
			}
	}

	@Override
	public List<Spell> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getFilteredList(con,filter);
			}
	}

	@Override
	public void delete(Spell value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.delete(con,value);
			 con.commit();
			}
	}

	@Override
	public void update(Spell value) throws SQLException {
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
}
