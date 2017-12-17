import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ProfileController implements ActionListener {

	private ProfileView pv;
	private Adapter data;
	private JFileChooser fileExplorer = new JFileChooser();
	private File pic;

	public ProfileController(ProfileView pv, Adapter data) {
		this.pv = pv;
		this.data = data;
		pv.getUpdatePasswordBtn().addActionListener(this);
		pv.getUpdatePictureBtn().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pv.getUpdatePasswordBtn()) {
			String input = JOptionPane.showInputDialog("Input current password");
			if (Authenticator.isCorrectPassword(input, Application.getInstance().getCurrentUser().getHashPassword(),
					Application.getInstance().getCurrentUser().getSalt())) {
				input = JOptionPane.showInputDialog("Input new password");
				if (input.equals(JOptionPane.showInputDialog("Confirm new password"))) {
					UserProxyInterface toSave = Application.getInstance().getCurrentUser();
					toSave.setHashPassword(Authenticator.hashString(input, toSave.getSalt()));
					data.writeUser(toSave);
					Application.getInstance().setCurrentUser(toSave);
				} else {
					JOptionPane.showMessageDialog(null, "Passwords didn't match!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Incorrect password!");
			}
		} else if (e.getSource() == pv.getUpdatePictureBtn()) {
			//
			try {
				if (fileExplorer.showOpenDialog(pv) == JFileChooser.APPROVE_OPTION) {
					pic = fileExplorer.getSelectedFile();
					BufferedImage img = null;
					img = ImageIO.read(pic);
					pv.getImageLabel().setIcon(new ImageIcon(img.getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
					UserProxyInterface user = Application.getInstance().getCurrentUser();
					user.setImage(img);
		 
					//user.setImagePath(pic.getAbsolutePath());
					//Application.getInstance().setCurrentUser(user);

					pv.pack();
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "An error occurred!  Could not update Picture!");
			}
		}

	}

	public void loadPicture() {
		UserProxyInterface user = Application.getInstance().getCurrentUser();
		try {
			BufferedImage img = user.getImage();
			pv.getImageLabel().setIcon(new ImageIcon(img.getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
			pv.pack();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "An error occurred!    Could not Load Picture!");
		}
		pv.getUserIDLabel().setText("User ID: " + Application.getInstance().getCurrentUser().getUserId());
		pv.getUserNameLabel().setText("User Name: " + Application.getInstance().getCurrentUser().getName());
		pv.setLocation((int) (pv.getScreen().getWidth() - (pv.getWidth() / 2)),
				(int) (pv.getScreen().getHeight() - (pv.getHeight() / 2)));

	}

}
