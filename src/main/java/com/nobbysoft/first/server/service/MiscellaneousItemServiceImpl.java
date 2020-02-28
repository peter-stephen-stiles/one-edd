package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.equipment.MiscellaneousItem;
import com.nobbysoft.first.common.servicei.MiscellaneousItemService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.MiscellaneousItemDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class MiscellaneousItemServiceImpl implements MiscellaneousItemService {

	private final ConnectionManager cm;
	private final MiscellaneousItemDAO dao;
	
	public MiscellaneousItemServiceImpl() {
		cm = new ConnectionManager();
		dao = new MiscellaneousItemDAO();
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
	public MiscellaneousItem get(String key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(MiscellaneousItem value) throws SQLException {
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
	public List<MiscellaneousItem> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<MiscellaneousItem> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(MiscellaneousItem value) throws SQLException {
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
	public void update(MiscellaneousItem value) throws SQLException {
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
	public List<MiscellaneousItem> getValidEquipmentForCharactersClasses(int pcId) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				
				return dao.getValidEquipmentForCharactersClasses(con, pcId);
			} finally {
				con.rollback();
			}
		}

	}
	
}
