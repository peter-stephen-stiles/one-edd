package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class UndeadType implements Comparable<UndeadType>, Serializable, DataDTOInterface<Integer>{

	public UndeadType() {
		
	}
	
	private int undeadType;
	private String undeadTypeExample;
	
	@Override
	public int compareTo(UndeadType o) {
		if(undeadType<o.getUndeadType()) {
			return -1;
		}
		if(undeadType>o.getUndeadType()) {
			return 1;
		}
		return undeadTypeExample.compareTo(o.getUndeadTypeExample());
	}

	public int getUndeadType() {
		return undeadType;
	}

	public void setUndeadType(int undeadType) {
		this.undeadType = undeadType;
	}

	public String getUndeadTypeExample() {
		return undeadTypeExample;
	}

	public void setUndeadTypeExample(String undeadTypeExample) {
		this.undeadTypeExample = undeadTypeExample;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (undeadType ^ (undeadType >>> 32));
		result = prime * result + ((undeadTypeExample == null) ? 0 : undeadTypeExample.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UndeadType other = (UndeadType) obj;
		if (undeadType != other.undeadType)
			return false;
		if (undeadTypeExample == null) {
			if (other.undeadTypeExample != null)
				return false;
		} else if (!undeadTypeExample.equals(other.undeadTypeExample))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UndeadType [undeadType=" + undeadType + ", undeadTypeExample=" + undeadTypeExample + "]";
	}

	@Override
	public Integer getKey() {
		return undeadType;
	}

	@Override
	public String getDescription() {

		return ""+undeadType+" - "+ undeadTypeExample;
	}

	@Override
	public Object[] getAsRow() {
		return new  Object[] {this,undeadType,undeadTypeExample};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"Type","Example"};
	}

	@Override
	public Integer[] getColumnWidths() {

		return new Integer[] {120,-1};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {null,null};
	}
	

}
