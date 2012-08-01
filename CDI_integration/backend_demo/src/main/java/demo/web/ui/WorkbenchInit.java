package demo.web.ui;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Initiator;

import org.zkoss.zkplus.cdi.DelegatingVariableResolver;
import demo.web.model.UserCredentialManager;

/**
 * @author zkessentials store
 * 
 *         This is a class which catches the initialization of a ZK page and
 *         redirects accordingly if no user credentials are found
 * 
 */
public class WorkbenchInit implements Initiator {

	
	private UserCredentialManager userCredentialManager;
	/*
	 * Invoked when the ZK Parser starts
	 */
	public void doInit(Page page, @SuppressWarnings("rawtypes") Map arg) throws Exception {
		if(userCredentialManager==null){
			userCredentialManager = (UserCredentialManager) 
			new DelegatingVariableResolver().resolveVariable("userCredentialManager");
		}
		if (!userCredentialManager.isAuthenticated()) {
			Executions.getCurrent().sendRedirect("login.zul");
		}
	}

	public boolean doCatch(Throwable parsingError) throws Exception {
		return false;
	}

	public void doAfterCompose(Page page) throws Exception {
	}

	public void doFinally() throws Exception {
	}

}
