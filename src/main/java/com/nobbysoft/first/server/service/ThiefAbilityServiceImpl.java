package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.ThiefAbility;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbilityKey;
import com.nobbysoft.first.common.servicei.ThiefAbilityService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.ThiefAbilityDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class ThiefAbilityServiceImpl implements ThiefAbilityService {

	private final ConnectionManager cm;
	private final ThiefAbilityDAO dao;
	
	public ThiefAbilityServiceImpl() {
		cm = new ConnectionManager();
		dao = new ThiefAbilityDAO();
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
	public ThiefAbility get(ThiefAbilityKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(ThiefAbility value) throws SQLException {
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
	public List<ThiefAbility> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<ThiefAbility> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(ThiefAbility value) throws SQLException {
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
	public void update(ThiefAbility value) throws SQLException {
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
	public List<CodedListItem<ThiefAbilityKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);
			} finally {
			con.rollback();
			}
			}
	}

	public List<ThiefAbility> getListForThiefLevel(int level) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
				return dao.getListForThiefLevel(con, level);
			} finally {
				con.rollback();
				}
		}
		
	}
	
}
