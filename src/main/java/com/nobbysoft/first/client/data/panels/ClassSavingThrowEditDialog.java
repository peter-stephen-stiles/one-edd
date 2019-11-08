package com.nobbysoft.first.client.data.panels;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.special.PComboSavingThrowType;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.SavingThrow;
import com.nobbysoft.first.common.entities.staticdto.SavingThrowKey;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class ClassSavingThrowEditDialog 
  extends AbstractDataPanel<SavingThrow, SavingThrowKey>
  implements MaintenancePanelInterface<SavingThrow> {

 

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private final PComboBox<CodedListItem<String>> txtCharacterClass = new PComboBox<CodedListItem<String>>() {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.width<150) {
				d.width = 150;
			}
			return d;
		}
	};
	
 
	
	private final class IC extends PIntegerCombo{
		public IC() {
			super(0,99);
		}
		public Dimension getPreferredSize() {
			Dimension d= super.getPreferredSize();
			if(d.width<60) {
				d.width=60;
			}
			return d;
		}
	}
	
	private final PComboSavingThrowType cbxSavingThrow = new PComboSavingThrowType();
	
	private final IC txtFromLevel = new IC();
	private final IC txtToLevel = new IC();
	
	private final PIntegerField txtRollRequired = new PIntegerField();
	
 

	
	private CharacterClass parent=null;
	public void setParent(CharacterClass parent) {
		this.parent=parent;
		if(txtCharacterClass.getModel().getSize()>1) {
			txtCharacterClass.setSelectedCode(parent.getClassId());
		}
	}
	
	public ClassSavingThrowEditDialog() {
		setLayout(new GridBagLayout());
		jbInit();
	}
	
	@Override
	PDataComponent[] getButtonComponents() {
		return new PDataComponent[] {};
	}

	@Override
	DataServiceI<?, ?> getDataService() {
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(SavingThrow.class);
		return dao;
	}

	PDataComponent[] dataComp = new PDataComponent[] {
			txtRollRequired
			};
	
	@Override
	PDataComponent[] getDataComponents() { 
		return dataComp;
	}

	@Override
	PDataComponent[] getKeyComponents() { 
		return new PDataComponent[] {txtFromLevel,txtToLevel,cbxSavingThrow};
	}

	@Override
	PDataComponent[] getMandatoryComponents() { 
		return new PDataComponent[] {};
	}



	
	@Override
	void jbInit() {
		txtCharacterClass.setReadOnly(true);
		
		txtCharacterClass.setName("Character Class");
		 
		txtFromLevel.setName("From Level");
		txtToLevel.setName("To Level");
		cbxSavingThrow.setName("Saving Throw Type");
		txtRollRequired.setName("Roll required");

		int row=0;
		add(new PLabel(txtCharacterClass.getName()),GBU.label(0, row));
		add(txtCharacterClass,GBU.text(1, row));		
 
		
		row++;
		
		add(new PLabel(txtFromLevel.getName()),GBU.label(0, row));
		add(txtFromLevel,GBU.text(1, row));
		
		add(new PLabel(txtToLevel.getName()),GBU.label(2, row));
		add(txtToLevel,GBU.text(3,row));
		
		row++;
		
		add(new PLabel(cbxSavingThrow.getName()),GBU.label(0, row));
		add(cbxSavingThrow,GBU.text(1, row,2));		 
		
		row++;
		//txtRollRequired
		add(new PLabel(txtRollRequired.getName()),GBU.label(0, row));
		add(txtRollRequired,GBU.text(1, row,2));		 
		
		
		// spacer
		add(new PLabel(""),GBU.label(99, 99));	
	}

	
	@Override
	void populateCombos() {

		try {
			CharacterClassService dao = (CharacterClassService)DataMapper.INSTANCE.getDataService(CharacterClass.class);
//			{
//				List<CodedListItem<String>> list = dao.getLevelClassesAsCodedList();
//
//				for (CodedListItem<String> cli : list) {
//					LOGGER.info("Level class " + cli);
//					txtLevelClass.addItem(cli);					
//				}
//			}
			{
				List<CodedListItem<String>> list = dao.getAsCodedList();

				for (CodedListItem<String> cli : list) {
					LOGGER.info("character class " + cli);
					if(parent!=null) {
						if(parent.getClassId()!=null) {
							if(parent.getClassId().contentEquals(cli.getItem())) {
								txtCharacterClass.addItem(cli);
							}
						}
					}
					if(parent==null) {
						txtCharacterClass.addItem(cli);
					}
				}
			}
			
		} catch (Exception ex) {
			Popper.popError(GuiUtils.getParent(this), ex);

		}
	}

	@Override
	void populateFromScreen(SavingThrow value, boolean includingKeys) {
		
		value.setFromLevel(txtFromLevel.getIntegerValue());
		value.setToLevel(txtToLevel.getIntegerValue());
		value.setRollRequired(txtRollRequired.getIntegerValue());
		value.setSavingThrowType(cbxSavingThrow.getSavingThrowType());


	}

	@Override
	void populateScreen(SavingThrow value) { 
		txtFromLevel.setIntegerValue(value.getFromLevel());
		txtToLevel.setIntegerValue(value.getToLevel());
		txtRollRequired.setIntegerValue(value.getRollRequired());
		cbxSavingThrow.setSavingThrowType(value.getSavingThrowType());
		
	}

	@Override
	ReturnValue<?> validateScreen() {
		
		long Level0 = txtFromLevel.getIntegerValue();
		long Level1 = txtToLevel.getIntegerValue();
		if(Level1<Level0){
			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"'To' Level cannot be less than 'From' Level");
		}
//		if(Level0==0 && Level1==0) {
//			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"level bigger than zero please"); NO NORMAL HUMANS!!!
//		}
		
		return new ReturnValue(""); //no error
	}

	@Override
	SavingThrow newT() { 
		SavingThrow ccs = new SavingThrow();
		ccs.setClassId(parent.getClassId());
		return ccs;
	}

	
}
