package com.nobbysoft.first.server.service;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.equipment.EquipmentI;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.EquipmentWhere;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipmentKey;
import com.nobbysoft.first.common.servicei.PlayerCharacterEquipmentService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.views.ViewPlayerCharacterEquipment;
import com.nobbysoft.first.server.dao.DAOI;
import com.nobbysoft.first.server.dao.PlayerCharacterEquipmentDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;
import com.nobbysoft.first.utils.DataMapper; 

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
			try {
		 dao.createTable(con);
			} finally {
			con.rollback();
			}
		}
	}

	@Override
	public PlayerCharacterEquipment get(PlayerCharacterEquipmentKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(PlayerCharacterEquipment value) throws SQLException {
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
	public List<PlayerCharacterEquipment> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<PlayerCharacterEquipment> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	 

	
	@Override
	public void delete(PlayerCharacterEquipment value) throws SQLException {
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
	public void update(PlayerCharacterEquipment value) throws SQLException {
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
	public List<CodedListItem<PlayerCharacterEquipmentKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);
			} finally {
			con.rollback();
			}
			}
	}

	public int getNextId() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getNextid(con);
			} finally {
			con.rollback();
			}
		}
	}

 

	@Override
	public List<ViewPlayerCharacterEquipment> getForPC(int pcId) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getForPC(con,pcId);

			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public EquipmentI getUnderlyingEquipment(String code, EquipmentType type) throws SQLException{
	EquipmentI e=null;
	try(Connection con = cm.getConnection()){
		try {
		e = getUnderlying(con,code, type);
		} finally {
		con.rollback();
		}
		}
	return e;
	}

	private EquipmentI getUnderlying(Connection con,String code, EquipmentType type) throws SQLException {
		EquipmentI e;
		try{
		DAOI<?, String> dao;
		try {
			dao = (DAOI<?, String>)DataMapper.INSTANCE.getEquipmentDao(type).newInstance();
		} catch (Exception e1) {
			throw new SQLException("Cannot create the DAO :I",e1);
		}
		e = (EquipmentI)dao.get(con, code);
		} finally {
		con.rollback();
		}
		return e;
	}

	public void equip(PlayerCharacterEquipment equipment,EquipmentWhere where) throws SQLException{

		try(Connection con = cm.getConnection()){
			try {
			con.setAutoCommit(false);
			
			EquipmentI e =getUnderlying(con,equipment.getCode(),equipment.getEquipmentType()); 
			
			
			dao.equip(con, equipment, e,where);
			
			 con.commit();

			} finally {
			con.rollback();
			}
			}
		}		 
	
}
