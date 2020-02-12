package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.CharacterClassSkill;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSkillKey;
import com.nobbysoft.first.common.servicei.CharacterClassSkillService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.server.dao.CharacterClassSkillDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class CharacterClassSkillServiceImpl implements CharacterClassSkillService {

	private final ConnectionManager cm;
	private final CharacterClassSkillDAO dao;
	
	public CharacterClassSkillServiceImpl() {
		cm = new ConnectionManager();
		dao = new CharacterClassSkillDAO();
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
	public CharacterClassSkill get(CharacterClassSkillKey key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);

			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(CharacterClassSkill value) throws SQLException {
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
	public List<CharacterClassSkill> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<CharacterClassSkill> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void delete(CharacterClassSkill value) throws SQLException {
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
	public void update(CharacterClassSkill value) throws SQLException {
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
	public List<CodedListItem<CharacterClassSkillKey>> getAsCodedList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getAsCodedList(con);
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
				List<CharacterClassSkill> listTo = dao.getFilteredList(con,toClassId);
				if(listTo.size()>0) {
					return new ReturnValue<Integer>(ReturnValue.IS_ERROR.TRUE,"You can't copy if you've already got some");
				}
				List<CharacterClassSkill> listFrom = dao.getFilteredList(con,fromClassId);
				if(listFrom.size()==0) {
					return new ReturnValue<Integer>(ReturnValue.IS_ERROR.TRUE,"There's nothing to copy :(");
				}
				for(CharacterClassSkill th: listFrom) {
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
 
	public ReturnValue<List<CharacterClassSkill>> getSkillForClassLevel(String classId,int level)throws SQLException{
		try(Connection con = cm.getConnection()){
			try {
			 return dao.getSkillForClassLevel(con,classId,level);
			} finally {
			con.rollback();
			}
			}
		
		
		
	}
	
}
