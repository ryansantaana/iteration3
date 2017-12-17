import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UserProxy implements UserProxyInterface {

	private User user = null;

	public UserProxy() {
		ImageIO.setUseCache(false);
	}

	@Override
	public BufferedImage getImage() {
		// only create image when needed;
		if (user == null) {
			return null;
		}
		if (user.getImage() == null) {
			try {
				user.setImage(ImageIO.read(new ByteArrayInputStream(
						Application.getInstance().getDataAdapter().loadPhoto(user.getUserId()))));
			} catch (IOException e) {
				return null;
			}
		}
		return user.getImage();
	}

	@Override
	public void setImage(BufferedImage imageIn) {
		// save image when needed
		if (user == null) {
			user = new User();
		}
		user.setImage(imageIn);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			//resize image
			BufferedImage resized = new BufferedImage(300, 300, BufferedImage.TYPE_4BYTE_ABGR_PRE);
	        Graphics2D g2d = resized.createGraphics();
	        g2d.drawImage(imageIn, 0, 0, 300, 300, null);
	        g2d.dispose();
	        //write
			ImageIO.write(resized, "png", byteArrayOutputStream);
			Application.getInstance().getDataAdapter().savePhoto(byteArrayOutputStream.toByteArray(), user.getUserId());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public String getName() {
		if (user == null) {
			return "";
		}
		return user.getName();
	}

	@Override
	public void setName(String name) {
		if (user == null) {
			user = new User();
		}
		user.setName(name);
	}

	@Override
	public int getUserId() {
		if (user == null) {
			return 0;
		}
		return user.getUserId();
	}

	@Override
	public void setUserId(int userId) {
		if (user == null) {
			user = new User();
		}
		user.setUserId(userId);
	}

	@Override
	public String getHashPassword() {
		if (user == null) {
			return "";
		}
		return user.getHashPassword();
	}

	@Override
	public void setHashPassword(String hashPassword) {
		if (user == null) {
			user = new User();
		}
		user.setHashPassword(hashPassword);
	}

	@Override
	public String getSalt() {
		if (user == null) {
			return "";
		}
		return user.getSalt();
	}

	@Override
	public void setSalt(String salt) {
		if (user == null) {
			user = new User();
		}
		user.setSalt(salt);
	}

	@Override
	public boolean isManager() {
		if (user == null) {
			return false;
		}
		return user.isManager();
	}

	@Override
	public void setIsManager(boolean isManager) {
		if (user == null) {
			user = new User();
		}
		user.setIsManager(isManager);
	}

	@Override
	public String getImagePath() {
		if (user == null) {
			return "";
		}
		return user.getImagePath();
	}

	@Override
	public void setImagePath(String imagePath) {
		if (user == null) {
			user = new User();
		}
		user.setImagePath(imagePath);
	}

}
