package Inventory.screens;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Inventory.program.Util;
import Inventory.dbHandlers.ProductHandler;
import Inventory.dbHandlers.ProductStockHandler;
import Inventory.dbHandlers.StockTicketHandler;
import Inventory.entities.ProductStock;
import Inventory.entities.StockTicket;

public class ProductStockScreen {

	private MainScreen main;
	private ProductStockHandler proStkHdr;
	private ProductHandler proHdr;
	private StockTicketHandler stkTktHdr;

	public ProductStockScreen(MainScreen main){
		this.main = main;
		proStkHdr = new ProductStockHandler();
		proHdr = new ProductHandler();
		stkTktHdr = new StockTicketHandler();
	}

	public void mainScreen(){
		System.out.println("Select desired action: ");
		System.out.println("1. Add new stock product");
		System.out.println("2. Move product from stock to store");
		System.out.println("3. Previous screen");

		int result = Inventory.program.Util.readIntFromUser(1, 3);

		switch(result){
			case 1:
				addNewStockProductScreen();
				break;
			case 2:
				moveProductToStore();
				break;
			case 3:
				main.mainScreen();
				break;
		}

	}

	private void addNewStockProductScreen() {
		try {
			proHdr.printAllProductCatalog();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Please choose desired product ID\nPress 0 to return to the previos screen");

		int prod_id = Inventory.program.Util.readIntFromUser(0, -1);

		try {
			while (!(proHdr.checkIfProductExists(prod_id)) && prod_id != 0) {
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

		try {
			proStkHdr.printAllProductInStock(prod_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Select desired action: ");
		System.out.println("1. Update an existing product");
		System.out.println("2. Add new stock product");
		System.out.println("3. return to previous screen");
		int result = Inventory.program.Util.readIntFromUser(1, 3);

		switch(result){
			case 1:
				updateExistingStockProduct(prod_id);
				break;
			case 2:
				addNewStockProduct(prod_id);
				break;
			case 3:
				mainScreen();
				break;
		}

	}


	private void addNewStockProduct(int prod_id) {
		String date;
		int amount;

		System.out.println("To proceed press 1\nTo return to the previous screen press 2");
		if(Util.readIntFromUser(1, 2) == 2)
		{
			mainScreen();
			return;
		}

		System.out.println("Please enter date in format yyyy-MM-dd");
		date = Inventory.program.Util.readDateFromUser();
		try {
			while(proStkHdr.checkIfProductStockExists(prod_id, date, 1, false))
			{
				System.out.println("Date allready exists please enter again");
				date = Inventory.program.Util.readDateFromUser();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Please enter amount in stock");
		amount = Inventory.program.Util.readIntFromUser(1, -1);

		ProductStock productStock = new ProductStock(prod_id, amount, date, true);

		try {
			proStkHdr.addProductStock(productStock);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Product stock added successfully!");
		mainScreen();
	}

	private void updateExistingStockProduct(int prod_id) {
		String date;
		System.out.println("To proceed press 1\nTo return to the previous screen press 2");
		if(Util.readIntFromUser(1, 2) == 2)
		{
			mainScreen();
			return;
		}
		System.out.println("Please enter existing date in format yyyy-MM-dd");
		date = Inventory.program.Util.readDateFromUser();
		try {
			while(!proStkHdr.checkIfProductStockExists(prod_id, date,1 ,false))
			{
				System.out.println("Date does not exist please enter again");
				date = Inventory.program.Util.readDateFromUser();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Please enter new amount");
		int amount = Util.readIntFromUser(1, -1);

		System.out.println("Is product valid? 1=no, 2=yes");
		int is_valid = Util.readIntFromUser(1, 2);
		is_valid--;

		boolean valid = false;
		if (is_valid == 1)
			valid = true;

		ProductStock productStock = new ProductStock(prod_id, amount, date, valid);
		try {
			proStkHdr.UpdateProductStock(productStock);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Product stock updated successfully!");
		mainScreen();
	}
	private void moveProductToStore() {
		boolean isExist= false;
		boolean isFinished = false;
		boolean endOneChoice = false;
		int existProductInStock = 0;
		int id = 0;
		String date ="";
		int amount = 0;
		int choice = 0;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String currDate = dateFormat.format(cal.getTime());

		StockTicket stockTicket = new StockTicket(1, currDate);

		while(!isFinished)
		{
			isExist = false;
			endOneChoice = false;

			System.out.println("Please select desired product ID to move to the store\nPress -1 to end the current stock ticket\nPress 0 to return to the main Screen");
			try {
				proHdr.printAllProductCatalog();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(!isExist)
			{
				id = Util.readIntFromUser(-1, -1);
				// Exit from "Move from stock to store" screen
				if(id == 0)
				{
					mainScreen();
					return;
				}

				// End stock ticket option
				else if (id == -1) {
					isExist = true;
					endOneChoice = true;
					isFinished = true;
				}
				else
				{
					try {
						isExist = proHdr.checkIfProductExists(id);

						if (!isExist)
							System.out.println("Invalid id, please insert id again");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (!endOneChoice) {
				try {
					existProductInStock = proStkHdr.printAllProductInStock(id);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (existProductInStock == 0)
				{
					endOneChoice = true;
					System.out.println("This product is not in stock");
				}
			}

			while(!endOneChoice)
			{
				System.out.println("If you want to return to the product catalog list, press 1, otherwise press 2");
				choice = Util.readIntFromUser(1, 2);

				if (choice == 2) {
					System.out.println("Please choose the date of a valid product stock");
					date = Util.readDateFromUser();
					System.out.println("Please choose the neccessery amount");
					amount = Util.readIntFromUser(1, -1);
					try {
						endOneChoice = proStkHdr.checkIfProductStockExists(id, date, 1, true) &&
								proStkHdr.checkIfThereEnoughInStock(id, date, amount) && (date.compareTo(currDate) > 0);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if(!endOneChoice)
						System.out.println("Your choice was invalid");
					else {
						ProductStock item = new ProductStock(id, amount, date, true);
						boolean flag = true;

						for (ProductStock product : stockTicket.get_items()) {
							if (product.get_product_id() == item.get_product_id() && product.get_date_of_exp().equals(item.get_date_of_exp())) {
								System.out.println("Invalid choice");
								flag = false;
								break;
							}
						}

						if (flag)
							stockTicket.addProductToList(item);

						System.out.println("If you want to choose another product please press 1\nTo finish this stockTicket press 2");
						isFinished = Util.readIntFromUser(1, 2) == 2;
					}
				}

				else
					endOneChoice = true;
			}
		}

		if (stockTicket.get_items().size() > 0) {
			try {
				stockTicket.set_id(stkTktHdr.createNewStockTicket(currDate));

				// For each ProductStock in this StockTicket, we update the amount in stock and delete it if the amount is 0
				stkTktHdr.addStockTicketItems(stockTicket);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Stock ticket added successfully!");
			System.out.println("The ID of added stock ticket is " + stockTicket.get_id());

			System.out.println("The product stock items with minimal amount are:");
			try {
				proStkHdr.printAllMinimalAmountProductStock();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		mainScreen();
	}
}