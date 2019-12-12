package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.equipment.EquipmentClass;
import com.nobbysoft.first.common.entities.equipment.EquipmentClassKey;
import com.nobbysoft.first.common.servicei.EquipmentClassService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.views.ViewClassEquipment;
import com.nobbysoft.first.server.dao.EquipmentClassDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class EquipmentClassServiceImpl implements EquipmentClassService {

	private final ConnectionManager cm;
	private final EquipmentClassDAO dao;

	public EquipmentClassServiceImpl() {
		cm = new ConnectionManager();
		dao = new EquipmentClassDAO();
	}

	@Override
	public void createTable() throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				dao.createTable(con);

			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public EquipmentClass get(EquipmentClassKey key) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.get(con, key);

			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public void insert(EquipmentClass value) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				con.setAutoCommit(false);
				dao.insert(con, value);
				con.commit();

			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public List<EquipmentClass> getList() throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getList(con);
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public List<EquipmentClass> getFilteredList(String filter) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getFilteredList(con, filter);
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public void delete(EquipmentClass value) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				con.setAutoCommit(false);
				dao.delete(con, value);
				con.commit();
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public void update(EquipmentClass value) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				con.setAutoCommit(false);
				dao.update(con, value);
				con.commit();
			} finally {
				con.rollback();
			}
		}
	}

	@Override
	public List<CodedListItem<EquipmentClassKey>> getAsCodedList() throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getAsCodedList(con);

			} finally {
				con.rollback();
			}
		}
	}

	public List<ViewClassEquipment> getViewForClass(String classId) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getViewForClass(con, classId);

			} finally {
				con.rollback();
			}
		}
	}

	public List<ViewClassEquipment> getViewForEquipmentType(String type) throws SQLException{
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getViewForEquipmentType(con,type);

			} finally {
			con.rollback();
			}
		}
	}

	public List<ViewClassEquipment> getViewForEquipment(String type, String code) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getViewForEquipment(con,type,code);

			} finally {
			con.rollback();
			}
		}
	}

	public List<ViewClassEquipment> getViewForClassAll(String classId) throws SQLException{
		try (Connection con = cm.getConnection()) {
			try {
				return dao.getViewForClassAll(con, classId);

			} finally {
				con.rollback();
			}
		}
	}
	
}
