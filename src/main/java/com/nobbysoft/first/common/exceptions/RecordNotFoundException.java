package com.nobbysoft.first.common.exceptions;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class RecordNotFoundException extends SQLException {

	public RecordNotFoundException() {
	}

	public RecordNotFoundException(String reason) {
		super(reason);
	}

	public RecordNotFoundException(Throwable cause) {
		super(cause);
	}

 

	public RecordNotFoundException(String reason, Throwable cause) {
		super(reason, cause);
	}
 

}
