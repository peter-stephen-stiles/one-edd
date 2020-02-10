package com.nobbysoft.first.common.entities.staticdto;

public class RaceSkillKey  
	
	implements Comparable<RaceSkillKey> {


		private String raceId;
		private String skillId;
		public RaceSkillKey(String raceId, String skillId ) {
			super();
			this.raceId = raceId;
			this.skillId = skillId;
		}
		public RaceSkillKey() { 
		}
		
		protected String compareKeyValue() {
			return raceId+"|"+skillId;
		}
		
		@Override
		public int compareTo(RaceSkillKey o) { 
			return compareKeyValue().compareTo(o.compareKeyValue());
		}
		public String getRaceId() {
			return raceId;
		}
		public void setRaceId(String raceId) {
			this.raceId = raceId;
		}
		public String getSkillId() {
			return skillId;
		}
		public void setSkillId(String skillId) {
			this.skillId = skillId;
		}

}
