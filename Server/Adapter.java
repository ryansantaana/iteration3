
public interface Adapter {

	boolean isAtLeastOneManagerInDatabase();

	boolean isAtLeastTwoManagerInDatabase();

	boolean isAtLeastOneProductInDatabase();

	Product loadProduct(int id);

	boolean saveProduct(Product product);

	Order loadOrder(int id);

	int getLastOrderID();

	boolean saveOrder(Order order);

	UserProxyInterface loadUser(int userID);

	boolean writeUser(UserProxyInterface uIn);

	BusinessReport generateBusinessReport() throws Exception;
	
	byte[] loadPhoto(int userID);
	
	boolean savePhoto(byte[] photoToSave, int userID);
	
	

}