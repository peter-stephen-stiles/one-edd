package com.nobbysoft.first.client.components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class IntegerLimitingDocument extends PlainDocument {
 
	private boolean allowNegative =false;
	
	  public boolean isAllowNegative() {
		return allowNegative;
	}
	public void setAllowNegative(boolean allowNegative) {
		this.allowNegative = allowNegative;
	}
	public IntegerLimitingDocument( ) {
	    super(); 
	  }
	  public IntegerLimitingDocument( boolean allowNegative) {
		    super(); 
		    this.allowNegative=allowNegative;
		  }
	  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
	    if (str == null){
	      return;
	    }
	    StringBuilder sb=new StringBuilder();
	    for(int i=0,n=str.length();i<n;i++){
	    	char c = str.charAt(i);
	    	if(c>='0' && c <='9'){
	    		sb.append(c);
	    	} else if(allowNegative && i==0 && c == '-') {
	    		sb.append(c);
	    	}
	    }
	    str = sb.toString(); 
	    if(!str.isEmpty()) {
	    	super.insertString(offset, str, attr); 
	    }
	  }
	}


