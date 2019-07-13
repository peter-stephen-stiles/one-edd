package com.nobbysoft.com.nobbysoft.first.client.data.panels;

import java.awt.GridBagLayout;
import java.awt.Window;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.com.nobbysoft.first.client.components.special.PComboAlignment;
import com.nobbysoft.com.nobbysoft.first.client.components.special.PComboGender;
import com.nobbysoft.com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.com.nobbysoft.first.common.servicei.PlayerCharacterService;
import com.nobbysoft.com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class PlayerCharacterPanel extends AbstractDataPanel<PlayerCharacter,Integer> implements MaintenancePanelInterface<PlayerCharacter> {
 

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public PlayerCharacterPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	private final PIntegerField txtPcId = new PIntegerField(true);  
	private final PTextField txtCharacterName = new PTextField(128);
	private final PTextField txtPlayerName = new PTextField(128);
	private final PComboAlignment txtAlignment = new PComboAlignment();

	
	private final CharacterClass noClass = new CharacterClass();

	private final PComboBox<CharacterClass> txtClass1 = new PComboBox<>();
	private final PComboBox<CharacterClass> txtClass2 = new PComboBox<>();
	private final PComboBox<CharacterClass> txtClass3 = new PComboBox<>();
 
	private final PComboBox[] classCombos = new PComboBox[]{txtClass1,txtClass2,txtClass3};
	
 
	private final PComboGender txtGender = new PComboGender();

	private final PLabel lblClass1 = new PLabel();
	private final PLabel lblClass2 = new PLabel();
	private final PLabel lblClass3 = new PLabel();

	private final PLabel[] classLabels = new PLabel[]{lblClass1,lblClass2,lblClass3};
	private final PComboBox<Race> txtRace = new PComboBox<>();
  
	private final PIntegerCombo txtAttrStr = new PIntegerCombo();
	private final PIntegerCombo txtAttrInt = new PIntegerCombo();
	private final PIntegerCombo txtAttrWis = new PIntegerCombo();
	private final PIntegerCombo txtAttrDex = new PIntegerCombo();
	private final PIntegerCombo txtAttrCon = new PIntegerCombo();
	private final PIntegerCombo txtAttrChr = new PIntegerCombo(); 
	
	private PButton btnRoll = new PButton("Roll");
 
	
	private final PIntegerCombo[] attCombos = new PIntegerCombo[] {
		txtAttrStr,txtAttrInt,txtAttrWis,txtAttrDex,txtAttrCon,txtAttrChr
	};
	 
	
	private PDataComponent[] dataComponents = new PDataComponent[] {  txtCharacterName,txtPlayerName,
			txtAttrStr,txtAttrInt,txtAttrWis,txtAttrDex,txtAttrCon,txtAttrChr,txtAlignment
			 };
	private PDataComponent[] keyComponents = new PDataComponent[] { btnRoll};//txtPcId };
	private PDataComponent[] buttonComponents = new PDataComponent[] {  };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { 
			txtPcId,  
			txtCharacterName };

  

	public void jbInit() {

		noClass.setClassId("");
		noClass.setName("");
		
		txtPcId.setName("Id");  
		txtCharacterName.setName("Character name"); 
		txtPlayerName.setName("Player name");
		txtGender.setName("Gender");
		txtRace.setName("Race");
		txtAlignment.setName("Alignment");
		txtClass1.setName("Class (1)");
		txtClass2.setName("Class (2)");
		txtClass3.setName("Class (3)");
		
		txtAttrStr.setName("Str");
		txtAttrInt.setName("Int");
		txtAttrWis.setName("Wis");
		txtAttrDex.setName("Dex");
		txtAttrCon.setName("Con");
		txtAttrChr.setName("Chr"); 
 
		PLabel lblPcId = new PLabel(txtPcId.getName()); 
		PLabel lblPlayerCharacterName = new PLabel(txtCharacterName.getName()); 
		PLabel lblPlayerName = new PLabel(txtPlayerName.getName()); 
		PLabel lblGender = new PLabel(txtGender.getName());
		PLabel lblRace = new PLabel(txtRace.getName());
		PLabel lblAlignment = new PLabel(txtAlignment.getName());
		


		lblClass1.setText(txtClass1.getName());
		lblClass2.setText(txtClass2.getName());
		lblClass3.setText(txtClass3.getName());
		
		PLabel lblMinStr = new PLabel(txtAttrStr.getName());
		PLabel lblMinInt = new PLabel(txtAttrInt.getName());
		PLabel lblMinWis = new PLabel(txtAttrWis.getName());
		PLabel lblMinDex = new PLabel(txtAttrDex.getName());
		PLabel lblMinCon = new PLabel(txtAttrCon.getName());
		PLabel lblMinChr = new PLabel(txtAttrChr.getName());

 
		
		PPanel pnlLeft = new PPanel(new GridBagLayout());
		PPanel pnlRight = new PPanel(new GridBagLayout());
		
		pnlRight.add(lblMinStr, GBU.label(0, 1));
		pnlRight.add(txtAttrStr, GBU.text(1, 1)); 
		pnlRight.add(lblMinInt, GBU.label(0, 2));
		pnlRight.add(txtAttrInt, GBU.text(1, 2)); 
		pnlRight.add(lblMinWis, GBU.label(0, 3));
		pnlRight.add(txtAttrWis, GBU.text(1, 3)); 
		pnlRight.add(lblMinDex, GBU.label(0, 4));
		pnlRight.add(txtAttrDex, GBU.text(1, 4)); 
		pnlRight.add(lblMinCon, GBU.label(0, 5));
		pnlRight.add(txtAttrCon, GBU.text(1, 5)); 
		pnlRight.add(lblMinChr, GBU.label(0, 6));
		pnlRight.add(txtAttrChr, GBU.text(1, 6)); 
		
 

		add(getLblInstructions(), GBU.text(0, 0, 2));
		add(pnlLeft,GBU.panel(0, 1));
		add(pnlRight,GBU.panel(1, 1));
		
		pnlLeft.add(lblPcId, GBU.label(0, 1));
		pnlLeft.add(txtPcId, GBU.text(1, 1)); 

		pnlLeft.add(lblPlayerCharacterName, GBU.label(0, 2));
		pnlLeft.add(txtCharacterName, GBU.text(1, 2));

		pnlLeft.add(lblPlayerName, GBU.label(0, 3));
		pnlLeft.add(txtPlayerName, GBU.text(1, 3));


		pnlLeft.add(lblAlignment, GBU.label(0,5));
		pnlLeft.add(txtAlignment, GBU.text(1, 5));
		
		pnlLeft.add(btnRoll, GBU.button(1, 4));

		pnlLeft.add(lblGender, GBU.label(0,6));
		pnlLeft.add(txtGender, GBU.text(1, 6));
		
		pnlLeft.add(lblRace, GBU.label(0,7));
		pnlLeft.add(txtRace, GBU.text(1, 7));


		pnlLeft.add(lblClass1, GBU.label(0,8));
		pnlLeft.add(lblClass2, GBU.label(0,9));
		pnlLeft.add(lblClass3, GBU.label(0,10));
		
		pnlLeft.add(txtClass1, GBU.text(1, 8));
		pnlLeft.add(txtClass2, GBU.text(1, 9));
		pnlLeft.add(txtClass3, GBU.text(1, 10));
		
		
		txtPcId.setEditable(false);
		txtClass1.setReadOnly(true);
		txtClass2.setReadOnly(true);
		txtClass3.setReadOnly(true);

		
		btnRoll.addActionListener(ae ->roll());

		
		add(new PLabel(""), GBU.label(99, 99));

	}
 

	  ReturnValue<?> validateScreen() {
 
		  if(!txtClass1.isVisible()) {
			  new ReturnValue<Object>(true,"You must roll and select a character class!");
		  }
		  
		return new ReturnValue<Object>("");
	}

	  void populateFromScreen(PlayerCharacter value, boolean includingKeys) {
		if (includingKeys) {
			value.setPcId(txtPcId.getIntegerValue());
		} 
		value.setCharacterName(txtCharacterName.getText()); 
		value.setPlayerName(txtPlayerName.getText());
		value.setGender(txtGender.getGender());
		value.setRaceId(((Race)txtRace.getSelectedItem()).getRaceId());
		CharacterClass c1 = ((CharacterClass)txtClass1.getSelectedItem());
		CharacterClass c2 = ((CharacterClass)txtClass2.getSelectedItem());
		CharacterClass c3 = ((CharacterClass)txtClass3.getSelectedItem());
		
		value.setFirstClass(c1.getClassId()); 
		LOGGER.info("first class "+value.getFirstClass() + " c1:"+c1.getClassId());
		if(!c2.equals(noClass)) {
			value.setSecondClass(c2.getClassId());
		} else {
			value.setSecondClass(null);
		}

		if(!c3.equals(noClass)) {
			value.setThirdClass(c3.getClassId());
		} else {
			value.setThirdClass(null);
		}
		value.setAlignment(txtAlignment.getAlignment());

		value.setAttrStr((Integer)txtAttrStr.getIntegerValue());
		value.setAttrInt((Integer)txtAttrInt.getIntegerValue());
		value.setAttrWis((Integer)txtAttrWis.getIntegerValue());
		value.setAttrDex((Integer)txtAttrDex.getIntegerValue());
		value.setAttrCon((Integer)txtAttrCon.getIntegerValue());
		value.setAttrChr((Integer)txtAttrChr.getIntegerValue());

 
	}


	  private void selectRace(PComboBox<Race> combo, String raceId) {
		  combo.setSelectedIndex(0);
		  if(raceId!=null) {
			  for(int i=0,n=combo.getItemCount();i<n;i++) {
				  Race c = combo.getItemAt(i);
				  if(c.getRaceId().contentEquals(raceId)) {
					  combo.setSelectedIndex(i);
					  break;
				  }
			  }
		  }
	  }
	  private void selectClass(PComboBox<CharacterClass> combo, String classId) {
		  combo.setSelectedIndex(0);
		  if(classId!=null) {
			  for(int i=0,n=combo.getItemCount();i<n;i++) {
				  CharacterClass c = combo.getItemAt(i);
				  if(c.getClassId().contentEquals(classId)) {
					  combo.setSelectedIndex(i);
					  break;
				  }
			  }
		  }
	  }
	  
	  void populateScreen(PlayerCharacter value){
		  int cid = value.getPcId();
		  if(cid<=0) {
			 PlayerCharacterService ccs= (PlayerCharacterService) getDataService();
			 try {
				cid = ccs.getNextId();
			} catch (SQLException e) {
				LOGGER.error("Error getting sequence",e);
			}
			 
		  }
		txtPcId.setIntegerValue(cid);  
		txtCharacterName.setText(value.getCharacterName());  
		txtPlayerName.setText(value.getPlayerName());
		txtGender.setGender(value.getGender());
		selectRace(txtRace,value.getRaceId());
		txtAlignment.setAlignment(value.getAlignment());

		selectClass(txtClass1,value.getFirstClass());
		selectClass(txtClass2,value.getSecondClass());
		selectClass(txtClass3,value.getThirdClass());
		
		txtAttrStr.setIntegerValue(value.getAttrStr());
		txtAttrInt.setIntegerValue(value.getAttrInt());
		txtAttrWis.setIntegerValue(value.getAttrWis());
		txtAttrDex.setIntegerValue(value.getAttrDex());
		txtAttrCon.setIntegerValue(value.getAttrCon());
		txtAttrChr.setIntegerValue(value.getAttrChr()); 
 }


	@Override
	PDataComponent[] getButtonComponents() { 
		return buttonComponents;
	}


	@Override
	DataServiceI<?, ?> getDataService() {
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(PlayerCharacter.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+PlayerCharacter.class);
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

		//CodedListService cliDao = (CodedListService)DataMapper.INSTANCE.getDataService(CodedListService.class);
 
		RaceService raceService = (RaceService)DataMapper.INSTANCE.getNonDataService(RaceService.class);
 
		try {
			txtRace.setList(raceService.getList());
		} catch (SQLException e) {
			LOGGER.error("Err getting races",e);
		}
		
		CharacterClassService characterClassService = (CharacterClassService)DataMapper.INSTANCE.getNonDataService(CharacterClassService.class);
		
		try {
			
			List<CharacterClass> classes = characterClassService.getList();
			
	 
			
			for(int i=0,n=3;i<n;i++){
				List<CharacterClass> useMe = new ArrayList<>();
				useMe.add(noClass);
				useMe.addAll(classes);
				classCombos[i].setList(useMe);
				classCombos[i].setSelectedItem(noClass);
			}
			
		} catch (SQLException e) {
			LOGGER.error("Err getting races",e);
		}
		
	}


	@Override
	PlayerCharacter newT() { 
		return new PlayerCharacter();
	}
	
	 
	public ReturnValue<?> initCopy(PlayerCharacter pc,String instructions){ 
			ReturnValue<?> rv= super.initCopy(pc,instructions);
			return addOrCopy(rv);
	}
	public ReturnValue<?> initAdd(String instructions){ 
		ReturnValue<?> rv= super.initAdd(instructions);
		return addOrCopy(rv);
	}


	private ReturnValue<?> addOrCopy(ReturnValue<?> rv) {
		if(!rv.isError()) {
			
			for(int i=0,n=3;i<n;i++){
				classCombos[i].setVisible(false); 
			}
			
			  {
					 PlayerCharacterService ccs= (PlayerCharacterService) getDataService();
					 try {
					int	cid = ccs.getNextId();
					txtPcId.setIntegerValue(cid);
					} catch (SQLException e) {
						LOGGER.error("Error getting sequence",e);
						rv= new ReturnValue(true,"Error getting sequence");
					}
					 
				  }
			
		}
		return rv;
	}
 

	private void roll() {
		Window w= GuiUtils.getParent(this) ;
		CharacterRoller cr = new CharacterRoller(w,"Roll new character");
		cr.pack();		
		cr.setLocationRelativeTo(null);
		cr.setVisible(true);
		if(!cr.isCancelled()) {
			txtGender.setGender(cr.getGender());
			txtRace.setSelectedItem(cr.getRace());
			int[] atts = cr.getAtts();
			for(int i=0,n=attCombos.length;i<n;i++) {
				attCombos[i].setIntegerValue(atts[i]);
			}
			List<CharacterClass> classes = cr.getCharacterClasses();
			for(int i=0,n=3;i<n;i++){
				classCombos[i].setVisible(false); 
				classCombos[i].setSelectedItem(noClass);
			}
			for(int i=0,n=classes.size();i<n;i++){
				classCombos[i].setVisible(true); 
				classCombos[i].setSelectedItem(classes.get(i)); 
			}
			
		}
	}
	
}
