import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class LoginView extends JFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints constraints = new GridBagConstraints();
	private JTextField userID;
	private JPasswordField pwdField;
	private JButton submit;
	private JLabel userLabel;
	private JLabel pwdLabel;
	private Font font;
	private JPanel container;
	private Dimension screen;

	public LoginView() {
		super();
		setTitle("Login");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		font = new Font("SansSerif Bold", Font.BOLD, 36);

		userID = new JTextField();

		submit = new JButton("Submit");
		submit.setFont(font);
		userLabel = new JLabel("User ID:");
		pwdLabel = new JLabel("Password:");
		userLabel.setFont(font);
		pwdLabel.setFont(font);

		pwdField = new JPasswordField();
		pwdField.setFont(font);
		userID.setFont(font);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = .5;
		constraints.weighty = .5;

		container = new JPanel(gbl);
		constraints.gridx = 0;
		constraints.gridy = 0;
		container.add(userLabel, constraints);
		constraints.gridx = 1;
		constraints.weightx = 100;
		container.add(userID, constraints);
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.weightx = .5;
		container.add(pwdLabel, constraints);
		constraints.gridx = 1;
		constraints.weightx = 100;
		container.add(pwdField, constraints);
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		container.add(submit, constraints);

		getContentPane().add(container);
		pack();
		setSize(800, 400);
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		screen.setSize(screen.getWidth() / 2, screen.getHeight() / 2);
		setLocation((int) (screen.getWidth() - (this.getWidth() / 2)),
				(int) (screen.getHeight() - (this.getHeight() / 2)));
	}

	public JButton getSubmitBtn() {
		return submit;
	}

	public JTextField getUserID() {
		return userID;
	}

	public JPasswordField getPassword() {
		return pwdField;
	}

}