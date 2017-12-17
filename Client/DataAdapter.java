
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import javax.swing.JOptionPane;

import com.google.gson.Gson;

public class DataAdapter implements Adapter{
	
    private PrintWriter outStream;
    private BufferedReader inStream;
    private Socket socket;
    private Gson gson = new Gson();

    public DataAdapter(String hostName, int portNumber) {
    	while(true){
        try {
            socket = new Socket(hostName, portNumber);
            outStream = new PrintWriter(socket.getOutputStream(), true);
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outStream.println(gson.toJson(new ClientRequest("Test", "None")));
            break;
        } catch (Exception ex) {
        	if(ex instanceof ConnectException && JOptionPane.showConfirmDialog(null,
                    "Connection could not be established!!", "Retry Connection?",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
        		continue;
        	}
        	else{
            //ex.printStackTrace();
            System.exit(1);
        	}
        }
    	}
    }

	@Override
	public boolean isAtLeastOneManagerInDatabase() {
        try {
            ClientRequest request = new ClientRequest("GET AL1MID", "None");
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, Boolean.TYPE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return false;
	}
	
	public void close(){
		try{
			outStream.println(gson.toJson(new ClientRequest("BYE", "None")));
		}
		catch(Exception ex){
			
		}
	}

	@Override
	public boolean isAtLeastTwoManagerInDatabase() {
        try {
            ClientRequest request = new ClientRequest("GET AL2MID", "None");
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, Boolean.TYPE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return false;
	}

	@Override
	public boolean isAtLeastOneProductInDatabase() {
        try {
            ClientRequest request = new ClientRequest("GET AL1PID", "None");
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, Boolean.TYPE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return false;
	}

	@Override
	public Product loadProduct(int id) {
        try {
            ClientRequest request = new ClientRequest("GET Product", String.valueOf(id));
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, Product.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
	}

	@Override
	public boolean saveProduct(Product product) {
        try {
            ClientRequest request = new ClientRequest("POST Product", gson.toJson(product));
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, Boolean.TYPE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return false;
	}

	@Override
	public Order loadOrder(int id) {
        try {
            ClientRequest request = new ClientRequest("GET Order", String.valueOf(id));
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, Order.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
	}

	@Override
	public int getLastOrderID() {
        try {
            ClientRequest request = new ClientRequest("GET LastNumberOrder", "None");
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, Integer.TYPE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return 0;
	}

	@Override
	public boolean saveOrder(Order order) {
        try {
            ClientRequest request = new ClientRequest("POST Order", gson.toJson(order));
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, Boolean.TYPE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return false;
	}

	@Override
	public UserProxyInterface loadUser(int userID) {
        try {
            ClientRequest request = new ClientRequest("GET User", String.valueOf(userID));
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, UserProxy.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
	}

	@Override
	public boolean writeUser(UserProxyInterface uIn) {
        try {
        	User copy = new User();
        	copy.setHashPassword(uIn.getHashPassword());
        	copy.setImagePath(uIn.getImagePath());
        	copy.setName(uIn.getName());
        	copy.setSalt(uIn.getSalt());
        	copy.setUserId(uIn.getUserId());
        	copy.setIsManager(uIn.isManager());
        	//make sure not to send Photo
            ClientRequest request = new ClientRequest("POST User", gson.toJson(copy));
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, Boolean.TYPE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return false;
	}

	@Override
	public BusinessReport generateBusinessReport() throws Exception {
        try {
            ClientRequest request = new ClientRequest("GET BusinessReport", "None");
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, BusinessReport.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
	}

	@Override
	public byte[] loadPhoto(int userID) {
		try{
			ClientRequest request = new ClientRequest("GET Photo", String.valueOf(userID));
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, byte[].class);
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean savePhoto(byte[] photoToSave, int userID) {
        try {
            ClientRequest request = new ClientRequest("POST Photo", gson.toJson(photoToSave));
            request.setAdditionalData(String.valueOf(userID));
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, Boolean.TYPE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return false;
	}
	
	
	
}