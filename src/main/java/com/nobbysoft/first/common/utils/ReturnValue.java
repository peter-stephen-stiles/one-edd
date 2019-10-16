package com.nobbysoft.first.common.utils;

public class ReturnValue<T> {

	private String errorMessage;
	private T value;
	private boolean error=true;

	public enum IS_ERROR{TRUE,FALSE};
	 
	public ReturnValue(T value) { 
		this.value=value;
		this.error=false;
		this.errorMessage=null;
	}

	public ReturnValue(IS_ERROR error,String errorMessage) { 
		this.value=null;
		this.error=error==IS_ERROR.TRUE;
		this.errorMessage=errorMessage;
	}
	
	public ReturnValue(boolean error,String errorMessage) { 
		this.value=null;
		this.error=error;
		this.errorMessage=errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public T getValue() {
		return value;
	}

	public boolean isError() {
		return error;
	}

}
