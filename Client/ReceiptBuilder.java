import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReceiptBuilder {
	private Order order;
	
	public ReceiptBuilder() {
		
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public TextReceipt generateTextReceipt(){
		if(order == null){
			return null;
		}
		String fileName = "Receipt_for_Order_ID" + order.getOrderID() + ".txt";
		TextReceipt returnValue = new TextReceipt(fileName);
		DateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss a");
		returnValue.setDate(formatter.format(order.getDate()));
		returnValue.setOlines((ArrayList<OrderLine>) order.getLines());
		returnValue.setSubtotal(order.getTotalCost());
		returnValue.setTotalTax(order.getTotalTax());
		return returnValue;
	}
	
	public HTMLReceipt generateHTMLReceipt(){
		if(order == null){
			return null;
		}
		String fileName = "Receipt_for_Order_ID" + order.getOrderID() + ".htm";
		HTMLReceipt returnValue = new HTMLReceipt(fileName);
		DateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss a");
		returnValue.setDate(formatter.format(order.getDate()));
		returnValue.setOlines((ArrayList<OrderLine>) order.getLines());
		returnValue.setSubtotal(order.getTotalCost());
		returnValue.setTotalTax(order.getTotalTax());
		return returnValue;
	}

}
