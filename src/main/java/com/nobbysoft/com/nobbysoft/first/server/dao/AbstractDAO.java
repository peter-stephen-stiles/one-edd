package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;

public abstract class AbstractDAO<T extends DataDTOInterface,K extends Comparable>  {


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


	abstract void setFilterParameters(PreparedStatement ps ,String filter) throws SQLException ;
	
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
		 
			
			private int addObjectsToPS(PreparedStatement ps, int col,Object[] fieldValues) throws SQLException{
				for(Object o:fieldValues) {
					if(o==null){
						ps.setNull(col++,Types.VARCHAR); // your milage may vary :O
					} else if(o instanceof BigDecimal) {
						ps.setBigDecimal(col++,(BigDecimal)o);
					} else if(o instanceof Integer) {
						ps.setInt(col++,(Integer)o);
					} else if(o instanceof Long) {
						ps.setLong(col++,(Long)o);
					} else if(o instanceof Double) {
						ps.setDouble(col++,(Double)o);
					} else if(o instanceof Float) {
						ps.setFloat(col++,(Float)o);
					} else if(o instanceof Boolean) {
						ps.setBoolean(col++,(Boolean)o);
					} else if(o instanceof Long) {
						ps.setLong(col++,(Long)o);
					} else if(o instanceof java.util.Date) {
						ps.setDate(col++,new java.sql.Date(((java.util.Date)o).getTime()));
					} else if(o instanceof java.sql.Date) {
						ps.setDate(col++, (java.sql.Date)o);
					} else if(o instanceof java.sql.Timestamp) {
						ps.setTimestamp(col++, (java.sql.Timestamp)o);
					} else {
						ps.setString(col++, o.toString());
					}
				}
				return col;
			}
		
			public List<T> getListFromPartialKey(Connection con, String[] queryFields,Object[] fieldValues) throws SQLException {
				DTORowListener<T> listener = null;
				return getListFromPartialKey(con, queryFields,fieldValues, listener);
			}
			
			public List<T> getListFromPartialKey(Connection con, String[] queryFields,Object[] fieldValues, DTORowListener<T> listener) throws SQLException {
				if(queryFields==null||queryFields.length==0) {
					// skip this 
					return getList(con);
				}
				
				String sql = "SELECT ";
				sql = addKeyFields(sql);
				sql = addDataFields(sql);
				sql = sql + " FROM "+getTableName()+"  ";
				for(int i=0,n=queryFields.length;i<n;i++) {
					if(i==0) {
						sql = sql + " WHERE ";
					} else {
						sql = sql + " AND ";
					}
					sql = sql + queryFields[i]+ " = ? ";
				}
				sql = addOrderByClause(sql);
				List<T> data = new ArrayList<>();
				boolean first=true;
				
				LOGGER.info("SQL:"+sql);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					addObjectsToPS(ps,1,fieldValues);
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

			public void insertList(Connection con, List<T> values) throws SQLException {
				for(T value:values) {
					insert(con,value);
				}
			}
			

			public void deleteList(Connection con, List<T> values) throws SQLException {
				for(T value:values) {
					delete(con,value);
				}
			}
			
			public void updateList(Connection con, List<T> values) throws SQLException {
				for(T value:values) {
					update(con,value);
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
