import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

public class DataServer extends Thread {

	protected Socket clientSocket;
	protected Adapter dataAdapter;
	private Gson gson = new Gson();

	private DataServer(Socket clientSocket, Adapter adapter) {
		this.clientSocket = clientSocket;
		this.dataAdapter = adapter;
		start();
	}

	private Object processRequest(ClientRequest request) throws Exception {
		if (request.getCommand().equals("GET Product"))
			return dataAdapter.loadProduct(Integer.parseInt(request.getData()));
		if (request.getCommand().equals("GET Order"))
			return dataAdapter.loadOrder(Integer.parseInt(request.getData()));
		if (request.getCommand().equals("GET User"))
			return dataAdapter.loadUser(Integer.parseInt(request.getData()));
		if (request.getCommand().equals("GET BusinessReport"))
			return dataAdapter.generateBusinessReport();
		if (request.getCommand().equals("GET LastNumberOrder")) {
			return dataAdapter.getLastOrderID();
		}

		if (request.getCommand().equals("POST Product")) {
			Product product = gson.fromJson(request.getData(), Product.class);
			return dataAdapter.saveProduct(product); // store successfully to
														// the database
		}
		if (request.getCommand().equals("POST User")) {
			UserProxyInterface user = gson.fromJson(request.getData(), User.class);
			return dataAdapter.writeUser(user); // store successfully to the
												// database
		}
		if (request.getCommand().equals("POST Order")) {
			Order order = gson.fromJson(request.getData(), Order.class);
			return dataAdapter.saveOrder(order); // store successfully to the
													// database
		}

		if (request.getCommand().equals("POST Photo")) {
			return dataAdapter.savePhoto(gson.fromJson(request.getData(), byte[].class),
					Integer.parseInt(request.getAdditionalData()));
		}
		if (request.getCommand().equals("GET Photo")) {
			return dataAdapter.loadPhoto(Integer.parseInt(request.getData()));
		}

		if (request.getCommand().equals("GET AL1MID")) {
			return dataAdapter.isAtLeastOneManagerInDatabase();
		}

		if (request.getCommand().equals("GET AL2MID")) {
			return dataAdapter.isAtLeastTwoManagerInDatabase();
		}

		if (request.getCommand().equals("GET AL1PID")) {
			return dataAdapter.isAtLeastOneProductInDatabase();
		}

		return null;
	}

	public void run() {
		System.out.println("New communication thread started for client socket " + clientSocket.getInetAddress()
				+ " at " + System.currentTimeMillis());

		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while (true) {
				String json = in.readLine();
				if (json == null)
					break;

				ClientRequest request = gson.fromJson(json, ClientRequest.class); // convert
																					// JSON
																					// text
																					// into
																					// a
																					// Request
																					// object
				if (request.getCommand().equals("Test"))
					continue;
				if (request.getCommand().equals("BYE"))
					break;

				String response = gson.toJson(processRequest(request)); // process
																		// the
																		// request...
				out.println(response); // write response as JSON back to client
			}
			System.out.println("Connection closed for client from " + clientSocket.getInetAddress());
			out.close();
			in.close();
			clientSocket.close();

		} catch (Exception e) {
			System.err.println("Problem with Communication Server" + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String args[]) {
		Adapter dataAdapter = null;
		Connection connection = null;
		Statement stmt;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:store.db");
			stmt = connection.createStatement();
			stmt.executeQuery("select * from Product");
		} catch (ClassNotFoundException ex) {
			System.out.println("SQLite is not installed. System exits with error!");
			System.exit(1);
		}

		catch (SQLException ex) {
			try {
				connection = DriverManager.getConnection("jdbc:sqlite:store.db");
				stmt = connection.createStatement();
				stmt.execute("CREATE TABLE if not exists \"Order\" (OrderID INTEGER PRIMARY KEY "
						+ "AUTOINCREMENT NOT NULL, OrderDate "
						+ "datetime NOT NULL, CustomerID int, TotalCost DOUBLE, TotalTax DOUBLE"
						+ ", EmployeeID INT NOT NULL);");
				stmt.execute("CREATE TABLE if not exists \"Product\" (ProductID INT NOT NULL, "
						+ "Name CHAR(30) NOT NULL, Price DOUBLE NOT NULL, Quantity DOUBLE, TaxRate DOUBLE);");
				stmt.execute("CREATE TABLE if not exists OrderLine (OrderID INT NOT NULL, "
						+ "ProductID INT NOT NULL, Quantity DOUBLE, Cost "
						+ "DOUBLE, Tax DOUBLE, PRIMARY KEY (ProductID, OrderID));");
				stmt.execute("create table if not exists User (UserID int primary key not null, "
						+ "UserName char(30), PasswordHash char(43), IsManager boolean, Salt char(14), Image varchar(100));");
				JOptionPane.showMessageDialog(null, "Database did not exist, but one was created for you. Please "
						+ "add items before attempting to checkout!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.exit(2);
			}
		}
		dataAdapter = new ServerDataAdapter(connection);
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(8888);

			System.out.println("Server Socket created!");
			while (true) {
				System.out.println("Waiting for a new connection...");
				new DataServer(serverSocket.accept(), dataAdapter); // if there
																	// is a
																	// client
																	// connecting

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
