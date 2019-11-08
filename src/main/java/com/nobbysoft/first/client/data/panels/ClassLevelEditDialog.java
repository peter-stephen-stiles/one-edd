package com.nobbysoft.first.client.data.panels;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCheckBox;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassLevelKey;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class ClassLevelEditDialog 
  extends AbstractDataPanel<CharacterClassLevel, CharacterClassLevelKey>
  implements MaintenancePanelInterface<CharacterClassLevel> {


	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 

 


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
	
	private final PIntegerField txtFromXp = new PIntegerField();
	private final PIntegerField txtToXp = new PIntegerField();
	
	private PTextField txtLevelTitle = new PTextField(30);
	private PCheckBox cbxNameLevel = new PCheckBox("Is this name level?");
	private PTextArea txtNotes = new PTextArea(4000);

	
	private CharacterClass parent=null;
	public void setParent(CharacterClass parent) {
		this.parent=parent;
		if(txtCharacterClass.getModel().getSize()>1) {
			txtCharacterClass.setSelectedCode(parent.getClassId());
		}
	}
	
	public ClassLevelEditDialog() {
		setLayout(new GridBagLayout());
		jbInit();
	}
	
	@Override
	PDataComponent[] getButtonComponents() {
		return new PDataComponent[] {};
	}

	@Override
	DataServiceI<?, ?> getDataService() {
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(CharacterClassLevel.class);
		return dao;
	}

	PDataComponent[] dataComp = new PDataComponent[] {
			txtFromXp ,
			txtFromXp,
			txtLevelTitle,
			cbxNameLevel,
			txtNotes
			};
	
	@Override
	PDataComponent[] getDataComponents() { 
		return dataComp;
	}

	@Override
	PDataComponent[] getKeyComponents() { 
		return new PDataComponent[] {txtClassLevel};
	}

	@Override
	PDataComponent[] getMandatoryComponents() { 
		return new PDataComponent[] {};
	}



	
	@Override
	void jbInit() {
		txtCharacterClass.setReadOnly(true);
		
		txtCharacterClass.setName("Character Class");
		
		txtClassLevel.setName("Class Level");
		txtFromXp.setName("From XP");
		txtToXp.setName("To XP");
		txtLevelTitle.setName("Level Title");
		txtNotes.setName("Notes");

		int row=0;
		add(new PLabel(txtCharacterClass.getName()),GBU.label(0, row));
		add(txtCharacterClass,GBU.text(1, row));		

		add(new PLabel(txtClassLevel.getName()),GBU.label(2, row));
		add(txtClassLevel,GBU.text(3, row));
		
		row++;
		
		add(new PLabel(txtFromXp.getName()),GBU.label(0, row));
		add(txtFromXp,GBU.text(1, row));
		
		add(new PLabel(txtToXp.getName()),GBU.label(2, row));
		add(txtToXp,GBU.text(3,row));
		
		row++;
		
		add(new PLabel(txtLevelTitle.getName()),GBU.label(0, row));
		add(txtLevelTitle,GBU.text(1, row,2));		
		add(cbxNameLevel,GBU.text(3, row));
		
		row++;
		add(new PLabel(txtNotes.getName()),GBU.label(0, row));//
		row++;
		JScrollPane sclNotes= new JScrollPane(txtNotes) {
			public Dimension getPreferredSize() {
				Dimension d= super.getPreferredSize();
				if(d.height<80) {
					d.height=80;
				}
				return d;
			}
		};
		add(sclNotes,GBU.panel(0, row,4,1));	
		
		// spacer
		add(new PLabel(""),GBU.label(99, 99));	
	}

	public void defaultAdd(int level, int xp){
		txtClassLevel.setIntegerValue(level);
		txtFromXp.setIntegerValue(xp);
	}
	
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
	void populateFromScreen(CharacterClassLevel value, boolean includingKeys) {
		value.setLevel(txtClassLevel.getIntegerValue());
		value.setFromXp(txtFromXp.getIntegerValue());
		value.setToXp(txtToXp.getIntegerValue());
		value.setLevelTitle(txtLevelTitle.getText());
		value.setNameLevel(cbxNameLevel.isSelected());
		value.setNotes(txtNotes.getText());


	}

	@Override
	void populateScreen(CharacterClassLevel value) {
		txtClassLevel.setIntegerValue(value.getLevel());
		txtFromXp.setIntegerValue(value.getFromXp());
		txtToXp.setIntegerValue(value.getToXp());
		txtLevelTitle.setText(value.getLevelTitle());
		cbxNameLevel.setSelected(value.isNameLevel());
		txtNotes.setText(value.getNotes());


		
	}

	@Override
	ReturnValue<?> validateScreen() {
		
		long xp0 = txtFromXp.getIntegerValue();
		long xp1 = txtToXp.getIntegerValue();
		if(xp1<xp0){
			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"'To' XP cannot be less than 'From' Xp");
		}
		if(xp0==0 && xp1==0) {
			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"'To' XP cannot be less than 'From' Xp");
		}
		
		return new ReturnValue(""); //no error
	}

	@Override
	CharacterClassLevel newT() { 
		CharacterClassLevel ccs = new CharacterClassLevel();
		ccs.setClassId(parent.getClassId());
		return ccs;
	}

	
}
