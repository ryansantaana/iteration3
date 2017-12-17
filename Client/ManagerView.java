import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ManagerView extends ClerkView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton btnManageProduct = new JButton("Manage Product");
	private JButton btnManageUser = new JButton("Manage Users");
	private JButton btnReport = new JButton("Generate Business Report");

	public ManagerView() {
		super();
		this.setTitle("Management Console");

		btnReport.setPreferredSize(new Dimension(400, 100));
		btnManageProduct.setPreferredSize(new Dimension(300, 100));
		btnManageUser.setPreferredSize(new Dimension(300, 100));

		btnReport.setFont(font);
		btnManageProduct.setFont(font);
		btnManageUser.setFont(font);

		panelButton.add(btnReport);
		panelButton.add(btnManageProduct);
		panelButton.add(btnManageUser);

		btnManageProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.getInstance().getProductView().setVisible(true);
			}
		});
		btnManageUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.getInstance().getUserView().setVisible(true);
			}
		});
		btnReport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Application.getInstance().getBusinessReportView().setVisible(true);
			}

		});
		pack();
	}

}
