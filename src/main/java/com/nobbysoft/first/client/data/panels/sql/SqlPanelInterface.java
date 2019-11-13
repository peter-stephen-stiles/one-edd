package com.nobbysoft.first.client.data.panels.sql;

import java.awt.event.ActionListener;

public interface SqlPanelInterface {
	
	public void setName(String name);
	public String getName();
	public void addActionListener(ActionListener al);

}
