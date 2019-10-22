package com.nobbysoft.first.client.data.panels;

import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.List;
 

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCheckBox;
import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.components.special.PComboArcaneOrDivine;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.common.utils.Roller;
import com.nobbysoft.first.server.dao.CharacterClassDAO;
import com.nobbysoft.first.server.dao.CodedListDAO;
import com.nobbysoft.first.server.dao.DAOI;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class CharacterClassPanel extends AbstractDataPanel<CharacterClass,String> implements MaintenancePanelInterface<CharacterClass> {
 

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public CharacterClassPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	private final PCodeField txtCharacterClassId = new PCodeField();  
	private final PTextField txtName = new PTextField(128); 
	private final PComboBox<CodedListItem<Integer>> txtHitDice = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtHitDiceAtFirstLevel = new PComboBox<>();
	
	private final PIntegerCombo txtMaxHdLevel = new PIntegerCombo(1,20);

	private final PIntegerCombo txtHpAfterNameLevel = new PIntegerCombo(0,5);
	private final PCheckBox cbxMasterSpellClass = new PCheckBox("Is a master spell class?");
	private final PComboArcaneOrDivine cbxArcaneOrDivine = new PComboArcaneOrDivine();


	private final PCheckBox cbxHighConBonus = new PCheckBox("Has high con bonus?");
	
	private final PComboBox<CodedListItem<String>> txtParentClass = new PComboBox<>();
	
	private final PComboBox<CodedListItem<Integer>> txtMinStr = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinInt = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinWis = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinDex = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinCon = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinChr = new PComboBox<>(); 
	

	private final PComboBox<CodedListItem<Integer>> txtPrimeRequisite1 = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtPrimeRequisite2 = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtPrimeRequisite3 = new PComboBox<>();
	
	/*
	 * 	private int proficienciesAtFirstLevel;
	private int newProficiencyEveryXLevels;
	private int nonProficiencyPenalty;
	 */
// txtProficienciesAtFirstLevel txtNonProficiencyPenalty txtNewProficiencyEveryXLevels
	private final PIntegerCombo txtProficienciesAtFirstLevel = new PIntegerCombo(1,5);
	private final PIntegerCombo txtNonProficiencyPenalty = new PIntegerCombo(-5,-1);
	private final PIntegerCombo txtNewProficiencyEveryXLevels = new PIntegerCombo(1,6);
	
	// xpBonusPercent

	private final PIntegerField txtXpBonusPercent = new PIntegerField();
	 
	private final PIntegerField txtXpPerLevelAfterNameLevel = new PIntegerField();
	
	private final PComboBox<CodedListItem<Integer>>[] attCombos = new PComboBox[] {
		txtMinStr,txtMinInt,txtMinWis,txtMinDex,txtMinCon,txtMinChr
	};
	
    // txtMaxHdLevel,cbxMasterSpellClass
	
	private PDataComponent[] dataComponents = new PDataComponent[] {  txtName,txtHitDice, txtHitDiceAtFirstLevel,txtMaxHdLevel,txtHpAfterNameLevel,cbxMasterSpellClass,txtParentClass,
			txtMinStr,txtMinInt,txtMinWis,txtMinDex,txtMinCon,txtMinChr,txtPrimeRequisite1,
			txtPrimeRequisite2,txtPrimeRequisite3,
			txtXpBonusPercent, txtProficienciesAtFirstLevel, txtNonProficiencyPenalty, txtNewProficiencyEveryXLevels,cbxHighConBonus,cbxArcaneOrDivine,
			txtXpPerLevelAfterNameLevel
			 };
	private PDataComponent[] keyComponents = new PDataComponent[] { txtCharacterClassId };
	private PDataComponent[] buttonComponents = new PDataComponent[] {  };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtCharacterClassId,  
			txtName ,txtHitDice, txtHitDiceAtFirstLevel ,txtMaxHdLevel};

  

	public void jbInit() {

		txtCharacterClassId.setName("Character class id");  
		txtName.setName("Character class name");
		txtHitDice.setName("Hit dice");
		txtHitDiceAtFirstLevel.setName("#Hit dice at first level");
		txtMaxHdLevel.setName("Maximum Level for hit dice");
		txtHpAfterNameLevel.setName("HP per level after Name level");
		txtXpPerLevelAfterNameLevel.setName("XP per level after Name level");
		cbxMasterSpellClass.setName(cbxMasterSpellClass.getText());
		cbxArcaneOrDivine.setName("If spell class, Arcane or Divine?");
		txtParentClass.setName("Parent class");
		txtMinStr.setName("Min Str");
		txtMinInt.setName("Min Int");
		txtMinWis.setName("Min Wis");
		txtMinDex.setName("Min Dex");
		txtMinCon.setName("Min Con");
		txtMinChr.setName("Min Chr");
		txtPrimeRequisite1.setName("Prime requisite");
		txtPrimeRequisite2.setName("Prime requisite#2");
		txtPrimeRequisite3.setName("Prime requisite#3");
		txtXpBonusPercent.setName("XP Bonus %age");
		 txtProficienciesAtFirstLevel.setName("Initial number of weapons"); 
		 txtNonProficiencyPenalty.setName("Non-proficiency penalty");
		 txtNewProficiencyEveryXLevels.setName("Added prof/level");
		 cbxHighConBonus.setName("Has high con bonus");
		 
		PLabel lblCharacterClassId = new PLabel(txtCharacterClassId.getName()); 
		PLabel lblCharacterClassName = new PLabel(txtName.getName()); 
		PLabel lblHitDice = new PLabel(txtHitDice.getName()); 
		PLabel lblHitDiceAtFirstLevel = new PLabel(txtHitDiceAtFirstLevel.getName());
		PLabel lblMaxHdLevel = new PLabel(txtMaxHdLevel.getName()); 
		PLabel lblHpAfterNameLevel = new PLabel(txtHpAfterNameLevel.getName());
		PLabel lblXpPerLevelAfterNameLevel = new PLabel(txtXpPerLevelAfterNameLevel.getName());
		PLabel lblParentClass = new PLabel(txtParentClass.getName());
		PLabel lblProficienciesAtFirstLevel = new PLabel(txtProficienciesAtFirstLevel.getName());
		PLabel lblNonProficiencyPenalty = new PLabel(txtNonProficiencyPenalty.getName());
		PLabel lblNewProficiencyEveryXLevels = new PLabel(txtNewProficiencyEveryXLevels.getName());
		
		PLabel lblMinStr = new PLabel(txtMinStr.getName());
		PLabel lblMinInt = new PLabel(txtMinInt.getName());
		PLabel lblMinWis = new PLabel(txtMinWis.getName());
		PLabel lblMinDex = new PLabel(txtMinDex.getName());
		PLabel lblMinCon = new PLabel(txtMinCon.getName());
		PLabel lblMinChr = new PLabel(txtMinChr.getName());


		PLabel lblPrimeRequisite1 = new PLabel(txtPrimeRequisite1.getName());
		PLabel lblPrimeRequisite2 = new PLabel(txtPrimeRequisite2.getName());
		PLabel lblPrimeRequisite3 = new PLabel(txtPrimeRequisite3.getName());

		PLabel lblXpBonusPercent = new PLabel(txtXpBonusPercent.getName()); 
		PLabel lblArcaneOrDivine = new PLabel(cbxArcaneOrDivine.getName());
		
		
		PPanel pnlLeft = new PPanel(new GridBagLayout());
		PPanel pnlRight = new PPanel(new GridBagLayout());
		
		pnlRight.add(lblMinStr, GBU.label(0, 1));
		pnlRight.add(txtMinStr, GBU.text(1, 1)); 
		pnlRight.add(lblMinInt, GBU.label(0, 2));
		pnlRight.add(txtMinInt, GBU.text(1, 2)); 
		pnlRight.add(lblMinWis, GBU.label(0, 3));
		pnlRight.add(txtMinWis, GBU.text(1, 3)); 
		pnlRight.add(lblMinDex, GBU.label(0, 4));
		pnlRight.add(txtMinDex, GBU.text(1, 4)); 
		pnlRight.add(lblMinCon, GBU.label(0, 5));
		pnlRight.add(txtMinCon, GBU.text(1, 5)); 
		pnlRight.add(lblMinChr, GBU.label(0, 6));
		pnlRight.add(txtMinChr, GBU.text(1, 6)); 
		
		pnlRight.add(new PLabel(""), GBU.label(0, 7));
		pnlRight.add(lblPrimeRequisite1, GBU.label(0, 8));
		pnlRight.add(txtPrimeRequisite1, GBU.text(1, 8)); 
		pnlRight.add(lblPrimeRequisite2, GBU.label(0,9 ));
		pnlRight.add(txtPrimeRequisite2, GBU.text(1, 9)); 
		pnlRight.add(lblPrimeRequisite3, GBU.label(0, 10));
		pnlRight.add(txtPrimeRequisite3, GBU.text(1, 10)); 
		pnlRight.add(lblXpBonusPercent, GBU.label(0, 11));
		pnlRight.add(txtXpBonusPercent, GBU.text(1, 11)); 
		
		

		
		int row=0;
		
		add(getLblInstructions(), GBU.text(0, row, 2));
		
		row++;
		add(pnlLeft,GBU.panel(0, row));
		add(pnlRight,GBU.panel(1, row));
		
		row++;
		pnlLeft.add(lblCharacterClassId, GBU.label(0, row));
		pnlLeft.add(txtCharacterClassId, GBU.text(1, row)); 
		row++;
		pnlLeft.add(lblCharacterClassName, GBU.label(0, row));
		pnlLeft.add(txtName, GBU.text(1, row));
		row++;
		pnlLeft.add(lblHitDice, GBU.label(0, row));
		pnlLeft.add(txtHitDice, GBU.text(1, row));
		row++;
		pnlLeft.add(lblHitDiceAtFirstLevel, GBU.label(0, row));
		pnlLeft.add(txtHitDiceAtFirstLevel, GBU.text(1, row));
		row++;
		pnlLeft.add(lblMaxHdLevel, GBU.label(0,row));
		pnlLeft.add(txtMaxHdLevel, GBU.text(1, row));
		row++;
		pnlLeft.add(lblHpAfterNameLevel, GBU.label(0,row));
		pnlLeft.add(txtHpAfterNameLevel, GBU.text(1, row));
		
		row++;
		pnlLeft.add(lblXpPerLevelAfterNameLevel, GBU.label(0,row));
		pnlLeft.add(txtXpPerLevelAfterNameLevel, GBU.text(1, row));
		
		
		
		
		row++;
		pnlLeft.add(cbxMasterSpellClass, GBU.text(1, row));
		row++;
		pnlLeft.add(lblArcaneOrDivine, GBU.label(0,row));
		pnlLeft.add(cbxArcaneOrDivine, GBU.text(1, row));
		
		
		
		
		row++;
		pnlLeft.add(cbxHighConBonus, GBU.text(1, row));
		
		row++;
		pnlLeft.add(lblParentClass, GBU.label(0,row));
		pnlLeft.add(txtParentClass, GBU.text(1,row));
		row++;
		pnlLeft.add(lblProficienciesAtFirstLevel, GBU.label(0,row));
		pnlLeft.add(txtProficienciesAtFirstLevel, GBU.text(1,row));
		row++;
		pnlLeft.add(lblNonProficiencyPenalty, GBU.label(0,row));
		pnlLeft.add(txtNonProficiencyPenalty, GBU.text(1,row));
		row++;
		pnlLeft.add(lblNewProficiencyEveryXLevels, GBU.label(0,row));
		pnlLeft.add(txtNewProficiencyEveryXLevels, GBU.text(1,row));

		

		
		add(new PLabel(""), GBU.label(99, 99));

	}
 

	  ReturnValue<?> validateScreen() {


		  
		 int pr1=((Integer)txtPrimeRequisite1.getSelectedCode());
		int pr2=((Integer)txtPrimeRequisite2.getSelectedCode());
		int pr3=((Integer)txtPrimeRequisite3.getSelectedCode());
		  if(pr1==0&&pr2>0) {
			  txtPrimeRequisite2.requestFocusInWindow();
			  return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE,"Cannot select pr#2 if you don't select pr#1");
		  }

		  if(pr2==0&&pr3>0) {
			  txtPrimeRequisite3.requestFocusInWindow();
			  return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE,"Cannot select pr#3 if you don't select pr#2 and pr#1");
		  }
		  
		  if(pr1>0 && pr1 == pr2) {
			  txtPrimeRequisite2.requestFocusInWindow();
			  return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE,"Cannot select same value for pr#1 and pr#2");
		  }
		  if(pr1>0 && pr1 == pr3) {
			  txtPrimeRequisite3.requestFocusInWindow();
			  return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE,"Cannot select same value for pr#1 and pr#3");
		  }

		  if(pr2>0 && pr2 == pr3) {
			  txtPrimeRequisite3.requestFocusInWindow();
			  return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE,"Cannot select same value for pr#2 and pr#3");
		  }
		  int bon = txtXpBonusPercent.getIntegerValue();
		  if(pr1==0 && bon > 0) {
			  txtXpBonusPercent.requestFocusInWindow();
			  return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE,"Can't have a bonus without prime requisites.");
			  
		  }
		  if(bon<0 || bon > 100) {
			  txtXpBonusPercent.requestFocusInWindow();
			  return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE,"Bonus must be between 0 and 100");
		  }
		  
		int inw =	txtProficienciesAtFirstLevel.getIntegerValue();
		int npp=	txtNonProficiencyPenalty.getIntegerValue();
		int npexl=	txtNewProficiencyEveryXLevels.getIntegerValue();
		if(inw<1) {
			txtProficienciesAtFirstLevel.requestFocusInWindow();
			  return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE, txtProficienciesAtFirstLevel.getName()+" must be > 0");
		}
		if(npp>=0) {
			txtNonProficiencyPenalty.requestFocusInWindow();
			  return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE, txtNonProficiencyPenalty.getName()+" must be < 0");
		}
		if(npexl<1) {
			txtNewProficiencyEveryXLevels.requestFocusInWindow();
			  return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE, txtNewProficiencyEveryXLevels.getName()+" must be > 0");
		}  
		
		String aod = cbxArcaneOrDivine.getArcaneOrDivine();
		if(cbxMasterSpellClass.isSelected()) {
			if(aod==null) {
				cbxMasterSpellClass.requestFocusInWindow();
				return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE, "If this is a master spell class you must pick spell type.");
			}
		} else {
			if(aod!=null) {
				cbxMasterSpellClass.requestFocusInWindow();
				return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE, "If this is not a master spell class you must pick neither for spell type.");
			}
		}
		
		return new ReturnValue<Object>("");
	}

	  void populateFromScreen(CharacterClass value, boolean includingKeys) {
		if (includingKeys) {
			value.setClassId(txtCharacterClassId.getText());
		} 
		value.setName(txtName.getText());
		value.setHitDice((Integer)txtHitDice.getSelectedCode());
		value.setHitDiceAtFirstLevel((Integer)txtHitDiceAtFirstLevel.getSelectedCode());
		value.setMaxHdLevel(txtMaxHdLevel.getIntegerValue());
		value.setMasterSpellClass(cbxMasterSpellClass.isSelected());
		value.setArcaneOrDivineMasterSpellClass(cbxArcaneOrDivine.getArcaneOrDivine());
		value.setParentClassId((String)txtParentClass.getSelectedCode());
		value.setHpAfterNameLevel(txtHpAfterNameLevel.getIntegerValue());
		value.setXpPerLevelAfterNameLevel(txtXpPerLevelAfterNameLevel.getIntegerValue());
		
		value.setMinStr((Integer)txtMinStr.getSelectedCode());
		value.setMinInt((Integer)txtMinInt.getSelectedCode());
		value.setMinWis((Integer)txtMinWis.getSelectedCode());
		value.setMinDex((Integer)txtMinDex.getSelectedCode());
		value.setMinCon((Integer)txtMinCon.getSelectedCode());
		value.setMinChr((Integer)txtMinChr.getSelectedCode());


		value.setPrimeRequisite1((Integer)txtPrimeRequisite1.getSelectedCode());
		value.setPrimeRequisite2((Integer)txtPrimeRequisite2.getSelectedCode());
		value.setPrimeRequisite3((Integer)txtPrimeRequisite3.getSelectedCode());

		value.setXpBonusPercent(txtXpBonusPercent.getIntegerValue());
		

		value.setProficienciesAtFirstLevel(txtProficienciesAtFirstLevel.getIntegerValue());
		value.setNonProficiencyPenalty(txtNonProficiencyPenalty.getIntegerValue());
		value.setNewProficiencyEveryXLevels(txtNewProficiencyEveryXLevels.getIntegerValue());
		
		value.setHighConBonus(cbxHighConBonus.isSelected());
		
	}

	  void populateScreen(CharacterClass value){
		txtCharacterClassId.setText(value.getClassId());  
		txtName.setText(value.getName()); 
		txtHitDice.setSelectedCode(value.getHitDice());
		txtHitDiceAtFirstLevel.setSelectedCode(value.getHitDiceAtFirstLevel());
		txtMaxHdLevel.setIntegerValue(value.getMaxHdLevel());
		cbxMasterSpellClass.setSelected(value.isMasterSpellClass());
		cbxArcaneOrDivine.setSelectedCode(value.getArcaneOrDivineMasterSpellClass());
		txtParentClass.setSelectedCode(value.getParentClassId());
		txtHpAfterNameLevel.setIntegerValue(value.getHpAfterNameLevel());
		txtXpPerLevelAfterNameLevel.setIntegerValue(value.getXpPerLevelAfterNameLevel());
		
		txtMinStr.setSelectedCode(value.getMinStr());
		txtMinInt.setSelectedCode(value.getMinInt());
		txtMinWis.setSelectedCode(value.getMinWis());
		txtMinDex.setSelectedCode(value.getMinDex());
		txtMinCon.setSelectedCode(value.getMinCon());
		txtMinChr.setSelectedCode(value.getMinChr());
		txtPrimeRequisite1.setSelectedCode(value.getPrimeRequisite1());
		txtPrimeRequisite2.setSelectedCode(value.getPrimeRequisite2());
		txtPrimeRequisite3.setSelectedCode(value.getPrimeRequisite3());
//
		txtXpBonusPercent.setIntegerValue(value.getXpBonusPercent());

		txtProficienciesAtFirstLevel.setIntegerValue(value.getProficienciesAtFirstLevel());
		txtNonProficiencyPenalty.setIntegerValue(value.getNonProficiencyPenalty());
		txtNewProficiencyEveryXLevels.setIntegerValue(value.getNewProficiencyEveryXLevels());
		
		cbxHighConBonus.setSelected(value.isHighConBonus());
	}


	@Override
	PDataComponent[] getButtonComponents() { 
		return buttonComponents;
	}


	@Override
	DataServiceI<?, ?> getDataService() {
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(CharacterClass.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+CharacterClass.class);
			}
		return dao;
	}


	@Override
	PDataComponent[] getDataComponents() { 
		return dataComponents;
	}


	@Override
	PDataComponent[] getKeyComponents() { 
		return keyComponents;
	}


	@Override
	PDataComponent[] getMandatoryComponents() { 
		return mandatoryComponents;
	}


	@Override
	void populateCombos() { 

		CodedListService cliDao = (CodedListService)DataMapper.INSTANCE.getNonDataService(CodedListService.class);
		txtHitDice.setList(cliDao.getDice());
		txtPrimeRequisite1.setList(cliDao.getAttributesWithZero());
		txtPrimeRequisite2.setList(cliDao.getAttributesWithZero());
		txtPrimeRequisite3.setList(cliDao.getAttributesWithZero());
		
		txtHitDiceAtFirstLevel.addItem(new CodedListItem(1,"One"));
		txtHitDiceAtFirstLevel.addItem(new CodedListItem(2,"Two"));
		
		for(PComboBox<CodedListItem<Integer>> c: attCombos) {
			List<CodedListItem<?>> list =cliDao.getAttRolls();
			for (CodedListItem cli:list) {
				if("0".equals(cli.getDescription())){
					cli.setDescription("");
				}
			}
			c.setList(list);
		}
		 
		 {
		
			try {
 
				

				CharacterClassService dao = (CharacterClassService)DataMapper.INSTANCE.getDataService(CharacterClass.class);

					List<CodedListItem<String>> list = dao.getAsCodedList();
					list.add(0, new CodedListItem(null,"(none)"));
					txtParentClass.setList(list);

			} catch (Exception ex) {
				Popper.popError(GuiUtils.getParent(this), ex);

			}
			
		}
		
		
	}


	@Override
	CharacterClass newT() { 
		return new CharacterClass();
	}
	
 

 

}
