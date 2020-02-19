package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.entities.equipment.RodStaffWand;

@SuppressWarnings("serial")
public class PComboRodStaffWand extends PComboBox<String> {
	
	public PComboRodStaffWand(){
		super();
		populate();
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		
		for(String gender:RodStaffWand.getRswValues()) {
			model.addElement(gender);	
		}
	}


	public void setText(String g) {
		this.setSelectedItem(g);
	}
	
	public String getText() {
		return (String)getSelectedItem();
	}
	
	
	public void setValue(String g) {
		this.setSelectedItem(g);
	}
	
	public String getValue() {
		return (String)getSelectedItem();
	}
	
}
