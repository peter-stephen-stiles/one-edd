package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.utils.COINAGE;

@SuppressWarnings("serial")
public class PComboCoinage extends PComboBox<COINAGE> {
	
	public PComboCoinage(){
		super();
		populate();
	}
	
	public void addNullOption() {
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		model.insertElementAt(null,0);
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		
		for(COINAGE COINAGE:COINAGE.values()) {
			model.addElement(COINAGE);	
		}
	}

	public void setCOINAGE(COINAGE g) {
		this.setSelectedItem(g);
	}
	
	public COINAGE getCOINAGE() {
		return (COINAGE)getSelectedItem();
	}
	
}
