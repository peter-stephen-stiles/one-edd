package com.nobbysoft.com.nobbysoft.first.client.components;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PIntegerCombo extends PComboBox<Integer> {

	int min=0;
	int max=100;
	
	public PIntegerCombo() {
		this(0,100);
	}
	
	public PIntegerCombo(int min, int max) {
		super();
		setRange(min,max);
	}
	
	public void setRange(int min,int max) {
		
		int cv = getIntegerValue();
		
		this.min=min;
		this.max=max;
		List<Integer> vals = new ArrayList<>();
		for(int i=min;i<max+1;i++) {
			vals.add(i);
		}
		setList(vals);
		setIntegerValue(cv);
	}
	public void setIntegerValue(int val) {
		setSelectedCode(new Integer(val));
	}
	public int getIntegerValue() {
		Object gv = getSelectedItem();
		if(gv!=null && gv instanceof Integer) {
		int v = (Integer)getSelectedItem();
		return v;
		}
		return 0;
	}
}
