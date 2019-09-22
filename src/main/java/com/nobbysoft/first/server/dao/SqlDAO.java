package com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nobbysoft.first.common.entities.meta.DTOColumn;
import com.nobbysoft.first.common.entities.meta.DTOConstraint;
import com.nobbysoft.first.common.entities.meta.DTOIndex;
import com.nobbysoft.first.common.entities.meta.DTOTable;
import com.nobbysoft.first.common.utils.ResultSetListener;

public class SqlDAO {

	public SqlDAO() { 
	}
	

	public void runSqlUpdate(Connection con,String sql) throws SQLException{

		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.executeUpdate();
		}
	}
	
	public void runSql(Connection con,String sql,ResultSetListener listener) throws SQLException{
		
		try(PreparedStatement ps = con.prepareStatement(sql)){
			
			try(ResultSet rs = ps.executeQuery()){
				
				ResultSetMetaData rsmd = rs.getMetaData();
				listener.haveTheMetaData(rsmd);
				
				List<String> labels = new ArrayList<>();
				for(int i=0,n=rsmd.getColumnCount();i<n;i++) {
					labels.add(rsmd.getColumnLabel(i+1));
				}
				listener.haveTheColumnLabels(labels.toArray(new String[labels.size()]));
				
				int row=0;
				while(rs.next()) {

					List<Object> data = new ArrayList<>();
					for(int i=0,n=rsmd.getColumnCount();i<n;i++) {
						Object o = rs.getObject(i+1);
						data.add(o);
					}
					listener.haveARow(row, data.toArray());
					row++;
				}
				
			}
			
		} finally {
			listener.finished();
		}
		
	}

	private String[] tableTypes(DatabaseMetaData dbmd) throws SQLException {
		List<String>ts=new ArrayList<>();
		try(ResultSet rs =dbmd.getTableTypes()){
			while(rs.next()) {
				ts.add(rs.getString(1));
			}
		}
		return ts.toArray(new String[ts.size()]);
	}
	

	
	public List<DTOTable> metaDataTables(Connection con,String tableFilter) throws SQLException{
		List<DTOTable> list = new ArrayList<>();
		DatabaseMetaData d = con.getMetaData();
		try {
			
			try(ResultSet rs = d.getTables(null, null, tableFilter, tableTypes(d))){
				
				/*
				TABLE_CAT String => table catalog (may be null) 
				2.TABLE_SCHEM String => table schema (may be null) 
				3.TABLE_NAME String => table name 
				4.TABLE_TYPE String => table type. Typical types are "TABLE","VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY","LOCAL TEMPORARY", "ALIAS", "SYNONYM". 
				5.REMARKS String => explanatory comment on the table 
				6.TYPE_CAT String => the types catalog (may be null) 
				7.TYPE_SCHEM String => the types schema (may be null) 
				8.TYPE_NAME String => type name (may be null) 
				9.SELF_REFERENCING_COL_NAME String => name of the designated"identifier" column of a typed table (may be null) 
				10.REF_GENERATION String => specifies how values inSELF_REFERENCING_COL_NAME are created. Values are"SYSTEM", "USER", "DERIVED". (may be null) 
					
				 */

				int row=0;
				while(rs.next()) {
					DTOTable dto = new DTOTable();
					dto.setTableCat(rs.getString("TABLE_CAT"));
					dto.setTableSchem(rs.getString("TABLE_SCHEM"));
					dto.setTableName(rs.getString("TABLE_NAME"));
					dto.setTableType(rs.getString("TABLE_TYPE"));
					dto.setRemarks(rs.getString("REMARKS"));
					dto.setTypeCat(rs.getString("TYPE_CAT"));
					dto.setTypeSchem(rs.getString("TYPE_SCHEM"));
					dto.setTypeName(rs.getString("TYPE_NAME"));
					dto.setSelfReferencingColName(rs.getString("SELF_REFERENCING_COL_NAME"));
					dto.setRefGeneration(rs.getString("REF_GENERATION"));
					
					list.add(dto);
					row++;
				}
				
			}
			
		} finally {

		}
		return list;
	}

	public List<DTOColumn>  metaDataColumns(Connection con,String catalog, String schema, String tableName)  throws SQLException {
	
			List<DTOColumn> list = new ArrayList<>();
			DatabaseMetaData d = con.getMetaData();
			try {
				
				try(ResultSet rs = d.getColumns(catalog, schema, tableName, "%")) {
					
 

					int row=0;
					while(rs.next()) {
						DTOColumn dto = new DTOColumn();
						dto.setTableCat(rs.getString("TABLE_CAT"));
						dto.setTableSchem(rs.getString("TABLE_SCHEM"));
						dto.setTableName(rs.getString("TABLE_NAME"));
						dto.setColumnName(rs.getString("COLUMN_NAME"));
						dto.setDataType(rs.getInt("DATA_TYPE"));
						dto.setTypeName(rs.getString("TYPE_NAME"));
						dto.setColumnSize(rs.getInt("COLUMN_SIZE"));
						dto.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
						dto.setNumPrecRadix(rs.getInt("NUM_PREC_RADIX"));
						dto.setNullable(rs.getInt("NULLABLE"));
						dto.setRemarks(rs.getString("REMARKS"));
						dto.setColumnDef(rs.getString("COLUMN_DEF"));
						dto.setCharOctetLength(rs.getInt("CHAR_OCTET_LENGTH"));
						dto.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
						dto.setIsNullable(rs.getString("IS_NULLABLE"));
						dto.setScopeCatalog(rs.getString("SCOPE_CATALOG"));
						dto.setScopeSchema(rs.getString("SCOPE_SCHEMA"));
						dto.setScopeTable(rs.getString("SCOPE_TABLE"));
						dto.setSourceDataType(rs.getShort("SOURCE_DATA_TYPE"));
						dto.setIsAutoincrement(rs.getString("IS_AUTOINCREMENT"));
						dto.setIsGeneratedColumn(rs.getString("IS_GENERATEDCOLUMN"));

						
						list.add(dto);
						row++;
					}
					
				}
				
			} finally {

			}
			return list;
		}
	public List<DTOIndex>  metaDataIndexes(Connection con,String catalog, String schema, String tableName)  throws SQLException {
		
		List<DTOIndex> list = new ArrayList<>();
		DatabaseMetaData d = con.getMetaData();
		try {
			
			try(ResultSet rs = d.getIndexInfo(catalog, schema, tableName, false, true)
				) {

				while(rs.next()) {
					DTOIndex dto = new DTOIndex();
					dto.setTableCat(rs.getString("TABLE_CAT"));
					dto.setTableSchem(rs.getString("TABLE_SCHEM"));
					dto.setTableName(rs.getString("TABLE_NAME"));
					dto.setNonUnique(rs.getBoolean("NON_UNIQUE"));
					dto.setIndexQualifier(rs.getString("INDEX_QUALIFIER"));
					dto.setIndexName(rs.getString("INDEX_NAME"));
					dto.setType(rs.getShort("TYPE"));
					dto.setOrdinalPosition(rs.getShort("ORDINAL_POSITION"));
					dto.setColumnName(rs.getString("COLUMN_NAME"));
					dto.setAscOrDesc(rs.getString("ASC_OR_DESC"));
					dto.setCardinality(rs.getLong("CARDINALITY"));
					dto.setPages(rs.getLong("PAGES"));
					dto.setFilterCondition(rs.getString("FILTER_CONDITION"));

					list.add(dto);
				}
				
			}
			
		} finally {

		}
		return list;
	}

	public List<DTOConstraint>  metaDataConstraints(Connection con,String catalog, String schema, String tableName)  throws SQLException {
		
		List<DTOConstraint> list = new ArrayList<>();
		DatabaseMetaData d = con.getMetaData();
		try {
			
			try(ResultSet rs = d.getExportedKeys(catalog, schema, tableName)
				) {

				while(rs.next()) {
					DTOConstraint dto = new DTOConstraint();
					dto.setPktableCat(rs.getString("PKTABLE_CAT"));
					dto.setPktableSchem(rs.getString("PKTABLE_SCHEM"));
					dto.setPktableName(rs.getString("PKTABLE_NAME"));
					dto.setPkcolumnName(rs.getString("PKCOLUMN_NAME"));
					dto.setFktableCat(rs.getString("FKTABLE_CAT"));
					dto.setFktableSchem(rs.getString("FKTABLE_SCHEM"));
					dto.setFktableName(rs.getString("FKTABLE_NAME"));
					dto.setFkcolumnName(rs.getString("FKCOLUMN_NAME"));
					dto.setKeySeq(rs.getShort("KEY_SEQ"));
					dto.setUpdateRule(rs.getShort("UPDATE_RULE"));
					dto.setDeleteRule(rs.getShort("DELETE_RULE"));
					dto.setFkName(rs.getString("FK_NAME"));
					dto.setPkName(rs.getString("PK_NAME"));
					dto.setDeferrability(rs.getShort("DEFERRABILITY"));


					list.add(dto);
				}
				
			}
			
			
			try(ResultSet rs = d.getImportedKeys(catalog, schema, tableName)
				) {

				while(rs.next()) {
					DTOConstraint dto = new DTOConstraint();
					dto.setPktableCat(rs.getString("PKTABLE_CAT"));
					dto.setPktableSchem(rs.getString("PKTABLE_SCHEM"));
					dto.setPktableName(rs.getString("PKTABLE_NAME"));
					dto.setPkcolumnName(rs.getString("PKCOLUMN_NAME"));
					dto.setFktableCat(rs.getString("FKTABLE_CAT"));
					dto.setFktableSchem(rs.getString("FKTABLE_SCHEM"));
					dto.setFktableName(rs.getString("FKTABLE_NAME"));
					dto.setFkcolumnName(rs.getString("FKCOLUMN_NAME"));
					dto.setKeySeq(rs.getShort("KEY_SEQ"));
					dto.setUpdateRule(rs.getShort("UPDATE_RULE"));
					dto.setDeleteRule(rs.getShort("DELETE_RULE"));
					dto.setFkName(rs.getString("FK_NAME"));
					dto.setPkName(rs.getString("PK_NAME"));
					dto.setDeferrability(rs.getShort("DEFERRABILITY"));

					list.add(dto);
				}
				
			}
			
		} finally {

		}
		return list;
	}
		
}
