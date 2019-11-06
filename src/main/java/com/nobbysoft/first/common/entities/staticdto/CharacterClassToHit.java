package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.utils.ToHitUtils;

@SuppressWarnings("serial")
public class CharacterClassToHit implements Serializable, DataDTOInterface<CharacterClassToHitKey> {

	private String classId;
	private int fromLevel;
	private int toLevel;
	private int biggestACHitBy20;
	
	
	public CharacterClassToHit() {
	}


	@Override
	public CharacterClassToHitKey getKey() {
		return new CharacterClassToHitKey(classId,fromLevel,toLevel) ;
	}


	@Override
	public String getDescription() {
		return ""+classId+":"+fromLevel+":"+toLevel+":"+biggestACHitBy20;
	}

	private Map<Integer,Integer> getACToHitMap(){
		return ToHitUtils.getACToHitMap(biggestACHitBy20);
	}

	@Override
	public Object[] getAsRow() {
		

		
		List<Object> list = new ArrayList<>();
		list.add(this);
		list.add(classId);
		list.add(fromLevel);
		list.add(toLevel);
		Map<Integer,Integer> map=getACToHitMap();
		for( int i=-10, n = 11;i<n;i++) {
			list.add(map.get(i));
		}
		
		return list.toArray(new Object[list.size()]);
		
	}


	@Override
	public String[] getRowHeadings() {
		
		List<String> list = new ArrayList<>();
		list.add("");
		list.add("Class");
		list.add("From Level");
		list.add("To Level");
		for( int i=-10, n = 11;i<n;i++) {
			list.add("AC "+i);
		}
		
		return list.toArray(new String[list.size()]);
		
 
	}


	@Override
	public Integer[] getColumnWidths() {

		List<Integer> list = new ArrayList<>();
		list.add(0);
		list.add(100);
		list.add(80);
		list.add(80);
		for( int i=-10, n = 11;i<n;i++) {
			list.add(50);
		}
		
		return list.toArray(new Integer[list.size()]);
		

	}

	

	@Override
	public String[] getColumnCodedListTypes() {
		
		List<String> head = new ArrayList<>();
		
		head.add(Constants.CLI_CLASS);
		head.add(null);
		head.add(null);
		for( int i=-10, n = 11;i<n;i++) {
			head.add(null);
		}
		head.add(null);
		return head.toArray(new String[head.size()]);


	}



	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getFromLevel() {
		return fromLevel;
	}

	public void setFromLevel(int fromLevel) {
		this.fromLevel = fromLevel;
	}

	public int getToLevel() {
		return toLevel;
	}

	public CharacterClassToHit(String classId, int fromLevel, int toLevel, int biggestACHitBy20) {
		super();
		this.classId = classId;
		this.fromLevel = fromLevel;
		this.toLevel = toLevel;
		this.biggestACHitBy20 = biggestACHitBy20;
	}

	public void setToLevel(int toLevel) {
		this.toLevel = toLevel;
	}

	public int getBiggestACHitBy20() {
		return biggestACHitBy20;
	}

	public void setBiggestACHitBy20(int biggestACHitBy20) {
		this.biggestACHitBy20 = biggestACHitBy20;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + biggestACHitBy20;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + fromLevel;
		result = prime * result + toLevel;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterClassToHit other = (CharacterClassToHit) obj;
		if (biggestACHitBy20 != other.biggestACHitBy20)
			return false;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (fromLevel != other.fromLevel)
			return false;
		if (toLevel != other.toLevel)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String s= "CharacterClassToHitKey [";
		boolean first=true;
		for(Object o: getAsRow()) {
			if(!first) {
				s = s+" "+o;
				s = s+",";
			}
			first=false;			
		}
		
		s=s + "]";
		return s;
	}

 

	
}
