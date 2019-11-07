package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.CharacterClassToHit;
import com.nobbysoft.first.common.entities.staticdto.SavingThrow;
import com.nobbysoft.first.common.entities.staticdto.SavingThrowKey;
import com.nobbysoft.first.common.servicei.SavingThrowService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.server.dao.SavingThrowDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class SavingThrowServiceImpl implements SavingThrowService {

	private final ConnectionManager cm;
	private final SavingThrowDAO dao;
	
	public SavingThrowServiceImpl() {
		cm = new ConnectionManager();
		dao = new SavingThrowDAO();
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
	public SavingThrow get(SavingThrowKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(SavingThrow value) throws SQLException {
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
	public List<SavingThrow> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<SavingThrow> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(SavingThrow value) throws SQLException {
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
	public void update(SavingThrow value) throws SQLException {
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
	public List<CodedListItem<SavingThrowKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);

			} finally {
			con.rollback();
			}
			}
	}

	public List<SavingThrow> getSavesForClassLevel(String classId, int level) throws SQLException{
		
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getSavesForClassLevel(con,classId,level);

			} finally {
			con.rollback();
			}
			}
	}

	
	public ReturnValue<Integer> copyFrom(String fromClassId, String toClassId) throws SQLException{
		int created = 0;
		try(Connection con = cm.getConnection()){
			try {
				if(fromClassId.equals(toClassId)) {
					return new ReturnValue<Integer>(ReturnValue.IS_ERROR.TRUE,"You can't copy something to itself!");
				}
				List<SavingThrow> listTo = dao.getFilteredList(con,toClassId);
				if(listTo.size()>0) {
					return new ReturnValue<Integer>(ReturnValue.IS_ERROR.TRUE,"You can't copy if you've already got some");
				}
				List<SavingThrow> listFrom = dao.getFilteredList(con,fromClassId);
				if(listFrom.size()==0) {
					return new ReturnValue<Integer>(ReturnValue.IS_ERROR.TRUE,"There's nothing to copy :(");
				}
				for(SavingThrow th: listFrom) {
					th.setClassId(toClassId);
					dao.insert(con, th);
				}
				con.commit();
			} finally {
			con.rollback();
			}
			}
		return new ReturnValue<Integer>(created);
	}
	
}
