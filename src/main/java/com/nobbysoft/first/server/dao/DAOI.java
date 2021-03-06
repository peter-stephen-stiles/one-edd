package com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.utils.CodedListItem;

public interface DAOI<T extends DataDTOInterface<K>, K extends Comparable<K>> extends CreateInterface {
	
	public T get(Connection con, K key) throws SQLException;
	public void insert(Connection con, T value) throws SQLException;
	public List<T> getList(Connection con) throws SQLException;
	public List<T> getFilteredList(Connection con, String filter) throws SQLException;
	public void delete(Connection con, T value) throws SQLException;
	public void update(Connection con, T value) throws SQLException;
	public List<CodedListItem<K>> getAsCodedList(Connection con) throws SQLException;
	
	public List<T> getList(Connection con,DTORowListener<T> listener) throws SQLException;
	public List<T> getFilteredList(Connection con,String filter,DTORowListener<T> listener) throws SQLException;

}
