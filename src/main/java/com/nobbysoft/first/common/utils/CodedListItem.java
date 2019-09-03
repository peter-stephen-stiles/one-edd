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

}
