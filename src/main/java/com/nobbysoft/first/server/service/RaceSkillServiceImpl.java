package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.RaceSkill;
import com.nobbysoft.first.common.entities.staticdto.RaceSkillKey;
import com.nobbysoft.first.common.servicei.RaceSkillService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.RaceSkillDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class RaceSkillServiceImpl implements RaceSkillService {


	private final ConnectionManager cm;
	private final RaceSkillDAO dao;
	
	public RaceSkillServiceImpl() {
		cm=new ConnectionManager();
		dao = new RaceSkillDAO();
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
	public RaceSkill get(RaceSkillKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(RaceSkill value) throws SQLException {
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
	public List<RaceSkill> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);

			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<RaceSkill> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(RaceSkill value) throws SQLException {
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
	public void update(RaceSkill value) throws SQLException {
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
	public List<CodedListItem<RaceSkillKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);
			} finally {
			con.rollback();
			}
			}
	}

}
