package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.attributes.Wisdom;
import com.nobbysoft.first.common.servicei.WisdomService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.WisdomDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class WisdomServiceImpl implements WisdomService {

	private final ConnectionManager cm;
	private final WisdomDAO dao;
	
	public WisdomServiceImpl() {
		cm = new ConnectionManager();
		dao = new WisdomDAO();
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
	public Wisdom get(Integer key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);

			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(Wisdom value) throws SQLException {
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
	public List<Wisdom> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<Wisdom> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(Wisdom value) throws SQLException {
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
	public void update(Wisdom value) throws SQLException {
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
	public List<CodedListItem<Integer>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);
			} finally {
			con.rollback();
			}
			}
	}

}
