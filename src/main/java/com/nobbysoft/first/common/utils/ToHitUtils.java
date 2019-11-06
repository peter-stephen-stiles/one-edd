package com.nobbysoft.first.common.utils;

import java.util.HashMap;
import java.util.Map;

public class ToHitUtils {

	private ToHitUtils() {

	}

	public static Map<Integer,Integer> getACToHitMap(int biggestACHitBy20){
		//

		Map<Integer,Integer> values = new HashMap<>();
		int ac =biggestACHitBy20;

		int toHit =20;
		values.put(ac, toHit);
		
		int countTwenties =1;
		while(ac>=-10) {
			ac--;			
			if(countTwenties>=6) {
				toHit++;
			}
			countTwenties++;
			values.put(ac, toHit);	
		}
		// 20 = AC 0
		// 19 = AC 1
		// 18 = AC 2
		ac = biggestACHitBy20;
		toHit =20;
		for(int i=biggestACHitBy20,n=10;i<n;i++) {			
			ac =i+1;
			toHit--;
			values.put(ac, toHit);
		}
		
		return values;
	}	
	
}
