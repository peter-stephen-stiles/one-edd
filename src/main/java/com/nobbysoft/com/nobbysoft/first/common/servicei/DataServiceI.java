package com.nobbysoft.com.nobbysoft.first.common.servicei;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;

public interface DataServiceI <T extends DataDTOInterface<K>, K extends Comparable<K>> {
	
	public void createTable() throws SQLException;
	public T get(K key) throws SQLException;
	public void insert(T value) throws SQLException;
	public List<T> getList() throws SQLException;
	public List<T> getFilteredList( String filter) throws SQLException;
	public void delete(T value) throws SQLException;
	public void update( T value) throws SQLException;
	public List<CodedListItem<K>> getAsCodedList() throws SQLException;
	
 
}
