package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.entities.staticdto.Alignment;
import com.nobbysoft.first.common.utils.CodedListItem;

@SuppressWarnings("serial")
public class PComboAbilitiesAsClass extends PComboBox<Integer> implements IIntegerField {

	private String className;
	
	public PComboAbilitiesAsClass(String className){
		super();
		this.className=className;
		populate();
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		CodedListItem<Integer> a = new CodedListItem<>(-1,"--");
		CodedListItem<Integer> b = new CodedListItem<>(0,"as per "+className);
		
		
		model.addElement(a);
		model.addElement(b);
		for( int i=1,n=5;i<n;i++) {
			CodedListItem<Integer> c = new CodedListItem<>(i,""+i+" levels worse than "+className) ;		
			model.addElement(c);	
		}
		
	}

	public void setIntegerValue(int val) {		
		setSelectedCode(new Integer(val));
	}
	public int getIntegerValue() {
		Object gv = getSelectedCode();
		if(gv!=null && gv instanceof Integer) {
			int v = (Integer)gv;
			return v;
		}
		return 0;
	}
	
	
}
