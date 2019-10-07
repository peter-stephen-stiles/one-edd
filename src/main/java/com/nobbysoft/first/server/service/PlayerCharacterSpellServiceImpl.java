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
import com.nobbysoft.first.server.utils.ConnectionManager; 

public class PlayerCharacterSpellServiceImpl implements PlayerCharacterSpellService {
	
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	

	private final ConnectionManager cm;
	private final PlayerCharacterSpellDAO dao; 
	
	public PlayerCharacterSpellServiceImpl() {
		cm = new ConnectionManager();
		dao = new PlayerCharacterSpellDAO(); 
	}

	@Override
	public void createTable() throws SQLException {
		try(Connection con = cm.getConnection()){
		 dao.createTable(con);
		}
	}

	@Override
	public PlayerCharacterSpell get(PlayerCharacterSpellKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.get(con,key);
			}
	}

	@Override
	public void insert(PlayerCharacterSpell value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.insert(con,value);
			 con.commit();
			}
	}

	@Override
	public List<PlayerCharacterSpell> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getList(con);
			}
	}

	@Override
	public List<PlayerCharacterSpell> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getFilteredList(con,filter);
			}
	}

	 

	
	@Override
	public void delete(PlayerCharacterSpell value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.delete(con,value);
			 //
			 con.commit();
			}
	}

	@Override
	public void update(PlayerCharacterSpell value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.update(con,value);
			 con.commit();
			}
	}

	@Override
	public List<CodedListItem<PlayerCharacterSpellKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			 return dao.getAsCodedList(con);
			}
	}
 

	@Override
	public List<PlayerCharacterSpell> getForPC(int pcId) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getForPC(con,pcId);
			}
	}

	public List<ViewPlayerCharacterSpell> getViewForPC(int pcId) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getViewForPC(con,pcId);
			}
	}

 
	public List<Spell> getViewNotForPC(int pcId, int level, String spellClassId,String filterName) throws SQLException
	{
		try(Connection con = cm.getConnection()){
			return dao.getViewNotForPC(con, pcId, level, spellClassId, filterName);
		}
	}
 		 
	
 
 		 
	
}
