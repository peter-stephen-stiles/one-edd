package com.nobbysoft.first.client.data.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.ComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PList;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.components.special.CharacterClassListCellRenderer;
import com.nobbysoft.first.client.components.special.PExceptionalStrength;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.client.utils.Utils;
import com.nobbysoft.first.common.entities.staticdto.Attribute;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Gender;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimit;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimitKey;
import com.nobbysoft.first.common.entities.staticdto.attributes.Constitution;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.ConstitutionService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.RaceClassLimitService;
import com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.DICE;
import com.nobbysoft.first.common.utils.Roller;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class CharacterRoller extends PDialog {
	
	
	private Preferences  prefs =Utils.getPrefs(CharacterRoller.class);
 
 
	
	private void savePrefs() {
		prefs.put("method", ((METHOD)cbxMethod.getSelectedItem()).name());
		prefs.put("gender", (String) cbxGender.getSelectedCode());
		CodedListItem<Race> cli = (CodedListItem<Race>) cbxRace.getSelectedItem();
		Race r = cli.getItem();
		prefs.put("race", r.getRaceId());
	}
	private void getPrefs() {
		String m = prefs.get("method", METHOD.METHOD_II.name());
		String g = prefs.get("gender", Gender.MALE.name());
		String r = prefs.get("race", null);
		if(m!=null) {
			cbxMethod.setSelectedItem(METHOD.valueOf(m));
		}
		if(g!=null) {
			cbxGender.setSelectedCode(g);
		}
		if(r!=null) {
			ComboBoxModel<CodedListItem<Race>> model = cbxRace.getModel();
			int n = model.getSize();
					for(int i=0;i<n;i++) {
						CodedListItem<Race> cli =model.getElementAt(i);
						Race race = cli.getItem();
						if(race.getRaceId().contentEquals(r)) {
							cbxRace.setSelectedItem(cli);
							break;
						}
					}
			
			
			
		}
	}
	
	private boolean cancelled = true;

	enum METHOD {
		METHOD_3D6("3d6", true), METHOD_I("Method I", true), METHOD_II("Method II", true),
		METHOD_III("Method III", false);
		private final String desc;
		private final boolean allowSwaps;

		METHOD(String desc, boolean allowSwaps) {
			this.desc = desc;
			this.allowSwaps = allowSwaps;
		}

		public String toString() {
			return desc;
		}

		public boolean isAllowSwaps() {
			return allowSwaps;
		}

		public String getDesc() {
			return desc;
		}
	}

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private Map<String, CharacterClass> classes = new HashMap<>();
	private Map<RaceClassLimitKey, RaceClassLimit> raceLimits = new HashMap<>();

	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		if (d.getHeight() < 400) {
			d.height = 400;
		}
		if (d.getWidth() < 600) {
			d.width = 600;
		}
		return d;
	}

	public CharacterRoller(Window owner, String title) {
		super(owner, title, ModalityType.DOCUMENT_MODAL);
		jbInit();
		populateCombos();
		getPrefs();
	}

	private final PComboBox<METHOD> cbxMethod = new PComboBox<>();
	private final PComboBox<String> cbxGender = new PComboBox<>();
	private final PComboBox<CodedListItem<Race>> cbxRace = new PComboBox<>();

	private PLabel lblStr = new PLabel(Attribute.STRENGTH.name());
	private PLabel lblInt = new PLabel(Attribute.INTELLIGENCE.name());
	private PLabel lblWis = new PLabel(Attribute.WISDOM.name());
	private PLabel lblDex = new PLabel(Attribute.DEXTERITY.name());
	private PLabel lblCon = new PLabel(Attribute.CONSTITUTION.name());
	private PLabel lblChr = new PLabel(Attribute.CHARISMA.name());

	private PLabel[] attributeLabels = new PLabel[] { lblStr, lblInt, lblWis, lblDex, lblCon, lblChr };

	private PLabel lblBonusStr = new PLabel("");
	private PLabel lblBonusInt = new PLabel("");
	private PLabel lblBonusWis = new PLabel("");
	private PLabel lblBonusDex = new PLabel("");
	private PLabel lblBonusCon = new PLabel("");
	private PLabel lblBonusChr = new PLabel("");

	private PLabel[] bonuses = new PLabel[] { lblBonusStr, lblBonusInt, lblBonusWis, lblBonusDex, lblBonusCon,
			lblBonusChr };

	private PLabel lblLimitStr = new PLabel("");
	private PLabel lblLimitInt = new PLabel("");
	private PLabel lblLimitWis = new PLabel("");
	private PLabel lblLimitDex = new PLabel("");
	private PLabel lblLimitCon = new PLabel("");
	private PLabel lblLimitChr = new PLabel("");

	private PLabel[] limits = new PLabel[] { lblLimitStr, lblLimitInt, lblLimitWis, lblLimitDex, lblLimitCon,
			lblLimitChr };

	private PLabel lblFourChars = new PLabel("WWWW");

	private PExceptionalStrength txtExceptionalStr = new PExceptionalStrength();
	
	private PIntegerField txtStr = new PIntegerField();
	private PIntegerField txtInt = new PIntegerField();
	private PIntegerField txtWis = new PIntegerField();
	private PIntegerField txtDex = new PIntegerField();
	private PIntegerField txtCon = new PIntegerField();
	private PIntegerField txtChr = new PIntegerField();

	private PIntegerField[] attValues = new PIntegerField[] { txtStr, txtInt, txtWis, txtDex, txtCon, txtChr };

	private PIntegerField txtTotalStr = new PIntegerField();
	private PIntegerField txtTotalInt = new PIntegerField();
	private PIntegerField txtTotalWis = new PIntegerField();
	private PIntegerField txtTotalDex = new PIntegerField();
	private PIntegerField txtTotalCon = new PIntegerField();
	private PIntegerField txtTotalChr = new PIntegerField();

	private PIntegerField[] attTotals = new PIntegerField[] { txtTotalStr, txtTotalInt, txtTotalWis, txtTotalDex,
			txtTotalCon, txtTotalChr };

	private PLabel lblInvalid = new PLabel("");
	private PLabel lblTotalValue = new PLabel("");
	private PList<CharacterClass> txtClasses = new PList<CharacterClass>() {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.width>100) {
				d.width=100;
			}
			return d;
		}
		public Dimension getMaximumSize() {
			return getPreferredSize();
		}
	};
	private PLabel lblXPBonus = new PLabel("");
	private PButton btnAccept;
	
	private List<PButton> upsAndDowns = new ArrayList<>();
	
	private PLabel lblHp1 = new PLabel("Hp for");
	private PLabel lblHp2 = new PLabel("Hp for");
	private PLabel lblHp3 = new PLabel("Hp for");
	private PLabel lblClass1 = new PLabel("");
	private PLabel lblClass2 = new PLabel("");
	private PLabel lblClass3 = new PLabel("");

	private PIntegerField txtHp1 = new PIntegerField();
	private PIntegerField txtHp2 = new PIntegerField();
	private PIntegerField txtHp3 = new PIntegerField();

	private PLabel[] lblClasses = new PLabel[] {
			lblClass1,lblClass2,lblClass3
	};
	
	private PIntegerField[] txtHps = new PIntegerField[] {
			txtHp1,txtHp2,txtHp3
	};
	

	private final PTextArea txtReasons = new PTextArea();
	
	private void jbInit() {

		txtClasses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		txtReasons.setEditable(false);
		Container thisPanel = getContentPane();
		thisPanel.setLayout(new BorderLayout());
		PButtonPanel pnlBottomButtons = new PButtonPanel();
		PButton btnExit = new PButton("Cancel");
		btnAccept = new PButton("Accept");
		btnAccept.setReadOnly(true);
		pnlBottomButtons.add(btnAccept);
		pnlBottomButtons.add(btnExit);
		thisPanel.add(pnlBottomButtons, BorderLayout.SOUTH);
		btnExit.addActionListener(ae -> {
			dispose();
		});
		btnAccept.addActionListener(ae ->{
			cancelled=false;
			dispose();
		});

		PPanel pnlMiddle = new PPanel(new GridBagLayout());
		thisPanel.add(pnlMiddle, BorderLayout.CENTER);

		pnlMiddle.add(new PLabel("Rolling method"), GBU.label(0, 0));
		pnlMiddle.add(cbxMethod, GBU.text(1, 0, 2));

		pnlMiddle.add(new PLabel("Gender"), GBU.label(0, 1));
		pnlMiddle.add(cbxGender, GBU.text(1, 1));
		cbxGender.addActionListener(ae -> {
			checkLimits();
		});
		pnlMiddle.add(new PLabel("Race"), GBU.label(0, 2));
		pnlMiddle.add(cbxRace, GBU.text(1, 2, 2));

		cbxMethod.addActionListener(ae -> {
			SwingUtilities.invokeLater(() -> {
				METHOD method = (METHOD) (cbxMethod.getSelectedItem());
				enableUpsAndDowns(method.isAllowSwaps());
			});
		});
		cbxRace.addActionListener(ae -> {
			changeRace();
		});

		PButton btnRoll = new PButton("Roll!");
		btnRoll.addActionListener(ae -> {
			roll();
		});

		pnlMiddle.add(btnRoll, GBU.button(5, 1));

		PLabel lblAtt = new PLabel("Attribute") {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				if(d.height<22) {
					d.height=22;
				}
				if(d.width<40) {
					d.width=40;
				}
				return d;
			}
		};
		PLabel lblBonus = new PLabel("Racial bonus");
		PLabel lblLimits = new PLabel("Limits");
		PLabel lblRoll = new PLabel(" Roll ");
		PLabel lblTotal = new PLabel("Total");
		PLabel lblClasses = new PLabel("Available classes");

		PLabel buttons = new PLabel("");
		buttons.linkSizeTo(lblFourChars);
		PLabel equals = new PLabel("");
		equals.linkSizeTo(lblFourChars);

		PPanel pnlRolling = new PPanel(new GridBagLayout()); // rows, cols

		pnlMiddle.add(pnlRolling, GBU.panel(0, 3, 5, 6));

		JScrollPane sclClasses = new JScrollPane(txtClasses);

		pnlMiddle.add(lblClasses, GBU.labelC(5, 2, 2));
		pnlMiddle.add(sclClasses, GBU.panel(5, 3, 2, 1));

		pnlMiddle.add(lblXPBonus, GBU.label(5, 4));

		txtClasses.addListSelectionListener(se -> {
			if (!se.getValueIsAdjusting()) {
				determineXpBonus();
				determineAccept();
			}
		});

		PLabel[] heads = new PLabel[] { lblAtt, lblBonus, lblLimits, buttons, lblRoll, equals, lblTotal

		};

		Font lf = heads[0].getFont();
		Font bold = lf.deriveFont(Font.BOLD);
		for (int i = 0, n = heads.length; i < n; i++) {
			heads[i].setFont(bold);
			pnlRolling.add(heads[i], GBU.labelC(i, 0));
		}

		for (int i = 0, n = bonuses.length; i < n; i++) {
			int row = i + 1;
			bonuses[i].setName("" + i + " BONUS");
			// 4 is down button
			// attValues[i].linkSizeTo(lblFourChars);
			JComponent down1 = null;
			if (i < 5) {
				PButton btnDown = new PButton("\\/");
				// btnDown.linkSizeTo(attValues[i]);
				btnDown.setActionCommand("" + i + " DOWN");
				btnDown.addActionListener(downOrUp);
				down1 = (btnDown);
				upsAndDowns.add(btnDown);
			} else {
				down1 = (new PLabel(""));
			}
			attValues[i].setName("" + i + " ATT");
			bonuses[i].addPropertyChangeListener("text", attChanges);
			final int attIndex = i;
			attValues[i].getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void insertUpdate(DocumentEvent e) {

					rollChanged(attIndex);

				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					rollChanged(attIndex);

				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					rollChanged(attIndex);

				}

			});
			// 6 is up button
			JComponent up1 = null;
			if (i > 0) {
				PButton btnUp = new PButton("/\\");
				// btnUp.linkSizeTo(attValues[i]);
				btnUp.setActionCommand("" + i + " UP");
				btnUp.addActionListener(downOrUp);
				up1 = (btnUp);
				upsAndDowns.add(btnUp);
			} else {
				up1 = (new PLabel(""));
			}

			JPanel pnlDU = new JPanel(new GridLayout(1, 2));
			pnlDU.add(down1);
			pnlDU.add(up1);
			int col = 0;
			pnlRolling.add(attributeLabels[i], GBU.button(col++, row));
			pnlRolling.add(bonuses[i], GBU.labelC(col++, row));
			pnlRolling.add(limits[i], GBU.labelC(col++, row));
			pnlRolling.add(pnlDU, GBU.button(col++, row));
			pnlRolling.add(attValues[i], GBU.text(col++, row));
			pnlRolling.add(new PLabel("="), GBU.labelC(col++, row));
			pnlRolling.add(attTotals[i], GBU.text(col++, row));
			if(i==0) {
				pnlRolling.add(new PLabel("/"), GBU.label(col++, row));
				pnlRolling.add(txtExceptionalStr, GBU.text(col++, row));
				
			}

		}

		PLabel lblTotalLabel = new PLabel("Total");
		pnlRolling.add(lblTotalLabel, GBU.label(5, 7));
		pnlRolling.add(lblTotalValue, GBU.text(6, 7));
		pnlRolling.add(lblInvalid, GBU.labelC(6, 8));

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

		txtClasses.setCellRenderer(new CharacterClassListCellRenderer());

		txtHp1.setReadOnly(true);
		txtHp2.setReadOnly(true);
		txtHp3.setReadOnly(true);
		
		pnlMiddle.add(lblHp1,GBU.label(0,20));
		pnlMiddle.add(lblClass1,GBU.label(1,20));
		pnlMiddle.add(txtHp1,GBU.text(2,20));
		pnlMiddle.add(lblHp2,GBU.label(0,21));
		pnlMiddle.add(lblClass2,GBU.label(1,21));
		pnlMiddle.add(txtHp2,GBU.text(2,21));
		pnlMiddle.add(lblHp3,GBU.label(0,22));
		pnlMiddle.add(lblClass3,GBU.label(1,22));
		pnlMiddle.add(txtHp3,GBU.text(2,22));
		
		pnlMiddle.add(sclReasons,GBU.hPanel(0, 24, 5, 1));
		
		
		pnlMiddle.add(new PLabel(""), GBU.label(99, 99));

	}

	private ActionListener downOrUp = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] a = e.getActionCommand().split(" ");
			int i = Integer.parseInt(a[0]);
			if ("DOWN".equals(a[1])) {
				LOGGER.info("Down with " + i);
				PIntegerField from = attValues[i];
				PIntegerField to = attValues[i + 1];
				int va = from.getIntegerValue();
				from.setIntegerValue(to.getIntegerValue());
				to.setIntegerValue(va);
			} else {
				// up!
				LOGGER.info("UP with " + i);
				PIntegerField from = attValues[i];
				PIntegerField to = attValues[i - 1];
				int va = from.getIntegerValue();
				from.setIntegerValue(to.getIntegerValue());
				to.setIntegerValue(va);
			}

		}

	};

	private PropertyChangeListener attChanges = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			JComponent f = (JComponent) evt.getSource();
			LOGGER.info("ROLL Property changed Object " + f.getName() + " from:" + evt.getOldValue() + " to:"
					+ evt.getNewValue());
			String[] a = f.getName().split(" ");
			int i = Integer.parseInt(a[0]);
			if ("BONUS".equals(a[1])) {
				rollChanged(i);
			} else {

			}

		}

	};

	private void changeRace() {

		CodedListItem<Race> cli = (CodedListItem<Race>) cbxRace.getSelectedItem();

		Race r = cli.getItem();

		if (r != null) {

			int[] mins;
			int[] maxs;

			String gender = (String) cbxGender.getSelectedCode();
			if (Gender.MALE.name().equalsIgnoreCase(gender)) {
				mins = r.getMaleMinimums();
				maxs = r.getMaleMaximums();
			} else {
				mins = r.getFemaleMinimums();
				maxs = r.getFemaleMaximums();
			}

			int[] bons = r.getBonuses();
			for (int i = 0, n = bonuses.length; i < n; i++) {
				PLabel lbl = bonuses[i];
				int val = bons[i];
				lbl.setText("" + val);

				PLabel lim = limits[i];
				lim.setText("" + mins[i] + "-" + maxs[i]);

			}
			
			
			if(r.isMultiClassable()) {
				txtClasses.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			} else {
				txtClasses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}
			
		}

	}

	
	DataServiceI<?, ?> getDataService() {
		DataServiceI<?, ?> dao;
		try {
		Class<DataServiceI> d =  DataMapper.INSTANCE.getServiceForEntity(Constitution.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI<?, ?>) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+Constitution.class);
			}
		return dao;
	}
	
	private Map<Integer,Constitution> conMap = new HashMap<>();
	{
		ConstitutionService cs = (ConstitutionService) getDataService();
		List<Constitution> l;
		try {
			l = cs.getList();
			for(Constitution c:l) {
				conMap.put(c.getKey(), c);
			}
		} catch (SQLException e) {
			LOGGER.error("error getting cons",e);
		}

	}
	
	private void populateCombos() {

		cbxGender.addItem("Male");
		cbxGender.addItem("Female");

		cbxMethod.addItem(METHOD.METHOD_3D6);
		cbxMethod.addItem(METHOD.METHOD_I);
		cbxMethod.addItem(METHOD.METHOD_II);
		cbxMethod.addItem(METHOD.METHOD_III);

		try {

			RaceService dao = (RaceService) DataMapper.INSTANCE.getDataService(Race.class);
			{
				List<Race> list = dao.getList();

				for (Race race : list) {
					cbxRace.addItem(new CodedListItem<Race>(race, race.getName()));
				}

			}
		} catch (Exception ex) {
			Popper.popError(GuiUtils.getParent(this), ex);
		}

	}

	private void rollChanged(int attIndex) {

		SwingUtilities.invokeLater(() -> {
			PIntegerField txtTotal = attTotals[attIndex];
			int value = calculateTotal(attIndex);
			txtTotal.setIntegerValue(value);
			if(attIndex==0) {
				if(value==18) {
					txtExceptionalStr.setExceptionalStrength(Roller.roll(DICE.D100, 1, 0, 0));
				} else {
					txtExceptionalStr.setExceptionalStrength(0);
				}
			}
			checkLimits();
		});

	}

	private int calculateTotal(int attIndex) {
		PIntegerField txtRoll = attValues[attIndex];
		PLabel lblBonus = bonuses[attIndex];

		int roll = txtRoll.getIntegerValue();
		int bonus = Integer.parseInt(lblBonus.getText());
		int value = roll + bonus;
		return value;
	}

	private void enableUpsAndDowns(boolean enable) {
		for (PButton but : upsAndDowns) {
			but.setEnabled(enable);
		}
	}

	private void determineXpBonus() {
		//
		
		lblClass1.setText("");
		lblClass2.setText("");
		lblClass3.setText("");
		txtHp1.setText("");
		txtHp2.setText("");
		txtHp3.setText("");
		
		String text = "";
		try {
			boolean allGood=true;
			boolean anySelected=false;
			int classIndex = 0;
			List<CharacterClass> classes =txtClasses.getSelectedValuesList();
			double countClasses = classes.size();
			for(CharacterClass cclass:classes){ 
				anySelected=true;
				int pr1 = cclass.getPrimeRequisite1() - 1;
				int pr2 = cclass.getPrimeRequisite2() - 1;
				int pr3 = cclass.getPrimeRequisite3() - 1;
				boolean bonus = true;
				if (pr1 >= 0) {
					int av = attTotals[pr1].getIntegerValue();
					if (av > 15) {
						if (pr2 >= 0) {
							av = attTotals[pr2].getIntegerValue();
							if (av > 15) {
								if (pr3 >= 0) {
									av = attTotals[pr3].getIntegerValue();
									if (av > 15) {

									} else {
										bonus = false;
									}
								}
							} else {
								bonus = false;
							}
						}
					} else {
						bonus = false;
					}
					if(!bonus) {
						allGood=false;
					}
						

				}
				if(classIndex<3) {
					lblClasses[classIndex].setText(cclass.getName());
					Constitution con = conMap.get(txtTotalCon.getIntegerValue());
					int cbonus = 0;
					if(cclass.isHighConBonus()) {
						cbonus=con.getHitPointAdjustmentHigh();
					} else {
						cbonus=con.getHitPointAdjustment();
					}
					DICE d = Roller.getDICE( cclass.getHitDice());
					int roll = Roller.roll(d, cclass.getHitDiceAtFirstLevel(), cbonus, 0);
					// divide by by number of classes
					if(countClasses>1) {
						BigDecimal rollBD = new BigDecimal(""+roll);
						BigDecimal countBD = new BigDecimal(""+countClasses);
						LOGGER.info("roll "+rollBD+ " count:"+countBD);
						BigDecimal res = rollBD.divide(countBD,new MathContext(5,RoundingMode.CEILING));
						LOGGER.info("roll "+rollBD+ " count:"+countBD+" res:"+res);
						roll = res.setScale(0, RoundingMode.HALF_UP).intValue();
						
					}
					txtHps[classIndex].setIntegerValue(roll);
				}
				classIndex++;
			}
			if (anySelected && allGood) {
				text = "10% xp bonus";
			}

		} finally {
			lblXPBonus.setText(text);
		}
	}

	private void roll() {
		
		savePrefs();
		
		METHOD method = (METHOD) (cbxMethod.getSelectedItem());
		enableUpsAndDowns(ru.rolling(method, attValues));

	}

	private void checkLimits() {
		
		// MOVED TO ROLLING UTILS
		//
		Race race = (Race) cbxRace.getSelectedCode();
		String gender = (String) cbxGender.getSelectedCode();
		if (race != null) {
			boolean ok=ru.checkLimits(race, attTotals, gender, lblInvalid, lblTotalValue, attValues, bonuses);
			if(ok) {
				checkClasses();
				determineAccept();
			}
		}

	}

	private RollingUtils ru = new RollingUtils();
	
	private void checkClasses() {
		txtReasons.setText("");
		String raceId = ((Race) cbxRace.getSelectedCode()).getRaceId();

		RollingUtils ru = new RollingUtils();
		List<CharacterClass> exceptTheseClasses = new ArrayList<>();// all classes allowed
		List<String> reasons = ru.checkClasses(raceId, raceLimits, classes, lblXPBonus, attValues, txtClasses,exceptTheseClasses,null);
		StringBuilder sb = new StringBuilder();
		for(String reason:reasons) {
			sb.append(reason).append("\n");				
		}
		txtReasons.setText(sb.toString());

	}

	private void determineAccept() {

		CharacterClass cclass = txtClasses.getSelectedValue();
		if (cclass != null && !lblInvalid.getForeground().equals(Color.RED)) {
			btnAccept.setReadOnly(false);
		} else {
			btnAccept.setReadOnly(true);
		}
		
	}
	
	
	public int[] getAtts() {
		int[] atts = new int[attTotals.length];
		for (int i = 0, n = attTotals.length; i < n; i++) {
			PIntegerField attributeTotal = attTotals[i];
			atts[i]=attributeTotal.getIntegerValue();
		}
			return atts;
		
	}
	public Race getRace() {
		CodedListItem<Race> cli = (CodedListItem<Race>) cbxRace.getSelectedItem();

		Race r = cli.getItem();
		LOGGER.info("race:"+r);
		return r;
	}
	public Gender getGender() {
		String gender = (String) cbxGender.getSelectedCode();
		if (Gender.MALE.name().equalsIgnoreCase(gender)) {
			return Gender.MALE;
		} else {
			return Gender.FEMALE;
		}
	}
	public List<CharacterClass> getCharacterClasses() {
		return txtClasses.getSelectedValuesList();
	}
	public List<Integer> getHitPoints(){
		int count= txtClasses.getSelectedValuesList().size();
		List<Integer> hps = new ArrayList<Integer>();
		hps.add(txtHp1.getIntegerValue());
		if(count>1) {
			hps.add(txtHp2.getIntegerValue());
			if(count>2) {
				hps.add(txtHp3.getIntegerValue());
			}

		}
		
		return hps;
	}
	
	public int getExceptionalStrength() {
		return txtExceptionalStr.getExceptionalStrength();
	}

	public boolean isCancelled() {
		return cancelled;
	}
	
}
