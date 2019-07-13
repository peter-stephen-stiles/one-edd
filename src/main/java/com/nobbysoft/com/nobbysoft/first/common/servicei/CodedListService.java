package com.nobbysoft.com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;

public interface CodedListService {
	
	public List<CodedListItem<?>> getCodedListWithNullOption(String type)throws SQLException ;
	public Map<Comparable,String> getCodedListMapWithNullOption(String type)throws SQLException ;
	public Map<Comparable,String> getCodedListMap(String type) throws SQLException ;
	public List<CodedListItem<?>> getCodedList(String type) throws SQLException ;
	public List<CodedListItem<?>> getAttBonus();
	public List<CodedListItem<?>> getAttributesWithZero();
	public List<CodedListItem<?>> getAttributes();
	public List<CodedListItem<?>> getAttRolls();
	public List<CodedListItem<?>> getDice();
	public List<CodedListItem<?>> getRaceClassLimitMaxLevelList();
}
