package com.nobbysoft.first.client.components.special;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.entities.equipment.EquipmentWhere; 

public class PComboEquipmentWhere extends PComboBox<EquipmentWhere> {
	
	public PComboEquipmentWhere(){
		super();
		populate();
	}
	
	private void populate() {
		
		
		
		EquipmentWhere[] wa= EquipmentWhere.values();
		
		
		setAndSort(wa);

		

	}

	private void setAndSort(EquipmentWhere[] wa) {
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();		
		model.removeAllElements();
		
		List<EquipmentWhere> wheres =new ArrayList<>();
		for(EquipmentWhere gender:wa) {
			wheres.add(gender);	
		}

		wheres.sort(new Comparator<EquipmentWhere>() {

			@Override
			public int compare(EquipmentWhere o1, EquipmentWhere o2) {
				if(o1.getIndex()<o2.getIndex()) {
					return -1;
				}
				if(o1.getIndex()>o2.getIndex()) {
					return 1;
				}
				return o1.getDescription().compareTo(o2.getDescription());
			}
			
		});
		
		for(EquipmentWhere gender:wheres) {
			model.addElement(gender);	
		}
	}

	public void setEquipmentWhere(EquipmentWhere g) {
		this.setSelectedItem(g);
	}
	
	public EquipmentWhere getEquipmentWhere() {
		return (EquipmentWhere)getSelectedItem();
	}
	
	public void setSubsetOfWheres(EquipmentWhere... wa) {
		if(wa==null || wa.length==0) {
			populate(); // everything
		} else {
			setAndSort(wa); // somethings
		}
	}
	
}
