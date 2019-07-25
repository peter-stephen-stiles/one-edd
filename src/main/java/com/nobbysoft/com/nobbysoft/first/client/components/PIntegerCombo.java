package com.nobbysoft.com.nobbysoft.first.client.components;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
public class PIntegerCombo extends PComboBox<Integer> {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	
	private int min=0;
	private int max=100;
	private boolean showPlus = false;
	private boolean showZero = true;
	
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
	
	public PIntegerCombo(int min, int max, boolean showPlus, boolean showZero) {
		super();
		this.showPlus=showPlus;
		this.showZero=showZero;
		setRange(min,max);
		addRenderer();
	}
	
	
	public PIntegerCombo(int min, int max) {
		super();
		setRange(min,max);
		addRenderer();
	}
	
	private void addRenderer() {
		super.setRenderer(new PComboIntRenderer(showPlus,showZero));
	}
	
	
	public void setRange(int min,int max) {
		
		int cv = getIntegerValue();
		
		this.min=min;
		this.max=max;
		List<Integer> vals = new ArrayList<>();
		for(int i=min;i<max+1;i++) {
			vals.add(new Integer(i));
		}
		setList(vals);
		setIntegerValue(cv);
	}
	public void setIntegerValue(int val) {		
		setSelectedItem(new Integer(val));
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
