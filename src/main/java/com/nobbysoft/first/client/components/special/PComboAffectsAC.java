package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.entities.staticdto.AffectsACType;
 
@SuppressWarnings("serial")
public class PComboAffectsAC extends PComboBox<AffectsACType> {
	
	public PComboAffectsAC(){
		super();
		populate();
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		
		for(AffectsACType a:AffectsACType.values()) {
			model.addElement(a);	
		}
	}

	public void setAffectsACType(AffectsACType g) {
		this.setSelectedItem(g);
	}
	
	public void setAffectsACTypeString(String s) {
		if(s==null) {
			this.setSelectedItem(AffectsACType.X);
		}
		AffectsACType g = AffectsACType.valueOf(s);
		this.setSelectedItem(g);
	}
	
	
	public String getAffectsACTypeString() {
		return ((AffectsACType)getSelectedItem()).name();
	}
	
}