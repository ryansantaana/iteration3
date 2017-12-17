import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HTMLReceipt {
	
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

	public HTMLReceipt(String fileName) {
		this.fileName = fileName;
	}
	
	public void printReceipt(){
		try {
			File file = new File(fileName);
			PrintWriter pw = new PrintWriter(file);
			pw.write("<!DOCTYPE HTML> <title>" + storeName + " Order Receipt</title>");
			pw.write("<head>"
					+ "<style>"
					+ "h1 {"
					+ "font-size: 200%;"
					+ "text-align: center;"
					+ "}"
					+ "body {"
					+ "background-color: #1111FF;"
					+ "color: yellow;"
					+ "}"
					+ "tr {"
					+ "text-align: center;"
					+ "}"
					+ "table {"
					+ "border: 1px solid yellow;"
					+ "width: 100%;"
					+ "}"
					+ "</style>"
					+ " </head>"
					+ "<body>"
					+ "<h1>"
					+ "Store Name: "
					+ storeName
					+ "</h1>"
					+ "<h1>"
					+ "Date: "
					+ date
					+ "</h1>"
					+ "<table><tr>"
					+ "<th>ID</th>"
					+ "<th>Name</th>"
					+ "<th>Price</th>"
					+ "<th>Quantity</th>"
					+ "<th>Tax Rate</th>"
					+ "<th>Tax</th>"
					+ "<th>Cost</th></tr>");
			
			OrderLine cl;
			int i = 0;
			while(i < olines.size()){
				cl = olines.get(i);
				pw.println("<tr>");
				pw.println("<td>");
				//ID
				pw.println(cl.getProductID());
				pw.println("</td>");
				pw.println("<td>");
				//Name
				pw.println(cl.getName());
				pw.println("</td>");
				pw.println("<td>");
				//Price
				pw.println(String.format("$%.2f", cl.getCost() / cl.getQuantity()));
				pw.println("</td>");
				pw.println("<td>");
				//Quantity
				pw.println(String.format("%.2f", cl.getQuantity()));
				pw.println("</td>");
				pw.println("<td>");
				//Tax Rate
				pw.println(String.format("%.2f", cl.getTax() / cl.getCost()));
				pw.println("</td>");
				pw.println("<td>");
				//Tax
				pw.println(String.format("$%.2f", cl.getTax()));
				pw.println("</td>");
				pw.println("<td>");
				//Cost
				pw.println(String.format("$%.2f", cl.getCost()));
				pw.println("</td>");
				pw.println("</tr>");
				i++;
			}
			pw.println("</table>"
					+ "<h1>"
					+ "Subtotal: $"
					+ String.format("%.2f", subtotal)
					+ " Total: $"
					+ String.format("%.2f", (subtotal + totalTax))
					+ " Tax: $"
					+ String.format("%.2f", totalTax)
					+ "</h1>"
					+ "<h1>"
					+ farewellMessage
					+ "</h1>"
					+ "</body>");
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