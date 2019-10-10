package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimit;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimitKey;
import com.nobbysoft.first.common.servicei.RaceClassLimitService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.RaceClassLimitDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class RaceClassLimitServiceImpl implements RaceClassLimitService {


	private final ConnectionManager cm;
	private final RaceClassLimitDAO dao;
	
	public RaceClassLimitServiceImpl() {
		cm=new ConnectionManager();
		dao = new RaceClassLimitDAO();
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
	public RaceClassLimit get(RaceClassLimitKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(RaceClassLimit value) throws SQLException {
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
	public List<RaceClassLimit> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);

			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<RaceClassLimit> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(RaceClassLimit value) throws SQLException {
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
	public void update(RaceClassLimit value) throws SQLException {
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
	public List<CodedListItem<RaceClassLimitKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);
			} finally {
			con.rollback();
			}
			}
	}

}
