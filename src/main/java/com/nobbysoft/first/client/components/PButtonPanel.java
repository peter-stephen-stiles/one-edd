package com.nobbysoft.first.client.components;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.nobbysoft.first.client.utils.GuiUtils;

/**
 * panel with a flow layout right aligned
 * unless you ask it different
 * 
 * @author nobby
 *
 */
public class PButtonPanel extends JPanel { 

	public PButtonPanel() {
		super();
		
		FlowLayout layThis = new FlowLayout(FlowLayout.RIGHT);
		layThis.setHgap(5);
		setLayout(layThis);
	}

	public void add(Component... components ) {
		for(Component c:components) {
			add(c);
		}
	}
	
	public PButtonPanel(int alignment) {
		super();
		
		FlowLayout layThis = new FlowLayout(FlowLayout.RIGHT);
		layThis.setHgap(5);
		layThis.setAlignment(alignment);
		setLayout(layThis);
	}
   

}
