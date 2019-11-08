package com.nobbysoft.first.client.data.panels;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassToHit;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassToHitKey;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class ClassToHitEditDialog 
  extends AbstractDataPanel<CharacterClassToHit, CharacterClassToHitKey>
  implements MaintenancePanelInterface<CharacterClassToHit> {

 

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
	

	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 

	
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
	
	private final PIntegerCombo txtFromLevel = new IC();
	private final PIntegerCombo txtToLevel = new IC();
	private final PIntegerCombo txtBiggestACHitBy20 = new PIntegerCombo(-40,10);
	
 

	
	private CharacterClass parent=null;
	public void setParent(CharacterClass parent) {
		this.parent=parent;
		if(txtCharacterClass.getModel().getSize()>1) {
			txtCharacterClass.setSelectedCode(parent.getClassId());
		}
	}
	
	public ClassToHitEditDialog() {
		setLayout(new GridBagLayout());
		jbInit();
	}
	
	@Override
	PDataComponent[] getButtonComponents() {
		return new PDataComponent[] {};
	}

	@Override
	DataServiceI<?, ?> getDataService() {
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(CharacterClassToHit.class);
		return dao;
	}

	PDataComponent[] dataComp = new PDataComponent[] {
			txtBiggestACHitBy20 ,
			};
	
	@Override
	PDataComponent[] getDataComponents() { 
		return dataComp;
	}

	@Override
	PDataComponent[] getKeyComponents() { 
		return new PDataComponent[] {txtFromLevel,txtToLevel};
	}

	@Override
	PDataComponent[] getMandatoryComponents() { 
		return new PDataComponent[] {};
	}



	
	@Override
	void jbInit() {
		txtCharacterClass.setReadOnly(true);
		
		txtCharacterClass.setName("Character Class");
		 
		txtFromLevel.setName("From-Level");
		txtToLevel.setName("To-Level");
		txtBiggestACHitBy20.setName("Biggest AC hit by a 20");

		int row=0;
		add(new PLabel(txtCharacterClass.getName()),GBU.label(0, row));
		add(txtCharacterClass,GBU.text(1, row));		

 	
		row++;
		
		add(new PLabel(txtFromLevel.getName()),GBU.label(0, row));
		add(txtFromLevel,GBU.text(1, row));
		
		add(new PLabel(txtToLevel.getName()),GBU.label(2, row));
		add(txtToLevel,GBU.text(3,row));
		
		row++;
		
		add(new PLabel(txtBiggestACHitBy20.getName()),GBU.label(0, row));
		add(txtBiggestACHitBy20,GBU.text(1, row,2));	 
		
		row++;
 
		
		// spacer
		add(new PLabel(""),GBU.label(99, 99));	
	}

//	public void defaultAdd(int level, int xp){
//		txtClassLevel.setIntegerValue(level);
//		txtFromXp.setIntegerValue(xp);
//	}
	
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
	void populateFromScreen(CharacterClassToHit value, boolean includingKeys) {
		value.setFromLevel(txtFromLevel.getIntegerValue());
		value.setToLevel(txtToLevel.getIntegerValue());
		value.setBiggestACHitBy20(txtBiggestACHitBy20.getIntegerValue());


	}

	@Override
	void populateScreen(CharacterClassToHit value) {
		txtFromLevel.setIntegerValue(value.getFromLevel());
		txtToLevel.setIntegerValue(value.getToLevel());
		txtBiggestACHitBy20.setIntegerValue(value.getBiggestACHitBy20());
		
		
	}

	@Override
	ReturnValue<?> validateScreen() {
		
 
		
		return new ReturnValue(""); //no error
	}

	@Override
	CharacterClassToHit newT() { 
		CharacterClassToHit ccs = new CharacterClassToHit();
		ccs.setClassId(parent.getClassId());
		return ccs;
	}

	
}
