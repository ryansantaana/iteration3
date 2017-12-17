import java.util.List;
import java.util.ArrayList;

public class Order {
	private int orderID;
	private int customerID;
	private double totalCost;
	private double totalTax;
	private long date;
	private int employeeID;

	private List<OrderLine> lines;

	public Order() {
		lines = new ArrayList<>();
		date = System.currentTimeMillis();
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public boolean containsLine(OrderLine olIn) {
		return lines.contains(olIn);
	}

	public void addLine(OrderLine line) {
		if (containsLine(line)) {
			OrderLine ol = lines.get(lines.indexOf(line));
			ol.setQuantity(ol.getQuantity() + line.getQuantity());
			ol.setCost(ol.getCost() + line.getCost());
			ol.setTax(ol.getTax() + line.getTax());
			lines.set(lines.indexOf(line), ol);
		} else {
			lines.add(line);
		}
	}

	public void removeLine(OrderLine line) {
		lines.remove(line);
	}

	public List<OrderLine> getLines() {
		return lines;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
}
