package Inventory.screens;

import Inventory.program.SQLiteConnector;

public class MainScreen {

	public static void  mainScreen() {
		System.out.println("Select desired action: ");
		System.out.println("1. Product catalog");
		System.out.println("2. Product stock");
		System.out.println("3. Categories");
		System.out.println("4. Reports");
		System.out.println("5. Back");
		
		int result = Inventory.program.Util.readIntFromUser(1, 5);
			
		switch(result){
		case 1:
			ProductCatalogScreen.mainScreen();
			break;
		case 2:
			ProductStockScreen.mainScreen();
			break;
		case 3:
			CategoryScreen.mainScreen();
			break;
		case 4:
			ReportsScreeen.mainScreen();
			break;
		case 5:
			SQLiteConnector.getInstance().CloseConnection();
			break;
		}
	}

}
