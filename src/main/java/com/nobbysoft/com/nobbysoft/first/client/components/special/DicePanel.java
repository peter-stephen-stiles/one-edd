package com.nobbysoft.com.nobbysoft.first.client.components.special;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.com.nobbysoft.first.common.utils.DICE;

public class DicePanel extends PPanel implements PDataComponent {

private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());


	public DicePanel() {
		super(new GridBagLayout());
		jbInit();
	}
	
	private PIntegerCombo cmbMultiplier = new PIntegerCombo(1,20) {
		public Dimension getPreferredSize() {			
			Dimension d = super.getPreferredSize();
			if(d.getWidth()< 40) {
				d.width=40;
			}
			return d;
		}
	};
	private PComboDICE    cmbDice = new PComboDICE    () {
		public Dimension getPreferredSize() {			
			Dimension d = super.getPreferredSize();
			if(d.getWidth()< 40) {
				d.width=40;
			}
			return d;
		}
	};
	private PIntegerCombo cmbModifier = new PIntegerCombo(-11,+11) {
		public Dimension getPreferredSize() {			
			Dimension d = super.getPreferredSize();
			if(d.getWidth()< 40) {
				d.width=40;
			}
			return d;
		}
	};
	
	private PDataComponent[] items = new PDataComponent[] {cmbMultiplier,cmbDice,cmbModifier}; 
	
	private void jbInit() {
		
		cmbMultiplier.setSelectedItem(1);
		cmbModifier.setSelectedItem(0);
		cmbDice.setSelectedItem(DICE.D8);
		
		add(cmbMultiplier,GBU.text(0, 0));
		add(cmbDice,GBU.text(1, 0));
		add(cmbModifier,GBU.text(2, 0));
	}

	@Override
	public void setTheValue(Object o) {
		///String in format    XdY+Z
		// X and Z are optional
		// Z can be negative
		cmbMultiplier.setSelectedItem(0);
		cmbModifier.setSelectedItem(0);
		cmbDice.setSelectedItem(DICE.D8);
		if(o==null) {
			return;
		}
		if( o instanceof DicePanelData) {
			DicePanelData dd = (DicePanelData)o;
			cmbMultiplier.setSelectedItem(dd.getMultiplier());
			cmbModifier.setSelectedItem(dd.getModifier());
			cmbDice.setSelectedItem(dd.getDice());
		} else {
			LOGGER.info("value:"+o);
			String s = ((String)o).trim();
			int d = s.indexOf('d');
			if(d<0) {
				throw new IllegalArgumentException("cannot parse "+o+" should be in format XdY+Z or XdY-Z");
			}
			int plus = s.indexOf('+',d);
			int minus = s.indexOf('-',d);
			int suffix = plus>0?plus:minus;
			LOGGER.info("value:"+s+" gives plus:"+plus+" minus:"+minus);
			// 
			// 01234 length=5
			// 5d8+1
			// substring(x,y) from X to y-1
			//
			if(suffix<0) {
				suffix=s.length();
			}
			String mul = s.substring(0,d);
			String dic = s.substring(d,suffix); // include the 'D not the suffix
			String mod = s.substring(suffix);
			LOGGER.info("value:"+s+" gives mul:"+mul+" dic:"+dic + " mod:"+mod + " suffix:"+suffix);
			DICE dice=DICE.fromDescription(dic);
			if(mul.length()==0) {
				mul="1";
			}
			if(mod.length()==0) {
				mod="0";
			}
			int mulI = Integer.parseInt(mul);
			int modI = Integer.parseInt(mod);
			LOGGER.info("value:"+s+" gives mul:"+mul+" dic:"+dic + " mod:"+mod + " suffix:"+suffix);
			cmbMultiplier.setIntegerValue(mulI);			
			cmbDice.setDICE(dice); 
			cmbModifier.setIntegerValue(Integer.parseInt(mod)); 
			LOGGER.info("value:"+s+" gives cmbMultiplier:"+cmbMultiplier.getSelectedItem()+
					" cmbDice:"+cmbDice.getSelectedItem() + 
					" cmbModifier:"+cmbModifier.getSelectedItem());
		}
		
	}

	public void setDicePanelData(DicePanelData dd) {
		cmbMultiplier.setSelectedItem(dd.getMultiplier());
		cmbDice.setSelectedItem(dd.getDice());
		cmbModifier.setSelectedItem(dd.getModifier());
	}
	public DicePanelData getDicePanelData() {
		DicePanelData dd = new DicePanelData(cmbMultiplier.getIntegerValue(),cmbDice.getDICE(),cmbModifier.getIntegerValue());
		return dd;
	}
	
	@Override
	public Object getTheValue() {
		DicePanelData dd = new DicePanelData(cmbMultiplier.getIntegerValue(),cmbDice.getDICE(),cmbModifier.getIntegerValue());
		return dd;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		for(PDataComponent c:items) {
			c.setReadOnly(readOnly);
		}
		
	}

	@Override
	public boolean isReadOnly() {
		return items[0].isReadOnly();
	}

	
	public static final class DicePanelData {
		private int multiplier;
		private DICE dice;
		private int modifier;
		DicePanelData (int multiplier,DICE dice, int modifier){
			this.multiplier=multiplier;
			this.dice =dice;
			this.modifier = modifier;
		}
		public int getMultiplier() {
			return multiplier;
		}
		public DICE getDice() {
			return dice;
		}
		public int getModifier() {
			return modifier;
		}
		public String toString() {
			StringBuilder sb = new StringBuilder();
			if(multiplier>1) {
				sb.append(multiplier);
			}
			sb.append(dice.getDesc());
			if(modifier<0) {
				sb.append(modifier);
			} else if(modifier>0) {
				sb.append("+");
				sb.append(modifier);
			}
			return sb.toString();
		}
	}
 

}
