package com.nobbysoft.first.client.data.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.*;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

public class ClassSpellsEditDialog 
  extends AbstractDataPanel<CharacterClassSpell, CharacterClassSpellKey>
  implements MaintenancePanelInterface<CharacterClassSpell> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private final PComboBox<CodedListItem<String>> txtCharacterClass = new PComboBox<>();
	private final PComboBox<CodedListItem<String>> txtSpellClass = new PComboBox<>();
	private final PIntegerCombo txtClassLevel = new PIntegerCombo(1,30) {
		public Dimension getPreferredSize() {
			Dimension d= super.getPreferredSize();
			if(d.width<60) {
				d.width=60;
			}
			return d;
		}
	};
	
	private final class IC extends PIntegerCombo{
		public IC() {
			super(0,30);
		}
		public Dimension getPreferredSize() {
			Dimension d= super.getPreferredSize();
			if(d.width<60) {
				d.width=60;
			}
			return d;
		}
	}
	
	private final PIntegerCombo txtLevel1 = new IC();
	private final PIntegerCombo txtLevel2 = new IC();
	private final PIntegerCombo txtLevel3 = new IC();
	private final PIntegerCombo txtLevel4 = new IC();
	private final PIntegerCombo txtLevel5 = new IC();
	private final PIntegerCombo txtLevel6 = new IC();
	private final PIntegerCombo txtLevel7 = new IC();
	private final PIntegerCombo txtLevel8 = new IC();
	private final PIntegerCombo txtLevel9 = new IC();
	
	private CharacterClass parent=null;
	public void setParent(CharacterClass parent) {
		this.parent=parent;
		if(txtCharacterClass.getModel().getSize()>1) {
			txtCharacterClass.setSelectedCode(parent.getClassId());
		}
	}

	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 

	public ClassSpellsEditDialog() {
		setLayout(new GridBagLayout());
		jbInit();
	}
	
	@Override
	protected PDataComponent[] getButtonComponents() {
		// TODO Auto-generated method stub
		return new PDataComponent[] {};
	}

	@Override
	protected DataServiceI<?, ?> getDataService() {
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(CharacterClassSpell.class);
		return dao;
	}

	PDataComponent[] spells = new PDataComponent[] {txtLevel1 ,
			txtLevel2,
			txtLevel3 ,
			txtLevel4 ,
			txtLevel5 ,
			txtLevel6 ,
			txtLevel7 ,
			txtLevel8 ,
			txtLevel9};
	
	@Override
	protected PDataComponent[] getDataComponents() { 
		return spells;
	}

	@Override
	protected PDataComponent[] getKeyComponents() { 
		return new PDataComponent[] {txtSpellClass,txtClassLevel};
	}

	@Override
	protected PDataComponent[] getMandatoryComponents() { 
		return new PDataComponent[] {};
	}



	
	@Override
	protected void jbInit() {
		txtCharacterClass.setReadOnly(true);
		
		txtCharacterClass.setName("Character Class");
		txtSpellClass.setName("Spell Class");
		txtClassLevel.setName("Class Level");
		txtLevel1.setName("Spells of Level 1");
		txtLevel2.setName("Spells of Level 2");
		txtLevel3.setName("Spells of Level 3");
		txtLevel4.setName("Spells of Level 4");
		txtLevel5.setName("Spells of Level 5");
		txtLevel6.setName("Spells of Level 6");
		txtLevel7.setName("Spells of Level 7");
		txtLevel8.setName("Spells of Level 8");
		txtLevel9.setName("Spells of Level 9");
		
		add(new PLabel(txtCharacterClass.getName()),GBU.label(0, 1));
		add(txtCharacterClass,GBU.text(1, 1));
		

		add(new PLabel(txtClassLevel.getName()),GBU.label(3, 1));
		add(txtClassLevel,GBU.text(4, 1));
		
		add(new PLabel(txtSpellClass.getName()),GBU.label(0, 2));
		add(txtSpellClass,GBU.text(1, 2));
		
		
		PPanel pnlSpells = new PPanel(new GridLayout(2,9));
		
		add(pnlSpells,GBU.hPanel(0, 4, 4, 2));
		
		for(int i=0,n=9;i<n;i++) {
			int sl = i+1;
			pnlSpells.add(new PLabel("Spl Lvl "+sl));
		}
		
		for(int i=0,n=9;i<n;i++) {			
			pnlSpells.add((Component)spells[i]);
		}
		
		// spacer
		add(new PLabel(""),GBU.label(99, 99));	
	}

	public void defaultAdd(int level){
		if(parent!=null) {
			txtSpellClass.setSelectedCode(parent.getClassId());
		}
		txtClassLevel.setIntegerValue(level);
	}
	
	@Override
	protected void populateCombos() {

		try {
			CharacterClassService dao = (CharacterClassService)DataMapper.INSTANCE.getDataService(CharacterClass.class);
			{
				List<CodedListItem<String>> list = dao.getSpellClassesAsCodedList();

				for (CodedListItem<String> cli : list) {
					LOGGER.info("spell class " + cli);
					txtSpellClass.addItem(cli);					
				}
			}
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
	protected void populateFromScreen(CharacterClassSpell value, boolean includingKeys) {
		value.setLevel(txtClassLevel.getIntegerValue());
		value.setSpellClassId((String)txtSpellClass.getSelectedCode());
		
		value.setLevel1Spells(txtLevel1.getIntegerValue());
		value.setLevel2Spells(txtLevel2.getIntegerValue());
		value.setLevel3Spells(txtLevel3.getIntegerValue());
		value.setLevel4Spells(txtLevel4.getIntegerValue());
		value.setLevel5Spells(txtLevel5.getIntegerValue());
		value.setLevel6Spells(txtLevel6.getIntegerValue());
		value.setLevel7Spells(txtLevel7.getIntegerValue());
		value.setLevel8Spells(txtLevel8.getIntegerValue());
		value.setLevel9Spells(txtLevel9.getIntegerValue());
		
	}

	@Override
	protected void populateScreen(CharacterClassSpell value) {
		txtClassLevel.setIntegerValue(value.getLevel());
		txtSpellClass.setSelectedCode(value.getSpellClassId());
		
		txtLevel1.setIntegerValue(value.getLevel1Spells());
		txtLevel2.setIntegerValue(value.getLevel2Spells());
		txtLevel3.setIntegerValue(value.getLevel3Spells());
		txtLevel4.setIntegerValue(value.getLevel4Spells());
		txtLevel5.setIntegerValue(value.getLevel5Spells());
		txtLevel6.setIntegerValue(value.getLevel6Spells());
		txtLevel7.setIntegerValue(value.getLevel7Spells());
		txtLevel8.setIntegerValue(value.getLevel8Spells());
		txtLevel9.setIntegerValue(value.getLevel9Spells());
		
		
	}

	@Override
	protected ReturnValue<?> validateScreen() {
		return new ReturnValue(""); //no error
	}

	@Override
	protected CharacterClassSpell newT() { 
		CharacterClassSpell ccs = new CharacterClassSpell();
		ccs.setClassId(parent.getClassId());
		return ccs;
	}

	
}
