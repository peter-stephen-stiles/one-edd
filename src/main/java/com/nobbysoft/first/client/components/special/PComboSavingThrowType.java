package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.entities.staticdto.SavingThrowType;

public class PComboSavingThrowType extends PComboBox<SavingThrowType> {
	
	public PComboSavingThrowType(){
		super();
		populate();
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		
		for(SavingThrowType SavingThrowType:SavingThrowType.values()) {
			model.addElement(SavingThrowType);	
		}
	}

	public void setSavingThrowType(SavingThrowType g) {
		this.setSelectedItem(g);
	}
	
	public SavingThrowType getSavingThrowType() {
		return (SavingThrowType)getSelectedItem();
	}
	
}
