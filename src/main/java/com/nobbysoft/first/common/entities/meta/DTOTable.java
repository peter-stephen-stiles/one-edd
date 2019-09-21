package com.nobbysoft.first.common.entities.meta;

import com.nobbysoft.first.common.entities.DataDTOInterface;

public class DTOTable implements DataDTOInterface<String>{

	private String tableCat;
	private String tableSchem;
	private String tableName;
	private String tableType;
	private String remarks;
	private String typeCat;
	private String typeSchem;
	private String typeName;
	private String selfReferencingColName;
	private String refGeneration;
	
	public DTOTable() {
		
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

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTypeCat() {
		return typeCat;
	}

	public void setTypeCat(String typeCat) {
		this.typeCat = typeCat;
	}

	public String getTypeSchem() {
		return typeSchem;
	}

	public void setTypeSchem(String typeSchem) {
		this.typeSchem = typeSchem;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSelfReferencingColName() {
		return selfReferencingColName;
	}

	public void setSelfReferencingColName(String selfReferencingColName) {
		this.selfReferencingColName = selfReferencingColName;
	}

	public String getRefGeneration() {
		return refGeneration;
	}

	public void setRefGeneration(String refGeneration) {
		this.refGeneration = refGeneration;
	}

	@Override
	public String getKey() {
		return tableCat+"."+tableSchem+"."+tableName;
	}

	@Override
	public String getDescription() {
		return tableCat+"."+tableSchem+"."+tableName;
	}

	@Override
	public Object[] getAsRow() {
		return new Object[] {
				this,
				tableCat,
				tableSchem,
				tableName,
				tableType,
				remarks,
				typeCat,
				typeSchem,
				typeName,
				selfReferencingColName,
				refGeneration};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {
				//"",
				"Catalog",
				"Schema",
				"Name",
				"Type",
				"Remarks",
				"Type Catalog",
				"Type Schema",
				"Type Name",
				"Self Referencing Col Name",
				"Ref Generation"};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new  Integer[] {
				//0,
				100,100,200,100,200,
				50,50,50,50,50
		};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {null,
				null,null,null,null,null,
				null,null,null,null,null
				};
	}


}
