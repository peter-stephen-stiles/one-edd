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
			try {
		 dao.createTable(con);

		} finally {
		con.rollback();
		}
		}
	}

	@Override
	public CharacterClass get(String key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);

		} finally {
		con.rollback();
		}
			}
	}

	@Override
	public void insert(CharacterClass value) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			con.setAutoCommit(false);
			 dao.insert(con,value);
			 con.commit();

			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<CharacterClass> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<CharacterClass> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(CharacterClass value) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			con.setAutoCommit(false);
			 dao.delete(con,value);
			 con.commit();
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void update(CharacterClass value) throws SQLException {
		try(Connection con = cm.getConnection()){
			try{
				con.setAutoCommit(false);
			 dao.update(con,value);
			 con.commit();
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<CodedListItem<String>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);

			} finally {
			con.rollback();
			}
			}
	}


	public List<CodedListItem<String>>  getSpellClassesAsCodedList(){
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getSpellClassesAsCodedList(con);
			} finally {
			con.rollback();
			}	
			} catch (SQLException e) {
				throw new IllegalStateException("Trouble with getting spell classes",e);

		}
	}
	
}
