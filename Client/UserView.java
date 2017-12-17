import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton saveUserBtn;
	private Dimension screen;
	private JTextField userIDField;
	private JLabel userName;
	private JLabel userLabel;
	private JButton selectPicture;
	private JLabel pwdLabel;
	private JTextField nameField;
	private JLabel imageLabel;

	public JTextField getNameField() {
		return nameField;
	}

	public void setNameField(JTextField nameField) {
		this.nameField = nameField;
	}

	private JLabel managerLabel;
	private JPasswordField passwordField;
	private JButton loadUserBtn;
	private JCheckBox isManagerBox;
	private GridBagLayout gbl;
	private Dimension preferredSize;
	private Font font;
	private GridBagConstraints constraints;

	public JCheckBox getIsManagerBox() {
		return isManagerBox;
	}

	public void setIsManagerBox(JCheckBox isManagerBox) {
		this.isManagerBox = isManagerBox;
	}

	public UserView() {
		super();
		selectPicture = new JButton("Choose Picture");
		setTitle("Manage Users");
		font = new Font("Sans Serif", Font.BOLD, 28);
		gbl = new GridBagLayout();
		JPanel container = new JPanel(gbl);
		saveUserBtn = new JButton("Save User");
		loadUserBtn = new JButton("Load User");
		saveUserBtn.setPreferredSize(preferredSize);
		loadUserBtn.setPreferredSize(preferredSize);

		selectPicture.setPreferredSize(preferredSize);

		selectPicture.setFont(font);

		imageLabel = new JLabel();
		imageLabel.setSize(100, 100);
		userLabel = new JLabel("User ID:");
		userName = new JLabel("User Name:");
		pwdLabel = new JLabel("Password:");
		managerLabel = new JLabel("manager?");
		passwordField = new JPasswordField();
		nameField = new JTextField();
		userIDField = new JTextField();
		isManagerBox = new JCheckBox();

		saveUserBtn.setFont(font);
		loadUserBtn.setFont(font);

		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 1.0;

		container.add(saveUserBtn, constraints);
		constraints.gridx = 1;
		container.add(loadUserBtn, constraints);
		getContentPane().add(container, BorderLayout.NORTH);

		container = new JPanel(gbl);
		userLabel.setFont(font);
		managerLabel.setFont(font);
		pwdLabel.setFont(font);
		passwordField.setFont(font);
		userIDField.setFont(font);
		userName.setFont(font);
		nameField.setFont(font);

		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 1.0;
		container.add(userName, constraints);
		constraints.weightx = 100;
		constraints.gridx = 1;
		container.add(nameField, constraints);
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 1;
		container.add(userLabel, constraints);
		constraints.weightx = 100;
		constraints.gridx = 1;
		container.add(userIDField, constraints);
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 2;
		container.add(pwdLabel, constraints);
		constraints.weightx = 100;
		constraints.gridx = 1;
		container.add(passwordField, constraints);
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 3;
		container.add(managerLabel, constraints);
		constraints.weightx = 100;
		constraints.gridx = 1;
		container.add(isManagerBox, constraints);
		JPanel imagePanel = new JPanel(new FlowLayout());

		imagePanel.add(selectPicture);
		imagePanel.add(imageLabel);
		getContentPane().add(container, BorderLayout.CENTER);
		getContentPane().add(imagePanel, BorderLayout.SOUTH);
		pack();
		setSize(600, 800);
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		screen.setSize(screen.getWidth() / 2, screen.getHeight() / 2);
		setLocation((int) (screen.getWidth() - (this.getWidth() / 2)),
				(int) (screen.getHeight() - (this.getHeight() / 2)));
	}

	public JButton getSaveUserBtn() {
		return saveUserBtn;
	}

	public void setSaveUserBtn(JButton saveUserBtn) {
		this.saveUserBtn = saveUserBtn;
	}

	public JTextField getUserIDField() {
		return userIDField;
	}

	public void setUserIDField(JTextField userIDField) {
		this.userIDField = userIDField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public JButton getLoadUserBtn() {
		return loadUserBtn;
	}

	public void setLoadUserBtn(JButton loadUserBtn) {
		this.loadUserBtn = loadUserBtn;
	}

	public JButton getSelectPicture() {
		return selectPicture;
	}

	public void setSelectPicture(JButton selectPicture) {
		this.selectPicture = selectPicture;
	}

	public JLabel getImageLabel() {
		return imageLabel;
	}

	public void setImageLabel(JLabel imageLabel) {
		this.imageLabel = imageLabel;
	}

}
