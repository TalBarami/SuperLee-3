package Inventory.program;

import Inventory.screens.MainScreen;

public class Run {

	public static void main(String[] args) {
		System.out.println("Welcome to Super-Lee!");
		MainScreen.mainScreen();
		SQLiteConnector.getInstance().CloseConnection();
	}

}