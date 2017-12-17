import javax.swing.*;
import java.awt.*;

public class CheckoutView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnAdd = new JButton("Add a new item");
	private JButton btnPay = new JButton("Finish and Pay");

	private ItemTable items = new ItemTable(); // store information for the
												// table!

	private JTable tblItems = new JTable(items); // null, new
													// String[]{"ProductID",
													// "Product Name", "Price",
													// "Quantity", "Cost"});
	private JLabel labTotal = new JLabel("Total: $0.00");
	private JLabel subTotalCost = new JLabel("Subtotal: $0.00");
	private Dimension screen;

	private Font font;

	public CheckoutView() {

		this.setTitle("Checkout");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		setExtendedState(getExtendedState() | Frame.MAXIMIZED_BOTH);

		btnAdd.setPreferredSize(new Dimension(300, 100));
		btnPay.setPreferredSize(new Dimension(300, 100));

		font = new Font("Sans Serif", Font.BOLD, 32);

		labTotal.setFont(font);
		subTotalCost.setFont(font);

		btnAdd.setFont(font);
		btnPay.setFont(font);

		items.addColumn("Product ID");
		items.addColumn("Name");
		items.addColumn("Price");
		items.addColumn("Quantity");
		items.addColumn("Cost");
		items.addColumn("Tax");

		JPanel panelOrder = new JPanel();
		panelOrder.setPreferredSize(new Dimension(400, 450));
		panelOrder.setLayout(new BoxLayout(panelOrder, BoxLayout.PAGE_AXIS));
		tblItems.setBounds(0, 0, 400, 350);
		panelOrder.add(tblItems.getTableHeader());
		panelOrder.add(tblItems);
		panelOrder.add(subTotalCost);
		panelOrder.add(labTotal);
		tblItems.setFillsViewportHeight(true);
		this.getContentPane().add(panelOrder);

		JPanel panelButton = new JPanel();
		panelButton.setPreferredSize(new Dimension(400, 100));
		panelButton.add(btnAdd);
		panelButton.add(btnPay);
		this.getContentPane().add(panelButton);
		pack();
		setSize(500, 700);
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		screen.setSize(screen.getWidth() / 2, screen.getHeight() / 2);
		setLocation((int) (screen.getWidth() - (this.getWidth() / 2)),
				(int) (screen.getHeight() - (this.getHeight() / 2)));
		setExtendedState(getExtendedState() | Frame.MAXIMIZED_BOTH);
	}

	public ItemTable getItems() {

		return items;
	}

	public JButton getBtnAdd() {
		return btnAdd;
	}

	public JButton getBtnPay() {
		return btnPay;
	}

	public JLabel getLabTotal() {
		return labTotal;
	}

	public void addRow(Object[] row) {
		items.addRow(row); // add a row to list of item!
		items.fireTableDataChanged();
	}

	public JLabel getSubTotalCost() {
		return subTotalCost;
	}

	public void setSubTotalCost(JLabel subTotalCost) {
		this.subTotalCost = subTotalCost;
	}
}
