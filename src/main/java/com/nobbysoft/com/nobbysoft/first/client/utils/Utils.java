package com.nobbysoft.com.nobbysoft.first.client.utils;

import java.util.prefs.Preferences;
 

public class Utils {

	private Utils() {
		// 
	}
	
	public static final Preferences getPrefs(Class clazz) {
		
		return Preferences.userNodeForPackage(clazz).node(clazz.getSimpleName());
	}

}
