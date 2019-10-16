package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.attributes.Intelligence;
import com.nobbysoft.first.common.servicei.IntelligenceService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.IntelligenceDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class IntelligenceServiceImpl implements IntelligenceService {

	private final ConnectionManager cm;
	private final IntelligenceDAO dao;
	
	public IntelligenceServiceImpl() {
		cm = new ConnectionManager();
		dao = new IntelligenceDAO();
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
	public Intelligence get(Integer key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);

			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(Intelligence value) throws SQLException {
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
	public List<Intelligence> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<Intelligence> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(Intelligence value) throws SQLException {
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
	public void update(Intelligence value) throws SQLException {
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
