package com.nobbysoft.com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.RaceClassLimit;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.RaceClassLimitKey;
import com.nobbysoft.com.nobbysoft.first.common.servicei.RaceClassLimitService;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.server.dao.RaceClassLimitDAO;
import com.nobbysoft.com.nobbysoft.first.server.utils.ConnectionManager;

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
		 dao.createTable(con);
		}
	}

	@Override
	public RaceClassLimit get(RaceClassLimitKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.get(con,key);
			}
	}

	@Override
	public void insert(RaceClassLimit value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.insert(con,value);
			 con.commit();
			}
	}

	@Override
	public List<RaceClassLimit> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getList(con);
			}
	}

	@Override
	public List<RaceClassLimit> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			return dao.getFilteredList(con,filter);
			}
	}

	@Override
	public void delete(RaceClassLimit value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.delete(con,value);
			 con.commit();
			}
	}

	@Override
	public void update(RaceClassLimit value) throws SQLException {
		try(Connection con = cm.getConnection()){
			con.setAutoCommit(false);
			 dao.update(con,value);
			con.commit();
			}
	}

	@Override
	public List<CodedListItem<RaceClassLimitKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			 return dao.getAsCodedList(con);
			}
	}

}
