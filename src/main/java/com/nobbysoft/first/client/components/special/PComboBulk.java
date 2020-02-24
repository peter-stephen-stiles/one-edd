package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.entities.equipment.BULK;
 
@SuppressWarnings("serial")
public class PComboBulk extends PComboBox<BULK> {
	
	public PComboBulk(){
		super();
		populate();
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		
		for(BULK BULK:BULK.values()) {
			model.addElement(BULK);	
		}
	}

	public void setBULK(BULK g) {
		this.setSelectedItem(g);
	}
	
	public BULK getBULK() {
		return (BULK)getSelectedItem();
	}
	
}
