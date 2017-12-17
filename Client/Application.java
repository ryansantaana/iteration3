import javax.swing.JOptionPane;

public class Application {

	private static Application instance; // Singleton pattern

	public static Application getInstance() {
		if (instance == null) {
			instance = new Application();
		}
		return instance;
	}
	// Main components of this application

	public void setLoginScreen(LoginView loginView) {
		this.loginView = loginView;
	}


	private boolean hasInitialManager = false;

	private Adapter dataAdapter;

	private UserProxyInterface CurrentUser = null;

	// Create the Product View and Controller here!

	private ProductView productView = new ProductView();

	private UserView userView = new UserView();

	private CheckoutView checkoutView = new CheckoutView();

	private ClerkView clerkView = new ClerkView();

	private ManagerView managerView = new ManagerView();

	private UserController userController;

	private LoginView loginView = new LoginView();

	private LoginController loginController;

	private PaymentView paymentView = new PaymentView();

	private PaymentController paymentController;

	private ProfileView profileView = new ProfileView();
	private ProfileController profileController;

	public ClerkView getClerkView() {
		return clerkView;
	}

	public ProductView getProductView() {
		return productView;
	}

	public CheckoutView getCheckoutView() {
		return checkoutView;
	}

	private ProductController productController;

	public ProductController getProductController() {
		return productController;
	}

	private CheckoutController checkoutController;

	public CheckoutController getCheckoutController() {
		return checkoutController;
	}

	public Adapter getDataAdapter() {
		return dataAdapter;
	}

	private BusinessReportView businessReportView = new BusinessReportView();
	private BusinessReportController businessReportController;

	private Application() {

		// Create data adapter here!
		dataAdapter = new DataAdapter("127.0.0.1", 8888); // new Remote data Adapter

		setLoginController(new LoginController(loginView, dataAdapter));

		productController = new ProductController(productView, dataAdapter);

		checkoutController = new CheckoutController(checkoutView, dataAdapter);

		paymentController = new PaymentController(paymentView, dataAdapter);

		setUserController(new UserController(userView, dataAdapter));

		setProfileController(new ProfileController(profileView, dataAdapter));

		businessReportController = new BusinessReportController(businessReportView, dataAdapter);
	}

	public UserView getUserView() {
		return userView;
	}

	public static void main(String[] args) {
		if (Application.getInstance().getDataAdapter().isAtLeastOneManagerInDatabase()) {
			// Application.getInstance().getBusinessReportView().setVisible(true);
			Application.getInstance().setHasInitialManager(true);
			Application.getInstance().getLoginScreen().setVisible(true);
		} else {
			// there is no manager in the system.
			JOptionPane.showMessageDialog(null,
					"There is no manager in the system. One has to be defined in order to continue!");
			Application.getInstance().getUserView().setVisible(true);
		}
	}

	public LoginView getLoginScreen() {
		return loginView;
	}

	public PaymentController getPaymentController() {
		return paymentController;
	}

	public PaymentView getPaymentView() {
		return paymentView;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public UserController getUserController() {
		return userController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	public boolean hasInitialManager() {
		return hasInitialManager;
	}

	public void setHasInitialManager(boolean hasInitialManager) {
		this.hasInitialManager = hasInitialManager;
	}

	public UserProxyInterface getCurrentUser() {
		return CurrentUser;
	}

	public void setCurrentUser(UserProxyInterface currentUser) {
		CurrentUser = currentUser;
	}

	public BusinessReportView getBusinessReportView() {
		return businessReportView;
	}

	public void setBusinessReportView(BusinessReportView businessReportView) {
		this.businessReportView = businessReportView;
	}

	public BusinessReportController getBusinessReportController() {
		return businessReportController;
	}

	public void setBusinessReportController(BusinessReportController businessReportController) {
		this.businessReportController = businessReportController;
	}

	public ManagerView getManagerView() {
		return managerView;
	}

	public void setManagerView(ManagerView managerView) {
		this.managerView = managerView;
	}

	public ProfileView getProfileView() {
		return profileView;
	}

	public void setProfileView(ProfileView profileView) {
		this.profileView = profileView;
	}

	public ProfileController getProfileController() {
		return profileController;
	}

	public void setProfileController(ProfileController profileController) {
		this.profileController = profileController;
	}

}
