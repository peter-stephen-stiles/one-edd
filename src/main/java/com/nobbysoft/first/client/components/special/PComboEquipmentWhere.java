package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.entities.equipment.EquipmentWhere; 

public class PComboEquipmentWhere extends PComboBox<EquipmentWhere> {
	
	public PComboEquipmentWhere(){
		super();
		populate();
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		
		for(EquipmentWhere gender:EquipmentWhere.values()) {
			model.addElement(gender);	
		}
	}

	public void setEquipmentWhere(EquipmentWhere g) {
		this.setSelectedItem(g);
	}
	
	public EquipmentWhere getEquipmentWhere() {
		return (EquipmentWhere)getSelectedItem();
	}
	
	public void setSubsetOfWheres(EquipmentWhere... wheres) {
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		model.removeAllElements();
		
		for(EquipmentWhere gender:wheres) {
			model.addElement(gender);	
		}
	}
	
}
