package com.nobbysoft.first.client.data.panels.staticdata;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbility;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbilityKey;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbilityType;
import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class ThiefAbilityPanel extends AbstractDataPanel<ThiefAbility,ThiefAbilityKey> implements MaintenancePanelInterface<ThiefAbility> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	
	public ThiefAbilityPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 
 

	private final PIntegerCombo txtThiefLevel = new PIntegerCombo(1,99) {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()<150){
				d.width=150;
			}
			return d;
		}
	};
 
	private final PTextField txtPercentageChance = new PTextField() {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()<150){
				d.width=150;
			}
			return d;
		}
	};
	
	private final PComboBox<ThiefAbilityType> txtThiefAbilityType = new PComboBox<ThiefAbilityType>() {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()<150){
				d.width=150;
			}
			return d;
		}
	};
 
	
	
	
	
	 
	private PDataComponent[] keyComponents = new PDataComponent[] { 
			txtThiefLevel,
			txtThiefAbilityType 
			};

	private PDataComponent[] dataComponents = new PDataComponent[] { 			
			txtPercentageChance, 
 			
			 };
	
	private PDataComponent[] buttonComponents = new PDataComponent[] {  };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] {   };

  

	public void jbInit() {

		  
		txtThiefLevel.setName("Thief level ");
		txtThiefAbilityType.setName("Ability");		
		txtPercentageChance.setName("Percentage chance");
		 
		
	 txtPercentageChance.addActionListener(ae->{
				ReturnValue<?> rv = ok();
				if(rv.isError()) {
					Popper.popError(this, "Error saving", rv);
				} else {
					parentd.dispose();
				}
			});

		int row=1;
		add(getLblInstructions(), GBU.text(0, row, 2));
		
		

		row++;		
		add(new PLabel(txtThiefLevel.getName()), GBU.label(0, row));
		add(txtThiefLevel, GBU.text(1, row)); 

		row++;		
		add(new PLabel(txtThiefAbilityType.getName()), GBU.label(0, row));
		add(txtThiefAbilityType, GBU.text(1, row)); 
 

		row++;		
		add(new PLabel(txtPercentageChance.getName()), GBU.label(0, row));
		add(txtPercentageChance, GBU.text(1, row)); 



 

		add(new PLabel(""), GBU.label(99, 99));

	}
 
	// 	minimums must be less or equal to maximums
	//
	protected ReturnValue<?> validateScreen() {

  

//		{
//		int from = txtThiefAbilityType.getIntegerValue();
//		int to = txtVictimLevelTo.getIntegerValue();
//		if(to<from) {
//			txtVictimLevelTo.requestFocusInWindow();
//			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"FROM cannot be bigger than TO");
//		}
//		}
		String pcs =txtPercentageChance.getText();
		if(pcs.trim().isEmpty()) {
			txtPercentageChance.setText("0");
			pcs="0";
		}
		double pc = 0.0d;
		try {
			 pc = Double.parseDouble(pcs);
		} catch (NumberFormatException e) {
			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"Invalid number ("+pcs+")");
		}

		if(pc<0) {
			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"Must be >= 0");
		}
		if(pc>125) {
			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"Must be <= 125");
		}
		
		
		return new ReturnValue<Object>("");
	}

	  protected  void populateFromScreen(ThiefAbility value, boolean includingKeys) {
		if (includingKeys) {
			value.setThiefLevel(txtThiefLevel.getIntegerValue());
			value.setThiefAbilityType((String)txtThiefAbilityType.getSelectedCode());
		} 		
		value.setPercentageChance( Double.parseDouble( txtPercentageChance.getText()));	 

	}

	  protected   void populateScreen(ThiefAbility value){
		  txtThiefLevel.setIntegerValue(value.getThiefLevel());
		  txtPercentageChance.setText(value.getPercentageChanceString());
		  txtThiefAbilityType.setSelectedCode(value.getThiefAbilityType());
		
	}


	@Override
	protected PDataComponent[] getButtonComponents() { 
		return buttonComponents;
	}


	@Override
	protected DataServiceI<?, ?> getDataService() {   
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(ThiefAbility.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+ThiefAbility.class);
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

		CodedListService cliDao = (CodedListService)DataMapper.INSTANCE.getNonDataService(CodedListService.class);
		
		try {
			txtThiefAbilityType.setList(cliDao.getCodedList(Constants.CLI_THIEF_ABILITY));
		} catch (SQLException e) {
			LOGGER.error("Error getting list",e);
			Popper.popError(this, e);
		}
		

	}


	@Override
	protected ThiefAbility newT() { 
		return new ThiefAbility();
	}
	
 

 

}
