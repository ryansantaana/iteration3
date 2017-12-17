import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TextReceipt {
	
	private String fileName = "";
	private String storeName = "[default Store Name]";
	private String farewellMessage = "Thank you for shopping with us today!!";
	private ArrayList<OrderLine> olines = new ArrayList<OrderLine>();
	private String date = "";
	
	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	private double subtotal = 0;
	private double totalTax = 0;

	public TextReceipt(String fileName) {
		this.fileName = fileName;
	}
	
	public void printReceipt(){
		try {
			File file = new File(fileName);
			PrintWriter pw = new PrintWriter(file);
			String header = "Store Name: " + storeName;
			String dateString = "Date: " + date;
			pw.println(String.format("%1$-" + (70 - (header.length() / 2)) + "s %2$s", "", header));
			pw.println(String.format("%1$-" + (70 - (dateString.length() / 2)) + "s %2$s", "", dateString));
			pw.println(String.format("%1$-10s %2$-30s %3$-20s %4$-20s %5$-20s %6$-20s %7$-20s", "ID", "Name", "Price",  "Quantity", "Tax Rate", "Tax", "Cost"));
			int i = 0;
			while(i < olines.size()){
				pw.println(olines.get(i));
				i++;
			}
			pw.println(String.format("%1$s%2$.2f %3$s%4$.2f %5$s%6$.2f", "Total: $", (subtotal + totalTax), "Subtotal: $", subtotal, "Tax: $", totalTax));
			pw.println(String.format("%1$-" + (70 - (farewellMessage.length() / 2)) + "s %2$s", "", farewellMessage));
			pw.close();
		}
		catch(IOException e){
			e.printStackTrace();	
		}
		
		
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public ArrayList<OrderLine> getOlines() {
		return olines;
	}

	public void setOlines(ArrayList<OrderLine> arrayList) {
		this.olines = arrayList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFarewellMessage() {
		return farewellMessage;
	}

	public void setFarewellMessage(String farewellMessage) {
		this.farewellMessage = farewellMessage;
	}

}
