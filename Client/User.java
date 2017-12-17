import java.awt.image.BufferedImage;

public class User implements UserProxyInterface {
	private String name;
	private int userId;
	private String hashPassword;
	private String salt;
	private boolean isManager;
	private String imagePath;
	
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	private BufferedImage image = null;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getUserId() {
		return userId;
	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String getHashPassword() {
		return hashPassword;
	}

	@Override
	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}

	@Override
	public String getSalt() {
		return salt;
	}

	@Override
	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public boolean isManager() {
		return isManager;
	}

	@Override
	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}

	@Override
	public String getImagePath() {
		return imagePath;
	}

	@Override
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
