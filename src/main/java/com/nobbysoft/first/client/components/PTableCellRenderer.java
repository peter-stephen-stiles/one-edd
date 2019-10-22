package com.nobbysoft.first.client.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.nobbysoft.first.common.utils.SU;

@SuppressWarnings("serial")
public class PTableCellRenderer extends DefaultTableCellRenderer {
	
	private Color text;
	private Color background;
	private Color darkBackground;
	private Color selected;
	private Color darkSelected;
	private Color oppositeText;
	public  PTableCellRenderer() {
		JLabel l = new JLabel();
		text =l.getForeground();
		background=l.getBackground();
		darkBackground =l.getBackground().darker(); 
		selected=l.getBackground().darker().darker().darker();
		darkSelected=l.getBackground().darker().darker().darker().darker().darker();
		oppositeText=new Color( 0xFFFFFF ^ text.getRGB());
	}

	private boolean zerosAsBlanks=false;
	public void setZerosAsBlanks(boolean zerosAsBlanks) {
		this.zerosAsBlanks=zerosAsBlanks;
	}

	private Color determineForeground(int row, boolean isSelected) {
		// row 0,1,2 = white
		// row 3,4,5 = grey
		// row 6,7,8
		// divide by 3
		// is it divisible by 2?
		int d = row / 3;
		if(d%2==0) {
			return text;
		} else {
			return isSelected?oppositeText:text;
		}
	}
	private Color determineBackground(int row, boolean isSelected) {
		// row 0,1,2 = white
		// row 3,4,5 = grey
		// row 6,7,8
		// divide by 3
		// is it divisible by 2?
		int d = row / 3;
		if(d%2==0) {
			return isSelected?darkSelected:darkBackground;			
		} else {
			return isSelected?selected:background;
		}
	}
	
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
    	Component c= super.getTableCellRendererComponent( table,  value,
                 isSelected,  hasFocus,
                 row,  column);
    	if(value!=null) {
	    	if (c instanceof JLabel) {
	    		String d = getItemDesc(value);
	    		((JLabel)c).setText(d);
	    	}	  
	    	
    	} 
    	{
    		c.setForeground(determineForeground(row,isSelected||hasFocus));
       		c.setBackground(determineBackground(row,isSelected||hasFocus));
    	}
    	return c;
    	
    }

    
    /**
     * override/re-write this method to implement something weird and wonderful
     * 
     * @param value
     * @return a string representation of that value
     */
	protected String getItemDesc(Object value) {
		String d= SU.getDescription(value);
		if(zerosAsBlanks&&"0".contentEquals(d)) {
			d="";
		}
		return d;
	}

	
}
