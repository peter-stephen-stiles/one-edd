package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.meta.DTOColumn;
import com.nobbysoft.first.common.entities.meta.DTOConstraint;
import com.nobbysoft.first.common.entities.meta.DTOIndex;
import com.nobbysoft.first.common.entities.meta.DTOTable;
import com.nobbysoft.first.common.utils.ResultSetListener;
import com.nobbysoft.first.common.utils.ReturnValue;

public interface SqlService {

	public void runSql(String sql,ResultSetListener listener) throws SQLException;
	public void runUpdate(String sql) throws SQLException;

	public List<DTOTable> metaDataTables(String tableNameFilter) throws SQLException;
	public List<DTOTable> metaDataTables(String catalog,String schema,String tableNameFilter) throws SQLException;
	public List<DTOColumn> metaDataColumns(String catalog, String schema, String tableName) throws SQLException;
	public List<DTOIndex> metaDataIndexes(String catalog, String schema, String tableName) throws SQLException;
	public List<DTOConstraint> metaDataConstraints(String catalog, String schema, String tableName) throws SQLException;
 
	public List<String> metaCatalogs() throws SQLException ;
	public List<String> metaSchema() throws SQLException ;
	
	public ReturnValue<String> export (String catalog,String schema,String fileName) throws SQLException ;
	
}
