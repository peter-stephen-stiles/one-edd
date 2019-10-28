package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.CharacterClassLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassLevelKey;
import com.nobbysoft.first.common.servicei.CharacterClassLevelService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.CharacterClassLevelDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class CharacterClassLevelServiceImpl implements CharacterClassLevelService {

	private final ConnectionManager cm;
	private final CharacterClassLevelDAO dao;
	
	public CharacterClassLevelServiceImpl() {
		cm = new ConnectionManager();
		dao = new CharacterClassLevelDAO();
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
	public CharacterClassLevel get(CharacterClassLevelKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);

			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(CharacterClassLevel value) throws SQLException {
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
	public List<CharacterClassLevel> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<CharacterClassLevel> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(CharacterClassLevel value) throws SQLException {
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
	public void update(CharacterClassLevel value) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			con.setAutoCommit(false);
			 dao.update(con,value);
			 con.commit();
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<CodedListItem<CharacterClassLevelKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);
			} finally {
			con.rollback();
			}
			}
	}


	@Override
	public int getMaxLevelLevelInTable(String characterClassId) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getMaxLevelInTable(con,characterClassId);
			} finally {
			con.rollback();
			}
			}
	}

	public int getMaxAllowedLevel(int pcId, String characterClassId) throws SQLException{
		
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getMaxAllowedLevel(con,pcId,characterClassId);
			} finally {
			con.rollback();
			}
			}
	}

	public CharacterClassLevel getThisLevel(String characterClassId, int level) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getThisLevel(con,characterClassId,level);
			} finally {
			con.rollback();
			}
			}
	}
	
	
	public CharacterClassLevel getNameLevel(String characterClassId) throws SQLException{
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getNameLevelInTable(con,characterClassId);
			} finally {
			con.rollback();
			}
			}
	}
	
}
