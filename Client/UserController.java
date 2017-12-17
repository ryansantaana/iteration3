import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class UserController implements ActionListener {

	private Adapter dataAdapter;
	private UserView view;
	private JFileChooser fileExplorer = new JFileChooser();
	private File pic;

	public UserController(UserView view, Adapter dataAdapter) {
		this.dataAdapter = dataAdapter;
		this.view = view;

		view.getSelectPicture().addActionListener(this);
		view.getLoadUserBtn().addActionListener(this);
		view.getSaveUserBtn().addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getSelectPicture()) {
			try {
				if (fileExplorer.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
					pic = fileExplorer.getSelectedFile();
					BufferedImage img = null;
					img = ImageIO.read(pic);
					ImageIcon icon = new ImageIcon(img.getScaledInstance(300, 300, Image.SCALE_SMOOTH));
					view.getImageLabel().setIcon(icon);
					view.pack();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "An error occurred!   Could not load Image!");
			}
		}

		else if (e.getSource() == view.getLoadUserBtn()) {
			int id = analyzeID();
			if (id == -1) {
				return;
			}
			UserProxyInterface user = dataAdapter.loadUser(id);
			if (user == null) {
				JOptionPane.showMessageDialog(null, "User did not exist!");
				return;
			}
			view.getNameField().setText(user.getName());
			view.getIsManagerBox().setSelected(user.isManager());
			view.getPasswordField().setText("");
			try {
				BufferedImage img = user.getImage();
				view.getImageLabel().setIcon(new ImageIcon(img.getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
				view.pack();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "An error occurred! - loading Photo!");
			}
		} else if (e.getSource() == view.getSaveUserBtn()) {
			int id = analyzeID();
			if (id == -1) {
				return;
			}
			String username = view.getNameField().getText();
			if (username.contains("\"") || username.contains("\'")) {
				JOptionPane.showMessageDialog(null, "User name cannot contain quotation marks!");
				return;
			}

			if (dataAdapter.loadUser(id) != null) {
				int check = JOptionPane.showConfirmDialog(view,
						"User with this user ID already exists!  Are you sure you want to overwrite?");
				if (check != 0) {
					return;
				}
			}

			String password = new String(view.getPasswordField().getPassword());

			String salt = Authenticator.generateSalt();

			UserProxyInterface userSaving = new UserProxy();

			userSaving.setSalt(salt);
			userSaving.setIsManager(view.getIsManagerBox().isSelected());
			userSaving.setName(username);
			userSaving.setUserId(id);
			if (pic != null) {
				userSaving.setImagePath(pic.getAbsolutePath());
				try {
					BufferedImage img = ImageIO.read(pic);
					userSaving.setImage(img);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "An error occurred! Did not save Image!");
				}
			}

			if (!Application.getInstance().hasInitialManager() && userSaving.isManager()) {
				Application.getInstance().setHasInitialManager(true);
				Application.getInstance().setCurrentUser(userSaving);
				Application.getInstance().getManagerView().setVisible(true);
				view.setVisible(false);
			} else if (Application.getInstance().getCurrentUser() != null
					&& userSaving.getUserId() == Application.getInstance().getCurrentUser().getUserId()) {
				if (!userSaving.isManager()) {
					if (!dataAdapter.isAtLeastTwoManagerInDatabase()) {
						JOptionPane.showMessageDialog(null,
								"You are the only manager of the system! you cannot change your status from manager!");
						return;
					}
					int choice = JOptionPane.showConfirmDialog(view,
							"You will be logged out as a manager and be logged back in as a normal user. Are you sure?");
					if (choice != 0) {
						return;
					}
					view.setVisible(false);
					Application.getInstance().getProductView().setVisible(false);
					Application.getInstance().getManagerView().setVisible(false);
					Application.getInstance().getClerkView().setVisible(true);
				}
				Application.getInstance().setCurrentUser(userSaving);
			}

			if (view.getPasswordField().getPassword().length == 0) {
				if (dataAdapter.loadUser(id) == null) {
					password = Authenticator.generateSalt();
					JOptionPane.showMessageDialog(null,
							"The User has been saved, And here is the default password: " + password);
					userSaving.setHashPassword(Authenticator.hashString(password, salt));
				} else {
					UserProxyInterface user = dataAdapter.loadUser(id);
					userSaving.setHashPassword(user.getHashPassword());
					userSaving.setSalt(user.getSalt());
					JOptionPane.showMessageDialog(null,
							"The password has not been updated, but the user has been saved since no password was specified!");
				}
			} else {
				userSaving.setHashPassword(Authenticator.hashString(password, salt));
				JOptionPane.showMessageDialog(null, "The password has been updated and user has been saved!");
			}
			dataAdapter.writeUser(userSaving);
		}

	}

	private int analyzeID() {
		int userIDFromField = 0;
		String userIDInput = view.getUserIDField().getText();
		// check to see format of User ID input is correct!!
		try {
			userIDFromField = Integer.parseInt(userIDInput);
			if (userIDFromField <= 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "User ID must only contain positive integer!");
			return -1;
		}
		return userIDFromField;
	}

}
