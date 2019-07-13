package com.nobbysoft.com.nobbysoft.first.client.data.panels;

import java.awt.Window;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.client.components.PButton;

public interface DataButtonsInterface<T> {

	public List<String> getRowButtonNames();
	public List<String> getTableButtonNames();
	
	public void doRowButton(Window window,String name, T object);
	public void doTableButton(Window window,String name);
	
}
