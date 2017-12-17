import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BusinessReportController implements ActionListener {

	BusinessReportView view;
	private Adapter dataAdapter;

	/////////////////////////
	// Generate View lines //
	/////////////////////////
	private void generateViewLines(int sort) {

		Object[] row = new Object[4];
		BusinessReport br = new BusinessReport();
		try {
			br = dataAdapter.generateBusinessReport();
		} catch (Exception e) {

			e.printStackTrace();
		}

		ArrayList<OrderLine> reportLines = br.getReportLines();

		// Sort by price
		if (sort == 1) {
			Collections.sort(reportLines, new Comparator<OrderLine>() {

				@Override
				public int compare(OrderLine o1, OrderLine o2) {
					if (o1.getCost() > o2.getCost()) {
						return -1;
					}

					return 1;
				}
			});

		}

		// Sort by quantity
		if (sort == 2) {
			Collections.sort(reportLines, new Comparator<OrderLine>() {

				@Override
				public int compare(OrderLine o1, OrderLine o2) {
					if (o1.getQuantity() > o2.getQuantity()) {
						return -1;
					}

					return 1;
				}
			});

		}

		for (OrderLine line : reportLines) {
			row[0] = line.getName();
			row[1] = line.getProductID();
			row[2] = line.getQuantity();
			row[3] = "$" + String.format("%.2f", line.getCost());
			this.view.addRow(row);
		}
		view.getTotalQuantity().setText("Total Quantity: " + br.getTotalQuantity());
		view.getTotalRev().setText("Total Revenue: $" + br.getTotalRevenue());
	}

	public BusinessReportController(BusinessReportView view, Adapter dataAdapter) {
		this.view = view;
		this.dataAdapter = dataAdapter;
		view.getGenerateButton().addActionListener(this);
		view.getSortByQuantity().addActionListener(this);
		view.getSortByPrice().addActionListener(this);
	}

	// Action Listener
	@Override
	public void actionPerformed(ActionEvent e) {
		view.getItemTable().clearTable();

		if (e.getSource() == view.getSortByQuantity()) {
			this.generateViewLines(2);
		}

		else if (e.getSource() == view.getSortByPrice()) {
			this.generateViewLines(1);
		}

		else if (e.getSource() == view.getGenerateButton()) {
			this.generateViewLines(0);

		}
	}
}
