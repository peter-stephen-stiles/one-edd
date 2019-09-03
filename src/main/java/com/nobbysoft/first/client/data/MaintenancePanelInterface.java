package com.nobbysoft.first.client.data;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.server.utils.ConnectionManager;

public interface MaintenancePanelInterface<T extends DataDTOInterface<?>> {

	public enum MODE{ADD,UPDATE,DELETE,COPY};
	 
	
	public ReturnValue<?> cancel(); // called when cancel pressed. this can return an error to say "oy! no!" but shouldn't
	public ReturnValue<?> ok();// called when ok pressed. this can return an error to say "oy! no!" if it goes wrong.

	public ReturnValue<?> initUpdate(T value,String instructions);
	public ReturnValue<?> initDelete(T value,String instructions);
	public ReturnValue<?> initCopy(T value,String instructions);
	public ReturnValue<?> initAdd(String instructions);
	
}
