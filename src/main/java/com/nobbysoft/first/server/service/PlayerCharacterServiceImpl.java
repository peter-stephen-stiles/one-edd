package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.servicei.PlayerCharacterService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.views.ViewPlayerCharacter;
import com.nobbysoft.first.server.dao.PlayerCharacterDAO;
import com.nobbysoft.first.server.dao.PlayerCharacterDetailI;
import com.nobbysoft.first.server.dao.PlayerCharacterEquipmentDAO;
import com.nobbysoft.first.server.dao.PlayerCharacterHpDAO;
import com.nobbysoft.first.server.dao.PlayerCharacterSpellDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class PlayerCharacterServiceImpl implements PlayerCharacterService {

	private final ConnectionManager cm;
	private final PlayerCharacterDAO dao; 
	
	private final PlayerCharacterDetailI[] children;
	
	public PlayerCharacterServiceImpl() {
		cm = new ConnectionManager();
		dao = new PlayerCharacterDAO();
		//
		// make sure you list the detail kiddies here
		// so we can do a good deleete
		//
		children= new PlayerCharacterDetailI[] {
				new PlayerCharacterSpellDAO(),
				new PlayerCharacterEquipmentDAO(),
		};

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
	public PlayerCharacter get(Integer key) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.get(con,key);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public void insert(PlayerCharacter value) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			con.setAutoCommit(false);
			 dao.insert(con,value);
			 // hpDao
			 //
			 con.commit();
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<PlayerCharacter> getList() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getList(con);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<PlayerCharacter> getFilteredList(String filter) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getFilteredList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}

	
	@Override

	public List<ViewPlayerCharacter> getViewList(String filter) throws SQLException{
		try(Connection con = cm.getConnection()){
			try {
			return dao.getViewList(con,filter);
			} finally {
			con.rollback();
			}
			}
	}
	
	@Override
	public void delete(PlayerCharacter value) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			con.setAutoCommit(false);			 
			 //
			for(PlayerCharacterDetailI child:children) {
				child.deleteForPC(con, value.getPcId());
			}
			 
			 //
			 dao.delete(con,value);
			 //
			 con.commit();
			} finally {
			con.rollback();
			}
			}
			
	}

	@Override
	public void update(PlayerCharacter value) throws SQLException {
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

	public int getNextId() throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getNextid(con);
			} finally {
			con.rollback();
			}
		}
	}
	
}
