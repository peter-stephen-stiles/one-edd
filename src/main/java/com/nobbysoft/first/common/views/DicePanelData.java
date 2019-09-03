package com.nobbysoft.first.common.views;

import com.nobbysoft.first.common.utils.DICE;

public final class DicePanelData {
	private int multiplier;
	private DICE dice;
	private int modifier;
	public DicePanelData (int multiplier,DICE dice, int modifier){
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
	
	
	public static final String damage(int mult,DICE dice, int mod) {
		StringBuilder sb = new StringBuilder();
		sb.append(mult);
		sb.append(dice.getDesc());
		if (mod>0) {
			sb.append("+");
		}
		if(mod!=0) {
			sb.append(mod);
		}
		return sb.toString();
	}
	
}