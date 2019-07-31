package com.nobbysoft.com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipmentKey;
import com.nobbysoft.com.nobbysoft.first.common.servicei.PlayerCharacterEquipmentService;
import com.nobbysoft.com.nobbysoft.first.common.servicei.PlayerCharacterService;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.common.views.ViewPlayerCharacter;
import com.nobbysoft.com.nobbysoft.first.server.dao.PlayerCharacterDAO;
import com.nobbysoft.com.nobbysoft.first.server.dao.PlayerCharacterEquipmentDAO;
import com.nobbysoft.com.nobbysoft.first.server.dao.PlayerCharacterSpellDAO;
import com.nobbysoft.com.nobbysoft.first.server.utils.ConnectionManager;

public class PlayerCharacterEquipmentServiceImpl implements PlayerCharacterEquipmentService {

	private final ConnectionManager cm;
	private final PlayerCharacterEquipmentDAO dao; 
	
	public PlayerCharacterEquipmentServiceImpl() {
		cm = new ConnectionManager();
		dao = new PlayerCharacterEquipmentDAO(); 
	}

	@Override
	public void createTable() throws SQLException {
		try(Connection con = cm.getConnection()){
		 dao.createTable(con);
		}
	}

	@Override
	public PlayerCharacterEquipment get(PlayerCharacterEquipmentKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.get(con,key);
			}
	}

	@Override
	public void insert(PlayerCharacterEquipment value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.insert(con,value);
			 con.commit();
			}
	}

	@Override
	public List<PlayerCharacterEquipment> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getList(con);
			}
	}

	@Override
	public List<PlayerCharacterEquipment> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getFilteredList(con,filter);
			}
	}

	 

	
	@Override
	public void delete(PlayerCharacterEquipment value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.delete(con,value);
			 //
			 con.commit();
			}
	}

	@Override
	public void update(PlayerCharacterEquipment value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.update(con,value);
			 con.commit();
			}
	}

	@Override
	public List<CodedListItem<PlayerCharacterEquipmentKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			 return dao.getAsCodedList(con);
			}
	}

	public int getNextId() throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getNextid(con);
		}
	}

 

	@Override
	public List<PlayerCharacterEquipment> getForPC(int pcId) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getForPC(con,pcId);
			}
	}
	
}
