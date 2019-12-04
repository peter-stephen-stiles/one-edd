package com.nobbysoft.first.client.data.panels.pc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.AlignmentPicker;
import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.components.special.PComboAlignment;
import com.nobbysoft.first.client.components.special.PComboGender;
import com.nobbysoft.first.client.components.special.PExceptionalStrength;
import com.nobbysoft.first.client.components.special.ThreeClasses;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.data.panels.CharacterRoller;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.staticdto.Alignment;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.PlayerCharacterService;
import com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class PlayerCharacterPanel extends AbstractDataPanel<PlayerCharacter, Integer>
		implements MaintenancePanelInterface<PlayerCharacter> ,PlayerCharacterUpdatedListener{

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public PlayerCharacterPanel() {
		setLayout(new BorderLayout());
		jbInit();
		populateCombos();
	}

	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		if (d.height < 400) {
			d.height = 400;
		}
		if (d.width < 800) {
			d.width = 800;
		}
		return d;
	}

	private final PIntegerField txtPcId = new PIntegerField(true);
	private final PTextField txtCharacterName = new PTextField(128);
	private final PTextField txtPlayerName = new PTextField(128);
	private final AlignmentPicker txtAlignment = new AlignmentPicker(true);

	private final PButton btnAddXp = new PButton("Add XP");
	private final CharacterClass noClass = new CharacterClass();

	private final PComboGender txtGender = new PComboGender();

	private final ThreeClasses threeClasses = new ThreeClasses();

	private final PComboBox<Race> txtRace = new PComboBox<>();

	private final PExceptionalStrength txtExceptionalStrength = new PExceptionalStrength();

	private final PIntegerCombo txtAttrStr = new PIntegerCombo();
	private final PIntegerCombo txtAttrInt = new PIntegerCombo();
	private final PIntegerCombo txtAttrWis = new PIntegerCombo();
	private final PIntegerCombo txtAttrDex = new PIntegerCombo();
	private final PIntegerCombo txtAttrCon = new PIntegerCombo();
	private final PIntegerCombo txtAttrChr = new PIntegerCombo();

	private PButton btnRoll = new PButton("Roll") {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if (d.height < 22) {
				d.height = 22;
			}
			if (d.width < 50) {
				d.width = 50;
			}
			return d;
		}
	};

	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 

	private final PIntegerCombo[] attCombos = new PIntegerCombo[] { txtAttrStr, txtAttrInt, txtAttrWis, txtAttrDex,
			txtAttrCon, txtAttrChr };

	private PDataComponent[] dataComponents = new PDataComponent[] { txtCharacterName, txtPlayerName, txtAlignment };
	private PDataComponent[] keyComponents = new PDataComponent[] { btnRoll };// txtPcId };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtPcId, txtCharacterName };

	private PDataComponent[] disableThese = new PDataComponent[] { txtAttrStr, txtAttrInt, txtAttrWis, txtAttrDex,
			txtAttrCon, txtAttrChr, txtGender, txtRace, txtExceptionalStrength, threeClasses };

	private PlayerCharacterEquipmentPanel pnlEquipmentDetails;
	private PlayerCharacterSpellPanel pnlSpellDetails;
	private PlayerCharacterHpPanel pnlHpDetails;

	public void jbInit() {

		
		
		final JTabbedPane pnlTabs = new JTabbedPane();

		final PPanel pnlCharacterDetails = new PPanel(new GridBagLayout());
		pnlEquipmentDetails = new PlayerCharacterEquipmentPanel();
		pnlSpellDetails = new PlayerCharacterSpellPanel();
		pnlHpDetails = new PlayerCharacterHpPanel();

		pnlEquipmentDetails.addPlayerCharacterUpdatedListener(this);
		pnlSpellDetails.addPlayerCharacterUpdatedListener(this);
		pnlHpDetails.addPlayerCharacterUpdatedListener(this);
		
		pnlTabs.addTab("Character", pnlCharacterDetails);
		pnlTabs.addTab("Equipment", pnlEquipmentDetails);
		pnlTabs.addTab("Spells", pnlSpellDetails);
		pnlTabs.addTab("Hit Points", pnlHpDetails);

		add(pnlTabs, BorderLayout.CENTER);

		// character details

		noClass.setClassId("");
		noClass.setName("");

		txtPcId.setName("Id");
		txtCharacterName.setName("Character name");
		txtPlayerName.setName("Player name");
		txtGender.setName("Gender");
		txtRace.setName("Race");
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
		PLabel lblRace = new PLabel(txtRace.getName());
		PLabel lblAlignment = new PLabel(txtAlignment.getName());

		PLabel lblMinStr = new PLabel(txtAttrStr.getName());
		PLabel lblMinInt = new PLabel(txtAttrInt.getName());
		PLabel lblMinWis = new PLabel(txtAttrWis.getName());
		PLabel lblMinDex = new PLabel(txtAttrDex.getName());
		PLabel lblMinCon = new PLabel(txtAttrCon.getName());
		PLabel lblMinChr = new PLabel(txtAttrChr.getName());

		PPanel pnlLeft = new PPanel(new GridBagLayout());
		PPanel pnlRight = new PPanel(new GridBagLayout());

		PPanel pnlBelow = new PPanel(new GridBagLayout());

		pnlRight.add(lblMinStr, GBU.label(0, 1));
		pnlRight.add(txtAttrStr, GBU.text(1, 1));
		pnlRight.add(new PLabel("/"), GBU.label(2, 1));
		pnlRight.add(txtExceptionalStrength, GBU.text(3, 1));

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

		pnlCharacterDetails.add(getLblInstructions(), GBU.text(0, 0, 2));

		pnlCharacterDetails.add(pnlLeft, GBU.panel(0, 1));
		pnlCharacterDetails.add(pnlRight, GBU.panel(1, 1));
		pnlCharacterDetails.add(pnlBelow, GBU.panel(0, 2, 2, 1));

		pnlLeft.add(lblPcId, GBU.label(0, 1));
		pnlLeft.add(txtPcId, GBU.text(1, 1));

		pnlLeft.add(lblPlayerCharacterName, GBU.label(0, 2));
		pnlLeft.add(txtCharacterName, GBU.text(1, 2));

		pnlLeft.add(lblPlayerName, GBU.label(0, 3));
		pnlLeft.add(txtPlayerName, GBU.text(1, 3));

		pnlLeft.add(lblAlignment, GBU.label(0, 5));
		pnlLeft.add(txtAlignment, GBU.text(1, 5));

		pnlLeft.add(btnRoll, GBU.button(1, 4));

		pnlLeft.add(lblGender, GBU.label(0, 6));
		pnlLeft.add(txtGender, GBU.text(1, 6));

		pnlLeft.add(lblRace, GBU.label(0, 7));
		pnlLeft.add(txtRace, GBU.text(1, 7));

		pnlBelow.add(threeClasses, GBU.hPanel(0, 0, 2, 1));

		pnlBelow.add(btnAddXp, GBU.label(1, 2));

		btnAddXp.addActionListener(ae -> addXp());

		btnRoll.addActionListener(ae -> roll());

		txtPcId.setEditable(false);

		for (PDataComponent c : disableThese) {
			c.setReadOnly(true);
		}

		pnlCharacterDetails.add(new PLabel(""), GBU.label(99, 99));

	}

	protected ReturnValue<?> validateScreen() {

		if (!threeClasses.hasCharacterClass()) {
			return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE, "You must roll and select a character class!");
		}

		Alignment selected = txtAlignment.getAlignment();
		if(selected==null) {
			return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE, "You must select an alignment");
		}
			
		
		for(CharacterClass c:threeClasses.getClasses()) {
			if(c!=null) {
				// check alignments against selected
				if(c.getClassId()!=null) {
					if(!c.getAlignmentsAllowed()[selected.getIndex()]) {					
						return new ReturnValue<Object>(ReturnValue.IS_ERROR.TRUE, "Class "+c.getName()+" can't be "+selected.getDescription() +":(");
					}
				}
					
			}
		}
		

		return new ReturnValue<Object>("");
	}

	protected void populateFromScreen(PlayerCharacter value, boolean includingKeys) {
		if (includingKeys) {
			value.setPcId(txtPcId.getIntegerValue());
		}
		value.setCharacterName(txtCharacterName.getText());
		value.setPlayerName(txtPlayerName.getText());
		value.setGender(txtGender.getGender());
		value.setRaceId(((Race) txtRace.getSelectedItem()).getRaceId());
		CharacterClass[] classes = threeClasses.getClasses();
		CharacterClass c1 = classes[0];
		CharacterClass c2 = classes[1];
		CharacterClass c3 = classes[2];

		value.setFirstClass(c1.getClassId());
		LOGGER.info("first class " + value.getFirstClass() + " c1:" + c1.getClassId());
		if (!c2.equals(noClass)) {
			value.setSecondClass(c2.getClassId());
		} else {
			value.setSecondClass(null);
		}

		if (!c3.equals(noClass)) {
			value.setThirdClass(c3.getClassId());
		} else {
			value.setThirdClass(null);
		}
		value.setAlignment(txtAlignment.getAlignment());

		value.setAttrStr((Integer) txtAttrStr.getIntegerValue());
		value.setAttrInt((Integer) txtAttrInt.getIntegerValue());
		value.setAttrWis((Integer) txtAttrWis.getIntegerValue());
		value.setAttrDex((Integer) txtAttrDex.getIntegerValue());
		value.setAttrCon((Integer) txtAttrCon.getIntegerValue());
		value.setAttrChr((Integer) txtAttrChr.getIntegerValue());

		int[] hp = threeClasses.getHp();
		value.setFirstClassHp(hp[0]);
		value.setSecondClassHp(hp[1]);
		value.setThirdClassHp(hp[2]);
		int[] levels = threeClasses.getLevels();
		value.setFirstClassLevel(levels[0]);
		value.setSecondClassLevel(levels[1]);
		value.setThirdClassLevel(levels[2]);
		int[] xp = threeClasses.getXp();
		value.setFirstClassExperience(xp[0]);
		value.setSecondClassExperience(xp[1]);
		value.setThirdClassExperience(xp[2]);

		value.setExceptionalStrength(txtExceptionalStrength.getExceptionalStrength());

	}

	private void selectRace(PComboBox<Race> combo, String raceId) {
		combo.setSelectedIndex(0);
		if (raceId != null) {
			for (int i = 0, n = combo.getItemCount(); i < n; i++) {
				Race c = combo.getItemAt(i);
				if (c.getRaceId().contentEquals(raceId)) {
					combo.setSelectedIndex(i);
					break;
				}
			}
		}
	}

	private PlayerCharacter pc;
	private boolean newPlayer = true;

	protected void populateScreen(PlayerCharacter value) {
		PlayerCharacterService ccs = (PlayerCharacterService) getDataService();		
		
		int cid = value.getPcId();
		if (cid <= 0) {			
			try {
				cid = ccs.getNextId();
			} catch (SQLException e) {
				LOGGER.error("Error getting sequence", e);
			}
			newPlayer = true;
		} else {
			try {
				pc = ccs.get(value.getKey());
				newPlayer = false;
			} catch (SQLException e) {
				LOGGER.error("Error getting sequence", e);
			}
		}
		//btnAddXp.setReadOnly(newPlayer);
		txtPcId.setIntegerValue(cid);
		txtCharacterName.setText(value.getCharacterName());
		txtPlayerName.setText(value.getPlayerName());
		txtGender.setGender(value.getGender());
		selectRace(txtRace, value.getRaceId());
		txtAlignment.setAlignment(value.getAlignment());

		txtAttrStr.setIntegerValue(value.getAttrStr());
		txtAttrInt.setIntegerValue(value.getAttrInt());
		txtAttrWis.setIntegerValue(value.getAttrWis());
		txtAttrDex.setIntegerValue(value.getAttrDex());
		txtAttrCon.setIntegerValue(value.getAttrCon());
		txtAttrChr.setIntegerValue(value.getAttrChr());

		threeClasses.setFromPlayer(value);

		txtExceptionalStrength.setExceptionalStrength(value.getExceptionalStrength());

		pnlEquipmentDetails.initialiseCharacter(cid, txtCharacterName.getText());
		if (pc != null) {
			pnlSpellDetails.initialiseCharacter(pc);
			pnlHpDetails.initialiseCharacter(pc);
		} else {
			PlayerCharacter c = new PlayerCharacter();
			c.setPcId(cid);
			c.setCharacterName(txtCharacterName.getText());
			c.setFirstClass(threeClasses.getClasses()[0].getClassId());// could be nasty

			pnlSpellDetails.initialiseCharacter(pc);
			pnlHpDetails.initialiseCharacter(pc);
		}

	}

	@Override
	protected PDataComponent[] getButtonComponents() {
		List<PDataComponent> bs = new ArrayList<>();
		bs.add(btnAddXp);//
		for (PDataComponent c : pnlEquipmentDetails.getButtonComponents()) {
			bs.add(c);
		}

		for (PDataComponent c : pnlSpellDetails.getButtonComponents()) {
			bs.add(c);
		}

		for (PDataComponent c : pnlHpDetails.getButtonComponents()) {
			bs.add(c);
		}
		return bs.toArray(new PDataComponent[bs.size()]);
	}

	@Override
	protected DataServiceI<?, ?> getDataService() {
		DataServiceI dao;
		try {
			Class d = DataMapper.INSTANCE.getServiceForEntity(PlayerCharacter.class);
			Constructor<DataServiceI> cc = d.getConstructor();
			dao = (DataServiceI) cc.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Can't get Service for " + PlayerCharacter.class);
		}
		return dao;
	}

	DataServiceI getDataService(Class clazz) {
		DataServiceI dao = DataMapper.INSTANCE.getDataService(clazz);
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
	protected void populateCombos() {

		// CodedListService cliDao =
		// (CodedListService)DataMapper.INSTANCE.getDataService(CodedListService.class);

		RaceService raceService = (RaceService) DataMapper.INSTANCE.getNonDataService(RaceService.class);

		try {
			txtRace.setList(raceService.getList());
		} catch (SQLException e) {
			LOGGER.error("Err getting races", e);
		}

	}

	@Override
	protected PlayerCharacter newT() {
		return new PlayerCharacter();
	}

	 public ReturnValue<?> initCopy(PlayerCharacter pc, String instructions) {
		ReturnValue<?> rv = super.initCopy(pc, instructions);
		return addOrCopy(rv);
	}

	 public ReturnValue<?> initAdd(String instructions) {
		ReturnValue<?> rv = super.initAdd(instructions);
		return addOrCopy(rv);
	}

	private ReturnValue<?> addOrCopy(ReturnValue<?> rv) {
		if (!rv.isError()) {

			threeClasses.setClassCombosVisible(false);
			txtAlignment.setAlignment(Alignment.NEUTRAL);// just default to one :)
			{
				PlayerCharacterService ccs = (PlayerCharacterService) getDataService();
				try {
					int cid = ccs.getNextId();
					txtPcId.setIntegerValue(cid);
				} catch (SQLException e) {
					LOGGER.error("Error getting sequence", e);
					rv = new ReturnValue<String>(true, "Error getting sequence");
				}

			}

		}
		return rv;
	}

	private void roll() {
		Window w = GuiUtils.getParent(this);
		CharacterRoller cr = new CharacterRoller(w, "Roll new character");
		cr.pack();
		cr.setLocationRelativeTo(null);
		cr.setVisible(true);
		if (!cr.isCancelled()) {
			txtGender.setGender(cr.getGender());
			selectRace(txtRace, cr.getRace().getRaceId());
			int[] atts = cr.getAtts();
			for (int i = 0, n = attCombos.length; i < n; i++) {
				attCombos[i].setIntegerValue(atts[i]);
			}
			List<CharacterClass> classes = cr.getCharacterClasses();
			List<Integer> hps = cr.getHitPoints();
			threeClasses.rolled(classes, hps);

			txtExceptionalStrength.setExceptionalStrength(cr.getExceptionalStrength());

		}
	}

	private void addXp() {
		// need to pop up a small window, showing experience and having an "enter some
		// new" button
		//
		if (newPlayer) {
			Popper.popError(this, "Have to save first", "This is a new character, save them first before adding XP");
			return;
		}

		PlayerCharacterService ccs = (PlayerCharacterService) getDataService();

		try {

			ccs.update(pc);
			pc = ccs.get(pc.getPcId());
		} catch (Exception ex) {
			Popper.popError(this, ex);
			return;
		}
		try {
			Race race = (Race) txtRace.getSelectedItem();
			PlayerCharacterAddXpDialog addXp = new PlayerCharacterAddXpDialog(GuiUtils.getParent(this));
			addXp.setPlayerCharacter(pc, race);
			addXp.pack();
			addXp.setLocationRelativeTo(null);
			addXp.setVisible(true);
			if (!addXp.isCancelled()) {
				ccs.update(pc);
				pc = ccs.get(pc.getPcId());
			}
		} catch (Exception ex) {
			Popper.popError(this, ex);
			return;
		} finally {
			populateScreen(pc);
		}
	}

	@Override
	public void playerCharacterUpdated(PlayerCharacter pc) {
		populateScreen(pc);
		
	}

}
