package com.nobbysoft.com.nobbysoft.first.common.utils;

import java.util.Random;

public class Roller {

	public enum DICE{
		D3(3,"d3"),
		D4(4,"d4"),
		D6(6,"d6"),
		D8(8,"d8"),
		D10(10,"d10"),
		D12(12,"d12"),
		D20(20,"d20"),
		D100(100,"d%"),
		D1000(1000,"d1000");
		private Random random = new Random();
		public int roll() {
			// next int gives number between ZERO and N-1
			// so given 6 would give 0 or 1 or 2 or 3 or 4 or 5
			// so we need to add one
			return 1+ random.nextInt(sides);
		}
		private final String desc;
		private final int sides;
		DICE(int sides,String desc){
			this.sides=sides;
			this.desc=desc;
		}
		public String getDesc() {
			return desc;
		}
		public int getSides() {
			return sides;
		}
	}
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
