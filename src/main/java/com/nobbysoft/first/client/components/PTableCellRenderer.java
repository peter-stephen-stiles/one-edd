package com.nobbysoft.first.client.components;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.common.utils.SU;

public class PTableCellRenderer extends DefaultTableCellRenderer {
	
	private Color text;
	private Color background;
	private Color selected;
	public  PTableCellRenderer() {
		JLabel l = new JLabel();
		text =l.getForeground();
		background=l.getBackground();
		selected=l.getBackground().darker().darker();
	}

	

	
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
    	Component c= super.getTableCellRendererComponent( table,  value,
                 isSelected,  hasFocus,
                 row,  column);
    	if(value!=null) {
	    	if (c instanceof JLabel) {
	    		((JLabel)c).setText(SU.getDescription(value));
	    	}	  
	    	
    	} 
    	{
    		c.setForeground(text);
    		if(isSelected||hasFocus) {
    			c.setBackground(selected);
    		} else {
    			c.setBackground(background);
    		}
    	}
    	return c;
    	
    }

	
}
