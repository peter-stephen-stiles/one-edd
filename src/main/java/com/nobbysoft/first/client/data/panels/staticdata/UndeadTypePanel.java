package com.nobbysoft.first.client.data.panels.staticdata;

import java.awt.GridBagLayout;
import java.lang.reflect.Constructor;

import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.common.entities.staticdto.UndeadType;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class UndeadTypePanel extends AbstractDataPanel<UndeadType,Integer> implements MaintenancePanelInterface<UndeadType> {
 
	public UndeadTypePanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 

	private final PIntegerField txtUndeadType = new PIntegerField();  
	private final PTextField txtUndeadTypeExample = new PTextField(128); 
	 
 

	private PDataComponent[] dataComponents = new PDataComponent[] {  txtUndeadTypeExample,  
			 };
	private PDataComponent[] keyComponents = new PDataComponent[] { txtUndeadType };
	private PDataComponent[] buttonComponents = new PDataComponent[] {  };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtUndeadType,  
			txtUndeadTypeExample  };

  

	public void jbInit() {

		txtUndeadType.setName("Undead Type id");  
		txtUndeadTypeExample.setName("Undead Type name");
 

		PLabel lblUndeadTypeId = new PLabel("UndeadType id"); 
		PLabel lblUndeadTypeName = new PLabel("UndeadType name"); 

		add(getLblInstructions(), GBU.text(0, 0, 2));
		add(lblUndeadTypeId, GBU.label(0, 1));
		add(txtUndeadType, GBU.text(1, 1)); 

		add(lblUndeadTypeName, GBU.label(0, 4));
		add(txtUndeadTypeExample, GBU.text(1, 4));
		
		PPanel pnlAtts = new PPanel(new GridBagLayout());
		
		add(pnlAtts, GBU.panel(0, 7, 3, 3)); 
		
 
		
 

		add(new PLabel(""), GBU.label(99, 99));

	}
 
	// 	minimums must be less or equal to maximums
	//
	protected ReturnValue<?> validateScreen() {

		return new ReturnValue<Object>("");
	}

	  protected  void populateFromScreen(UndeadType value, boolean includingKeys) {
		if (includingKeys) {
			value.setUndeadType(txtUndeadType.getIntegerValue());
		} 
		value.setUndeadTypeExample(txtUndeadTypeExample.getText()); 

		
	}

	  protected   void populateScreen(UndeadType value){
		txtUndeadType.setIntegerValue(value.getUndeadType());   
		txtUndeadTypeExample.setText(value.getUndeadTypeExample()); 
	
	}


	@Override
	protected PDataComponent[] getButtonComponents() { 
		return buttonComponents;
	}


	@Override
	protected DataServiceI<?, ?> getDataService() {   
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(UndeadType.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+UndeadType.class);
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
	protected UndeadType newT() { 
		return new UndeadType();
	}
	
 

 

}
