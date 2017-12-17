import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.math.BigDecimal;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PaymentView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnCash, btnEBT, btnCredit;
	private JPanel container;
	private JLabel AmountDueLabel;
	private BigDecimal amountDue;

	private Font font;
	private Dimension screen;

	public PaymentView(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public PaymentView() {
		super("Payment Menu");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		btnCash = new JButton("Cash");
		btnEBT = new JButton("EBT");
		btnCredit = new JButton("Credit");
		container = new JPanel();
		font = new Font("Sans Serif", Font.BOLD, 28);
		btnCash.setFont(font);
		btnEBT.setFont(font);
		btnCredit.setFont(font);

		amountDue = getAmountDue();
		AmountDueLabel = new JLabel("Amount Due: $" + amountDue);
		AmountDueLabel.setFont(font);

		btnCash.setPreferredSize(new Dimension(300, 100));
		btnEBT.setPreferredSize(new Dimension(300, 100));
		btnCredit.setPreferredSize(new Dimension(300, 100));

		container.add(btnCash);
		container.add(btnEBT);
		container.add(btnCredit);

		getContentPane().add(container);
		getContentPane().add(AmountDueLabel);
		pack();
		setSize(700, 700);
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		screen.setSize(screen.getWidth() / 2, screen.getHeight() / 2);
		setLocation((int) (screen.getWidth() - (this.getWidth() / 2)),
				(int) (screen.getHeight() - (this.getHeight() / 2)));
		setExtendedState(getExtendedState() | Frame.MAXIMIZED_BOTH);

	}

	public BigDecimal getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(BigDecimal amountDueIn) {
		amountDue = amountDueIn;
	}

	public JPanel getJPanel() {
		return container;
	}

	public JLabel getAmountDueLabel() {
		return AmountDueLabel;
	}

	public JButton getBtnCash() {
		return btnCash;
	}

	public JButton getBtnEBT() {
		return btnEBT;
	}

	public JButton getBtnCredit() {
		return btnCredit;
	}

	public PaymentView(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

}
