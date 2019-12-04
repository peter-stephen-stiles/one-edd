package com.nobbysoft.first.client.data.panels;

import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PList;
import com.nobbysoft.first.client.components.special.IIntegerField;
import com.nobbysoft.first.client.data.panels.CharacterRoller.METHOD;
import com.nobbysoft.first.common.entities.staticdto.Alignment;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimit;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimitKey;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.RaceClassLimitService;
import com.nobbysoft.first.common.utils.DICE;
import com.nobbysoft.first.common.utils.Roller;
import com.nobbysoft.first.utils.DataMapper;

public class RollingUtils {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public RollingUtils() { 
	}
	
	public boolean checkLimits(Race race,PIntegerField[] attTotals,String gender,PLabel lblInvalid, PLabel lblTotalValue,PIntegerField[] attValues,PLabel[] bonuses) {
		boolean ret=false;
		if (race != null) {
			PLabel l = new PLabel();
			Color normal = l.getForeground();
			Color bad = Color.RED;
			for (JComponent c : attTotals) {
				c.setForeground(normal);
			} 
			int[] mins;
			int[] maxs;
			if ("Male".equalsIgnoreCase(gender)) {
				mins = race.getMaleMinimums();
				maxs = race.getMaleMaximums();
			} else {
				mins = race.getFemaleMinimums();
				maxs = race.getFemaleMaximums();
			}
			boolean valid = true;
			int tot = 0;
			for (int i = 0, n = attTotals.length; i < n; i++) {

				Color colour = normal;
				int value = calculateTotal(i,attValues,bonuses);
				PIntegerField attributeTotal = attTotals[i];
				attributeTotal.setIntegerValue(value);
				if (value < mins[i]) {
					colour = bad;
					valid = false;
				} else if (value > maxs[i]) {
					attributeTotal.setIntegerValue(maxs[i]);
				}
				attributeTotal.setForeground(colour);
				tot = tot + attributeTotal.getIntegerValue();
			}
			lblInvalid.setText("Invalid");
			lblInvalid.setForeground(valid ? lblInvalid.getBackground() : Color.RED);
			lblTotalValue.setText("" + tot);
			ret=true;
		}
		return ret;
	}
	
	private int calculateTotal(int attIndex,PIntegerField[] attValues,PLabel[] bonuses) {
		PIntegerField txtRoll = attValues[attIndex];
		PLabel lblBonus = bonuses[attIndex];

		int roll = txtRoll.getIntegerValue();
		int bonus = Integer.parseInt(lblBonus.getText());
		int value = roll + bonus;
		return value;
	}
	
	
	public List<String> checkClasses(String raceId,
			Map<RaceClassLimitKey, RaceClassLimit> raceLimits,
			Map<String, CharacterClass> classes,
			PLabel lblXPBonus,
			IIntegerField[] attValues,
			PList<CharacterClass> txtClasses,
			List<CharacterClass> exceptTheseClasses,
			Alignment alignment) {

		List<String> reasons = new ArrayList<>();
		
		if (raceLimits.size() == 0) {
			RaceClassLimitService dao = (RaceClassLimitService) DataMapper.INSTANCE
					.getDataService(RaceClassLimit.class);
			try {
				for (RaceClassLimit dto : dao.getList()) {
					raceLimits.put(dto.getKey(), dto);
				}
			} catch (SQLException e) {
				LOGGER.error("Error getting class limits", e);
				reasons.add("Error getting class limits "+e);
				return reasons;
			}
		}

		if (classes.size() == 0) {
			CharacterClassService dao = (CharacterClassService) DataMapper.INSTANCE
					.getDataService(CharacterClass.class);

			try {
				for (CharacterClass cclass : dao.getList()) {
					classes.put(cclass.getName(), cclass);
				}
			} catch (SQLException e) {
				LOGGER.error("Error getting classes", e);
				reasons.add("Error getting classes"+e);
				return reasons;
			}
		}

		String[] att = new String[]{
				"Strength","Intelligence","Wisdom",
				"Dexterity","Constitution","Charisma"
		};
		
		{

			List<CharacterClass> validClasses = new ArrayList<>();
			lblXPBonus.setText("");

			for (CharacterClass cclass : classes.values()) {
				//LOGGER.info("Class " + cclass.getClassId());

				// only include
				if (!exceptTheseClasses.contains(cclass)) {

					RaceClassLimitKey rclKey = new RaceClassLimitKey();
					rclKey.setRaceId(raceId);
					rclKey.setClassId(cclass.getClassId());
					RaceClassLimit rcl = raceLimits.get(rclKey);
					if (rcl != null && !rcl.isNpcClassOnly() && !(rcl.getMaxLevel() < 1)) {
						int[] mins = cclass.getMinimums();
						boolean valid = true;
						for (int i = 0, n = 6; i < n; i++) {
							if (attValues[i].getIntegerValue() < mins[i]) {
								String reason = "Can't be a "+cclass.getName()+" because "+ " "+att[i]+" "+attValues[i].getIntegerValue()+" is less than "+mins[i];
								reasons.add(reason);
								LOGGER.info(reason);
								valid = false;
							}
						}
						if(valid) {
							/// check alignmen
							if(alignment!=null) {
								if(!cclass.getAlignmentsAllowed()[alignment.getIndex()]) {
									String reason=("Can't be a "+cclass.getName()+" because alignment is "+alignment.getDescription());
									reasons.add(reason);
									LOGGER.info(reason);
									valid=false;
								}
							}
						}
						if (valid) {
							validClasses.add(cclass);
						}

					} else {
						String reason = " RCL FAILURE " + rcl;						
						reasons.add("Can't be a " + cclass.getName()+" because of race ");
						LOGGER.info(reason);
					}
				} else {
					String reason= ("Already class " + cclass.getName());
					reasons.add(reason);
					LOGGER.info(reason);
				}
			}
			txtClasses.setListData(validClasses.toArray(new CharacterClass[validClasses.size()]));
		}
		return reasons;
	}

	public boolean rolling(METHOD method,PIntegerField[] attValues) {
		boolean allowSwaps = method.isAllowSwaps();
		if (METHOD.METHOD_3D6.equals(method)) {
			for (int i = 0, n = 6; i < n; i++) {
				int roll = Roller.roll(DICE.D6, 3, 0, 0);
				attValues[i].setText("" + roll);
			}
		} else if (METHOD.METHOD_I.equals(method)) {
			List<Integer> finalRolls = new ArrayList<>();
			for (int i = 0, n = 6; i < n; i++) {
				List<Integer> rolls = new ArrayList<>(4);
				for (int j = 0, m = 4; j < m; j++) {
					int roll = Roller.roll(DICE.D6, 1, 0, 0);
					rolls.add(roll);
				}
				Collections.sort(rolls);
				int roll = 0;
				for (int j = 1, m = 4; j < m; j++) {
					int r = rolls.get(j);
					roll += r;
				}
				finalRolls.add(roll);
			}
			for (int i = 0, n = 6; i < n; i++) {
				attValues[i].setText("" + finalRolls.get(i));
			}

		} else if (METHOD.METHOD_II.equals(method)) {
			List<Integer> rolls = new ArrayList<>(12);
			for (int i = 0, n = 12; i < n; i++) {
				int roll = Roller.roll(DICE.D6, 3, 0, 0);
				rolls.add(roll);
			}
			Collections.sort(rolls);
			for (int i = 0, n = 6; i < n; i++) {
				attValues[i].setText("" + rolls.get(i + 6));
			}
		} else if (METHOD.METHOD_III.equals(method)) {
			// roll 3d6 6 times for each attribute
			List<Integer> finalRolls = new ArrayList<>();
			for (int j = 0, m = 6; j < m; j++) {

				List<Integer> rolls = new ArrayList<>();
				for (int i = 0, n = 6; i < n; i++) {
					int roll = Roller.roll(DICE.D6, 3, 0, 0);
					rolls.add(roll);
				}
				Collections.sort(rolls);
				finalRolls.add(rolls.get(5));// biggest
			}
			for (int i = 0, n = 6; i < n; i++) {
				attValues[i].setText("" + finalRolls.get(i));
			}
		}
		return allowSwaps;
	}
	
}
