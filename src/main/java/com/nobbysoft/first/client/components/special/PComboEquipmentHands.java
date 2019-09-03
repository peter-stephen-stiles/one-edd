package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.first.common.entities.staticdto.Gender;

public class PComboEquipmentHands extends PComboBox<EquipmentHands> {
	
	public PComboEquipmentHands(){
		super();
		populate();
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		
		for(EquipmentHands gender:EquipmentHands.values()) {
			model.addElement(gender);	
		}
	}

	public void setEquipmentHands(EquipmentHands g) {
		this.setSelectedItem(g);
	}
	
	public EquipmentHands getEquipmentHands() {
		return (EquipmentHands)getSelectedItem();
	}
	
}
