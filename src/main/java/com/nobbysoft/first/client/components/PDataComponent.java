package com.nobbysoft.first.client.components;

import java.awt.event.ActionListener;

public interface PDataComponent {

	
	public void setTheValue(Object o);
	public Object getTheValue();
	public void setReadOnly(boolean readOnly);
	public boolean isReadOnly();
	
	public void setMinimumHeight(int height);
	public void setMinimumWidth(int width); 
}
