package com.nobbysoft.first.client.data.panels.staticdata;

import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.RaceThiefAbilityBonus;
import com.nobbysoft.first.common.entities.staticdto.RaceThiefAbilityBonusKey;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbilityType;
import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.first.common.servicei.ThiefAbilityTypeService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class RaceThiefAbilityBonusPanel extends AbstractDataPanel<RaceThiefAbilityBonus,RaceThiefAbilityBonusKey> implements MaintenancePanelInterface<RaceThiefAbilityBonus> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public RaceThiefAbilityBonusPanel() { 
		setLayout(new GridBagLayout());
		jbInit();
	}


	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 

	private final PComboBox<CodedListItem<String>> txtRaceId = new PComboBox<>();
	private final PComboBox<CodedListItem<String>> txtThiefAbilityType = new PComboBox<>();


	private final PIntegerCombo txtPercentageBonus = new PIntegerCombo(-50,50,5); 
	  
	
	private PDataComponent[] dataComponents = new PDataComponent[] {  txtPercentageBonus 
	 };
private PDataComponent[] keyComponents = new PDataComponent[] { txtRaceId, txtThiefAbilityType};
private PDataComponent[] buttonComponents = new PDataComponent[] {  };

private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtRaceId, txtThiefAbilityType,txtPercentageBonus};

	
	@Override
	protected PDataComponent[] getButtonComponents() {
		return buttonComponents;
	}

	@Override
	protected DataServiceI<?, ?> getDataService() {   
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(RaceThiefAbilityBonus.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+RaceThiefAbilityBonus.class);
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
	protected void jbInit() { 

		txtRaceId.setName("Race id");  
		txtThiefAbilityType.setName("Thief ability type"); 
		txtPercentageBonus.setName("Percentage bonus");
 
		

		PLabel lblRaceId = new PLabel(txtRaceId.getName()); 
		PLabel lblThiefAbilityType = new PLabel(txtThiefAbilityType.getName());  
		PLabel lblPercentageBonus = new PLabel(txtPercentageBonus.getName());   
		
		
		int row=0;
		
		add(getLblInstructions(), GBU.text(0, row, 2));
		
		row++;
		add(lblRaceId, GBU.label(0, row));
		add(txtRaceId, GBU.text(1, row)); 
		row++;
		add(lblThiefAbilityType, GBU.label(0, row));
		add(txtThiefAbilityType, GBU.text(1, row)); 
		row++; 
		 
		add(lblPercentageBonus, GBU.label(0, row));
		add(txtPercentageBonus, GBU.text(1, row));
   
		row++;
		
		add(new PLabel(""), GBU.label(99, 99));
	}

	@Override
	protected void populateCombos() { 

		CodedListService cliDao = (CodedListService)DataMapper.INSTANCE.getNonDataService(CodedListService.class);
 
		
 
		 {
				
			try {

				ThiefAbilityTypeService dao = (ThiefAbilityTypeService)DataMapper.INSTANCE.getDataService(ThiefAbilityType.class);
				{
					List<CodedListItem<String>> list = dao.getAsCodedList();

					for (CodedListItem<String> cli : list) {
						LOGGER.info("TAT class " + cli);
						txtThiefAbilityType.addItem(cli);
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
	protected void populateFromScreen(RaceThiefAbilityBonus value, boolean includingKeys) { 
		if (includingKeys) {
			value.setThiefAbilityType((String)txtThiefAbilityType.getSelectedCode());
			value.setRaceId((String)txtRaceId.getSelectedCode());
		}  
		value.setPercentageBonus((Integer)txtPercentageBonus.getSelectedCode());
 
	}

	@Override
	protected void populateScreen(RaceThiefAbilityBonus value) { 
		

		txtThiefAbilityType.setSelectedCode(value.getThiefAbilityType());
		txtRaceId.setSelectedCode(value.getRaceId()); 
		txtPercentageBonus.setSelectedCode(value.getPercentageBonus());
 
	}

	@Override
	protected ReturnValue<?> validateScreen() {

		return new ReturnValue<Object>("");
	}

	@Override
	protected RaceThiefAbilityBonus newT() {
		return new RaceThiefAbilityBonus();
	}

}
