import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProfileView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;
	private JLabel userNameLabel, userIDLabel;

	public JLabel getUserNameLabel() {
		return userNameLabel;
	}

	public void setUserNameLabel(JLabel userNameLabel) {
		this.userNameLabel = userNameLabel;
	}

	public JLabel getUserIDLabel() {
		return userIDLabel;
	}

	public void setUserIDLabel(JLabel userIDLabel) {
		this.userIDLabel = userIDLabel;
	}

	private Font font;
	private JButton updatePasswordBtn, updatePictureBtn;
	private Dimension screen;

	public Dimension getScreen() {
		return screen;
	}

	public void setScreen(Dimension screen) {
		this.screen = screen;
	}

	private JLabel imageLabel;

	public JButton getUpdatePasswordBtn() {
		return updatePasswordBtn;
	}

	public void setUpdatePasswordBtn(JButton updatePasswordBtn) {
		this.updatePasswordBtn = updatePasswordBtn;
	}

	public JButton getUpdatePictureBtn() {
		return updatePictureBtn;
	}

	public void setUpdatePictureBtn(JButton updatePictureBtn) {
		this.updatePictureBtn = updatePictureBtn;
	}

	public ProfileView() {
		super();
		this.setTitle("Profile");
		imageLabel = new JLabel();
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		font = new Font("Sans Serif", Font.BOLD, 28);
		mainPanel = new JPanel();
		userNameLabel = new JLabel("User Name: ");
		userIDLabel = new JLabel("User ID: ");
		userNameLabel.setFont(font);
		updatePasswordBtn = new JButton("Update Password");
		updatePictureBtn = new JButton("Update Profile Picture");
		userIDLabel.setFont(font);
		updatePasswordBtn.setFont(font);
		updatePictureBtn.setFont(font);

		mainPanel.add(userNameLabel);
		mainPanel.add(userIDLabel);
		mainPanel.add(updatePasswordBtn);
		mainPanel.add(updatePictureBtn);
		mainPanel.add(imageLabel);

		getContentPane().add(mainPanel);

		pack();
		setSize(500, 350);

		screen = Toolkit.getDefaultToolkit().getScreenSize();
		screen.setSize(screen.getWidth() / 2, screen.getHeight() / 2);
		setLocation((int) (screen.getWidth() - (this.getWidth() / 2)),
				(int) (screen.getHeight() - (this.getHeight() / 2)));

	}

	public ProfileView(GraphicsConfiguration gc) {
		super(gc);

	}

	public ProfileView(String title) throws HeadlessException {
		super(title);

	}

	public ProfileView(String title, GraphicsConfiguration gc) {
		super(title, gc);

	}

	public JLabel getImageLabel() {
		return imageLabel;
	}

	public void setImageLabel(JLabel imageLabel) {
		this.imageLabel = imageLabel;
	}

}
