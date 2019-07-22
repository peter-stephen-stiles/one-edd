package com.nobbysoft.com.nobbysoft.first.common.utils;

import java.util.Random;

public enum DICE{
	D2(2,"d2"),
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
	
	public static final DICE fromDescription(String desc) {
		for(DICE d:DICE.values()) {
			if(desc.equalsIgnoreCase(d.name())||desc.equalsIgnoreCase(d.getDesc())) {
				return d;
			}
		}
		return null;
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