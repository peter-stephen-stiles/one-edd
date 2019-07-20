package com.nobbysoft.com.nobbysoft.first.common.utils;

public class Roller {
 
	public static DICE getDICE(int sides) {
		for(DICE d:DICE.values()) {
			if(sides==d.getSides()) {
				return d;
			}
		}
		return null;
	}
	public static final int[] rollHistory(DICE dice, int multiple, int perDiceAdds) {
		int[] rolls = new int[multiple];
		for(int i=0,n=multiple;i<n;i++) {
			rolls[i] =dice.roll() +  perDiceAdds;
		}
		return rolls;
	}
	
	public static final int roll(DICE dice, int multiple, int perDiceAdds, int totalAdd) {
		int total=0;
		for(int i=0,n=multiple;i<n;i++) {
			total+=dice.roll();
			total+=perDiceAdds;
		}
		total+=totalAdd;
		return total;
	}
	
	private Roller() { 
	}

}
