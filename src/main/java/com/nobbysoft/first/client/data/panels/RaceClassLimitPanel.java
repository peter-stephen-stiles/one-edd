package com.nobbysoft.first.client.data.panels;

import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.List;

import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCheckBox;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimit;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimitKey;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class RaceClassLimitPanel extends AbstractDataPanel<RaceClassLimit,RaceClassLimitKey> implements MaintenancePanelInterface<RaceClassLimit> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public RaceClassLimitPanel() { 
		setLayout(new GridBagLayout());
		jbInit();
	}


	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 

	private final PComboBox<CodedListItem<String>> txtRaceId = new PComboBox<>();
	private final PComboBox<CodedListItem<String>> txtClassId = new PComboBox<>();


	private final PComboBox<CodedListItem<Integer>> txtMaxLevel = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxLevelPrEq17 = new PComboBox<>();
	private final PComboBox<CodedListItem<Integer>> txtMaxLevelPrLt17 = new PComboBox<>();
	
	private final PComboBox<CodedListItem<Integer>> txtlimitingAttribute = new PComboBox<>();
	
	//private final PIntegerField txtMaxLevel = new PIntegerField();
	private final PTextArea txtLimitingFactors = new PTextArea(256); 
	private final PCheckBox cbxNpcClassOnly = new PCheckBox("NPC only?");
	
	private PDataComponent[] dataComponents = new PDataComponent[] {  txtMaxLevel,txtLimitingFactors,
			cbxNpcClassOnly,txtMaxLevelPrEq17,txtMaxLevelPrLt17,txtlimitingAttribute
	 };
private PDataComponent[] keyComponents = new PDataComponent[] { txtRaceId, txtClassId};
private PDataComponent[] buttonComponents = new PDataComponent[] {  };

private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtRaceId, txtClassId,txtMaxLevel};

	
	@Override
	PDataComponent[] getButtonComponents() {
		return buttonComponents;
	}

	@Override
	DataServiceI<?, ?> getDataService() {   
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(RaceClassLimit.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+RaceClassLimit.class);
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
	void jbInit() { 

		txtRaceId.setName("Race id");  
		txtClassId.setName("Character class id"); 
		txtMaxLevel.setName("Maximum level");
		txtMaxLevelPrEq17.setName("Maximum level if PR=17");
		txtMaxLevelPrLt17.setName("Maximum level if Pr<17");
		txtLimitingFactors.setName("Level limiting factors");
		cbxNpcClassOnly.setName(cbxNpcClassOnly.getText());
		txtlimitingAttribute.setName("Limiting attribute");
		

		PLabel lblRaceId = new PLabel(txtRaceId.getName()); 
		PLabel lblClassId = new PLabel(txtClassId.getName());  
		PLabel lblMaxLevel = new PLabel(txtMaxLevel.getName()); 
		PLabel lblMaxLevelPrEq17 = new PLabel(txtMaxLevelPrEq17.getName()); 
		PLabel lblMaxLevelPrLt17 = new PLabel(txtMaxLevelPrLt17.getName()); 
		PLabel lblLimitingFactors = new PLabel(txtLimitingFactors.getName()); 
		PLabel lbllimitingAttribute = new PLabel(txtlimitingAttribute.getName());
		
		
		int row=0;
		
		add(getLblInstructions(), GBU.text(0, row, 2));
		
		row++;
		add(lblRaceId, GBU.label(0, row));
		add(txtRaceId, GBU.text(1, row)); 
		row++;
		add(lblClassId, GBU.label(0, row));
		add(txtClassId, GBU.text(1, row)); 
		row++;
		add(cbxNpcClassOnly, GBU.text(1, row));
		
		row++;
		add(lblMaxLevel, GBU.label(0, row));
		add(txtMaxLevel, GBU.text(1, row));
		row++;
		add(lblMaxLevelPrEq17, GBU.label(0, row));
		add(txtMaxLevelPrEq17, GBU.text(1, row));
		row++;
		add(lblMaxLevelPrLt17, GBU.label(0, row));
		add(txtMaxLevelPrLt17, GBU.text(1, row));  
		row++;
		add(lbllimitingAttribute, GBU.label(0, row));
		add(txtlimitingAttribute, GBU.text(1, row));  
		row++;
		add(lblLimitingFactors, GBU.label(0, row));

		JScrollPane sclLimitingFactors = new JScrollPane(txtLimitingFactors);
		add(sclLimitingFactors, GBU.panel(1, row, 1, 2));

		row++;
		add(new PLabel(""), GBU.label(0, row));
		row++;
		
		add(new PLabel(""), GBU.label(99, 99));
	}

	@Override
	void populateCombos() { 

		CodedListService cliDao = (CodedListService)DataMapper.INSTANCE.getNonDataService(CodedListService.class);
		for(CodedListItem cli: cliDao.getRaceClassLimitMaxLevelList()) {
			txtMaxLevel.addItem(cli);
			txtMaxLevelPrEq17.addItem(cli);
			txtMaxLevelPrLt17.addItem(cli);
		}
		
		 
		{
			
		 
			txtlimitingAttribute.setList(cliDao.getAttributesWithZero());
			
		}
		 {
				
			try {

				CharacterClassService dao = (CharacterClassService)DataMapper.INSTANCE.getDataService(CharacterClass.class);
				{
					List<CodedListItem<String>> list = dao.getAsCodedList();

					for (CodedListItem<String> cli : list) {
						LOGGER.info("spell class " + cli);
						txtClassId.addItem(cli);
					}

				}
			} catch (Exception ex) {
				Popper.popError(GuiUtils.getParent(this), ex);
			}
			
			try {
 
				RaceService dao = (RaceService)DataMapper.INSTANCE.getDataService(Race.class);
				  {
					List<CodedListItem<String>> list = dao.getAsCodedList();

					for (CodedListItem<String> cli : list) {
						//LOGGER.info("spell class " + cli);
						txtRaceId.addItem(cli);
					}

				}
			} catch (Exception ex) {
				Popper.popError(GuiUtils.getParent(this), ex);
			}
			
		}
		
	}

	@Override
	void populateFromScreen(RaceClassLimit value, boolean includingKeys) { 
		if (includingKeys) {
			value.setClassId((String)txtClassId.getSelectedCode());
			value.setRaceId((String)txtRaceId.getSelectedCode());
		} 
		value.setLimitingFactors(txtLimitingFactors.getText());
		value.setMaxLevel((Integer)txtMaxLevel.getSelectedCode());
		value.setMaxLevelPrEq17((Integer)txtMaxLevelPrEq17.getSelectedCode());
		value.setMaxLevelPrLt17((Integer)txtMaxLevelPrLt17.getSelectedCode());
		value.setNpcClassOnly(cbxNpcClassOnly.isSelected());
		value.setLimitingAttribute((Integer)txtlimitingAttribute.getSelectedCode());
	}

	@Override
	void populateScreen(RaceClassLimit value) { 
		

		txtClassId.setSelectedCode(value.getClassId());
		txtRaceId.setSelectedCode(value.getRaceId());
		txtLimitingFactors.setText(value.getLimitingFactors());
		txtMaxLevel.setSelectedCode(value.getMaxLevel());
		txtMaxLevelPrEq17.setSelectedCode(value.getMaxLevelPrEq17());
		txtMaxLevelPrLt17.setSelectedCode(value.getMaxLevelPrLt17());
		cbxNpcClassOnly.setSelected(value.isNpcClassOnly());
		txtlimitingAttribute.setSelectedCode(value.getLimitingAttribute());
	}

	@Override
	ReturnValue<?> validateScreen() {
		int ml=((Integer)txtMaxLevel.getSelectedCode());
		int mlPrEq17=((Integer)txtMaxLevelPrEq17.getSelectedCode());
		int mlPrLt17=((Integer)txtMaxLevelPrLt17.getSelectedCode());
		LOGGER.info("ML:"+ml+" ML=17:"+mlPrEq17+" ML<17:"+mlPrLt17);
		if(ml<mlPrEq17) {
			LOGGER.info("BAD ML:"+ml+" ML=17:"+mlPrEq17+" ML<17:"+mlPrLt17);
			return new ReturnValue<Object>(true,"Max levels illogical, must be equal or get smaller.");
		}
		if(ml<mlPrLt17) {
			LOGGER.info("BAD ML:"+ml+" ML=17:"+mlPrEq17+" ML<17:"+mlPrLt17);
			return new ReturnValue<Object>(true,"Max levels illogical, must be equal or get smaller.");
		}
		if(mlPrLt17>mlPrEq17) {
			LOGGER.info("BAD ML:"+ml+" ML=17:"+mlPrEq17+" ML<17:"+mlPrLt17);
			return new ReturnValue<Object>(true,"Max levels illogical, must be equal or get smaller.");
		}
		return new ReturnValue<Object>("");
	}

	@Override
	RaceClassLimit newT() {
		return new RaceClassLimit();
	}

}
