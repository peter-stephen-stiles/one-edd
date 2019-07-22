package com.nobbysoft.com.nobbysoft.first.client.components;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PIntegerCombo extends PComboBox<Integer> {

	private int min=0;
	private int max=100;
	private boolean showPlus = false;
	
	public PIntegerCombo() {
		this(0,100);
		addRenderer();
	}

	public PIntegerCombo(int min, int max, boolean showPlus) {
		super();
		this.showPlus=showPlus;
		setRange(min,max);
		addRenderer();
	}
	
	
	public PIntegerCombo(int min, int max) {
		super();
		setRange(min,max);
		addRenderer();
	}
	
	private void addRenderer() {
		super.setRenderer(new PComboIntRenderer(showPlus));
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
