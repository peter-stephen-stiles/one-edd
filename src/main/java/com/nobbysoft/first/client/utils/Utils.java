package com.nobbysoft.first.client.utils;

import java.util.prefs.Preferences;
 

public class Utils {

	private Utils() {
		// 
	}
	
	public static final Preferences getPrefs(Class clazz) {
		
		return Preferences.userNodeForPackage(clazz).node(clazz.getSimpleName());
	}

	public static final String getMessage(Throwable t) {
		String s= t.getMessage();
		if(s==null) {
			s = t.toString();
		}
		return s;
	}
	
	public static final String stackTrace(Throwable t) {
		StringBuilder sb = new StringBuilder(getMessage(t));
		for(StackTraceElement ste:t.getStackTrace()) {
			sb.append(" at ");
			sb.append(ste.getClassName()).append(".");
			sb.append(ste.getMethodName()).append("(");
			sb.append(ste.getLineNumber()).append(")").append("\n");
			
		}
		if(t.getCause()!=null) {
			sb.append("caused by\n").append(stackTrace(t.getCause()));
		}
				return sb.toString();
	}

	
}
