package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;

public abstract class AbstractDAO<T extends DataDTOInterface,K extends Comparable> {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public AbstractDAO() {
		 
	}
	
	abstract T dtoFromRS(ResultSet rs) throws SQLException;
	abstract void setPSFromKey(PreparedStatement ps,K key) throws SQLException;
	
	
	abstract String[] getKeys();
	abstract String[] getData();
	abstract String getTableName();
	
	abstract String addOrderByClause(String sql) ;
	abstract String getFilterWhere();

	//sql = sql + "  character_name like ? or player_name like ?";
	abstract void setFilterParameters(PreparedStatement ps ,String filter) throws SQLException ;
	/*
	 * 					String f = "%" + filter + "%";
			ps.setString(1, f); 
			ps.setString(2, f); 
	 */
	
	
	 String questionsForInsert() {
		String s = "";
		boolean first = true;
		for(String k:getKeys()) {
			if(!first) {
				s = s  + ", ";
			} 
			s = s + "?";
			first = false;
		}
		for(String k:getData()) {
			if(!first) {
				s = s  + ", ";
			} 
			s = s + "?";
			first = false;
		}
		return s;
	}
	
	 String addKeyFields(String sql) {
		for(String key:getKeys()) {
			sql = sql + " "+key+",";
		}
		return sql;
	}
	
	 String addKeyColumnsForUpdate(String sql) {
		boolean first=true;
		for(String key:getKeys()) {
			if(!first) {
				sql=sql+" AND ";
			}
			sql = sql + " "+key+" = ?";
			first = false;
		} 
		return sql;
	}
	
	 String addDataColumnsForUpdate(String sql) {
		 return addDataColumnsForUpdate(sql,true);
	 }
	 
	 String addDataColumnsForUpdate(String sql, boolean commaAtBeginning) {		
		 boolean first = true;		 
			for(String key:getData()) {				
				if(first) {
					if(commaAtBeginning) {
						sql=sql+", ";						
					}
				} else {
					sql=sql+", ";				
				}
				
				sql = sql + " "+key+" = ?";
				first=false;
			} 
			return sql;
	 }
	 
	 String addDataFields(String sql) {
		boolean first=true;
		for(String field:getData()) {
			if(!first) {
				sql=sql+", ";
			}
			sql = sql + " "+field;
			first=false;
		}
		return sql;
	}

		public T get(Connection con, K key) throws SQLException {
			String sql = "SELECT ";
			sql = addKeyFields(sql);
			sql = addDataFields(sql);
			sql = sql + " FROM "+getTableName()+"  WHERE ";
			sql = addKeyColumnsForUpdate(sql); 
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				setPSFromKey(ps,key);
				
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						T dto = dtoFromRS(rs);
						return (dto);
					} else {
						throw new RecordNotFoundException("Cannot find "+getTableName() + " " + key);
					}

				}
			}

		}
	 
			abstract int setPSFromKeys(T value, PreparedStatement ps, int col) throws SQLException;
			abstract int setPSFromData(T value, PreparedStatement ps, int col) throws SQLException;
		 
			
			
			public void insert(Connection con, T value) throws SQLException {
				String sql = "INSERT INTO "+getTableName()+ " ( ";
				sql = addKeyFields(sql);
				sql = addDataFields(sql);
				sql = sql + ") values ( ";
				sql = sql + questionsForInsert();
				sql = sql + ")";
				LOGGER.info("sql is:"+sql);
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					int col = 1;
					col = setPSFromKeys(value, ps, col);
					col = setPSFromData(value, ps, col);
					ps.executeUpdate();
				}
			}
			 
			public List<T> getList(Connection con) throws SQLException {
				return getList(con,null);
			}			
		 
			public List<T> getList(Connection con, DTORowListener<T> listener) throws SQLException {
				
				String sql = "SELECT ";
				sql = addKeyFields(sql);
				sql = addDataFields(sql);
				sql = sql + " FROM "+getTableName()+"  ";
				sql = addOrderByClause(sql);
				List<T> data = new ArrayList<>();
				boolean first=true;
				
				LOGGER.info("SQL:"+sql);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					try (ResultSet rs = ps.executeQuery()) {
						while (rs.next()) {
							T dto = dtoFromRS(rs);
							if(listener!=null) {
								listener.hereHaveADTO(dto, first);
							} else {
							data.add(dto);
							}
							first=false;
						}

					}
				}

				return data;
			}			
			
			public void delete(Connection con, T value) throws SQLException {
				String sql = "DELETE FROM "+getTableName()+" WHERE";
				sql = addKeyColumnsForUpdate(sql);

				try (PreparedStatement ps = con.prepareStatement(sql)) {
					int col = 1;
					col = setPSFromKeys(value, ps, col);
					int rows = ps.executeUpdate();
					if (rows == 0) {
						throw new RecordNotFoundException("Can't find " +getTableName()+ 
								" to delete it id=" +value.getKey());
					}
				}

			}

			public void update(Connection con, T value) throws SQLException {
				String sql = " UPDATE " +getTableName()+ "  SET ";
				sql = addDataColumnsForUpdate(sql,false);
				sql = sql + " WHERE ";
				sql = addKeyColumnsForUpdate(sql);


				LOGGER.info("SQL:\n\n"+sql);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					int col = 1;
					col = setPSFromData(value, ps, col);

					col = setPSFromKeys(value, ps, col);

					int rows = ps.executeUpdate();
					if (rows == 0) {
						throw new RecordNotFoundException("Can't find " +getTableName()+ 
								" to update it id=" +value.getKey());
					}
				}
			}			

			

			public List<T> getFilteredList(Connection con, String filter,
					DTORowListener<T> listener) throws SQLException {
				
				if (filter == null || filter.isEmpty()) {
					return getList(con,listener);
				}
				String sql = "SELECT ";
				sql = addKeyFields(sql);
				sql = addDataFields(sql);
				sql = sql + " FROM " +getTableName()+"  WHERE ";
				sql = sql+ getFilterWhere();
				sql = addOrderByClause(sql);
				List<T> data = new ArrayList<>();
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					setFilterParameters(ps,filter);

					boolean first=true;
					try (ResultSet rs = ps.executeQuery()) {
						while (rs.next()) {
							T dto = dtoFromRS(rs);
							if(listener!=null) {
								listener.hereHaveADTO(dto, first);
							} else {
								data.add(dto);
							}
							first = false;
						}

					}
				}

				return data;
			}			

			public List<T> getFilteredList(Connection con, String filter) throws SQLException {
				return getFilteredList(con,filter,null);
			}
			
			
			abstract String getSELECTForCodedList();
			abstract void setCodedListItemFromRS(CodedListItem<K> dto,ResultSet rs) throws SQLException;
			
			public List<CodedListItem<K>> getAsCodedList(Connection con) throws SQLException {
				String sql =  getSELECTForCodedList();
				List<CodedListItem<K>> data = new ArrayList<>();
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					try (ResultSet rs = ps.executeQuery()) {
						while (rs.next()) {
							CodedListItem<K> dto = new CodedListItem<>();							
							setCodedListItemFromRS(dto,rs); //dto.setItem(rs.getString(col++));
							dto.setDescription(rs.getString(2));
							data.add(dto);
						}

					}
				}

				return data;
			}			
			
}
