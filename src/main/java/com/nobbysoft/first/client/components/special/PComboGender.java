package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.entities.staticdto.Gender;

public class PComboGender extends PComboBox<Gender> {
	
	public PComboGender(){
		super();
		populate();
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		
		for(Gender gender:Gender.values()) {
			model.addElement(gender);	
		}
	}

	public void setGender(Gender g) {
		this.setSelectedItem(g);
	}
	
	public Gender getGender() {
		return (Gender)getSelectedItem();
	}
	
}
