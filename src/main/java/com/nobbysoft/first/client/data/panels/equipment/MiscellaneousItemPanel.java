package com.nobbysoft.first.client.data.panels.equipment;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCheckBox;
import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.components.special.PComboCoinage;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.equipment.MiscellaneousItem;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class MiscellaneousItemPanel extends AbstractDataPanel<MiscellaneousItem, String> implements MaintenancePanelInterface<MiscellaneousItem> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public MiscellaneousItemPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	@Override
	protected
	DataServiceI<?, ?> getDataService() {   
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(MiscellaneousItem.class);
		return dao;
		
	}
	
	private PDialog parent;
	public void setParentDialog(PDialog parent) {
		this.parent=parent;
	}
 
 
	private final PCodeField txtCode = new PCodeField(); 
	private final PTextField txtName = new PTextField(60) {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()< 300) {
				d.width=300;
			}
			return d;
		}
	};
	private final PIntegerField txtWeightGP = new PIntegerField(); 
	private final PIntegerField txtCapacityGP = new PIntegerField(); 
	private final PTextArea txtDefinition = new PTextArea(2000);
	
	private final PComboCoinage txtValueCoinage = new PComboCoinage(); 
	private final PIntegerField txtValue = new PIntegerField(false);
	
	private final PCheckBox cbxValue = new PCheckBox("Has monetary value?");
	
	private PDataComponent[] dataComponents = new PDataComponent[] { 
			txtName, 
			txtWeightGP, 
			txtCapacityGP,
			txtDefinition,
			txtValueCoinage,
			txtValue,
			cbxValue
			 };
	private PDataComponent[] keyComponents = new PDataComponent[] { txtCode };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { 
			txtCode,   
			txtName,  
			txtWeightGP };

	private PDataComponent[] buttonComponents = new PDataComponent[] {};

	
	public void jbInit() {

		txtValueCoinage.addNullOption();
		
		txtCode.setName("Id");
 
		txtName.setName("Name");
		txtWeightGP.setName("Weight (gp)"); 
		txtCapacityGP.setName("Capacity (gp)");
		txtDefinition.setName("Definition"); 
		txtValueCoinage.setName("(unit)");
		txtValue.setName("Value ");
		cbxValue.setName("Has monetary value?");
		
		add(getLblInstructions(), GBU.text(0, 0, 2));
		int row=1;
		add(new JLabel(txtCode.getName()), GBU.label(0, row));
		add(txtCode, GBU.text(1, row)); 
		 
 
		row++;
		add(new JLabel(txtName.getName()), GBU.label(0, row));
		add(txtName, GBU.text(1, row,3));
		row++;
	
		add(new JLabel(txtWeightGP.getName()), GBU.label(0, row));
		add(txtWeightGP, GBU.text(1, row));
		row++;	 

		add(new JLabel(txtCapacityGP.getName()), GBU.label(0, row));
		add(txtCapacityGP, GBU.text(1, row)); 		 

		
		row++;	
		
		
		add(cbxValue, GBU.text(0, row,4));

		row++;
		cbxValue.addChangeListener(ce->{
			boolean val = cbxValue.isSelected();
			txtValue.setEnabled(val);
			txtValueCoinage.setEnabled(val);
		});
		
		add(new JLabel(txtValue.getName()), GBU.label(0, row));
		add(txtValue, GBU.text(1, row)); 	
		add(new JLabel(txtValueCoinage.getName()), GBU.label(2, row));
		add(txtValueCoinage, GBU.text(3, row)); 		 
		row++;		 

	 

		
		
		add(new JLabel(txtDefinition.getName()), GBU.label(0, row));
		row++;
		
		JScrollPane sclDefinition= new JScrollPane(txtDefinition) {
			public Dimension getPreferredSize() {
				Dimension d= super.getPreferredSize();
				if(d.height<80) {
					d.height=80;
				}
				if(d.width<150) {
					d.width=150;
				}
				return d;
			}
		};
		
		add(sclDefinition, GBU.panel(0, row,4,3)); 
		  
		row++;
		
		add(new PLabel(""), GBU.label(99, 99));

	}

	protected ReturnValue<?> validateScreen() {
		{
			int val = txtValue.getIntegerValue();
			if(cbxValue.isSelected()&& val>0) {
				if(txtValueCoinage.getSelectedItem()==null) {
					return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"If it has a value you have to say what type of value!");
				}
			}
		}

		return new ReturnValue("");
	}

	protected MiscellaneousItem newT() {
		return new MiscellaneousItem();
	}

	protected void populateFromScreen(MiscellaneousItem value, boolean includingKeys) {
		if (includingKeys) {
			value.setCode(txtCode.getText());
		}   
		value.setEncumberanceGP(txtWeightGP.getIntegerValue());
		value.setCapacityGP(txtCapacityGP.getIntegerValue());
		value.setName(txtName.getText());  	
		value.setDefinition(txtDefinition.getText());
		if(cbxValue.isSelected()) {
			value.setValueCoin(txtValueCoinage.getCOINAGE());
			value.setValue(txtValue.getIntegerValue());
		} else {
			value.setValueCoin(null);
			value.setValue(0);
		}
	}

	protected void populateScreen(MiscellaneousItem value) {
		txtCode.setText(value.getCode());  
		txtWeightGP	.setIntegerValue(	value.getEncumberanceGP());
		txtCapacityGP	.setIntegerValue(	value.getCapacityGP());
		txtName	.setText(	value.getName());  
		txtDefinition.setText(value.getDefinition());
		
		if(value.getValue()>0) {
			txtValueCoinage.setCOINAGE(null);
			txtValue.setIntegerValue(0);	
			cbxValue.setSelected(true);
		} else {
			cbxValue.setSelected(true);
			cbxValue.setSelected(false);
			txtValueCoinage.setCOINAGE(value.getValueCoin());
			txtValue.setIntegerValue(value.getValue());
			
		}
		
	}

	protected void populateCombos() {

		try {
 
		} catch (Exception ex) {
			Popper.popError(GuiUtils.getParent(this), ex);

		}
	}

	@Override
	protected PDataComponent[] getDataComponents() {
		return dataComponents;
	}

	@Override
	protected PDataComponent[] getKeyComponents() {
		return keyComponents;
	}

	@Override
	protected PDataComponent[] getButtonComponents() {
		return buttonComponents;
	}

	@Override
	protected PDataComponent[] getMandatoryComponents() {
		return mandatoryComponents;
	}
	
 
	

}
