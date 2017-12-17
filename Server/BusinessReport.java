import java.util.ArrayList;

public class BusinessReport {

	private ArrayList<OrderLine> reportLines = new ArrayList<OrderLine>();
	private double totalQuantity;
	private double totalRevenue;

	public void addLine(OrderLine line) {
		reportLines.add(line);
		setTotalQuantity(getTotalQuantity() + line.getQuantity());
		setTotalRevenue(getTotalRevenue() + line.getCost());
	}

	public ArrayList<OrderLine> getReportLines() {
		return reportLines;
	}

	public double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

}
