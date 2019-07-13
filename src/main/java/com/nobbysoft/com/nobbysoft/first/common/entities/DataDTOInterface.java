package com.nobbysoft.com.nobbysoft.first.common.entities;

public interface DataDTOInterface<T extends Comparable<T>> {
	
	public T getKey();
	public String getDescription();
	// first object should always be the "this" object
	public Object[] getAsRow();
	// should be one less than "getAsRow" because, we dont show the object
	public String[] getRowHeadings();
	public Integer[] getColumnWidths();
	public String[] getColumnCodedListTypes();
	

}
