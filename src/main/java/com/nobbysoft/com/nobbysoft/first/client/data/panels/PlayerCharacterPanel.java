package com.nobbysoft.com.nobbysoft.first.client.data.panels;

import java.awt.GridBagLayout;
import java.awt.Window;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.sql.SQLException;

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
	private final PComboGender txtGender = new PComboGender();
	private final PComboAlignment txtAlignment = new PComboAlignment();
	
	private final PComboBox<CodedListItem<String>> txtRace = new PComboBox<>();
  
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

		txtPcId.setName("Id");  
		txtCharacterName.setName("Character name"); 
		txtPlayerName.setName("Player name");
		txtGender.setName("Gender");
		txtAlignment.setName("Alignment");
		
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
		PLabel lblAlignment = new PLabel(txtAlignment.getName());
 
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

		pnlLeft.add(lblGender, GBU.label(0,4));
		pnlLeft.add(txtGender, GBU.text(1, 4));

		pnlLeft.add(lblAlignment, GBU.label(0,5));
		pnlLeft.add(txtAlignment, GBU.text(1, 5));
		
		pnlLeft.add(btnRoll, GBU.button(1, 6));
		
		txtPcId.setEditable(false);
		
		btnRoll.addActionListener(ae ->roll());

		
		add(new PLabel(""), GBU.label(99, 99));

	}
 

	  ReturnValue<?> validateScreen() {
 
		return new ReturnValue<Object>("");
	}

	  void populateFromScreen(PlayerCharacter value, boolean includingKeys) {
		if (includingKeys) {
			value.setPcId(txtPcId.getIntegerValue());
		} 
		value.setCharacterName(txtCharacterName.getText()); 
		value.setPlayerName(txtPlayerName.getText());
		value.setGender(txtGender.getGender());
		value.setRaceId((String)txtRace.getSelectedCode());
		value.setAlignment(txtAlignment.getAlignment());

		value.setAttrStr((Integer)txtAttrStr.getIntegerValue());
		value.setAttrInt((Integer)txtAttrInt.getIntegerValue());
		value.setAttrWis((Integer)txtAttrWis.getIntegerValue());
		value.setAttrDex((Integer)txtAttrDex.getIntegerValue());
		value.setAttrCon((Integer)txtAttrCon.getIntegerValue());
		value.setAttrChr((Integer)txtAttrChr.getIntegerValue());

 
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
		txtRace.setSelectedCode(value.getRaceId());
		txtAlignment.setAlignment(value.getAlignment());
		
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

		//CodedListService cliDao = (CodedListService)DataMapper.INSTANCE.getNonDataService(CodedListService.class);
 
		RaceService raceService = (RaceService)DataMapper.INSTANCE.getNonDataService(RaceService.class);
		 
		try {
			txtRace.setList(raceService.getAsCodedList());
		} catch (SQLException e) {
			LOGGER.error("Err getting races",e);
		}
		
	}


	@Override
	PlayerCharacter newT() { 
		return new PlayerCharacter();
	}
	
 
	public ReturnValue<?> initAdd(String instructions){
 
		ReturnValue<?> rv= super.initAdd(instructions);
		if(!rv.isError()) {
			
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
			cr.getClass();
		}
	}
	
}
