import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class CheckoutController implements ActionListener {
	private CheckoutView view;
	private Adapter dataAdapter; // to save and load product
	private Order order = null;
	private double fullTotal;

	public CheckoutController(CheckoutView view, Adapter dataAdapter) {
		this.dataAdapter = dataAdapter;
		this.view = view;

		view.getBtnAdd().addActionListener(this);
		view.getBtnPay().addActionListener(this);

		order = new Order();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getBtnAdd())
			addProduct();
		else if (e.getSource() == view.getBtnPay()) {
			if (view.getItems().getRowCount() == 0) {
				JOptionPane.showMessageDialog(null, "There are no items in your order!");
				return;
			}
			saveOrder();
			if (fullTotal == 0) {
				view.getLabTotal().setText("Total: $0.00");
				view.getSubTotalCost().setText("Subtotal: $0.00");
				JOptionPane.showMessageDialog(null, "Thank you for shopping with us!");
				return;
			}
			Application.getInstance().getPaymentController().setCurrentOrder(dataAdapter.loadOrder(dataAdapter.getLastOrderID()));
			Application.getInstance().getPaymentView()
					.setAmountDue(new BigDecimal(fullTotal).setScale(2, BigDecimal.ROUND_HALF_UP));
			Application.getInstance().getPaymentView().getAmountDueLabel()
					.setText("Amount Due: $" + String.format("%.2f", fullTotal));
			Application.getInstance().getPaymentView().setVisible(true);
			view.getLabTotal().setText("Total: $0.00");
			view.getSubTotalCost().setText("Subtotal: $0.00");
			fullTotal = 0.0;
			order = new Order();
		}
	}

	private void saveOrder() {
		view.getItems().clearTable();
		order.setDate(System.currentTimeMillis());
		order.setEmployeeID(Application.getInstance().getCurrentUser().getUserId());
		dataAdapter.saveOrder(order);
	}

	private void addProduct() {
		String id = JOptionPane.showInputDialog("Enter ProductID: ");
		if (id == null || id == "") {
			return;
		}
		int i = 0;
		try {
			i = (Integer.parseInt(id));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid product ID. Only contains numbers!");
			return;
		}
		Product product = dataAdapter.loadProduct(i);
		if (product == null) {
			JOptionPane.showMessageDialog(null, "This product does not exist!");
			return;
		}
		double quantity = 0;
		String quantityInput = JOptionPane.showInputDialog(null, "Enter quantity: ");
		if (quantityInput == null || quantityInput == "") {
			return;
		}
		try {
			quantity = Double.parseDouble(quantityInput);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid quantity!");
			return;
		}
		if (quantity <= 0 || quantity > product.getQuantity()) {
			JOptionPane.showMessageDialog(null, "This quantity is not valid!");
			return;
		}

		OrderLine line = new OrderLine();
		line.setOrderID(this.order.getOrderID());
		line.setProductID(product.getProductID());
		line.setQuantity(quantity);
		line.setCost(quantity * product.getPrice());
		line.setTax(product.getTaxRate() * quantity * product.getPrice());

		product.setQuantity(product.getQuantity() - quantity); // update new
																// quantity!!
		dataAdapter.saveProduct(product); // and store this product back right
											// away!!!
		fullTotal += line.getCost() * (1 + product.getTaxRate());
		order.setTotalCost(order.getTotalCost() + line.getCost());
		order.setTotalTax(order.getTotalTax() + line.getCost() * (product.getTaxRate()));
		this.view.getSubTotalCost().setText("Subtotal: $" + String.format("%.2f", order.getTotalCost()));
		view.getLabTotal().setText("Total: $" + String.format("%.2f", fullTotal));
		if (order.containsLine(line)) {
			order.addLine(line);
			java.util.List<OrderLine> olines = order.getLines();
			int index = olines.indexOf(line);
			line = olines.get(index);
			view.getItems().setValueAt(line.getQuantity(), index, 3);
			view.getItems().setValueAt("$" + String.format("%.2f", line.getCost()), index, 4);
			view.getItems().setValueAt("$" + String.format("%.2f", line.getCost() * product.getTaxRate()), index, 5);
		} else {
			order.addLine(line);
			Object[] row = new Object[6];
			row[0] = line.getProductID();
			row[1] = product.getName();
			row[2] = product.getPrice();
			row[3] = line.getQuantity();
			row[4] = "$" + String.format("%.2f", line.getCost());
			row[5] = "$" + String.format("%.2f", line.getCost() * product.getTaxRate());
			this.view.addRow(row);
		}

		this.view.invalidate();
	}

}