package Inventory.screens;

import java.sql.SQLException;

import Inventory.program.Util;
import Inventory.dbHandlers.CategoryHandler;
import Inventory.dbHandlers.ProductHandler;
import Inventory.entities.Category;
import Inventory.entities.ProductCatalog;

public class ProductCatalogScreen {

	public static void mainScreen(){
		System.out.println("Select desired action: ");
		System.out.println("1. Add new product");
		System.out.println("2. Update existing product");
		System.out.println("3. Delete existing Product");
		System.out.println("4. Previous screen");

		int result = Inventory.program.Util.readIntFromUser(1, 4);

		switch(result){
			case 1:
				addNewProductScreen();
				break;
			case 2:
				updateExistingProductScreen();
				break;
			case 3:
				deleteExistingProductScreen();
				break;
			case 4:
				MainScreen.mainScreen();
				break;
		}

	}

	private static void deleteExistingProductScreen() {

		System.out.println("Please choose product id to update\nPress 0 to return to the previous screen");
		int prod_id;
		try {
			ProductHandler.printAllProductCatalog();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		prod_id = Inventory.program.Util.readIntFromUser(0, -1);

		try {
			while (!(ProductHandler.checkIfProductExists(prod_id)) && prod_id!= 0) {
				System.out.println("Invalid choice, please enter again");
				prod_id = Inventory.program.Util.readIntFromUser(0, -1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(prod_id == 0)
		{
			mainScreen();
			return;
		}
		try {
			ProductHandler.deleteExistingProduct(prod_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Product deleted successfully!");
		mainScreen();

	}

	private static void updateExistingProductScreen() {
		System.out.println("Please choose product id to update\nPress 0 to return to the previous screen");
		int prod_id;
		try {
			ProductHandler.printAllProductCatalog();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		prod_id = Inventory.program.Util.readIntFromUser(0, -1);

		try {
			while (!(ProductHandler.checkIfProductExists(prod_id)) && prod_id != 0) {
				System.out.println("Invalid product id, please enter again");
				prod_id = Inventory.program.Util.readIntFromUser(0, -1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(prod_id == 0)
		{
			mainScreen();
			return;
		}
		ProductCatalog updatedProductCat = createProduct(prod_id);

		try {
			ProductHandler.updateExistingProduct(updatedProductCat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Product edited successfully!");
		mainScreen();
	}

	private static void addNewProductScreen() {

		// -1 so he know its a new product
		ProductCatalog product = createProduct(-1);
		if(product == null)
			return;


		try {
			ProductHandler.addProductCatalog(product);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Product added successfully!");
		mainScreen();
	}

	private static ProductCatalog createProduct(int product_id) {
		Category mainCat = null;
		Category subCat = null;
		Category ssubCat = null;
		int cat_id;
		String name;
		int manufacture_id;
		int minAmount;
		System.out.println("Please enter product name");
		name = Inventory.program.Util.readStringFromUser();

		try {
			ProductHandler.printAllManufactures();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Please enter manufacture id");

		manufacture_id = Inventory.program.Util.readIntFromUser(1, -1);

		try {
			while (!ProductHandler.checkIfManufactureExists(manufacture_id)) {
				System.out.println("Invalid choice, please choose again");
				manufacture_id = Inventory.program.Util.readIntFromUser(1, -1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("Please enter minimal amount");
		minAmount = Inventory.program.Util.readIntFromUser(1, -1);
		System.out.println("Please choose main category id\n Press 0 to return to main screen");

		try {
			CategoryHandler.PrintAllMainCategories();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cat_id = Inventory.program.Util.readIntFromUser(0, -1);

		try {
			while ((mainCat=CategoryHandler.checkIfCategoryExists(cat_id, 1, null)) == null && cat_id !=0) {
				System.out.println("Invalid id, please enter again");
				cat_id = Inventory.program.Util.readIntFromUser(0, -1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(cat_id == 0)
		{
			mainScreen();
			return null;
		}

		System.out.println("Would you like to choose sub-category?\nPress 1 if so, 2 otherwise");
		int choice = Inventory.program.Util.readIntFromUser(1, 2);

		if (choice == 1) {
			try {
				CategoryHandler.PrintAllSubCategories(cat_id, 2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Please choose sub category id\nPress 0 to return to the previous screen");

			cat_id = Inventory.program.Util.readIntFromUser(0, -1);

			try {
				while ((subCat=CategoryHandler.checkIfCategoryExists(cat_id, 2, mainCat)) == null && cat_id != 0) {
					System.out.println("Invalid id, please enter again");
					cat_id = Inventory.program.Util.readIntFromUser(0, -1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(cat_id == 0)
			{
				mainScreen();
				return null;
			}

			System.out.println("Would you like to choose sub-sub-category?\nPress 1 if so, 2 otherwise");
			choice = Inventory.program.Util.readIntFromUser(1, 2);

			if (choice == 1) {
				try {
					CategoryHandler.PrintAllSubCategories(cat_id, 3);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Please choose sub-sub category id\nPress 0 to return to the previous screen");

				cat_id = Inventory.program.Util.readIntFromUser(0, -1);

				try {
					while ((ssubCat=CategoryHandler.checkIfCategoryExists(cat_id,3, subCat)) == null && cat_id != 0) {
						System.out.println("Invalid id, please enter again");
						cat_id = Inventory.program.Util.readIntFromUser(0, -1);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(cat_id == 0)
				{
					mainScreen();
					return null;
				}
			}
		}

		ProductCatalog product = new ProductCatalog(product_id, name, manufacture_id, minAmount, mainCat, subCat, ssubCat);
		return product;
	}

}