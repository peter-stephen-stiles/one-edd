package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.equipment.MiscellaneousMagicItem;
import com.nobbysoft.first.common.servicei.MiscellaneousMagicItemService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.MiscellaneousMagicItemDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class MiscellaneousMagicItemServiceImpl implements MiscellaneousMagicItemService {

	private final ConnectionManager cm;
	private final MiscellaneousMagicItemDAO dao;
	
	public MiscellaneousMagicItemServiceImpl() {
		cm = new ConnectionManager();
		dao = new MiscellaneousMagicItemDAO();
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
	public MiscellaneousMagicItem get(String key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(MiscellaneousMagicItem value) throws SQLException {
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
	public List<MiscellaneousMagicItem> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<MiscellaneousMagicItem> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(MiscellaneousMagicItem value) throws SQLException {
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
	public void update(MiscellaneousMagicItem value) throws SQLException {
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
	public List<MiscellaneousMagicItem> getValidEquipmentForCharactersClasses(int pcId) throws SQLException {
		try (Connection con = cm.getConnection()) {
			try {
				
				return dao.getValidEquipmentForCharactersClasses(con, pcId);
			} finally {
				con.rollback();
			}
		}

	}
	
}
