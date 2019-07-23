package com.nobbysoft.com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.WeaponAmmunition;
import com.nobbysoft.com.nobbysoft.first.common.servicei.WeaponAmmunitionService;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.server.dao.WeaponAmmunitionDAO;
import com.nobbysoft.com.nobbysoft.first.server.utils.ConnectionManager;

public class WeaponAmmunitionServiceImpl implements WeaponAmmunitionService {

	private final ConnectionManager cm;
	private final WeaponAmmunitionDAO dao;
	
	public WeaponAmmunitionServiceImpl() {
		cm = new ConnectionManager();
		dao = new WeaponAmmunitionDAO();
	}

	@Override
	public void createTable() throws SQLException {
		try(Connection con = cm.getConnection()){
		 dao.createTable(con);
		}
	}

	@Override
	public WeaponAmmunition get(String key) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.get(con,key);
			}
	}

	@Override
	public void insert(WeaponAmmunition value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.insert(con,value);
			 con.commit();
			}
	}

	@Override
	public List<WeaponAmmunition> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getList(con);
			}
	}

	@Override
	public List<WeaponAmmunition> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getFilteredList(con,filter);
			}
	}

	@Override
	public void delete(WeaponAmmunition value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.delete(con,value);
			 con.commit();
			}
	}

	@Override
	public void update(WeaponAmmunition value) throws SQLException {
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
