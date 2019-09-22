package com.nobbysoft.first.common.entities.meta;

import com.nobbysoft.first.common.entities.DataDTOInterface;

public class DTOIndex  implements DataDTOInterface<String> {
/*
1.TABLE_CAT String => table catalog (may be null) 
2.TABLE_SCHEM String => table schema (may be null) 
3.TABLE_NAME String => table name 
4.NON_UNIQUE boolean => Can index values be non-unique.false when TYPE is tableIndexStatistic 
5.INDEX_QUALIFIER String => index catalog (may be null); null when TYPE is tableIndexStatistic 
6.INDEX_NAME String => index name; null when TYPE istableIndexStatistic 
7.TYPE short => index type: ◦ tableIndexStatistic - this identifies table statistics that arereturned in conjuction with a table's index descriptions 
8.ORDINAL_POSITION short => column sequence numberwithin index; zero when TYPE is tableIndexStatistic 
9.COLUMN_NAME String => column name; null when TYPE istableIndexStatistic 
10.ASC_OR_DESC String => column sort sequence, "A" => ascending,"D" => descending, may be null if sort sequence is not supported; null when TYPE is tableIndexStatistic 
11.CARDINALITY long => When TYPE is tableIndexStatistic, thenthis is the number of rows in the table; otherwise, it is thenumber of unique values in the index. 
12.PAGES long => When TYPE is tableIndexStatisic thenthis is the number of pages used for the table, otherwise itis the number of pages used for the current index. 
13.FILTER_CONDITION String => Filter condition, if any.(may be null) 
 */
	private String tableCat;
	private String tableSchem;
	private String tableName;
	private  boolean nonUnique;
	private String indexQualifier;
	private String indexName;
	private short type;
	private short ordinalPosition;
	private String columnName;
	private String ascOrDesc;
	private long cardinality;
	private long pages;
	private String filterCondition;
	
	public DTOIndex() {
		
	}

	@Override
	public String getKey() {
		return tableCat + "." + tableSchem + "." + tableName
				+ "." + indexQualifier + "." + indexName
				+ "." + ordinalPosition + "." + columnName;
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
				nonUnique,
				indexQualifier,
				indexName,
				type,
				ordinalPosition,
				columnName,
				ascOrDesc,
				cardinality,
				pages,
				filterCondition

		};
	}

	@Override
	public String[] getRowHeadings() {
		// TODO Auto-generated method stub
		return new String[] {
				"Table Catalog",
				"Table Schema",
				"Table Name",
				"Non Unique",
				"Index Qualifier",
				
				"Index Name",
				"Type",
				"Ordinal Position",
				"Column Name",
				"Asc Or Desc",
				
				"Cardinality",
				"Pages",
				"Filter Condition"
		};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {
				100,100,200,100,100,
				200,100,100,100,100,
				100,100,100
		};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {
				null,null,null,null,null,
				null,null,null,null,null,
				null,null,null
				
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

	public boolean isNonUnique() {
		return nonUnique;
	}

	public void setNonUnique(boolean nonUnique) {
		this.nonUnique = nonUnique;
	}

	public String getIndexQualifier() {
		return indexQualifier;
	}

	public void setIndexQualifier(String indexQualifier) {
		this.indexQualifier = indexQualifier;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public short getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(short ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAscOrDesc() {
		return ascOrDesc;
	}

	public void setAscOrDesc(String ascOrDesc) {
		this.ascOrDesc = ascOrDesc;
	}

	public long getCardinality() {
		return cardinality;
	}

	public void setCardinality(long cardinality) {
		this.cardinality = cardinality;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}

	public String getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}

	@Override
	public String toString() {
		return "DTOIndex [tableCat=" + tableCat + ", tableSchem=" + tableSchem + ", tableName=" + tableName
				+ ", nonUnique=" + nonUnique + ", indexQualifier=" + indexQualifier + ", indexName=" + indexName
				+ ", type=" + type + ", ordinalPosition=" + ordinalPosition + ", columnName=" + columnName
				+ ", ascOrDesc=" + ascOrDesc + ", cardinality=" + cardinality + ", pages=" + pages
				+ ", filterCondition=" + filterCondition + "]";
	}

}
