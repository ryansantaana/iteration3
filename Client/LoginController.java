import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

public class LoginController implements ActionListener, WindowListener {

	private LoginView view;
	private Adapter dataAdapter;

	public LoginController(LoginView view, Adapter dataAdapter) {
		this.view = view;
		this.dataAdapter = dataAdapter;

		view.getSubmitBtn().addActionListener(this);
		view.addWindowListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getSubmitBtn()) {
			// check password here.
			int userIDFromField = 0;
			String userIDInput = view.getUserID().getText();
			// check to see format of User ID input is correct!!
			try {
				userIDFromField = Integer.parseInt(userIDInput);
				if (userIDFromField <= 0) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "User ID must only contain positive integer!");
				return;
			}
			UserProxyInterface current = dataAdapter.loadUser(userIDFromField);
			if (current == null) {
				JOptionPane.showMessageDialog(null, "User does not exist!");
				return;
			}
			String passwordInput = new String(view.getPassword().getPassword());
			if (Authenticator.isCorrectPassword(passwordInput, current.getHashPassword(), current.getSalt())) {
				// login is successfull, go to management console if manager,
				// but only checkout screen if not manager
				if (current.isManager()) {
					Application.getInstance().getManagerView().setVisible(true);
				} else {
					Application.getInstance().getClerkView().setVisible(true);
				}
				Application.getInstance().setCurrentUser(current);
				view.setVisible(false);
				view.getPassword().setText("");
				view.getUserID().setText("");
			} else {
				JOptionPane.showMessageDialog(null, "Incorrect password for your user ID!");
				return;
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		((DataAdapter) Application.getInstance().getDataAdapter()).close();
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
