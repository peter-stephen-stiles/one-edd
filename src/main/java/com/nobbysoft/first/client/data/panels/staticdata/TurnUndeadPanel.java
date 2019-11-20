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
import com.nobbysoft.first.client.components.special.PTurnUndeadRollRequired;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.staticdto.TurnUndead;
import com.nobbysoft.first.common.entities.staticdto.TurnUndeadKey;
import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class TurnUndeadPanel extends AbstractDataPanel<TurnUndead,TurnUndeadKey> implements MaintenancePanelInterface<TurnUndead> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	
	public TurnUndeadPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 
 

	private final PIntegerCombo txtEffectiveClericLevelFrom = new PIntegerCombo(1,99) {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()<100){
				d.width=100;
			}
			return d;
		}
	};;
	private final PComboBox<CodedListItem<Integer>> txtUndeadType = new PComboBox<CodedListItem<Integer>>() {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()<150){
				d.width=150;
			}
			return d;
		}
	};
	private final PIntegerCombo txtEffectiveClericLevelTo = new PIntegerCombo(1,99) {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()<100){
				d.width=100;
			}
			return d;
		}
	};
	private final PTurnUndeadRollRequired cbxRollRequired = new PTurnUndeadRollRequired();
	
	private final PIntegerCombo txtNumberAffectedFrom = new PIntegerCombo(1,99);
	private final PIntegerCombo txtNumberAffectedTo = new PIntegerCombo(1,99);
	
 
	
	
	
	
	 
	private PDataComponent[] keyComponents = new PDataComponent[] { 
			txtEffectiveClericLevelFrom,
			txtUndeadType 
			};

	private PDataComponent[] dataComponents = new PDataComponent[] { 
			txtEffectiveClericLevelTo,
			cbxRollRequired,
			txtNumberAffectedFrom,
			txtNumberAffectedTo
 			
			 };
	
	private PDataComponent[] buttonComponents = new PDataComponent[] {  };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtEffectiveClericLevelTo  };

  

	public void jbInit() {

		  
		txtEffectiveClericLevelFrom.setName("Effective cleric level (from)");
		txtUndeadType.setName("Undead type");
		txtEffectiveClericLevelTo.setName("Effective cleric level (to)");
		cbxRollRequired.setName("Roll required");
		txtNumberAffectedFrom.setName("Number affected (from)");
		txtNumberAffectedTo.setName("Number affected (to)");
		
 

		int row=1;
		add(getLblInstructions(), GBU.text(0, row, 2));
		
		

		row++;		
		add(new PLabel(txtUndeadType.getName()), GBU.label(0, row));
		add(txtUndeadType, GBU.text(1, row)); 

		
		row++;		
		add(new PLabel(txtEffectiveClericLevelFrom.getName()), GBU.label(0, row));
		add(txtEffectiveClericLevelFrom, GBU.text(1, row)); 

 	
		add(new PLabel(txtEffectiveClericLevelTo.getName()), GBU.label(2, row));
		add(txtEffectiveClericLevelTo, GBU.text(3, row)); 

		row++;		
		add(new PLabel(cbxRollRequired.getName()), GBU.label(0, row));
		add(cbxRollRequired, GBU.text(1, row)); 

		row++;		
		add(new PLabel(txtNumberAffectedFrom.getName()), GBU.label(0, row));
		add(txtNumberAffectedFrom, GBU.text(1, row)); 


		row++;		
		add(new PLabel(txtNumberAffectedTo.getName()), GBU.label(0, row));
		add(txtNumberAffectedTo, GBU.text(1, row)); 

 

		add(new PLabel(""), GBU.label(99, 99));

	}
 
	// 	minimums must be less or equal to maximums
	//
	protected ReturnValue<?> validateScreen() {

		{
		int from = txtEffectiveClericLevelFrom.getIntegerValue();
		int to = txtEffectiveClericLevelTo.getIntegerValue();
		if(to<from) {
			txtEffectiveClericLevelTo.requestFocusInWindow();
			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"FROM cannot be bigger than TO");
		}
		}

		{
		int from = txtNumberAffectedFrom.getIntegerValue();
		int to = txtNumberAffectedTo.getIntegerValue();
		if(to<from) {
			txtNumberAffectedTo.requestFocusInWindow();
			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"FROM cannot be bigger than TO");
		}
		}
		
		
		
		return new ReturnValue<Object>("");
	}

	  protected  void populateFromScreen(TurnUndead value, boolean includingKeys) {
		if (includingKeys) {
			value.setEffectiveClericLevelFrom(txtEffectiveClericLevelFrom.getIntegerValue());
			value.setUndeadType( Integer.parseInt( (String)txtUndeadType.getSelectedCode()));
		} 
		value.setEffectiveClericLevelTo(txtEffectiveClericLevelTo.getIntegerValue());
		value.setRollRequired(cbxRollRequired.getRollRequired());
		value.setNumberAffectedFrom(txtNumberAffectedFrom.getIntegerValue());
		value.setNumberAffectedTo(txtNumberAffectedTo.getIntegerValue());
		
 

		
	}

	  protected   void populateScreen(TurnUndead value){
		  txtEffectiveClericLevelFrom.setIntegerValue(value.getEffectiveClericLevelFrom());
		  txtUndeadType.setSelectedCode(""+value.getUndeadType());
		  txtEffectiveClericLevelTo.setIntegerValue(value.getEffectiveClericLevelTo());
		  cbxRollRequired.setRollRequired(value.getRollRequired());
		  txtNumberAffectedFrom.setIntegerValue(value.getNumberAffectedFrom());
		  txtNumberAffectedTo.setIntegerValue(value.getNumberAffectedTo());
		  
 

		
	}


	@Override
	protected PDataComponent[] getButtonComponents() { 
		return buttonComponents;
	}


	@Override
	protected DataServiceI<?, ?> getDataService() {   
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(TurnUndead.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+TurnUndead.class);
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
			txtUndeadType.setList(cliDao.getCodedList(Constants.CLI_UNDEAD));
		} catch (SQLException e) {
			LOGGER.error("Error getting list",e);
			Popper.popError(this, e);
		}
		

	}


	@Override
	protected TurnUndead newT() { 
		return new TurnUndead();
	}
	
 

 

}
