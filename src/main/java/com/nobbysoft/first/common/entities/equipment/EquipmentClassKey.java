package com.nobbysoft.first.common.entities.equipment;

public class EquipmentClassKey implements Comparable<EquipmentClassKey>{

	
	private String type;
	private String code;
	private String classId;
	
	
	public EquipmentClassKey() {
	}


	public EquipmentClassKey(String type, String code,String classId) {
		super();
		this.type = type;
		this.code = code;
		this.classId=classId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	@Override
	public int compareTo(EquipmentClassKey o) {
		int ret = type.compareTo(o.getType());
		if(ret==0) {
			ret = code.compareTo(o.getCode());
			if(ret==0) {
				ret = classId.compareTo(o.getClassId());
			}
		}
		return ret;
	}


	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		EquipmentClassKey other = (EquipmentClassKey) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "EquipmentClassKey [type=" + type + ", code=" + code + ", classId=" + classId + "]";
	}

}
