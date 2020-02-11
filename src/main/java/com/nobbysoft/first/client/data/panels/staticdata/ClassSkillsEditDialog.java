package com.nobbysoft.first.client.data.panels.staticdata;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSkill;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSkillKey;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

public class ClassSkillsEditDialog 
  extends AbstractDataPanel<CharacterClassSkill, CharacterClassSkillKey>
  implements MaintenancePanelInterface<CharacterClassSkill> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private final PComboBox<CodedListItem<String>> txtCharacterClass = new PComboBox<>();
	private final PCodeField txtSkillId = new PCodeField();
	
	private final PIntegerCombo txtFromLevel = new PIntegerCombo(1,30) {
		public Dimension getPreferredSize() {
			Dimension d= super.getPreferredSize();
			if(d.width<60) {
				d.width=60;
			}
			return d;
		}
	};
		
	private final PTextField txtSkillName = new PTextField(60);
	private final PTextArea txtSkillDefinition = new PTextArea(512); 
	
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
 

	public ClassSkillsEditDialog() {
		setLayout(new GridBagLayout());
		jbInit();
	}
	
	@Override
	protected PDataComponent[] getButtonComponents() {
		return new PDataComponent[] {};
	}

	@Override
	protected DataServiceI<?, ?> getDataService() {
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(CharacterClassSkill.class);
		return dao;
	}
 
	@Override
	protected PDataComponent[] getDataComponents() { 
		return new PDataComponent[] {txtFromLevel ,txtSkillName ,
			txtSkillDefinition };
	}

	@Override
	protected PDataComponent[] getKeyComponents() { 
		return new PDataComponent[] {txtCharacterClass,txtSkillId};
	}

	@Override
	protected PDataComponent[] getMandatoryComponents() { 
		return new PDataComponent[] {txtFromLevel ,txtSkillName ,
				txtSkillDefinition};
	}



	
	@Override
	protected void jbInit() {
		txtCharacterClass.setReadOnly(true);
		
		txtCharacterClass.setName("Character Class");
		txtSkillId.setName("Skill Id");
		txtFromLevel.setName("From Level");
		txtSkillName.setName("Skills Name");
		txtSkillDefinition.setName("Skills Definition");
		
		
		int row=0;
		add(new PLabel(txtCharacterClass.getName()),GBU.label(0, row));
		add(txtCharacterClass,GBU.text(1, row));
		row++;

		add(new PLabel(txtFromLevel.getName()),GBU.label(0, row));
		add(txtFromLevel,GBU.text(1, row));
		row++;

		add(new PLabel(txtSkillId.getName()),GBU.label(0, row));
		add(txtSkillId,GBU.text(1, row));
		row++;
		

		add(new PLabel(txtSkillName.getName()),GBU.label(0, row));
		add(txtSkillName,GBU.text(1, row));
		row++;
		

		add(new PLabel(txtSkillDefinition.getName()),GBU.label(0, row));
		
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
		
		

		
		// spacer
		add(new PLabel(""),GBU.label(99, 99));	
	}

	public void defaultAdd(){
		if(parent!=null) {
			txtCharacterClass.setSelectedCode(parent.getClassId());
		} 
	}
	
	@Override
	protected void populateCombos() {

		try {
			CharacterClassService dao = (CharacterClassService)DataMapper.INSTANCE.getDataService(CharacterClass.class);

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
	protected void populateFromScreen(CharacterClassSkill value, boolean includingKeys) {
		value.setFromLevel(txtFromLevel.getIntegerValue());
		value.setClassId((String)txtCharacterClass.getSelectedCode());
		value.setSkillId(txtSkillId.getText());
		value.setSkillName(txtSkillName.getText());
		value.setSkillDefinition(txtSkillDefinition.getText()); 
		
	}

	@Override
	protected void populateScreen(CharacterClassSkill value) {
		txtFromLevel.setIntegerValue(value.getFromLevel());
		txtCharacterClass.setSelectedCode(value.getClassId());
		txtSkillId.setText(value.getSkillId());
		txtSkillName.setText(value.getSkillName());
		txtSkillDefinition.setText(value.getSkillDefinition()); 
		
		
	}

	@Override
	protected ReturnValue<?> validateScreen() {
		return new ReturnValue(""); //no error
	}

	@Override
	protected CharacterClassSkill newT() { 
		CharacterClassSkill ccs = new CharacterClassSkill();
		ccs.setClassId(parent.getClassId());
		return ccs;
	}

	
}
