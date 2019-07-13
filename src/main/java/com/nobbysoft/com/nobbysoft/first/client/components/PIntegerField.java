package com.nobbysoft.com.nobbysoft.first.client.components;

import java.awt.Dimension;
import java.math.BigDecimal;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.Document;

import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;

public class PIntegerField extends JTextField  implements PDataComponent {

	public PIntegerField() {
		setDocument (new IntegerLimitingDocument());
		
	}
	public PIntegerField(boolean allowNegative) {
		setDocument (new IntegerLimitingDocument(allowNegative));
		
	}
	private JComponent linkedComponent=null;
	
	public void linkSizeTo(JComponent c) {
		linkedComponent=c;
	}
	public Dimension getMinimumSize() {
		if(linkedComponent==null) {
			return super.getMinimumSize();
		}
		return linkedComponent.getMinimumSize();
		
	}	public Dimension getMaximumSize() {
		if(linkedComponent==null) {
			return super.getMaximumSize();
		}
		return linkedComponent.getMaximumSize();
	}	public Dimension getPreferredSize() {
		if(linkedComponent==null) {
			return super.getPreferredSize();
		}
		return linkedComponent.getPreferredSize();
	}	public Dimension getSize() {
		if(linkedComponent==null) {
			return super.getSize();
		}
		return linkedComponent.getSize();
	}
	
	public int getIntegerValue() {
		try {
			return Integer.parseInt(getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	public void setIntegerValue(int value) {
		setText(""+value);
	}
	
	@Override
	public void setTheValue(Object o) {
		if(o!=null){
			if(o instanceof BigDecimal){
				setText(((BigDecimal)o).toPlainString());
			} else {
				setText(o.toString());
			}
		}
		
	}

	@Override
	public Object getTheValue() {
		try {
			return Integer.parseInt(getText());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		setEditable(!readOnly);
	}
	@Override
	public boolean isReadOnly() {
		return !isEditable();
	}
 
 

}
