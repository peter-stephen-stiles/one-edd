package com.nobbysoft.com.nobbysoft.first.common.entities.equipment;

public interface CostI {

	public int getCostCP();
	public void setCostCP(int costCp);
	
	public enum COIN{
		COPPER(1,"cp"),
		SILVER(10,"sp"),
		ELECTRUM(20,"ep"),
		GOLD(100,"gp"),
		PLATINUM(500,"pp")
		;
		private final int copperValue;
		private final String desc;
		COIN(int copperValue,String desc){
			this.copperValue=copperValue;
			this.desc=desc;
		}
		
		public int toCopper(int value) {
			return value * copperValue;
		}
		
		
		public int fromCopper(int value) {
			return value / copperValue;
		}
		public int remainderFromCopper(int value) {
			return value % copperValue;
		}
		
		
		public int convertTo(int value,COIN to) {
			if(to==this) {
				return value;
			}
			return to.fromCopper(toCopper(value));
		}
		
		/**
		 * NOTE: changing UP loses money
		 * so changing 1cp to Gold is ZERO
		 * 
		 * @param value
		 * @param from
		 * @param to
		 * @return
		 */
		public static int convert(int value,COIN from, COIN to) {
			if(from==to) {
				return value;
			}			
			return to.fromCopper(from.toCopper(value));
		}
	};
	
}
