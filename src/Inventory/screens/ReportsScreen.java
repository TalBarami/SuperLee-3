package Inventory.screens;

import java.sql.SQLException;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import Inventory.program.Util;
import Inventory.dbHandlers.CategoryHandler;
import Inventory.dbHandlers.ProductHandler;
import Inventory.dbHandlers.ProductStockHandler;
import Inventory.dbHandlers.StockTicketHandler;
import Inventory.entities.Category;

public class ReportsScreen {
	private MainScreen main;
	private CategoryHandler catHdr;
	private ProductHandler proHdr;
	private ProductStockHandler proStkHdr;
	private StockTicketHandler stkTktHdr;

	public ReportsScreen(MainScreen main){
		this.main = main;
		catHdr = new CategoryHandler();
		proHdr = new ProductHandler();
		proStkHdr = new ProductStockHandler();
		stkTktHdr = new StockTicketHandler();
	}

	public void mainScreen() {
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
				main.mainScreen();
				break;
		}
	}

	private void ReportByCategory() {
		Category parentCat = null;
		Category currCat = null;

		System.out.println("Please select main category id\npress 0 to return to previous screen");
		try {
			catHdr.PrintAllMainCategories();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int choice = Util.readIntFromUser(0, -1);
		try {
			while((currCat = catHdr.checkIfCategoryExists(choice, 1, null)) == null && choice !=0)
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
					catHdr.PrintAllSubCategories(choice, 2);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				choice = Util.readIntFromUser(-1, -1);
				try {
					while((currCat = catHdr.checkIfCategoryExists(choice, 2, parentCat)) == null && choice !=0 &&choice !=-1)
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
							proStkHdr.printAllProductInStockByCategory(parentCat.get_id(), 1);
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
							catHdr.PrintAllSubCategories(choice, 3);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						choice = Util.readIntFromUser(0, -1);
						try {
							while((currCat = catHdr.checkIfCategoryExists(choice, 3, parentCat)) == null && choice !=0 && choice !=-1)
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
									proStkHdr.printAllProductInStockByCategory(parentCat.get_id(), 2);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								mainScreen();
								break;

							default:
								try {
									proStkHdr.printAllProductInStockByCategory(currCat.get_id(), 3);
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


	private void ReportByProduct() {
		System.out.println("Please select id of relevant product\nOr press 0 to return to previous screen");
		try {
			proHdr.printAllProductCatalog();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int choice = Util.readIntFromUser(0, -1);
		try {
			while(!proHdr.checkIfProductExists(choice) && choice !=0)
			{
				System.out.println("Invalid choice please insert again");
				choice = Util.readIntFromUser(0, -1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			proStkHdr.printAllProductInStock(choice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mainScreen();
	}

	private void ReportByMinAmount() {
		try {
			proStkHdr.printAllMinimalAmountProductStock();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainScreen();
	}

	private void ReportOfExpiredProducts() {
		try {
			proStkHdr.printAllExpiredProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainScreen();

	}

	private void ReportOfNonValidProducts() {
		try {
			proStkHdr.printAllNonValidProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainScreen();

	}

	private void ReportByStocktickets() {
		System.out.println("Please select relevant stock ticket ID\nPress 0 to return to the previous screen");
		try {
			stkTktHdr.printAllStockTickets();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int choice = Util.readIntFromUser(0, -1);

		try {
			while (!stkTktHdr.checkIfStockTicketExists(choice) && choice != 0) {
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
				stkTktHdr.printStockTicketItemByID(choice);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mainScreen();
		}
	}

}
