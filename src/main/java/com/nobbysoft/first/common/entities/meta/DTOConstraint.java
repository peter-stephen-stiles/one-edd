package com.nobbysoft.first.common.entities.meta;

import com.nobbysoft.first.common.entities.DataDTOInterface;

public class DTOConstraint implements DataDTOInterface<String> {
	private String pktableCat;
	private String pktableSchem;
	private String pktableName;
	private String pkcolumnName;
	private String fktableCat;
	private String fktableSchem;
	private String fktableName;
	private String fkcolumnName;
	private short keySeq;
	private short updateRule;
	private short deleteRule;
	private String fkName;
	private String pkName;
	private short deferrability;

	public DTOConstraint() {
	}

	@Override
	public String getKey() {
		return  pktableCat + "." + pktableSchem + "."
				+ pktableName + "." + pkcolumnName + "==" + fktableCat + "."
				+ fktableSchem + "." + fktableName + "." + fkcolumnName + "#"
				+ keySeq + "#" + fkName
				+ "#" + pkName ;
	}

	@Override
	public String getDescription() {
		return getKey();
	}

	@Override
	public Object[] getAsRow() {
		return new Object[]{
				this,
				pktableCat,
				pktableSchem,
				pktableName,
				pkcolumnName,
				fktableCat,
				fktableSchem,
				fktableName,
				fkcolumnName,
				keySeq,
				updateRule,
				deleteRule,
				fkName,
				pkName

		};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {
				"Pktable Catalog",
				"Pktable Schema",
				"Pktable Name",
				"Pkcolumn Name",
				"Fktable Catalog",
				
				"Fktable Schema",
				"Fktable Name",
				"Fkcolumn Name",
				"Key Seq",
				"Update Rule",
				
				"Delete Rule",
				"Fk Name",
				"Pk Name",
				"Deferrability"

		};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {
				100,100,200,200,100,
				100,200,200,100,100,
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

	public String getPktableCat() {
		return pktableCat;
	}

	public void setPktableCat(String pktableCat) {
		this.pktableCat = pktableCat;
	}

	public String getPktableSchem() {
		return pktableSchem;
	}

	public void setPktableSchem(String pktableSchem) {
		this.pktableSchem = pktableSchem;
	}

	public String getPktableName() {
		return pktableName;
	}

	public void setPktableName(String pktableName) {
		this.pktableName = pktableName;
	}

	public String getPkcolumnName() {
		return pkcolumnName;
	}

	public void setPkcolumnName(String pkcolumnName) {
		this.pkcolumnName = pkcolumnName;
	}

	public String getFktableCat() {
		return fktableCat;
	}

	public void setFktableCat(String fktableCat) {
		this.fktableCat = fktableCat;
	}

	public String getFktableSchem() {
		return fktableSchem;
	}

	public void setFktableSchem(String fktableSchem) {
		this.fktableSchem = fktableSchem;
	}

	public String getFktableName() {
		return fktableName;
	}

	public void setFktableName(String fktableName) {
		this.fktableName = fktableName;
	}

	public String getFkcolumnName() {
		return fkcolumnName;
	}

	public void setFkcolumnName(String fkcolumnName) {
		this.fkcolumnName = fkcolumnName;
	}

	public short getKeySeq() {
		return keySeq;
	}

	public void setKeySeq(short keySeq) {
		this.keySeq = keySeq;
	}

	public short getUpdateRule() {
		return updateRule;
	}

	public void setUpdateRule(short updateRule) {
		this.updateRule = updateRule;
	}

	public short getDeleteRule() {
		return deleteRule;
	}

	public void setDeleteRule(short deleteRule) {
		this.deleteRule = deleteRule;
	}

	public String getFkName() {
		return fkName;
	}

	public void setFkName(String fkName) {
		this.fkName = fkName;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public short getDeferrability() {
		return deferrability;
	}

	public void setDeferrability(short deferrability) {
		this.deferrability = deferrability;
	}

	@Override
	public String toString() {
		return "DTOConstraint [pktableCat=" + pktableCat + ", pktableSchem=" + pktableSchem + ", pktableName="
				+ pktableName + ", pkcolumnName=" + pkcolumnName + ", fktableCat=" + fktableCat + ", fktableSchem="
				+ fktableSchem + ", fktableName=" + fktableName + ", fkcolumnName=" + fkcolumnName + ", keySeq="
				+ keySeq + ", updateRule=" + updateRule + ", deleteRule=" + deleteRule + ", fkName=" + fkName
				+ ", pkName=" + pkName + ", deferrability=" + deferrability + "]";
	}


	

}
