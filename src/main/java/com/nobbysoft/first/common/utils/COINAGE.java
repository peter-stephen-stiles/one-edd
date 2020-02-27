package com.nobbysoft.first.common.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public enum COINAGE {
	CP(1,"Copper","c.p.",5),
	SP(10,"Silver","s.p.",4),
	EP(100,"Electrum","e.p.",3),
	GP(200,"Gold","g.p.",2),
	PP(1000,"Platinum","p.p.",1) ;		
	private final int worthInCp;
	private final String description;
	private final String abbr;
	private final int sequence;
	
	private COINAGE(int worthInCp, String description, String abbr, int sequence) {
		this.worthInCp=worthInCp;
		this.description=description;
		this.abbr=abbr;
		this.sequence=sequence;
	}

	public int getWorthInCp() {
		return worthInCp;
	}

	public String getDescription() {
		return description;
	}

	public String getAbbr() {
		return abbr;
	}
	
	public static String getValueString(Map<COINAGE,Integer> coins) {
		StringBuilder sb = new StringBuilder();
		List<COINAGE> list = new ArrayList<>();
		list.addAll(coins.keySet());
		list.sort(new Comparator<COINAGE>() {
			@Override
			public int compare(COINAGE o1, COINAGE o2) {				
				return o2.sequence - o1.sequence; 
			}});
		boolean first = true;
		for(COINAGE coin:list) {
			int num = coins.get(coin);
			if(num>0) {
				if(!first) {
					sb.append(", ");
				}
				first=false;
				sb.append(coin).append(" ").append(coin.getAbbr());
			}
		}
		
		return sb.toString().trim();
		
	}
	public static int getValueInCopper(Map<COINAGE,Integer> coins) {
		int copper=0;
		for(COINAGE coin:coins.keySet()) {
			int num = coin.getWorthInCp();
			copper+=num*coin.getWorthInCp();		
		}
		
		return copper;
	}
	
}
