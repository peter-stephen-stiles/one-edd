package com.nobbysoft.first.client.data.panels;

import java.awt.Window;
import java.util.List;

import com.nobbysoft.first.client.components.PButton;

public interface DatasButtonsInterface<T> {

	public List<String> getRowButtonNames();
	public List<String> getTableButtonNames();
	
	public boolean doRowsButton(Window window,String name, List<T> objecta);
	public boolean doTableButton(Window window,String name);
	
}
