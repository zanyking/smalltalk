/* UnAuthenticatedException.java

	Purpose:
		
	Description:
		
	History:
		Aug 1, 2012, Created by Ian Tsai(Zanyking)

Copyright (C) 2010 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under ZOL in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package org.zkoss.springdemo.web.model;

/**
 * @author Ian Y.T Tsai(zanyking)
 *
 */
public class UnAuthenticatedException extends RuntimeException {

	private static final long serialVersionUID = 3581530228974151382L;

	public UnAuthenticatedException() {
		super("You are not authenticated, please do login first!");
	}

	public UnAuthenticatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnAuthenticatedException(String message) {
		super(message);
	}

	public UnAuthenticatedException(Throwable cause) {
		super(cause);
	}

	
	
}
