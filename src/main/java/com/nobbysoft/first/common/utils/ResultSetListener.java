package com.nobbysoft.first.common.utils;

import java.sql.ResultSetMetaData;

public interface ResultSetListener {

	public void haveARow(int count,Object[] data);
	
	public void haveTheMetaData(ResultSetMetaData metadata);
	
	public void haveTheColumnLabels(String[] labels);
	
	public void finished();
	
}
