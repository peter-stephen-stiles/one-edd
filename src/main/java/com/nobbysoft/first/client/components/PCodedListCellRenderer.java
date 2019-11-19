package com.nobbysoft.first.client.components;

import java.lang.invoke.MethodHandles;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class PCodedListCellRenderer extends PTableCellRenderer {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private final String codedListType;
	
 
	
	@SuppressWarnings("rawtypes")
	private Map<Comparable,String> itemMap = null; 
	
	public PCodedListCellRenderer(String codedListType) {
		super();
		this.codedListType=codedListType;
		
	}

 
 
	public String getItemDesc(Object value) {
		if(itemMap==null) {
			// given the "codedListType" call the CodeListDAO
			try{
				CodedListService cliDao = (CodedListService)DataMapper.INSTANCE.getNonDataService(CodedListService.class);
				itemMap = cliDao.getCodedListMap(codedListType);
						
			} catch (Exception e) {
				LOGGER.error("Sql error getting coded list "+codedListType,e);
				return "SQL PROBLEM!";
			}
		} 
			String v = itemMap.get(""+value); // always strings in a renderererer
			if(v==null) {
				return null;//"Unknown:"+value;
			}
			return v; 
		
		
	}
 
    
}
