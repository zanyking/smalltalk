package demo.web.ui.ctrl;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import demo.web.model.UserCredentialManager;

/**
 * @author zkessentials store
 * 
 *         This is the controller for the login view as referenced in index.zul
 * 
 */
@VariableResolver(org.zkoss.zkplus.cdi.DelegatingVariableResolver.class)
public class LoginViewCtrl extends SelectorComposer<Window> {

	private static final long serialVersionUID = 5730426085235946339L;
	
	@Wire
	private Textbox nameTxb, passwordTxb;
	
	@Wire
	private Label mesgLbl;
	
	@WireVariable("userCredentialManager")
	private UserCredentialManager userCredentialManager;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if (userCredentialManager.isAuthenticated()) {
			Executions.getCurrent().sendRedirect("index.zul");
		}
		nameTxb.setFocus(true);

	}
	
	@Listen("onOK=#passwordTxb")
	public void onOK() {
		doLogin();
	}
	
	@Listen("onClick=#confirmBtn")
	public void confirm() {
		doLogin();
	}

	private void doLogin() {
		userCredentialManager.login(nameTxb.getValue(), passwordTxb.getValue());
		if (userCredentialManager.isAuthenticated()) {
			Executions.getCurrent().sendRedirect("index.zul");
		} else {
			mesgLbl.setValue("Your User Name or Password is invalid!");
		}
	}

}
