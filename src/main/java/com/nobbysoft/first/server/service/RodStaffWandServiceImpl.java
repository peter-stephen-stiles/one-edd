package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.equipment.RodStaffWand;
import com.nobbysoft.first.common.servicei.RodStaffWandService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.RodStaffWandDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class RodStaffWandServiceImpl implements RodStaffWandService {

	private final ConnectionManager cm;
	private final RodStaffWandDAO dao;
	
	public RodStaffWandServiceImpl() {
		cm = new ConnectionManager();
		dao = new RodStaffWandDAO();
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
	public RodStaffWand get(String key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(RodStaffWand value) throws SQLException {
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
	public List<RodStaffWand> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<RodStaffWand> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(RodStaffWand value) throws SQLException {
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
	public void update(RodStaffWand value) throws SQLException {
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
	public List<RodStaffWand> getValidEquipmentForCharactersClasses(int pcId) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				
				return dao.getValidEquipmentForCharactersClasses(con, pcId);
			} finally {
				con.rollback();
			}
		}

	}
	
}
