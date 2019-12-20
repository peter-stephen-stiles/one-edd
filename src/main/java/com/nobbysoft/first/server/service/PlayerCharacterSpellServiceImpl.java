package com.nobbysoft.first.server.service;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpell;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpellKey;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.servicei.PlayerCharacterSpellService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.views.ViewPlayerCharacterSpell;
import com.nobbysoft.first.server.dao.PlayerCharacterSpellDAO;
import com.nobbysoft.first.server.dao.SpellDAO;
import com.nobbysoft.first.server.utils.ConnectionManager; 

public class PlayerCharacterSpellServiceImpl implements PlayerCharacterSpellService {
	
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	

	private final ConnectionManager cm;
	private final PlayerCharacterSpellDAO dao; 
	private final SpellDAO spellDao; 
	
	public PlayerCharacterSpellServiceImpl() {
		cm = new ConnectionManager();
		dao = new PlayerCharacterSpellDAO(); 
		spellDao = new SpellDAO(); 
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
	public PlayerCharacterSpell get(PlayerCharacterSpellKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(PlayerCharacterSpell value) throws SQLException {
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
	public List<PlayerCharacterSpell> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<PlayerCharacterSpell> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	 

	
	@Override
	public void delete(PlayerCharacterSpell value) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			con.setAutoCommit(false);
			 dao.delete(con,value);
			 //
			 con.commit();
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void update(PlayerCharacterSpell value) throws SQLException {
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
	public List<CodedListItem<PlayerCharacterSpellKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);
			} finally {
			con.rollback();
			}
			}
	}
 

	@Override
	public List<PlayerCharacterSpell> getForPC(int pcId) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getForPC(con,pcId);
			} finally {
			con.rollback();
			}
			}
	}

	public List<ViewPlayerCharacterSpell> getViewForPC(int pcId) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getViewForPC(con,pcId);
			} finally {
				con.rollback();
			}
		}
	}

 
	public List<Spell> getSpellsNotForPC(int pcId, int level, String spellClassId,String filterName) throws SQLException
	{
		try(Connection con = cm.getConnection()){
			try {
			return spellDao.getSpellsNotForPC(con, pcId, level, spellClassId, filterName);
			} finally {
			con.rollback();
			}
		}
	}
 		 
	
 
 		 
	
}
