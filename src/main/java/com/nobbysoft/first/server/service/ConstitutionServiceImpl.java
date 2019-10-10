package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.attributes.Constitution;
import com.nobbysoft.first.common.servicei.ConstitutionService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.ConstitutionDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class ConstitutionServiceImpl implements ConstitutionService {

	private final ConnectionManager cm;
	private final ConstitutionDAO dao;
	
	public ConstitutionServiceImpl() {
		cm = new ConnectionManager();
		dao = new ConstitutionDAO();
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
	public Constitution get(Integer key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);

			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(Constitution value) throws SQLException {
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
	public List<Constitution> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<Constitution> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(Constitution value) throws SQLException {
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
	public void update(Constitution value) throws SQLException {
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
