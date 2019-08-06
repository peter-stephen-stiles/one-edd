package com.nobbysoft.com.nobbysoft.first.server.service;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentI;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentWhere;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipmentKey;
import com.nobbysoft.com.nobbysoft.first.common.servicei.PlayerCharacterEquipmentService;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.common.views.ViewPlayerCharacterEquipment;
import com.nobbysoft.com.nobbysoft.first.server.dao.DAOI;
import com.nobbysoft.com.nobbysoft.first.server.dao.PlayerCharacterEquipmentDAO;
import com.nobbysoft.com.nobbysoft.first.server.utils.ConnectionManager;
import com.nobbysoft.com.nobbysoft.first.utils.DataMapper; 

public class PlayerCharacterEquipmentServiceImpl implements PlayerCharacterEquipmentService {
	
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	

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
	public List<ViewPlayerCharacterEquipment> getForPC(int pcId) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getForPC(con,pcId);
			}
	}

	@Override
	public EquipmentI getUnderlyingEquipment(String code, EquipmentType type) throws SQLException{
	EquipmentI e=null;
	try(Connection con = cm.getConnection()){
		e = getUnderlying(con,code, type);
		}
	return e;
	}

	private EquipmentI getUnderlying(Connection con,String code, EquipmentType type) throws SQLException {
		EquipmentI e;
		DAOI<?, String> dao;
		try {
			dao = (DAOI<?, String>)DataMapper.INSTANCE.getEquipmentDao(type).newInstance();
		} catch (Exception e1) {
			throw new SQLException("Cannot create the DAO :I",e1);
		}
		e = (EquipmentI)dao.get(con, code);
		return e;
	}

	public void equip(PlayerCharacterEquipment equipment,EquipmentWhere where) throws SQLException{

		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			
			EquipmentI e =getUnderlying(con,equipment.getCode(),equipment.getEquipmentType()); 
			
			
			dao.equip(con, equipment, e,where);
			
			 con.commit();
			}
		}		 
	
}
