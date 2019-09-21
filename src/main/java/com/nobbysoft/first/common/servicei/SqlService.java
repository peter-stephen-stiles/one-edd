package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.meta.DTOColumn;
import com.nobbysoft.first.common.entities.meta.DTOTable;
import com.nobbysoft.first.common.utils.ResultSetListener;

public interface SqlService {

	public void runSql(String sql,ResultSetListener listener) throws SQLException;
	public void runUpdate(String sql) throws SQLException;

	public List<DTOTable> metaDataTables(String tableNameFilter) throws SQLException;
	public List<DTOColumn> metaDataColumns(String catalog, String schema, String tableName) throws SQLException;
 
	
}
