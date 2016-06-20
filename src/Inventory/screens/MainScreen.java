package Inventory.screens;

import Suppliers.Application.Utils;

public class MainScreen {
	private ProductCatalogScreen proCatScn;
	private ProductStockScreen proStcScn;
	private CategoryScreen CatScn;
	private ReportsScreen reportScn;

	public MainScreen(){
		proCatScn = new ProductCatalogScreen(this);
		proStcScn = new ProductStockScreen(this);
		CatScn = new CategoryScreen(this);
		reportScn = new ReportsScreen(this);
	}

	public void start(){
		if(login())
			mainScreen();
	}

	private boolean login() {
		System.out.println("Please enter your password: (c to cancel)");
		String password;
		while(true) {
			password = Utils.readLine();
			switch (password) {
				case "54321":
					System.out.println("You have successfully logged into the inventory system!");
					return true;
				case "c":
					return false;
				default:
					System.out.println("Invalid password, please try again.");
					break;
			}
		}
	}

	public void  mainScreen() {
		System.out.println("Select desired action: ");
		System.out.println("1. Product catalog");
		System.out.println("2. Product stock");
		System.out.println("3. Categories");
		System.out.println("4. Reports");
		System.out.println("5. Back");

		int result = Inventory.program.Util.readIntFromUser(1, 5);

		switch(result){
			case 1:
				proCatScn.mainScreen();
				break;
			case 2:
				proStcScn.mainScreen();
				break;
			case 3:
				CatScn.mainScreen();
				break;
			case 4:
				reportScn.mainScreen();
				break;
			case 5:
				break;
		}
	}

}
