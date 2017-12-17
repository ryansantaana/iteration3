import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ClerkView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JButton btnCheckout = new JButton("Checkout");
	protected JButton btnEditProfile = new JButton("Edit Profile");
	protected Font font;
	protected JPanel panelButton;
	private Dimension screen;

	public ClerkView() {
		super();
		this.setTitle("Clerk Console");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		font = new Font("Sans Serif", Font.BOLD, 28);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		btnCheckout.setPreferredSize(new Dimension(300, 100));
		btnCheckout.setFont(font);
		btnEditProfile.setPreferredSize(new Dimension(300, 100));
		btnEditProfile.setFont(font);

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?") == 0) {
					// bring up Main Login
					Application.getInstance().getLoginScreen().setVisible(true);
					// make invisible
					setVisible(false);
					// make other open windows invisible
					Application.getInstance().getCheckoutView().dispatchEvent(
							new WindowEvent(Application.getInstance().getCheckoutView(), WindowEvent.WINDOW_CLOSING));
					Application.getInstance().getPaymentView().dispatchEvent(
							new WindowEvent(Application.getInstance().getPaymentView(), WindowEvent.WINDOW_CLOSING));
					Application.getInstance().getBusinessReportView().dispatchEvent(
							new WindowEvent(Application.getInstance().getCheckoutView(), WindowEvent.WINDOW_CLOSING));
					Application.getInstance().getUserView().dispatchEvent(
							new WindowEvent(Application.getInstance().getPaymentView(), WindowEvent.WINDOW_CLOSING));
					Application.getInstance().getProductView().dispatchEvent(
							new WindowEvent(Application.getInstance().getCheckoutView(), WindowEvent.WINDOW_CLOSING));
					Application.getInstance().getProfileView().dispatchEvent(
							new WindowEvent(Application.getInstance().getPaymentView(), WindowEvent.WINDOW_CLOSING));
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}

		});

		JLabel title = new JLabel("Store Management System");
		title.setFont(font);
		JPanel panelTitle = new JPanel();
		panelTitle.add(title);
		this.getContentPane().add(panelTitle);

		panelButton = new JPanel();
		panelButton.add(btnEditProfile);
		panelButton.add(btnCheckout);

		this.getContentPane().add(panelButton);

		btnEditProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Application.getInstance().getProfileController().loadPicture();
				Application.getInstance().getProfileView().setVisible(true);

			}

		});
		btnCheckout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!Application.getInstance().getDataAdapter().isAtLeastOneProductInDatabase()) {
					JOptionPane.showMessageDialog(null, "There are no products in database!");
					return;
				}
				Application.getInstance().getCheckoutView().setVisible(true);
			}
		});

		pack();
		setSize(700, 700);
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		screen.setSize(screen.getWidth() / 2, screen.getHeight() / 2);
		setLocation((int) (screen.getWidth() - (this.getWidth() / 2)),
				(int) (screen.getHeight() - (this.getHeight() / 2)));
		setExtendedState(getExtendedState() | Frame.MAXIMIZED_BOTH);
	}

}
