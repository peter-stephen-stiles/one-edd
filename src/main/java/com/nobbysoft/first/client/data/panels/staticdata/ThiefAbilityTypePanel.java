package com.nobbysoft.first.client.data.panels.staticdata;

import java.awt.GridBagLayout;
import java.lang.reflect.Constructor;

import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbilityType;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class ThiefAbilityTypePanel extends AbstractDataPanel<ThiefAbilityType,String> implements MaintenancePanelInterface<ThiefAbilityType> {
 
	public ThiefAbilityTypePanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 

	private final PCodeField txtThiefAbilityType = new PCodeField();  
	private final PTextField txtThiefAbilityName = new PTextField(128); 
	 
 

	private PDataComponent[] dataComponents = new PDataComponent[] {  txtThiefAbilityName,  
			 };
	private PDataComponent[] keyComponents = new PDataComponent[] { txtThiefAbilityType };
	private PDataComponent[] buttonComponents = new PDataComponent[] {  };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtThiefAbilityType,  
			txtThiefAbilityName  };

  

	public void jbInit() {

		txtThiefAbilityType.setName("Thief Ability Type");  
		txtThiefAbilityName.setName("Thief Ability name");
 
 

		add(getLblInstructions(), GBU.text(0, 0, 2));
		add(new PLabel(txtThiefAbilityType.getName()), GBU.label(0, 1));
		add(txtThiefAbilityType, GBU.text(1, 1)); 

		add(new PLabel("          "), GBU.label(2, 1));
		add(new PLabel("          "), GBU.label(3, 1));
		
		add(new PLabel(txtThiefAbilityName.getName()), GBU.label(0, 2));
		add(txtThiefAbilityName, GBU.text(1, 2,4));
		
 
		
 
		
 

		add(new PLabel(""), GBU.label(99, 99));

	}
 
	// 	minimums must be less or equal to maximums
	//
	protected ReturnValue<?> validateScreen() {

		return new ReturnValue<Object>("");
	}

	  protected  void populateFromScreen(ThiefAbilityType value, boolean includingKeys) {
		if (includingKeys) {
			value.setThiefAbilityType(txtThiefAbilityType.getText());
		} 
		value.setThiefAbilityName(txtThiefAbilityName.getText()); 

		
	}

	  protected   void populateScreen(ThiefAbilityType value){
		txtThiefAbilityType.setText(value.getThiefAbilityType());   
		txtThiefAbilityName.setText(value.getThiefAbilityName()); 
	
	}


	@Override
	protected PDataComponent[] getButtonComponents() { 
		return buttonComponents;
	}


	@Override
	protected DataServiceI<?, ?> getDataService() {   
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(ThiefAbilityType.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+ThiefAbilityType.class);
			}
		return dao;
		
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
	protected PDataComponent[] getMandatoryComponents() { 
		return mandatoryComponents;
	}


	@Override
	protected void populateCombos() { 

 
	}


	@Override
	protected ThiefAbilityType newT() { 
		return new ThiefAbilityType();
	}
	
 

 

}
