package com.nobbysoft.com.nobbysoft.first.client.components;

import java.awt.Dimension;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;

import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;

public class PCodeField extends JTextField implements PDataComponent {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	public PCodeField() {
		LengthLimitingDocument doc = new LengthLimitingDocument(20,true,true);
		setDocument(doc);
		
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
		return getText();
	}
	@Override
	public void setReadOnly(boolean readOnly) {
		setEditable(!readOnly);
	}
	@Override
	public boolean isReadOnly() {
		return !isEditable();
	}

	int ph = 0;
	int pw=0;
	@Override
	public void setMinimumHeight(int height) {
		ph=height;
	}
	@Override
	public void setMinimumWidth(int width) {
		pw=width;
	}
	@Override
	public Dimension getPreferredSize() {
		
 
		
		Dimension d = super.getPreferredSize();
		if(d.getWidth()<pw) {
			d.width=pw;
		}
		if(d.getHeight()<ph) {
			d.height=ph;
		}
		return d;
	}

}
