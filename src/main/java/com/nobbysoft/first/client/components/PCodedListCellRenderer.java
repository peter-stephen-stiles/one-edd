package com.nobbysoft.first.client.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.dao.CodedListDAO;
import com.nobbysoft.first.server.utils.ConnectionManager;
import com.nobbysoft.first.utils.DataMapper;

public class PCodedListCellRenderer extends PTableCellRenderer {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private final String codedListType;
	
 
	
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
			String v = itemMap.get(value);
			if(v==null) {
				return null;//"Unknown:"+value;
			}
			return v; 
		
		
	}
 
    
}
