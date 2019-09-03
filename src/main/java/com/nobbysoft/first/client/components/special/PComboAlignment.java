package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.entities.staticdto.Alignment;

public class PComboAlignment extends PComboBox<Alignment> {

	public PComboAlignment(){
		super();
		populate();
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		
		for(Alignment alignment:Alignment.values()) {
			model.addElement(alignment);	
		}
	}

	public void setAlignment(Alignment g) {
		this.setSelectedItem(g);
	}
	
	public Alignment getAlignment() {
		return (Alignment)getSelectedItem();
	}
	
}
