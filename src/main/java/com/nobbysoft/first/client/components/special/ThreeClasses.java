package com.nobbysoft.first.client.components.special;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.utils.DataMapper;

public class ThreeClasses extends JPanel implements PDataComponent {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private final CharacterClass noClass = new CharacterClass();

	private final PComboBox<CharacterClass> txtClass1 = new PComboBox<>();
	private final PComboBox<CharacterClass> txtClass2 = new PComboBox<>();
	private final PComboBox<CharacterClass> txtClass3 = new PComboBox<>();

	private final PComboBox[] classCombos = new PComboBox[] { txtClass1, txtClass2, txtClass3 };

	private final PLabel lblClass1 = new PLabel();
	private final PLabel lblClass2 = new PLabel();
	private final PLabel lblClass3 = new PLabel();

	private final PIntegerField txtClassExperience1 = new PIntegerField(false);
	private final PIntegerField txtClassExperience2 = new PIntegerField(false);
	private final PIntegerField txtClassExperience3 = new PIntegerField(false);

	private final PIntegerField[] experience = new PIntegerField[] { txtClassExperience1, txtClassExperience2,
			txtClassExperience3 };

	private final PIntegerField txtClassLevel1 = new PIntegerField();
	private final PIntegerField txtClassLevel2 = new PIntegerField();
	private final PIntegerField txtClassLevel3 = new PIntegerField();

	private final PIntegerField txtClassHp1 = new PIntegerField();
	private final PIntegerField txtClassHp2 = new PIntegerField();
	private final PIntegerField txtClassHp3 = new PIntegerField();
	private final PIntegerField txtClassHpTotal = new PIntegerField();

	private final PIntegerField[] levels = new PIntegerField[] { txtClassLevel1, txtClassLevel2, txtClassLevel3 };

	private final PLabel[] classLabels = new PLabel[] { lblClass1, lblClass2, lblClass3 };
	private final PIntegerField[] hpValuesLabels = new PIntegerField[] { txtClassHp1, txtClassHp2, txtClassHp3 };

	private PDataComponent[] disableThese = new PDataComponent[] {

			txtClass1, txtClass2, txtClass3, txtClassLevel1, txtClassLevel2, txtClassLevel3, txtClassHp1, txtClassHp2,
			txtClassHp3, txtClassHpTotal, txtClassExperience1, txtClassExperience2, txtClassExperience3 };

	public ThreeClasses() {
		super(new GridBagLayout());
		populateCombos();
		jbInit();
	}
	
	private void setMinimumWidths() {
		 for(PComboBox c: classCombos) {
			 c.setMinimumWidth(50);
		 }
		 for(PIntegerField c: experience) {
			 c.setMinimumWidth(50);
		 }
		 for(PIntegerField c: levels) {
			 c.setMinimumWidth(50);
		 }
		 for(PIntegerField c: hpValuesLabels) {
			 c.setMinimumWidth(50);
		 }
		 
	}

	private void jbInit() {

		txtClass1.setName("Class (1)");
		txtClass2.setName("Class (2)");
		txtClass3.setName("Class (3)");
		txtClassExperience1.setName("XP for Class (1)");
		txtClassExperience2.setName("XP for Class (2)");
		txtClassExperience3.setName("XP for Class (3)");

		lblClass1.setText(txtClass1.getName());
		lblClass2.setText(txtClass2.getName());
		lblClass3.setText(txtClass3.getName());

		add(lblClass1, GBU.label(0, 8));
		add(lblClass2, GBU.label(0, 9));
		add(lblClass3, GBU.label(0, 10));

		add(txtClass1, GBU.text(1, 8));
		add(txtClass2, GBU.text(1, 9));
		add(txtClass3, GBU.text(1, 10));
		//

		add(new PLabel("Level"), GBU.label(2, 8));
		add(new PLabel("Level"), GBU.label(2, 9));
		add(new PLabel("Level"), GBU.label(2, 10));

		add(txtClassLevel1, GBU.text(3, 8));
		add(txtClassLevel2, GBU.text(3, 9));
		add(txtClassLevel3, GBU.text(3, 10));

		add(new PLabel("HP"), GBU.label(4, 8));
		add(new PLabel("HP"), GBU.label(4, 9));
		add(new PLabel("HP"), GBU.label(4, 10));

		add(txtClassHp1, GBU.text(5, 8));
		add(txtClassHp2, GBU.text(5, 9));
		add(txtClassHp3, GBU.text(5, 10));

		add(new PLabel("XP"), GBU.label(6, 8));
		add(new PLabel("XP"), GBU.label(6, 9));
		add(new PLabel("XP"), GBU.label(6, 10));

		add(txtClassExperience1, GBU.text(7, 8));
		add(txtClassExperience2, GBU.text(7, 9));
		add(txtClassExperience3, GBU.text(7, 10));

		add(new PLabel("total"), GBU.label(4, 11));
		add(txtClassHpTotal, GBU.text(5, 11));

		for (PDataComponent d : disableThese) {
			d.setReadOnly(true);
		}
		setMinimumWidths();
	}

	public boolean hasCharacterClass() {
		return txtClass1.isVisible();
	}

	public CharacterClass[] getClasses() {
		CharacterClass c1 = ((CharacterClass) txtClass1.getSelectedItem());
		CharacterClass c2 = ((CharacterClass) txtClass2.getSelectedItem());
		CharacterClass c3 = ((CharacterClass) txtClass3.getSelectedItem());
		return new CharacterClass[] { c1, c2, c3 };
	}

	public int[] getHp() {
		int x1 = txtClassHp1.getIntegerValue();
		int x2 = txtClassHp2.getIntegerValue();
		int x3 = txtClassHp3.getIntegerValue();
		return new int[] { x1, x2, x3 };
	}

	public int[] getLevels() {
		int x1 = (txtClassLevel1.getIntegerValue());
		int x2 = (txtClassLevel2.getIntegerValue());
		int x3 = (txtClassLevel3.getIntegerValue());
		return new int[] { x1, x2, x3 };
	}

	public int[] getXp() {
		int x1 = (txtClassExperience1.getIntegerValue());
		int x2 = (txtClassExperience2.getIntegerValue());
		int x3 = (txtClassExperience3.getIntegerValue());
		return new int[] { x1, x2, x3 };
	}

	private void selectClass(PComboBox<CharacterClass> combo, String classId) {
		combo.setSelectedIndex(0);
		if (classId != null) {
			for (int i = 0, n = combo.getItemCount(); i < n; i++) {
				CharacterClass c = combo.getItemAt(i);
				if (c == null) {
					LOGGER.error("NO CLASS AT " + i);
					continue;
				}
				if (c.getClassId() == null) {
					LOGGER.error("NO CLASS ID AT " + i);
					continue;
				}
				if (c.getClassId().contentEquals(classId)) {
					combo.setSelectedIndex(i);
					break;
				}
			}
		}
	}

	public void setFromPlayer(PlayerCharacter value) {
		String[] classes = new String[] { value.getFirstClass(), value.getSecondClass(), value.getThirdClass() };
		setClasses(classes);

		int[] hp = new int[] { value.getFirstClassHp(), value.getSecondClassHp(), value.getThirdClassHp() };
		setHp(hp);

		int[] levels = new int[] { value.getFirstClassLevel(), value.getSecondClassLevel(),
				value.getThirdClassLevel() };
		setClassLevels(levels);

		int[] xp = new int[] { (value.getFirstClassExperience()), (value.getSecondClassExperience()),
				(value.getThirdClassExperience()) };
		setExperience(xp);

		setClassHpTotal(value.getHp());
	}

	public void setClasses(String[] classIds) {
		selectClass(txtClass1, classIds[0]);
		selectClass(txtClass2, classIds[1]);
		selectClass(txtClass3, classIds[2]);
	}

	public void setHp(int[] hp) {
		txtClassHp1.setIntegerValue(hp[0]);
		txtClassHp2.setIntegerValue(hp[1]);
		txtClassHp3.setIntegerValue(hp[2]);
	}

	public void setClassLevels(int[] levels) {

		txtClassLevel1.setIntegerValue(levels[0]);
		txtClassLevel2.setIntegerValue(levels[1]);
		txtClassLevel3.setIntegerValue(levels[2]);
	}

	public void setExperience(int[] xp) {
		txtClassExperience1.setIntegerValue(xp[0]);
		txtClassExperience2.setIntegerValue(xp[1]);
		txtClassExperience3.setIntegerValue(xp[2]);
	}

	public void setClassHpTotal(int hp) {
		txtClassHpTotal.setIntegerValue(hp);
	}

	private void populateCombos() {
		CharacterClassService characterClassService = (CharacterClassService) DataMapper.INSTANCE
				.getNonDataService(CharacterClassService.class);

		try {

			List<CharacterClass> classes = characterClassService.getList();

			for (int i = 0, n = 3; i < n; i++) {
				List<CharacterClass> useMe = new ArrayList<>();
				useMe.add(noClass);
				useMe.addAll(classes);
				classCombos[i].setList(useMe);
				classCombos[i].setSelectedItem(noClass);
			}

		} catch (SQLException e) {
			LOGGER.error("Err getting races", e);
		}
	}

	public void setClassCombosVisible(boolean visible) {
		for (int i = 0, n = 3; i < n; i++) {
			classCombos[i].setVisible(visible);
		}
	}

	public void rolled(List<CharacterClass> classes, List<Integer> hps) {

		txtClassHpTotal.setIntegerValue(0);
		int totalhp = 0;
		for (int i = 0, n = 3; i < n; i++) {
			classCombos[i].setVisible(false);
			classCombos[i].setSelectedItem(noClass);
			hpValuesLabels[i].setText("");
			levels[i].setIntegerValue(0);
		}
		for (int i = 0, n = classes.size(); i < n; i++) {
			classCombos[i].setVisible(true);
			classCombos[i].setSelectedItem(classes.get(i));
			hpValuesLabels[i].setIntegerValue(hps.get(i));
			totalhp += hps.get(i);
			levels[i].setIntegerValue(1);
		}

		txtClassHpTotal.setIntegerValue(totalhp);

	}

	/**
	 * PDataComponent properties
	 */
	@Override
	public void setTheValue(Object o) {
	}

	@Override
	public Object getTheValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReadOnly(boolean readOnly) {

	}

	@Override
	public boolean isReadOnly() {

		return txtClass1.isReadOnly();
	}

	private int minH = 0;
	private int minW = 0;

	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		if (d.width < minW) {
			d.width = minW;
		}
		if (d.height < minH) {
			d.height = minH;
		}
		return d;
	}

	@Override
	public void setMinimumHeight(int height) {
		minH = height;

	}

	@Override
	public void setMinimumWidth(int width) {
		minW = width;

	}

}
