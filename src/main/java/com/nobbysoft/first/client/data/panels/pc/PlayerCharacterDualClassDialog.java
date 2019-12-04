package com.nobbysoft.first.client.data.panels.pc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PList;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.components.special.CharacterClassListCellRenderer;
import com.nobbysoft.first.client.components.special.PComboAlignment;
import com.nobbysoft.first.client.components.special.PExceptionalStrength;
import com.nobbysoft.first.client.components.special.ThreeClasses;
import com.nobbysoft.first.client.data.panels.RollingUtils;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimit;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimitKey;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.PlayerCharacterService;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class PlayerCharacterDualClassDialog extends PDialog {

	private boolean cancelled = true;

	private PlayerCharacter pc;
	
	private final PComboAlignment txtAlignment = new PComboAlignment();

	private final PTextArea txtReasons = new PTextArea();

	private final PIntegerCombo txtAttrStr = new PIntegerCombo();
	private final PIntegerCombo txtAttrInt = new PIntegerCombo();
	private final PIntegerCombo txtAttrWis = new PIntegerCombo();
	private final PIntegerCombo txtAttrDex = new PIntegerCombo();
	private final PIntegerCombo txtAttrCon = new PIntegerCombo();
	private final PIntegerCombo txtAttrChr = new PIntegerCombo();
	private final PExceptionalStrength txtExceptionalStrength = new PExceptionalStrength();
	private final PIntegerCombo[] attCombos = new PIntegerCombo[] { txtAttrStr, txtAttrInt, txtAttrWis, txtAttrDex,
			txtAttrCon, txtAttrChr };

	private PList<CharacterClass> txtClasses = new PList<CharacterClass>() {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if (d.width > 100) {
				d.width = 100;
			}
			return d;
		}

		public Dimension getMaximumSize() {
			return getPreferredSize();
		}
	};
	private PLabel lblXPBonus = new PLabel("");

	private Map<RaceClassLimitKey, RaceClassLimit> raceLimits = new HashMap<>();
	private Map<String, CharacterClass> classes = new HashMap<>();

	private PButton btnSave = new PButton("Save");

	private PLabel lblInvalid = new PLabel("");
	private final ThreeClasses threeClasses = new ThreeClasses();

	private PDataComponent[] disableThese = new PDataComponent[] {txtAlignment, txtAttrStr, txtAttrInt, txtAttrWis, txtAttrDex,
			txtAttrCon, txtAttrChr, txtExceptionalStrength, threeClasses, btnSave };

	public PlayerCharacterDualClassDialog(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		jbInit();
	}

	private RollingUtils ru = new RollingUtils();

	private void jbInit() {

		setLayout(new GridBagLayout());
		
		txtReasons.setEditable(false);

		txtClasses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		txtClasses.setCellRenderer(new CharacterClassListCellRenderer());

		for (PDataComponent dc : disableThese) {
			dc.setReadOnly(true);
		}

		PButtonPanel pnlButtons = new PButtonPanel();

		txtAttrStr.setName("Str");
		txtAttrInt.setName("Int");
		txtAttrWis.setName("Wis");
		txtAttrDex.setName("Dex");
		txtAttrCon.setName("Con");
		txtAttrChr.setName("Chr");
		PLabel lblMinStr = new PLabel(txtAttrStr.getName());
		PLabel lblMinInt = new PLabel(txtAttrInt.getName());
		PLabel lblMinWis = new PLabel(txtAttrWis.getName());
		PLabel lblMinDex = new PLabel(txtAttrDex.getName());
		PLabel lblMinCon = new PLabel(txtAttrCon.getName());
		PLabel lblMinChr = new PLabel(txtAttrChr.getName());
		PPanel pnlLeft = new PPanel(new GridBagLayout());
		PPanel pnlRight = new PPanel(new GridBagLayout());
		PPanel pnlBelow = new PPanel(new BorderLayout(5,5));
		pnlBelow.add(threeClasses);		
		JScrollPane sclReasons = new JScrollPane(txtReasons) {
			public Dimension getMinimumSize(){
				Dimension d = super.getMinimumSize();
				if(d.height<30) {
					d.height = 30;
				}
				return d;
			}
			public Dimension getPreferredSize(){
				Dimension d = super.getPreferredSize();
				if(d.height<30) {
					d.height = 30;
				}
				return d;
			}
		};
		pnlBelow.add(sclReasons,BorderLayout.SOUTH);

		add(pnlLeft, GBU.hPanel(0, 0, 1, 1));
		add(pnlRight, GBU.hPanel(1, 0, 1, 1));
		add(pnlBelow, GBU.hPanel(0, 1, 2, 1));
		add(pnlButtons, GBU.hPanel(0, 2, 2, 1));
		PButton btnClose = new PButton("Cancel");
		pnlButtons.add(btnSave);
		pnlButtons.add(btnClose);
		btnSave.addActionListener(ae -> save());
		btnClose.addActionListener(ae -> close());

		
		
		pnlLeft.add(lblMinStr, GBU.label(0, 1));
		pnlLeft.add(txtAttrStr, GBU.text(1, 1));
		pnlLeft.add(new PLabel("/"), GBU.label(2, 1));
		pnlLeft.add(txtExceptionalStrength, GBU.text(3, 1));

		pnlLeft.add(lblMinInt, GBU.label(0, 2));
		pnlLeft.add(txtAttrInt, GBU.text(1, 2));
		pnlLeft.add(lblMinWis, GBU.label(0, 3));
		pnlLeft.add(txtAttrWis, GBU.text(1, 3));
		pnlLeft.add(lblMinDex, GBU.label(0, 4));
		pnlLeft.add(txtAttrDex, GBU.text(1, 4));
		pnlLeft.add(lblMinCon, GBU.label(0, 5));
		pnlLeft.add(txtAttrCon, GBU.text(1, 5));
		pnlLeft.add(lblMinChr, GBU.label(0, 6));
		pnlLeft.add(txtAttrChr, GBU.text(1, 6));
		
		pnlLeft.add(txtAlignment, GBU.text(0, 7,2));

		JScrollPane sclClasses = new JScrollPane(txtClasses);
		pnlRight.add(sclClasses, GBU.panel(0, 0));// ,1,1));
		txtClasses.addListSelectionListener(se -> {
			if (!se.getValueIsAdjusting()) {
				// meh determineXpBonus();
				determineAccept();
			}
		});

		pnlRight.add(lblInvalid, GBU.labelC(0, 1));

		this.add(new PLabel(""), GBU.label(99, 99));

	}

	private void determineAccept() {

		CharacterClass cclass = txtClasses.getSelectedValue();
		if (cclass != null && !lblInvalid.getForeground().equals(Color.RED)) {
			btnSave.setReadOnly(false);
		} else {
			btnSave.setReadOnly(true);
		}

	}

	public void initialiseCharacter(PlayerCharacter pc) {
		cancelled = true;
		this.pc = pc;
		this.setTitle("Dual class " + pc.getCharacterName());
		if (pc.getThirdClass() != null) {
			throw new IllegalStateException("Already dual classed enough!");
		}

		txtAttrStr.setIntegerValue(pc.getAttrStr());
		txtAttrInt.setIntegerValue(pc.getAttrInt());
		txtAttrWis.setIntegerValue(pc.getAttrWis());
		txtAttrDex.setIntegerValue(pc.getAttrDex());
		txtAttrCon.setIntegerValue(pc.getAttrCon());
		txtAttrChr.setIntegerValue(pc.getAttrChr());

		threeClasses.setFromPlayer(pc);
		
		txtAlignment.setAlignment(pc.getAlignment());

		txtExceptionalStrength.setExceptionalStrength(pc.getExceptionalStrength());

		CharacterClassService ccs = (CharacterClassService) DataMapper.INSTANCE.getDataService(CharacterClass.class);
		try {
			List<CharacterClass> exceptTheseClasses = new ArrayList<>();// all classes allowed
			for (PlayerCharacterLevel pcd : pc.getClassDetails()) {
				if (pcd.getThisClass() != null) {
					exceptTheseClasses.add(ccs.get(pcd.getThisClass()));
				}
			}
			List<String> reasons = ru.checkClasses(pc.getRaceId(), raceLimits, classes, lblXPBonus, attCombos, txtClasses, exceptTheseClasses,pc.getAlignment());
			StringBuilder sb = new StringBuilder();
			for(String reason:reasons) {
				sb.append(reason).append("\n");				
			}
			txtReasons.setText(sb.toString());
		} catch (Exception ex) {
			Popper.popError(this, ex);
		}
	}

	private void save() {

		CharacterClass newClass = txtClasses.getSelectedValue();
		if(newClass==null) {
			Popper.popError(this, "Nothing selected", "You've not selected a new class :(");
 			return;
		}
		// update the PC
		int cc=pc.classCount();
		if(cc<3) {
			pc.dualClassTo(newClass.getClassId());
			PlayerCharacterService ccs = (PlayerCharacterService) DataMapper.INSTANCE.getDataService(PlayerCharacter.class);
			try {
				ccs.update(pc);
			} catch (SQLException e) {
				Popper.popError(this, e);
				dispose();
	 			return;
			}
		
			cancelled = false;
			dispose();
		} else {

			Popper.popError(this, "Greedy", "You've already got three classes, stop being so fickle!");
 			return;
		}
	}

	private void close() {
		dispose();
	}

	public boolean isCancelled() {
		return cancelled;
	}

}
