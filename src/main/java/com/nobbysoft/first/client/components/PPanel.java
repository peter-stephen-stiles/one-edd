package com.nobbysoft.first.client.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.nobbysoft.first.client.utils.GuiUtils;

/**
 * default to border layout
 * @author nobby
 *
 */
public class PPanel extends JPanel {

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
	
	
	public PPanel() { 
		super(new BorderLayout(5,5));
		
	}

	public PPanel(LayoutManager layout) {
		super(layout); 
		
	}
	
	
	public Window getWindow() {
		return GuiUtils.getParent(this);
	}
 
}
