package com.nobbysoft.first.client.data.panels;

import java.awt.GridBagLayout;
import java.lang.reflect.Constructor;

import com.nobbysoft.first.client.components.PCheckBox;
import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimit;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.server.dao.CodedListDAO;
import com.nobbysoft.first.server.dao.DAOI;
import com.nobbysoft.first.server.dao.RaceDAO;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class RacePanel extends AbstractDataPanel<Race,String> implements MaintenancePanelInterface<Race> {
 
	public RacePanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 

	private final PCodeField txtRaceId = new PCodeField();  
	private final PTextField txtName = new PTextField(128);
	private final PCheckBox cbxHasMagicDefenceBonus = new PCheckBox("Has magic defence bonus?");
	
	private final PCheckBox cbxMultiClassable = new PCheckBox("Can multi class?");
	
	

	private final PComboBox<CodedListItem<Integer>> txtMinMaleStr = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinMaleInt = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinMaleWis = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinMaleDex = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinMaleCon = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinMaleChr = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinFemaleStr = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinFemaleInt = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinFemaleWis = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinFemaleDex = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinFemaleCon = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMinFemaleChr = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxMaleStr = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxMaleInt = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxMaleWis = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxMaleDex = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxMaleCon = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxMaleChr = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxFemaleStr = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxFemaleInt = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxFemaleWis = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxFemaleDex = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxFemaleCon = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxFemaleChr = new PComboBox<>();

	private final PComboBox<CodedListItem<Integer>> txtBonusStr = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtBonusInt = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtBonusWis = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtBonusDex = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtBonusCon = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtBonusChr = new PComboBox<>(); 

	private PComboBox<CodedListItem<Integer>>[] attBonusCombos = new PComboBox[] {
			txtBonusStr,
			txtBonusInt,
			txtBonusWis,
			txtBonusDex,
			txtBonusCon,
			txtBonusChr		
		};
	
	private PComboBox<CodedListItem<Integer>>[] attCombos = new PComboBox[] {
			txtMinMaleStr	,
			txtMaxMaleStr	,
			txtMinFemaleStr	,
			txtMaxFemaleStr	,
			txtBonusStr	,
			txtMinMaleInt	,
			txtMaxMaleInt	,
			txtMinFemaleInt	,
			txtMaxFemaleInt	,
			txtBonusInt	,
			txtMinMaleWis	,
			txtMaxMaleWis	,
			txtMinFemaleWis	,
			txtMaxFemaleWis	,
			txtBonusWis	,
			txtMinMaleDex	,
			txtMaxMaleDex	,
			txtMinFemaleDex	,
			txtMaxFemaleDex	,
			txtBonusDex	,
			txtMinMaleCon	,
			txtMaxMaleCon	,
			txtMinFemaleCon	,
			txtMaxFemaleCon	,
			txtBonusCon	,
			txtMinMaleChr	,
			txtMaxMaleChr	,
			txtMinFemaleChr	,
			txtMaxFemaleChr	,
			txtBonusChr	
	
	};
	private String[] attComboNames = new String[] {
			"Str",
			"Int",
			"Wis",
			"Dex",
			"Con",
			"Chr",		
	};
	private String[] attComboHeader = new String[] {
			"Male Min",
			"Male Max",
			"Fem Min",
			"Fem Max",
			"Bonus"	
	};
	
	private String[] attComboLabels = new String[] {
			"Minimum Male Str",
			"Minimum Male Int",
			"Minimum Male Wis",
			"Minimum Male Dex",
			"Minimum Male Con",
			"Minimum Male Chr",
			"Maximum Male Str",
			"Maximum Male Int",
			"Maximum Male Wis",
			"Maximum Male Dex",
			"Maximum Male Con",
			"Maximum Male Chr",
			"Minimum Female Str",
			"Minimum Female Int",
			"Minimum Female Wis",
			"Minimum Female Dex",
			"Minimum Female Con",
			"Minimum Female Chr",
			"Maximum Female Str",
			"Maximum Female Int",
			"Maximum Female Wis",
			"Maximum Female Dex",
			"Maximum Female Con",
			"Maximum Female Chr",
			"Bonus Str",
			"Bonus Int",
			"Bonus Wis",
			"Bonus Dex",
			"Bonus Con",
			"Bonus Chr"	
		};

	private PDataComponent[] dataComponents = new PDataComponent[] {  txtName, cbxHasMagicDefenceBonus,cbxMultiClassable,
			txtMinMaleStr,
			txtMinMaleInt,
			txtMinMaleWis,
			txtMinMaleDex,
			txtMinMaleCon,
			txtMinMaleChr,
			txtMaxMaleStr,
			txtMaxMaleInt,
			txtMaxMaleWis,
			txtMaxMaleDex,
			txtMaxMaleCon,
			txtMaxMaleChr,
			txtMinFemaleStr,
			txtMinFemaleInt,
			txtMinFemaleWis,
			txtMinFemaleDex,
			txtMinFemaleCon,
			txtMinFemaleChr,
			txtMaxFemaleStr,
			txtMaxFemaleInt,
			txtMaxFemaleWis,
			txtMaxFemaleDex,
			txtMaxFemaleCon,
			txtMaxFemaleChr,
			txtBonusStr,
			txtBonusInt,
			txtBonusWis,
			txtBonusDex,
			txtBonusCon,
			txtBonusChr				
			 };
	private PDataComponent[] keyComponents = new PDataComponent[] { txtRaceId };
	private PDataComponent[] buttonComponents = new PDataComponent[] {  };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtRaceId,  
			txtName  };

  

	public void jbInit() {

		txtRaceId.setName("Race id");  
		txtName.setName("Race name");
		cbxHasMagicDefenceBonus.setName("Has magic defence bonus?");
		cbxMultiClassable.setName("Can multi class?");
		PLabel[] attLabels = new PLabel[30];
		for(int i=0,n=30;i<n;i++) {
			attCombos[i].setName(attComboLabels[i]);			
			attLabels[i]=new PLabel(attCombos[i].getName());
		}

		PLabel lblRaceId = new PLabel("Race id"); 
		PLabel lblRaceName = new PLabel("Race name"); 

		add(getLblInstructions(), GBU.text(0, 0, 2));
		add(lblRaceId, GBU.label(0, 1));
		add(txtRaceId, GBU.text(1, 1)); 

		add(lblRaceName, GBU.label(0, 4));
		add(txtName, GBU.text(1, 4));

		add(cbxHasMagicDefenceBonus, GBU.text(1, 5)); 
		add(cbxMultiClassable, GBU.text(1, 6));
		
		
		
		PPanel pnlAtts = new PPanel(new GridBagLayout());
		
		add(pnlAtts, GBU.panel(0, 7, 3, 3)); 
		
		// now we want FIVE columns and labels each with six things in
		int index =0;
		
		 
		for(int col=0;col<attComboHeader.length;col++) {			
			String label = attComboHeader[col];
			// will put first one in column 1 etc
			PLabel lbl = new PLabel(label);
			pnlAtts.add(lbl, GBU.button(col+1, 0));
		}
		
		for (int row = 0;row<6;row++) {
		
			PLabel lbl = new PLabel(attComboNames[row]);
			pnlAtts.add(lbl, GBU.label(0, row+1));
			for(int col=0;col<5;col++) {
				
				int x = index++;				
				pnlAtts.add(attCombos[x],GBU.text(col+1, row+1));
			}
		}
		
 

		add(new PLabel(""), GBU.label(99, 99));

	}
 
	// 	minimums must be less or equal to maximums
	//
	  ReturnValue<?> validateScreen() {

		return new ReturnValue<Object>("");
	}

	  void populateFromScreen(Race value, boolean includingKeys) {
		if (includingKeys) {
			value.setRaceId(txtRaceId.getText());
		} 
		value.setName(txtName.getText());
		value.setHasMagicDefenceBonus(cbxHasMagicDefenceBonus.isSelected());
		
		value.setMultiClassable(cbxMultiClassable.isSelected());
		
		value.setMinMaleStr((Integer)txtMinMaleStr.getSelectedCode());
		value.setMinMaleInt((Integer)txtMinMaleInt.getSelectedCode());
		value.setMinMaleWis((Integer)txtMinMaleWis.getSelectedCode());
		value.setMinMaleDex((Integer)txtMinMaleDex.getSelectedCode());
		value.setMinMaleCon((Integer)txtMinMaleCon.getSelectedCode());
		value.setMinMaleChr((Integer)txtMinMaleChr.getSelectedCode());
		value.setMinFemaleStr((Integer)txtMinFemaleStr.getSelectedCode());
		value.setMinFemaleInt((Integer)txtMinFemaleInt.getSelectedCode());
		value.setMinFemaleWis((Integer)txtMinFemaleWis.getSelectedCode());
		value.setMinFemaleDex((Integer)txtMinFemaleDex.getSelectedCode());
		value.setMinFemaleCon((Integer)txtMinFemaleCon.getSelectedCode());
		value.setMinFemaleChr((Integer)txtMinFemaleChr.getSelectedCode());
		value.setMaxMaleStr((Integer)txtMaxMaleStr.getSelectedCode());
		value.setMaxMaleInt((Integer)txtMaxMaleInt.getSelectedCode());
		value.setMaxMaleWis((Integer)txtMaxMaleWis.getSelectedCode());
		value.setMaxMaleDex((Integer)txtMaxMaleDex.getSelectedCode());
		value.setMaxMaleCon((Integer)txtMaxMaleCon.getSelectedCode());
		value.setMaxMaleChr((Integer)txtMaxMaleChr.getSelectedCode());
		value.setMaxFemaleStr((Integer)txtMaxFemaleStr.getSelectedCode());
		value.setMaxFemaleInt((Integer)txtMaxFemaleInt.getSelectedCode());
		value.setMaxFemaleWis((Integer)txtMaxFemaleWis.getSelectedCode());
		value.setMaxFemaleDex((Integer)txtMaxFemaleDex.getSelectedCode());
		value.setMaxFemaleCon((Integer)txtMaxFemaleCon.getSelectedCode());
		value.setMaxFemaleChr((Integer)txtMaxFemaleChr.getSelectedCode());
		value.setBonusStr((Integer)txtBonusStr.getSelectedCode());
		value.setBonusInt((Integer)txtBonusInt.getSelectedCode());
		value.setBonusWis((Integer)txtBonusWis.getSelectedCode());
		value.setBonusDex((Integer)txtBonusDex.getSelectedCode());
		value.setBonusCon((Integer)txtBonusCon.getSelectedCode());
		value.setBonusChr((Integer)txtBonusChr.getSelectedCode());

		
	}

	  void populateScreen(Race value){
		txtRaceId.setText(value.getRaceId());  
		cbxHasMagicDefenceBonus.setSelected(value.isHasMagicDefenceBonus()); 
		cbxMultiClassable.setSelected(value.isMultiClassable());
		txtName.setText(value.getName()); 
		
		txtMinMaleStr.setSelectedCode(value.getMinMaleStr());
		txtMinMaleInt.setSelectedCode(value.getMinMaleInt());
		txtMinMaleWis.setSelectedCode(value.getMinMaleWis());
		txtMinMaleDex.setSelectedCode(value.getMinMaleDex());
		txtMinMaleCon.setSelectedCode(value.getMinMaleCon());
		txtMinMaleChr.setSelectedCode(value.getMinMaleChr());
		txtMinFemaleStr.setSelectedCode(value.getMinFemaleStr());
		txtMinFemaleInt.setSelectedCode(value.getMinFemaleInt());
		txtMinFemaleWis.setSelectedCode(value.getMinFemaleWis());
		txtMinFemaleDex.setSelectedCode(value.getMinFemaleDex());
		txtMinFemaleCon.setSelectedCode(value.getMinFemaleCon());
		txtMinFemaleChr.setSelectedCode(value.getMinFemaleChr());
		txtMaxMaleStr.setSelectedCode(value.getMaxMaleStr());
		txtMaxMaleInt.setSelectedCode(value.getMaxMaleInt());
		txtMaxMaleWis.setSelectedCode(value.getMaxMaleWis());
		txtMaxMaleDex.setSelectedCode(value.getMaxMaleDex());
		txtMaxMaleCon.setSelectedCode(value.getMaxMaleCon());
		txtMaxMaleChr.setSelectedCode(value.getMaxMaleChr());
		txtMaxFemaleStr.setSelectedCode(value.getMaxFemaleStr());
		txtMaxFemaleInt.setSelectedCode(value.getMaxFemaleInt());
		txtMaxFemaleWis.setSelectedCode(value.getMaxFemaleWis());
		txtMaxFemaleDex.setSelectedCode(value.getMaxFemaleDex());
		txtMaxFemaleCon.setSelectedCode(value.getMaxFemaleCon());
		txtMaxFemaleChr.setSelectedCode(value.getMaxFemaleChr());
		txtBonusStr.setSelectedCode(value.getBonusStr());
		txtBonusInt.setSelectedCode(value.getBonusInt());
		txtBonusWis.setSelectedCode(value.getBonusWis());
		txtBonusDex.setSelectedCode(value.getBonusDex());
		txtBonusCon.setSelectedCode(value.getBonusCon());
		txtBonusChr.setSelectedCode(value.getBonusChr());

		
	}


	@Override
	PDataComponent[] getButtonComponents() { 
		return buttonComponents;
	}


	@Override
	DataServiceI<?, ?> getDataService() {   
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(Race.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+Race.class);
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
		for(PComboBox<CodedListItem<Integer>> c: attCombos) {		
			c.setList(cliDao.getAttRolls());
		}
		for(PComboBox<CodedListItem<Integer>> c: attBonusCombos) {
			c.setList(cliDao.getAttBonus());
		}
	}


	@Override
	Race newT() { 
		return new Race();
	}
	
 

 

}
