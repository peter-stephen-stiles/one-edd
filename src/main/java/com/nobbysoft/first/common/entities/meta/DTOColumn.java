package com.nobbysoft.first.common.entities.meta;

import com.nobbysoft.first.common.entities.DataDTOInterface;

public class DTOColumn implements DataDTOInterface<String>{
	private String tableCat;
	private String tableSchem;
	private String tableName;
	private String columnName;
	private int dataType;
	private String typeName;
	private int columnSize; 
	private int decimalDigits;
	private int numPrecRadix;
	private int nullable;
	private String remarks;
	private String  columnDef;
	private int charOctetLength;
	private int ordinalPosition;
	private String isNullable;
	private String scopeCatalog;
	private String scopeSchema;
	private String scopeTable;
	private short sourceDataType;
	private String isAutoincrement;
	private String isGeneratedColumn;
	
/*
Each column description has the following columns: 
1.TABLE_CAT String => table catalog (may be null) 
2.TABLE_SCHEM String => table schema (may be null) 
3.TABLE_NAME String => table name 
4.COLUMN_NAME String => column name 
5.DATA_TYPE int => SQL type from java.sql.Types 
6.TYPE_NAME String => Data source dependent type name,for a UDT the type name is fully qualified 
7.COLUMN_SIZE int => column size.  
9.DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types whereDECIMAL_DIGITS is not applicable. 
10.NUM_PREC_RADIX int => Radix (typically either 10 or 2) 
11.NULLABLE int => is NULL allowed. ◦ columnNoNulls - might not allow NULL values 
◦ columnNullable - definitely allows NULL values 
◦ columnNullableUnknown - nullability unknown 

12.REMARKS String => comment describing column (may be null) 
13.COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null) 
 
16.CHAR_OCTET_LENGTH int => for char types themaximum number of bytes in the column 
17.ORDINAL_POSITION int => index of column in table(starting at 1) 
18.IS_NULLABLE String => ISO rules are used to determine the nullability for a column. ◦ YES --- if the column can include NULLs 
◦ NO --- if the column cannot include NULLs 
◦ empty string --- if the nullability for thecolumn is unknown 

19.SCOPE_CATALOG String => catalog of table that is the scopeof a reference attribute (null if DATA_TYPE isn't REF) 
20.SCOPE_SCHEMA String => schema of table that is the scopeof a reference attribute (null if the DATA_TYPE isn't REF) 
21.SCOPE_TABLE String => table name that this the scopeof a reference attribute (null if the DATA_TYPE isn't REF) 
22.SOURCE_DATA_TYPE short => source type of a distinct type or user-generatedRef type, SQL type from java.sql.Types (null if DATA_TYPEisn't DISTINCT or user-generated REF) 
23.IS_AUTOINCREMENT String => Indicates whether this column is auto incremented ◦ YES --- if the column is auto incremented 
◦ NO --- if the column is not auto incremented 
◦ empty string --- if it cannot be determined whether the column is auto incremented 

24.IS_GENERATEDCOLUMN String => Indicates whether this is a generated column ◦ YES --- if this a generated column 
◦ NO --- if this not a generated column 
◦ empty string --- if

 
 */
	public DTOColumn() {
	}

	@Override
	public String getKey() {
		return  tableCat+"."+
 tableSchem+"."+
		  tableName+"."+
		 columnName;
	}

	@Override
	public String getDescription() {

		return getKey();
	}

	@Override
	public Object[] getAsRow() {

		return new Object[] {
				this,
				tableCat,
				tableSchem,
				tableName,
				columnName,
				dataType,
				typeName,
				columnSize, 
				decimalDigits,
				numPrecRadix,
				nullable,
				remarks,
				columnDef,
				charOctetLength,
				ordinalPosition,
				isNullable,
				scopeCatalog,
				scopeSchema,
				scopeTable,
				sourceDataType,
				isAutoincrement,
				isGeneratedColumn
		};
	}

	@Override
	public String[] getRowHeadings() {

		return new String[] {
				//"",
				"Table Catalog",
				"Table Schema",
				"Table Name",
				"Column Name",
				"Data Type",
				
				"Type Name",
				"Column Size", 
				"Decimal Digits",
				"Num Precision Radix",
				"Nullable",
				
				"Remarks",
				"Column Definition",
				"Char Octet Length",
				"Ordinal Position",
				"Nullable?",
				
				"Scope Catalog",
				"Scope Schema",
				"Scope Table",
				"Source Data Type",
				"Auto increment?",
				
				"Generated Column?"
		};
	}

	@Override
	public Integer[] getColumnWidths() {

		return new Integer[] {
				100,100,200,200,100,
				100,100,100,100,100,
				100,100,100,100,100,
				100,100,100,100,100,
				100};
	}

	@Override
	public String[] getColumnCodedListTypes() {

		return new String[] {
				null,null,null,null,null,
				null,null,null,null,null,
				null,null,null,null,null,
				null,null,null,null,null,
				null
				
		};
	}

	public String getTableCat() {
		return tableCat;
	}

	public void setTableCat(String tableCat) {
		this.tableCat = tableCat;
	}

	public String getTableSchem() {
		return tableSchem;
	}

	public void setTableSchem(String tableSchem) {
		this.tableSchem = tableSchem;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public int getNumPrecRadix() {
		return numPrecRadix;
	}

	public void setNumPrecRadix(int numPrecRadix) {
		this.numPrecRadix = numPrecRadix;
	}

	public int getNullable() {
		return nullable;
	}

	public void setNullable(int nullable) {
		this.nullable = nullable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getColumnDef() {
		return columnDef;
	}

	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}

	public int getCharOctetLength() {
		return charOctetLength;
	}

	public void setCharOctetLength(int charOctetLength) {
		this.charOctetLength = charOctetLength;
	}

	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getScopeCatalog() {
		return scopeCatalog;
	}

	public void setScopeCatalog(String scopeCatalog) {
		this.scopeCatalog = scopeCatalog;
	}

	public String getScopeSchema() {
		return scopeSchema;
	}

	public void setScopeSchema(String scopeSchema) {
		this.scopeSchema = scopeSchema;
	}

	public String getScopeTable() {
		return scopeTable;
	}

	public void setScopeTable(String scopeTable) {
		this.scopeTable = scopeTable;
	}

	public short getSourceDataType() {
		return sourceDataType;
	}

	public void setSourceDataType(short sourceDataType) {
		this.sourceDataType = sourceDataType;
	}

	public String getIsAutoincrement() {
		return isAutoincrement;
	}

	public void setIsAutoincrement(String isAutoincrement) {
		this.isAutoincrement = isAutoincrement;
	}

	public String getIsGeneratedColumn() {
		return isGeneratedColumn;
	}

	public void setIsGeneratedColumn(String isGeneratedColumn) {
		this.isGeneratedColumn = isGeneratedColumn;
	}

}
