package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.equipment.Armour;
import com.nobbysoft.first.common.entities.equipment.WeaponMelee;
import com.nobbysoft.first.common.servicei.ArmourService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.ArmourDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class ArmourServiceImpl implements ArmourService {

	private final ConnectionManager cm;
	private final ArmourDAO dao;
	
	public ArmourServiceImpl() {
		cm = new ConnectionManager();
		dao = new ArmourDAO();
	}

	@Override
	public void createTable() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
				dao.createTable(con);
				con.commit();
			} finally {
			con.rollback();
			}
		}
	}

	@Override
	public Armour get(String key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(Armour value) throws SQLException {
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
	public List<Armour> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<Armour> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(Armour value) throws SQLException {
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
	public void update(Armour value) throws SQLException {
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
	public List<CodedListItem<String>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);

			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<Armour> getValidEquipmentForCharactersClasses(int pcId) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				
				return dao.getValidEquipmentForCharactersClasses(con, pcId);
			} finally {
				con.rollback();
			}
		}

	}
	
}
