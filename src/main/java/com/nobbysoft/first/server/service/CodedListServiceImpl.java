package com.nobbysoft.first.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.CodedListDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;

public class CodedListServiceImpl implements CodedListService {


	private final ConnectionManager cm;
	private final CodedListDAO dao;
	
	public CodedListServiceImpl() {
		 cm = new ConnectionManager();
		 dao = new CodedListDAO();
	}

	@Override
	public List<CodedListItem<?>> getCodedListWithNullOption(String type) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getCodedListWithNullOption(con,type);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public Map<Comparable, String> getCodedListMapWithNullOption(String type) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getCodedListMapWithNullOption(con,type);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public Map<Comparable, String> getCodedListMap(String type) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getCodedListMap(con,type);
			} finally {
			con.rollback();
			}
			}
	}

	@Override
	public List<CodedListItem<?>> getCodedList(String type) throws SQLException {
		try(Connection con = cm.getConnection()){
			try {
			return dao.getCodedList(con,type);
			} finally {
			con.rollback();
			}
			}
	}

 

	@Override
	public List<CodedListItem<?>> getAttBonus() {
			return dao.getAttBonus();
	}

	@Override
	public List<CodedListItem<?>> getAttributesWithZero() {
		return dao.getAttributesWithZero();
	}

	@Override
	public List<CodedListItem<?>> getAttributes() {
		return dao.getAttributes();
	}

	@Override
	public List<CodedListItem<?>> getAttRolls() {
		return dao.getAttRolls();
	}

	@Override
	public List<CodedListItem<?>> getDice() {
		return dao.getDice();
	}

	@Override
	public List<CodedListItem<?>> getRaceClassLimitMaxLevelList() {
		return dao.getRaceClassLimitMaxLevelList();
	}

}
