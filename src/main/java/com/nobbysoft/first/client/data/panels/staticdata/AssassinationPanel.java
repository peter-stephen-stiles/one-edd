package com.nobbysoft.first.client.data.panels.staticdata;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.common.entities.staticdto.Assassination;
import com.nobbysoft.first.common.entities.staticdto.AssassinationKey;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class AssassinationPanel extends AbstractDataPanel<Assassination,AssassinationKey> implements MaintenancePanelInterface<Assassination> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	
	public AssassinationPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 
 

	private final PIntegerCombo txtAssassinLevel = new PIntegerCombo(1,99) {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()<100){
				d.width=100;
			}
			return d;
		}
	};
 
	private final PIntegerCombo cbxPercentageChance = new PIntegerCombo(0,100,"--") {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()<100){
				d.width=100;
			}
			return d;
		}
	};
	
	private final PIntegerCombo txtVictimLevelFrom = new PIntegerCombo(0,99) {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()<100){
				d.width=100;
			}
			return d;
		}
	};
	private final PIntegerCombo txtVictimLevelTo = new PIntegerCombo(0,99) {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()<100){
				d.width=100;
			}
			return d;
		}
	};
	
 
	
	
	
	
	 
	private PDataComponent[] keyComponents = new PDataComponent[] { 
			txtAssassinLevel,
			txtVictimLevelFrom 
			};

	private PDataComponent[] dataComponents = new PDataComponent[] { 			
			cbxPercentageChance,
			txtVictimLevelTo
 			
			 };
	
	private PDataComponent[] buttonComponents = new PDataComponent[] {  };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] {   };

  

	public void jbInit() {

		  
		txtAssassinLevel.setName("Assassin level ");
				
		cbxPercentageChance.setName("Percentage chance");
		txtVictimLevelFrom.setName("Victim level (from)");
		txtVictimLevelTo.setName("Victim level (to)");
		
 

		int row=1;
		add(getLblInstructions(), GBU.text(0, row, 2));
		
		

		row++;		
		add(new PLabel(txtAssassinLevel.getName()), GBU.label(0, row));
		add(txtAssassinLevel, GBU.text(1, row)); 

		row++;		
		add(new PLabel(txtVictimLevelFrom.getName()), GBU.label(0, row));
		add(txtVictimLevelFrom, GBU.text(1, row)); 

 	
		add(new PLabel(txtVictimLevelTo.getName()), GBU.label(2, row));
		add(txtVictimLevelTo, GBU.text(3, row)); 		
   

		row++;		
		add(new PLabel(cbxPercentageChance.getName()), GBU.label(0, row));
		add(cbxPercentageChance, GBU.text(1, row)); 



 

		add(new PLabel(""), GBU.label(99, 99));

	}
 
	// 	minimums must be less or equal to maximums
	//
	protected ReturnValue<?> validateScreen() {

  

		{
		int from = txtVictimLevelFrom.getIntegerValue();
		int to = txtVictimLevelTo.getIntegerValue();
		if(to<from) {
			txtVictimLevelTo.requestFocusInWindow();
			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"FROM cannot be bigger than TO");
		}
		}
		
		
		
		return new ReturnValue<Object>("");
	}

	  protected  void populateFromScreen(Assassination value, boolean includingKeys) {
		if (includingKeys) {
			value.setAssassinLevel(txtAssassinLevel.getIntegerValue());
			value.setVictimLevelFrom(txtVictimLevelFrom.getIntegerValue());
		} 		
		value.setPercentageChance(cbxPercentageChance.getIntegerValue());		
		value.setVictimLevelTo(txtVictimLevelTo.getIntegerValue());		

	}

	  protected   void populateScreen(Assassination value){
		  txtAssassinLevel.setIntegerValue(value.getAssassinLevel());
		  cbxPercentageChance.setIntegerValue(value.getPercentageChance());
		  txtVictimLevelFrom.setIntegerValue(value.getVictimLevelFrom());
		  txtVictimLevelTo.setIntegerValue(value.getVictimLevelTo());
		  
 

		
	}


	@Override
	protected PDataComponent[] getButtonComponents() { 
		return buttonComponents;
	}


	@Override
	protected DataServiceI<?, ?> getDataService() {   
		DataServiceI dao;
		try {
		Class<DataServiceI> d = DataMapper.INSTANCE.getServiceForEntity(Assassination.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+Assassination.class);
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

//		CodedListService cliDao = (CodedListService)DataMapper.INSTANCE.getNonDataService(CodedListService.class);
//		
//		try {
//			txtUndeadType.setList(cliDao.getCodedList(Constants.CLI_UNDEAD));
//		} catch (SQLException e) {
//			LOGGER.error("Error getting list",e);
//			Popper.popError(this, e);
//		}
		

	}


	@Override
	protected Assassination newT() { 
		return new Assassination();
	}
	
 

 

}
