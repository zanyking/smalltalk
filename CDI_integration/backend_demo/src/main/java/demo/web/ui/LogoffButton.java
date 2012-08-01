/* LogoffButton.java

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
package demo.web.ui;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.cdi.DelegatingVariableResolver;
import org.zkoss.zul.Button;

import demo.web.model.UserCredentialManager;

/**
 * @author Ian Y.T Tsai(zanyking)
 *
 */
public class LogoffButton extends Button {

		public void onClick(){
			UserCredentialManager userCredentialManager = (UserCredentialManager) 
				new DelegatingVariableResolver().resolveVariable("userCredentialManager");
			userCredentialManager.logOff();
			Sessions.getCurrent().invalidate();
			Executions.getCurrent().sendRedirect("login.zul");
		}
	
}
