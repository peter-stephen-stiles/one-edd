package com.nobbysoft.first.common.utils;

public class CodedListItem<T extends Comparable> implements Comparable<CodedListItem<T>>{

	public CodedListItem() { 
	}
	public CodedListItem(T item, String description) {
		this.item=item;
		this.description=description;
	}
	private T item;
	private String description;
	
	@Override
	public int compareTo(CodedListItem<T> o) {		
		return item.compareTo(o);
	}
	public T getItem() {
		return item;
	}
	public void setItem(T item) {
		this.item = item;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String toString(){
		return description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
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
		CodedListItem<?> other = (CodedListItem<?>) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}
	
	
	

}
