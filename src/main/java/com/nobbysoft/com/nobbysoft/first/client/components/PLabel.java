package com.nobbysoft.com.nobbysoft.first.client.components;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;

public class PLabel extends JLabel {

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
	
	public PLabel() {
		
	}

	public PLabel(String text) {
		super(text);
		
	}

	public PLabel(Icon image) {
		super(image);
		
	}
 

}
