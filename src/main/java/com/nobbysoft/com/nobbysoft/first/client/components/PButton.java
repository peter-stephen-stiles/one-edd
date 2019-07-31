package com.nobbysoft.com.nobbysoft.first.client.components;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;

import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;

@SuppressWarnings("serial")
public class PButton extends JButton implements PDataComponent {
	
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
	
	public void removeActionListeners() {
		for(ActionListener al:super.getActionListeners()) {
			removeActionListener(al);
		}
	}
	
	public PButton() {
		super(" ");
		
	}

	public PButton(Icon icon) {
		super(icon);
		
	}

	public PButton(String text) {
		super(text);
		
	}
	@Override
	public void setTheValue(Object o) {
		if(o ==null) {
			setText("");
		} else {
			setText(o.toString());
		}
	}
	@Override
	public Object getTheValue() {
		return getText();
	}
	@Override
	public void setReadOnly(boolean readOnly) {
		setEnabled(!readOnly);
		
	}
	@Override
	public boolean isReadOnly() {
		return !isEnabled();
	}

}
