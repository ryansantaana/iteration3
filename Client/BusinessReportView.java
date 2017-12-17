import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class BusinessReportView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel totalRev, totalQuantity;
	private JButton generateButton, sortByPrice, sortByQuantity;
	private Dimension screen;
	ItemTable items = new ItemTable();
	JTable tblItems = new JTable(items);

	///////////////////
	//// Constructor //
	///////////////////
	public BusinessReportView() {
		super();
		this.setTitle("Business Report");
		totalRev = new JLabel("Total Revenue: ");
		totalQuantity = new JLabel("Total Quantity: ");
		sortByPrice = new JButton("Sort By Price");
		sortByQuantity = new JButton("Sort By Quantity");
		generateButton = new JButton("Generate");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		JPanel mainPanel = new JPanel();
		Font font = new Font("Sans Serif", Font.BOLD, 28);
		totalRev.setFont(font);
		totalQuantity.setFont(font);
		generateButton.setFont(font);
		sortByPrice.setFont(font);
		sortByQuantity.setFont(font);
		mainPanel.add(generateButton);
		mainPanel.add(sortByQuantity);
		mainPanel.add(sortByPrice);
		mainPanel.add(totalRev);
		mainPanel.add(totalQuantity);
		getContentPane().add(mainPanel);

		// Added to display correct GUI for BusinessReport
		items.addColumn("Name");
		items.addColumn("Product ID");
		items.addColumn("Quantity");
		items.addColumn("Total Revenue");

		JPanel panelOrder = new JPanel();
		panelOrder.setPreferredSize(new Dimension(400, 450));
		panelOrder.setLayout(new BoxLayout(panelOrder, BoxLayout.PAGE_AXIS));
		tblItems.setBounds(0, 0, 400, 350);
		panelOrder.add(tblItems.getTableHeader());
		panelOrder.add(tblItems);
		tblItems.setFillsViewportHeight(true);
		this.getContentPane().add(panelOrder);
		pack();
		setSize(700, 700);
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		screen.setSize(screen.getWidth() / 2, screen.getHeight() / 2);
		setLocation((int) (screen.getWidth() - (this.getWidth() / 2)),
				(int) (screen.getHeight() - (this.getHeight() / 2)));
	}

	/////////////////////////////////////////////////////////
	// addRow method -> adds a row to the ItemTable object //
	/////////////////////////////////////////////////////////
	public void addRow(Object[] row) {
		items.addRow(row); // add a row to list of item!
		items.fireTableDataChanged();
	}

	public JButton getGenerateButton() {
		return generateButton;
	}

	public ItemTable getItemTable() {
		return items;
	}

	public JButton getSortByPrice() {
		return sortByPrice;
	}

	public void setSortByPrice(JButton sortByPrice) {
		this.sortByPrice = sortByPrice;
	}

	public JButton getSortByQuantity() {
		return sortByQuantity;
	}

	public void setSortByQuantity(JButton sortByQuantity) {
		this.sortByQuantity = sortByQuantity;
	}

	public JLabel getTotalRev() {
		return totalRev;
	}

	public void setTotalRev(JLabel totalRev) {
		this.totalRev = totalRev;
	}

	public JLabel getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(JLabel totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
}
