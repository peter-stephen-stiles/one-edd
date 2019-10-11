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
			try {
		 dao.createTable(con);

			} finally {
			con.rollback();
			}
		}
	}

	@Override
	public CharacterClassSpell get(CharacterClassSpellKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);

			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(CharacterClassSpell value) throws SQLException {
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
	public List<CharacterClassSpell> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<CharacterClassSpell> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(CharacterClassSpell value) throws SQLException {
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
	public void update(CharacterClassSpell value) throws SQLException {
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
	public List<CodedListItem<CharacterClassSpellKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);
			} finally {
			con.rollback();
			}
			}
	}


	@Override
	public int getMaxSpellLevelInTable(String characterClassId) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getMaxSpellLevelInTable(con,characterClassId);
			} finally {
			con.rollback();
			}
			}
	}

	
	public List<CharacterClassSpell> getAllowedSpells( int pcId) throws SQLException{
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAllowedSpells(con,pcId);
			} finally {
			con.rollback();
			}
			}
	}
	
	
	public List<String> getSpellClassesForClasses(List<String> classIds) throws SQLException{
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getSpellClassesForClasses(con,classIds);
			} finally {
			con.rollback();
			}
			}
	}
	
}
