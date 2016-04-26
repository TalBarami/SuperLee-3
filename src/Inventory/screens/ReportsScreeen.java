package Inventory.screens;

import java.sql.SQLException;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import Inventory.program.Util;
import Inventory.dbHandlers.CategoryHandler;
import Inventory.dbHandlers.ProductHandler;
import Inventory.dbHandlers.ProductStockHandler;
import Inventory.dbHandlers.StockTicketHandler;
import Inventory.entities.Category;

public class ReportsScreeen {

	public static void mainScreen() {
		System.out.println("Select desired action: ");
		System.out.println("1. Report by Category");
		System.out.println("2. Report by Product");
		System.out.println("3. Report by minimal amount");
		System.out.println("4. Report of all expired products");
		System.out.println("5. Report of all non-valid products");
		System.out.println("6. Report by stockTicket");
		System.out.println("7. Return to main screen");
		
		int result = Inventory.program.Util.readIntFromUser(1, 7);
			
		switch(result){
		case 1:
			ReportByCategory();
			break;
		case 2:
			ReportByProduct();
			break;
		case 3:
			ReportByMinAmount();
			break;
		case 4:
			ReportOfExpiredProducts();
			break;
		case 5:
			ReportOfNonValidProducts();
			break;
		case 6:
			ReportByStocktickets();
			break;
		case 7:
			MainScreen.mainScreen();
			break;
		}		
	}

	private static void ReportByCategory() {
		Category parentCat = null;
		Category currCat = null;
		
		System.out.println("Please select main category id\npress 0 to return to previous screen");
		try {
			CategoryHandler.PrintAllMainCategories();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int choice = Util.readIntFromUser(0, -1);
		try {
			while((currCat = CategoryHandler.checkIfCategoryExists(choice, 1, null)) == null && choice !=0)
			{
				System.out.println("Invalid choice please insert again");
				choice = Util.readIntFromUser(-1, -1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		switch (choice) {
		case 0:
			mainScreen();
			break;

		default:
			System.out.println("To get Report of this category press 0\n Press -1 to return to the previous screen\nOtherwise choose sub category id");
			parentCat = currCat;
			try {
				CategoryHandler.PrintAllSubCategories(choice, 2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			choice = Util.readIntFromUser(-1, -1);
			try {
				while((currCat = CategoryHandler.checkIfCategoryExists(choice, 2, parentCat)) == null && choice !=0 &&choice !=-1)
				{
					System.out.println("Invalid choice please insert again");
					choice = Util.readIntFromUser(-1, -1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			switch (choice) {
			case -1:
				mainScreen();
				break;

			case 0:
				try {
					ProductStockHandler.printAllProductInStockByCategory(parentCat.get_id(), 1);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainScreen();
				break;
				
				//chose sub category
			default:
				System.out.println("To get Report of this category press 0\n Press -1 to return to the previous screen\nOtherwise choose sub sub category id");
				parentCat = currCat;
				try {
					CategoryHandler.PrintAllSubCategories(choice, 3);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				choice = Util.readIntFromUser(0, -1);
				try {
					while((currCat = CategoryHandler.checkIfCategoryExists(choice, 3, parentCat)) == null && choice !=0 && choice !=-1)
					{
						System.out.println("Invalid choice please insert again");
						choice = Util.readIntFromUser(-1, -1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				switch (choice) {
				case -1:
					mainScreen();
					break;

				case 0:
					try {
						ProductStockHandler.printAllProductInStockByCategory(parentCat.get_id(), 2);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mainScreen();
					break;
				
				default:
					try {
						ProductStockHandler.printAllProductInStockByCategory(currCat.get_id(), 3);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mainScreen();
					break;
			}
				break;
			}
			break;
		}
	}
	

	private static void ReportByProduct() {
		System.out.println("Please select id of relevant product\nOr press 0 to return to previous screen");
		try {
			ProductHandler.printAllProductCatalog();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int choice = Util.readIntFromUser(0, -1);
		try {
			while(!ProductHandler.checkIfProductExists(choice) && choice !=0)
			{
				System.out.println("Invalid choice please insert again");
				choice = Util.readIntFromUser(0, -1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			ProductStockHandler.printAllProductInStock(choice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mainScreen();
	}

	private static void ReportByMinAmount() {
		try {
			ProductStockHandler.printAllMinimalAmountProductStock();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainScreen();
	}

	private static void ReportOfExpiredProducts() {
		try {
			ProductStockHandler.printAllExpiredProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainScreen();
		
	}

	private static void ReportOfNonValidProducts() {
		try {
			ProductStockHandler.printAllNonValidProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainScreen();
		
	}

	private static void ReportByStocktickets() {
		System.out.println("Please select relevant stock ticket ID\nPress 0 to return to the previous screen");
		try {
			StockTicketHandler.printAllStockTickets();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int choice = Util.readIntFromUser(0, -1);
		
		try {
			while (!StockTicketHandler.checkIfStockTicketExists(choice) && choice != 0) {
				System.out.println("Invalid choice, please choose again");
				choice = Util.readIntFromUser(0, -1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (choice == 0)
			mainScreen();
		
		else {
			try {
				StockTicketHandler.printStockTicketItemByID(choice);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mainScreen();
		}
	}

}
