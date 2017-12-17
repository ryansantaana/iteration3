
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ServerDataAdapter implements Adapter {
	private Connection connection;

	public ServerDataAdapter(Connection connection) {
		ImageIO.setUseCache(false);
		this.connection = connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#isAtLeastOneManagerInDatabase()
	 */
	
	@Override
	public boolean isAtLeastOneManagerInDatabase() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("Select * from User where IsManager = 1");
			// returns if there is anything in the set.
			return resultSet.next();
		} catch (Exception ex) {
			System.out.println("Database access error!");
			ex.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#isAtLeastTwoManagerInDatabase()
	 */
	@Override
	public boolean isAtLeastTwoManagerInDatabase() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("Select * from User where IsManager = 1");
			// returns if there is anything in the set.
			resultSet.next();
			return resultSet.next();
		} catch (Exception ex) {
			System.out.println("Database access error!");
			ex.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#isAtLeastOneProductInDatabase()
	 */
	@Override
	public boolean isAtLeastOneProductInDatabase() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("Select * from Product");
			// returns if there is anything in the set.
			return resultSet.next();
		} catch (Exception ex) {
			System.out.println("Database access error!");
			ex.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#loadProduct(int)
	 */
	@Override
	public Product loadProduct(int id) {
		try {
			String query = "SELECT * FROM Product WHERE ProductID = " + id;

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				Product product = new Product();
				product.setProductID(resultSet.getInt(1));
				product.setName(resultSet.getString(2));
				product.setPrice(resultSet.getDouble(3));
				product.setQuantity(resultSet.getDouble(4));
				product.setTaxRate(resultSet.getDouble(5));
				resultSet.close();
				statement.close();

				return product;
			}

		} catch (SQLException e) {
			System.out.println("Database access error!");
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#saveProduct(Product)
	 */
	@Override
	@SuppressWarnings("resource")
	public boolean saveProduct(Product product) {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Product WHERE ProductID = ?");
			statement.setInt(1, product.getProductID());

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) { // this product exists, update its fields
				statement = connection.prepareStatement(
						"UPDATE Product SET Name = ?, Price = ?, Quantity = ?, TaxRate = ? WHERE ProductID = ?");
				statement.setString(1, product.getName());
				statement.setDouble(2, product.getPrice());
				statement.setDouble(3, product.getQuantity());
				statement.setInt(5, product.getProductID());
				statement.setDouble(4, product.getTaxRate());
			} else { // this product does not exist, use insert into
				statement = connection.prepareStatement("INSERT INTO Product VALUES (?, ?, ?, ?, ?)");
				statement.setString(2, product.getName());
				statement.setDouble(3, product.getPrice());
				statement.setDouble(4, product.getQuantity());
				statement.setInt(1, product.getProductID());
				statement.setDouble(5, product.getTaxRate());
			}
			statement.execute();
			resultSet.close();
			statement.close();
			return true; // save successfully

		} catch (SQLException e) {
			System.out.println("Database access error!");
			e.printStackTrace();
			return false; // cannot save!
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#loadOrder(int)
	 */
	@Override
	public Order loadOrder(int id) {
		try {
			Order order = null;
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM \"Order\" WHERE OrderID = " + id);

			if (resultSet.next()) {
				order = new Order();
				order.setOrderID(resultSet.getInt("OrderID"));
				order.setCustomerID(resultSet.getInt("CustomerID"));
				order.setTotalCost(resultSet.getDouble("TotalCost"));
				order.setTotalTax(resultSet.getDouble("TotalTax"));
				order.setDate(resultSet.getLong("OrderDate"));
				resultSet.close();
				statement.close();
			}

			// loading the order lines for this order
			resultSet = statement.executeQuery("SELECT * FROM OrderLine WHERE OrderID = " + id);

			while (resultSet.next()) {
				OrderLine line = new OrderLine();
				line.setName(loadProduct(resultSet.getInt(2)).getName());
				line.setOrderID(resultSet.getInt(1));
				line.setProductID(resultSet.getInt(2));
				line.setQuantity(resultSet.getDouble(3));
				line.setCost(resultSet.getDouble(4));
				line.setTax(resultSet.getDouble(5));
				order.addLine(line);
			}

			return order;

		} catch (SQLException e) {
			System.out.println("Database access error!");
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#getLastOrderID()
	 */
	@Override
	public int getLastOrderID() {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT MAX(OrderID) from \"Order\"");
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#saveOrder(Order)
	 */
	@Override
	public boolean saveOrder(Order order) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO \"Order\" (OrderDate, CustomerID, TotalCost, TotalTax, EmployeeID) VALUES (?, ?, ?, ?, ?)");
			statement.setLong(1, order.getDate());
			statement.setInt(2, order.getCustomerID());
			statement.setDouble(3, order.getTotalCost());
			statement.setDouble(4, order.getTotalTax());
			statement.setInt(5, order.getEmployeeID());
			statement.execute(); // commit to the database;
			statement.close();
			
			ResultSet resultSet;
			Statement statementRead = connection.createStatement();

			resultSet = statementRead.executeQuery("SELECT OrderID FROM \"Order\" ORDER BY OrderID DESC LIMIT 1");

			resultSet.next();

			int orderID = resultSet.getInt(1);

			statement = connection.prepareStatement(
					"INSERT INTO OrderLine (OrderID, ProductID, Quantity, Cost, Tax) VALUES (?, ?, ?, ?, ?)");
			for (OrderLine line : order.getLines()) { // store for each order
														// line!
				statement.setInt(1, orderID);
				statement.setInt(2, line.getProductID());
				statement.setDouble(3, line.getQuantity());
				statement.setDouble(4, line.getCost());
				statement.setDouble(5, line.getTax());
				statement.execute(); // commit to the database;
			}
			statement.close();
			return true; // save successfully!
		} catch (SQLException e) {
			System.out.println("Database access error!");
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#loadUser(int)
	 */
	@Override
	public UserProxyInterface loadUser(int userID) {
		UserProxyInterface returnUser = null;
		try {
			Statement stmt = connection.createStatement();
			String queryString = "SELECT * from User where userID = " + userID;

			ResultSet rs = stmt.executeQuery(queryString);
			if (rs.next()) {
				returnUser = new UserProxy();
				returnUser.setUserId(rs.getInt(1));
				returnUser.setName(rs.getString(2));
				returnUser.setHashPassword(rs.getString(3));
				returnUser.setIsManager(rs.getBoolean(4));
				returnUser.setSalt(rs.getString(5));
				returnUser.setImagePath(rs.getString(6));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return returnUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#loadUser(java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#writeUser(User)
	 */
	@Override
	public boolean writeUser(UserProxyInterface uIn) {
		try {
			int im = 0;
			if (uIn.isManager()) {
				im = 1;
			}
			String queryString = "REPLACE INTO User (UserID, UserName, PasswordHash, IsManager, Salt, Image)"
					+ " Values (" + uIn.getUserId() + ", \"" + uIn.getName() + "\",\"" + uIn.getHashPassword() + "\" , "
					+ im + ", \"" + uIn.getSalt() + "\",\"" + uIn.getImagePath() + "\");";
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(queryString);
			stmt.close();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Adapter#generateBusinessReport()
	 */
	@Override
	public BusinessReport generateBusinessReport() throws Exception {

		ArrayList<OrderLine> orderLineArrayList = new ArrayList<OrderLine>();
		BusinessReport businessReport = new BusinessReport();
		OrderLine orderLine = new OrderLine();

		Statement stmt = connection.createStatement();
		String queryString = "SELECT Product.Name, OrderLine.ProductID, "
				+ "SUM(OrderLine.Quantity) as Total_Quantity, " + "SUM(OrderLine.Cost) as Total_Price FROM Product "
				+ "Join OrderLine on Product.ProductID = " + "OrderLine.ProductID Group By "
				+ "OrderLine.ProductID Order by Product.Name asc;";

		ResultSet rs = stmt.executeQuery(queryString);

		while (rs.next()) {
			orderLine = new OrderLine();

			orderLine.setName(rs.getString(1));
			orderLine.setProductID(rs.getInt(2));
			orderLine.setQuantity(rs.getDouble(3));
			orderLine.setCost(rs.getDouble(4));
			orderLineArrayList.add(orderLine);

		}

		rs.close();
		stmt.close();

		for (OrderLine line : orderLineArrayList) {
			businessReport.addLine(line);
		}

		return businessReport;
	}

	@Override
	public byte[] loadPhoto(int userID) {
		try{
			BufferedImage image = ImageIO.read(new File(userID + ".png"));
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(image, "png", byteArrayOutputStream);
			return byteArrayOutputStream.toByteArray();
		}catch(Exception e){
			System.out.println("No Picture Here!!!");
			
		}
		return null;
	}

	@Override
	public boolean savePhoto(byte[] photoToSave, int userID) {
		try{
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(photoToSave));
		ImageIO.write(image, "png", new File(userID + ".png"));
		
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
}
