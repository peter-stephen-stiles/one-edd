package com.nobbysoft.com.nobbysoft.first.client.components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class LengthLimitingDocument extends PlainDocument {
	  private int limit;
	  private boolean upper=false;
	  private boolean code=false;
	  public LengthLimitingDocument(int limit) {
	    super();
	    this.limit = limit;
	  }

	  public  LengthLimitingDocument(int limit, boolean upper) {
	    super();
	    this.limit = limit;
	    this.upper=upper;
	  }


	  public  LengthLimitingDocument(int limit, boolean upper,boolean code) {
	    super();
	    this.limit = limit;
	    this.upper=upper;
	    this.code=code;
	  }
	  
	  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
	    if (str == null) {
	      return;
	    }
	    if(upper){
	    	str=str.toUpperCase();
	    }
	    if(code){
	    	str=str.replace(' ', '_');
	    }
	    if ((getLength() + str.length()) <= limit) {
	      super.insertString(offset, str, attr);
	    }
	  }
	}
 
 
