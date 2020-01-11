package com.nobbysoft.first.common.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.xml.internal.serializer.ToStream;

public class SU {

	private SU() {
	}

	/**
	 * only works for strings :)
	 * @param value
	 * @param strings
	 * @return
	 */
	public static boolean inListCaseInsensitive(String value, String... strings ) {
		for(String string:strings) {
			if(string==null) {
				if(value==null) {
					return true;
				}
			} else if(string.equalsIgnoreCase(value)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * works for any object type
	 * @param value
	 * @param strings
	 * @return
	 */
	public static boolean inList(Object value, Object... strings ) {
		for(Object string:strings) {
			if(string==null) {
				if(value==null) {
					return true;
				}
			} else if(string.equals(value)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * string form of a chance on a D6 i.e. 1/6 or 3/6; zero not displayed
	 * @param value
	 * @return
	 */
	public static String i6(int value) {
		if(value==0) {
			return "";
		} else {
			return ""+value+"/6";
		} 
	}	
		
	
	/**
	 * string form of a percentage i.e. 35% or -16%; zero not displayed
	 * @param value
	 * @return
	 */
	public static String p(int value) {
		if(value>0) {
			return ""+value+"%";
		} else if (value<0) {
			return ""+value+"%";
		} else {
			return "";
		}
	}	
	
	public static String p(int value, String zeroValue) {
		if(value>0) {
			return ""+value+"%";
		} else if (value<0) {
			return ""+value+"%";
		} else {
			return zeroValue;
		}
	}	
	
	
	/**
	 * string form of a percentage "adjustment" i.e. +35% or -16%; zero not displayed
	 * @param value
	 * @return
	 */
	public static String ap(int value) {
		if(value>0) {
			return "+"+value+"%";
		} else if (value<0) {
			return ""+value+"%";
		} else {
			return "";
		}
	}
	
	public static String ap(int value, String zeroValue) {
		if(value>0) {
			return "+"+value+"%";
		} else if (value<0) {
			return ""+value+"%";
		} else {
			return zeroValue;
		}
	}
	
	/**
	 * string form of an "adjustment" i.e. +32 or -4; zero not displayed
	 * @param value
	 * @return
	 */
	public static String a(int value) {
		if(value>0) {
			return "+"+value;
		} else if (value<0) {
			return ""+value;
		} else {
			return "";
		}
	}

	public static String a(int value,String zeroValue) {
		if(value>0) {
			return "+"+value;
		} else if (value<0) {
			return ""+value;
		} else {
			return zeroValue;
		}
	}
	
	public static final boolean notBlank(String s) {
		if(s==null) {
			return false;
		}
		return !(s.trim().isEmpty());
	}
	
	public static final boolean blank(String s) {
		if(s==null) {
			return true;
		}
		return (s.trim().isEmpty());
	}
	
	private static final Map<Class,Method> dmethods = new HashMap<>(); 
	public static final String getDescription(Object value) {
		if(value==null) {
			return "";
		}
		String sv = value.toString();
		// if its got a description method, use that!
		Class<?> clazz=value.getClass();
		try {
			if(dmethods.containsKey(clazz)) {
				Method m =dmethods.get(clazz);
				if(m!=null) {
					sv=(String)m.invoke(value, new Object[0]);
				}
			} else {
				Method m = null;
				try {
					m=value.getClass().getMethod("getDescription", new Class<?>[0]);
					sv=(String)m.invoke(value, new Object[0]);
				} catch (NoSuchMethodException xx) {
					// ignore!
					sv=value.toString();
				}
				dmethods.put(clazz, m); // store anyway, stops repeated attempts to get description!			
			}
		} catch (Exception e) {
			// no panic - no description method..
			sv=value.toString();
		}
		
		return sv;
	}
	
	public static final String proper(String s) {
		String t = s.toLowerCase().trim();
		StringBuilder sb = new StringBuilder();
		char last=' ';
		for(int i=0,n=t.length();i<n;i++) {
			char now=t.charAt(i);
			if(last>='a' && last<='z') {
				// prior char was alpha so use as is
				sb.append(now);
			} else {				
				sb.append(String.valueOf(now).toUpperCase());
			}
			last=now;
		}
		
		return sb.toString();
		}

}
