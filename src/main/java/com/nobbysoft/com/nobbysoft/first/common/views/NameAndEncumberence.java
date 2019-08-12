package com.nobbysoft.com.nobbysoft.first.common.views;

public class NameAndEncumberence {


	private String name;
	private int encumberence;
	
	public NameAndEncumberence(String name, int encumberence) {
		super();
		this.name = name;
		this.encumberence = encumberence;
	}

	public NameAndEncumberence() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEncumberence() {
		return encumberence;
	}

	public void setEncumberence(int encumberence) {
		this.encumberence = encumberence;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + encumberence;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		NameAndEncumberence other = (NameAndEncumberence) obj;
		if (encumberence != other.encumberence)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NameAndEncumberence [name=" + name + ", encumberence=" + encumberence + "]";
	}

}
