package com.nobbysoft.com.nobbysoft.first.client.components;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;

public class PCheckBox extends JCheckBox implements PDataComponent {

	public PCheckBox() { 
		
	}

	public PCheckBox(Icon icon) {
		super(icon); 
		
	}

	public PCheckBox(String text) {
		super(text); 
		
	}

 
 

	@Override
	public void setTheValue(Object o) {
		if(o!=null){
			if(o instanceof Boolean){
				setSelected((((Boolean)o).booleanValue()));
			}
		}
	}

	@Override
	public Object getTheValue() {
		return Boolean.valueOf(isSelected());
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		setEnabled(!readOnly);
	}

	@Override
	public boolean isReadOnly() {
		return !isEnabled();
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
