package com.nobbysoft.com.nobbysoft.first.client.components;

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

import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.server.dao.CodedListDAO;
import com.nobbysoft.com.nobbysoft.first.server.utils.ConnectionManager;
import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

public class PCodedListCellRenderer extends DefaultTableCellRenderer {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private final String codedListType;
	
	private Color text;
	private Color background;
	private Color selected; 
	
	private Map<Comparable,String> itemMap = null; 
	
	public PCodedListCellRenderer(String codedListType) {
		this.codedListType=codedListType;
		JLabel l = new JLabel();
		text =l.getForeground();
		background=l.getBackground();
		selected=l.getBackground().darker().darker();

	}
	private String getItemDesc(Object value) {
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
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
    	Component c= super.getTableCellRendererComponent( table,  value,
                 isSelected,  hasFocus,
                 row,  column);
    	
    	if (c instanceof JLabel) {
    		JLabel lbl =((JLabel)c);
    		lbl.setText(getItemDesc(value));
    		
        	{
        		c.setForeground(text);
        		if(isSelected||hasFocus) {
        			c.setBackground(selected);
        		} else {
        			c.setBackground(background);
        		}
        	}
    		
    	} 
    	return c;
    	
    }

 
    
}
