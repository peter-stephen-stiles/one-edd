package com.nobbysoft.com.nobbysoft.first.client.components.special;

import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.WeaponRangedI;

public class PWeaponRanged extends PPanel implements PDataComponent  {

	public PWeaponRanged() {
		jbInit();
	}

	private PComboBox<BigDecimal> txtFireRate = new PComboBox<>();
	private PComboBox<BigDecimal> txtRangeS = new PComboBox<>();
	private PComboBox<BigDecimal> txtRangeM = new PComboBox<>();
	private PComboBox<BigDecimal> txtRangeL = new PComboBox<>(); 
	
	private void jbInit() {
		setLayout(new GridBagLayout());
		add(new PLabel("Fire rate"),GBU.labelC(0, 0));
		add(txtFireRate,GBU.text(0, 1));
		add(new PLabel("|"),GBU.labelC(1, 0));
		add(new PLabel("|"),GBU.labelC(1, 1));
		add(new PLabel("Range S"),GBU.labelC(2, 0));
		add(txtRangeS,GBU.text(2, 1));
		add(new PLabel("Range M"),GBU.labelC(3, 0));
		add(txtRangeM,GBU.text(3, 1));
		add(new PLabel("Range L"),GBU.labelC(4, 0));
		add(txtRangeL,GBU.text(4, 1));
		populateCombos();
	}
	
	private PDataComponent[] comps = new PDataComponent[] {txtFireRate,txtRangeS,txtRangeM,txtRangeL};
	
	
	public void populateScreen(WeaponRangedI data) {
		txtFireRate.setSelectedItem(data.getFireRate());
		txtRangeS.setSelectedItem(data.getRangeS());
		txtRangeM.setSelectedItem(data.getRangeS());
		txtRangeL.setSelectedItem(data.getRangeS());
		
	}
	
	public void populateFromScreen(WeaponRangedI data) {
		data.setFireRate((BigDecimal)txtFireRate.getSelectedItem());
		data.setRangeS((BigDecimal)txtRangeS.getSelectedItem());
		data.setRangeM((BigDecimal)txtRangeM.getSelectedItem());
		data.setRangeL((BigDecimal)txtRangeL.getSelectedItem());
	}

	@Override
	public void setTheValue(Object o) {
		if(o instanceof WeaponRangedI) {
		populateScreen((WeaponRangedI)o);
		}
		
	}

	@Override
	public Object getTheValue() {
		throw new IllegalStateException();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		for(PDataComponent c: comps ) {
			c.setReadOnly(readOnly);
		}
		
	}

	@Override
	public boolean isReadOnly() {
		return comps[0].isReadOnly();
	}

	private final BigDecimal two = new BigDecimal("2");
	private final BigDecimal half = new BigDecimal("0.5");
	
	private List<BigDecimal> getList(){
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		
		BigDecimal b=two;// just to ensure its initalised
		
		for(int i=0,n=20;i<n;i++) {
			b = new BigDecimal(""+(i+1));
			b = b.divide(two,MathContext.DECIMAL32);// only need 1.2s
			list.add(b); 
		}
		int start = b.intValue();
		for(int i=start,n=40;i<n;i++) {
			b = new BigDecimal(""+i);
			list.add(b); 
		}
		
		return list;
	}
	
private void populateCombos() {
	txtFireRate.setList(getList());
	txtRangeS.setList(getList());
	txtRangeM.setList(getList());
	txtRangeL.setList(getList());
}

}
