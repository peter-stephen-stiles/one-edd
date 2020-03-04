package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.TurnUndead;
import com.nobbysoft.first.common.entities.staticdto.TurnUndeadKey;
import com.nobbysoft.first.common.servicei.TurnUndeadService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.TurnUndeadDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class TurnUndeadServiceImpl implements TurnUndeadService {

	private final ConnectionManager cm;
	private final TurnUndeadDAO dao;
	
	public TurnUndeadServiceImpl() {
		cm = new ConnectionManager();
		dao = new TurnUndeadDAO();
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
	public TurnUndead get(TurnUndeadKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(TurnUndead value) throws SQLException {
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
	public List<TurnUndead> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<TurnUndead> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(TurnUndead value) throws SQLException {
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
	public void update(TurnUndead value) throws SQLException {
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
	public List<CodedListItem<TurnUndeadKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<TurnUndead> getListForClericLevel(int level) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getListForClericLevel(con,level);
			} finally {
			con.rollback();
			}
			}
	}
	
}
