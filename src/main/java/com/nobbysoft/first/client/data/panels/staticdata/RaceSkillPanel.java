package com.nobbysoft.first.client.data.panels.staticdata;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.List;

import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.RaceSkill;
import com.nobbysoft.first.common.entities.staticdto.RaceSkillKey;
import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class RaceSkillPanel extends AbstractDataPanel<RaceSkill, RaceSkillKey>
		implements MaintenancePanelInterface<RaceSkill> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public RaceSkillPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	private PDialog parentd;

	public void setParentDialog(PDialog parentd) {
		this.parentd = parentd;
	}

	private final PComboBox<CodedListItem<String>> txtRaceId = new PComboBox<>();
	private final PCodeField txtSkillId = new PCodeField();

	private final PTextField txtSkillName = new PTextField(60);
	private final PTextArea txtSkillDefinition = new PTextArea(512);

	private PDataComponent[] dataComponents = new PDataComponent[] { txtSkillName, txtSkillDefinition, };
	private PDataComponent[] keyComponents = new PDataComponent[] { txtRaceId, txtSkillId };
	private PDataComponent[] buttonComponents = new PDataComponent[] {};

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtRaceId, txtSkillId, txtSkillName,
			txtSkillDefinition };

	@Override
	protected PDataComponent[] getButtonComponents() {
		return buttonComponents;
	}

	@Override
	protected DataServiceI<?, ?> getDataService() {
		DataServiceI dao;
		try {
			Class d = DataMapper.INSTANCE.getServiceForEntity(RaceSkill.class);
			Constructor<DataServiceI> cc = d.getConstructor();
			dao = (DataServiceI) cc.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Can't get Service for " + RaceSkill.class);
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
		txtSkillId.setName("Skill id");
		txtSkillName.setName("Skill Name");
		txtSkillDefinition.setName("Skill Definition");

		PLabel lblRaceId = new PLabel(txtRaceId.getName());
		PLabel lblSkillId = new PLabel(txtSkillId.getName());
		PLabel lblMaxLevel = new PLabel(txtSkillName.getName());
		PLabel lblMaxLevelPrEq17 = new PLabel(txtSkillDefinition.getName());

		int row = 0;

		add(getLblInstructions(), GBU.text(0, row, 2));

		row++;
		add(lblRaceId, GBU.label(0, row));
		add(txtRaceId, GBU.text(1, row));
		row++;
		add(lblSkillId, GBU.label(0, row));
		add(txtSkillId, GBU.text(1, row));

		row++;
		add(lblMaxLevel, GBU.label(0, row));
		add(txtSkillName, GBU.text(1, row));
		row++;
		add(lblMaxLevelPrEq17, GBU.label(0, row));		
		JScrollPane sclSkillDefinition = new JScrollPane(txtSkillDefinition){
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				if(d.getWidth()< 300) {
					d.width=300;
				}
				if(d.getWidth()>1000) {
					d.width=1000;
				}
				if(d.getHeight()<100){
					d.height=100;
				}
				if(d.getHeight()>300){
					d.height=300;
				}
				return d;
			}
		};
		add(sclSkillDefinition, GBU.panel(1, row, 2, 2));

		row++;
		add(new PLabel(""), GBU.label(0, row));
		row++;

		add(new PLabel(""), GBU.label(99, 99));
	}

	@Override
	protected void populateCombos() {

		CodedListService cliDao = (CodedListService) DataMapper.INSTANCE.getNonDataService(CodedListService.class);
//		for(CodedListItem cli: cliDao.getRaceSkillMaxLevelList()) {
//			txtMaxLevel.addItem(cli);
//			txtMaxLevelPrEq17.addItem(cli);
//			txtMaxLevelPrLt17.addItem(cli);
//		}

//		 
//		{
//			
//		 
//			txtlimitingAttribute.setList(cliDao.getAttributesWithZero());
//			
//		}
		{

			try {

				RaceService dao = (RaceService) DataMapper.INSTANCE.getDataService(Race.class);
				{
					List<CodedListItem<String>> list = dao.getAsCodedList();

					for (CodedListItem<String> cli : list) {
						// LOGGER.info("spell class " + cli);
						txtRaceId.addItem(cli);
					}

				}
			} catch (Exception ex) {
				Popper.popError(GuiUtils.getParent(this), ex);
			}

		}

	}

	@Override
	protected void populateFromScreen(RaceSkill value, boolean includingKeys) {
		if (includingKeys) {
			value.setSkillId(txtSkillId.getText());
			value.setRaceId((String) txtRaceId.getSelectedCode());
		}
		value.setSkillName(txtSkillName.getText());
		value.setSkillDefinition(txtSkillDefinition.getText());
	}

	@Override
	protected void populateScreen(RaceSkill value) {

		txtSkillId.setText(value.getSkillId());
		txtRaceId.setSelectedCode(value.getRaceId());
		txtSkillName.setText(value.getSkillName());
		txtSkillDefinition.setText(value.getSkillDefinition());
	}

	@Override
	protected ReturnValue<?> validateScreen() {
//		int ml=((Integer)txtMaxLevel.getSelectedCode());
//		int mlPrEq17=((Integer)txtMaxLevelPrEq17.getSelectedCode());
//		int mlPrLt17=((Integer)txtMaxLevelPrLt17.getSelectedCode());
//		LOGGER.info("ML:"+ml+" ML=17:"+mlPrEq17+" ML<17:"+mlPrLt17);
//		if(ml<mlPrEq17) {
//			LOGGER.info("BAD ML:"+ml+" ML=17:"+mlPrEq17+" ML<17:"+mlPrLt17);
//			return new ReturnValue<Object>(true,"Max levels illogical, must be equal or get smaller.");
//		}
//		if(ml<mlPrLt17) {
//			LOGGER.info("BAD ML:"+ml+" ML=17:"+mlPrEq17+" ML<17:"+mlPrLt17);
//			return new ReturnValue<Object>(true,"Max levels illogical, must be equal or get smaller.");
//		}
//		if(mlPrLt17>mlPrEq17) {
//			LOGGER.info("BAD ML:"+ml+" ML=17:"+mlPrEq17+" ML<17:"+mlPrLt17);
//			return new ReturnValue<Object>(true,"Max levels illogical, must be equal or get smaller.");
//		}
		return new ReturnValue<Object>("");
	}

	@Override
	protected RaceSkill newT() {
		return new RaceSkill();
	}

}
