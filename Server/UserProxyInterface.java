import java.awt.image.BufferedImage;

public interface UserProxyInterface {

	BufferedImage getImage();
	
	void setImage(BufferedImage imageIn);
	
	String getName();

	void setName(String name);

	int getUserId();

	void setUserId(int userId);

	String getHashPassword();

	void setHashPassword(String hashPassword);

	String getSalt();

	void setSalt(String salt);

	boolean isManager();

	void setIsManager(boolean isManager);

	String getImagePath();

	void setImagePath(String imagePath);

}