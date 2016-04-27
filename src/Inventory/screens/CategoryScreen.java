package Inventory.screens;

import java.sql.SQLException;

import Inventory.program.Util;
import Inventory.dbHandlers.CategoryHandler;
import Inventory.entities.Category;

public class CategoryScreen {

	private MainScreen main;
	private CategoryHandler catHdr;

	public CategoryScreen(MainScreen main){
		this.main = main;
		catHdr = new CategoryHandler();
	}

	public void mainScreen(){
		System.out.println("Select desired action: ");
		System.out.println("1. Add new category");
		System.out.println("2. Update existing categories");
		System.out.println("3. Previous screen");

		int result = Inventory.program.Util.readIntFromUser(1, 3);

		switch(result){
			case 1:
				addNewCategoryScreen();
				break;
			case 2:
				updateDeleteExistingCategoryScreen();
				break;
			case 3:
				main.mainScreen();
				break;
		}
	}

	private void addNewCategoryScreen() {
		Category fatherCategory = null;
		Category currCat = null;
		String name;
		System.out.println("To proceed press 1\nTo return to the previous screen press 2");
		if(Util.readIntFromUser(1, 2) == 2)
		{
			mainScreen();
			return;
		}

		System.out.println("First please insert the new category name");
		name = Util.readStringFromUser();

		try {
			catHdr.PrintAllMainCategories();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("If you want to add new main category press 0\nIf you want to return to the main screen press -1\nOtherwise select father category ID");
		int choice = Util.readIntFromUser(-1, -1);
		try {
			while((currCat = catHdr.checkIfCategoryExists(choice, 1, null)) == null && choice !=0 &&choice !=-1)
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
					catHdr.addNewCategory(name, 1, fatherCategory);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				System.out.println("The category was added successfully");
				mainScreen();
				break;

			default:
				fatherCategory = currCat;
				try {
					catHdr.PrintAllSubCategories(choice, 2);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("If you want to add new sub category press 0\nIf you want to return to the main screen press -1\nOtherwise select father category ID");
				choice = Util.readIntFromUser(-1, -1);
				try {
					while((currCat = catHdr.checkIfCategoryExists(choice, 2, fatherCategory)) == null && choice !=0 &&choice !=-1)
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
							catHdr.addNewCategory(name, 2, fatherCategory);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println("The category was added successfully");
						mainScreen();
						break;

					default:
						fatherCategory = currCat;
						try {
							catHdr.PrintAllSubCategories(choice, 3);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("If you want to add new sub sub category press 0\nIf you want to return to the main screen press -1");
						choice = Util.readIntFromUser(-1, 0);
						switch (choice) {
							case -1:
								mainScreen();
								break;

							case 0:
								try {
									catHdr.addNewCategory(name, 3, fatherCategory);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println("The category was added successfully");
								mainScreen();
								break;
						}
						break;
				}
				break;

		}
	}

	private void updateDeleteExistingCategoryScreen() {
		int choice = 0;
		String name = null;
		Category parentCategory = null;
		Category currCat = null;

		System.out.println("Please choose the category id you wish to update\nPress 0 to return to the previous screen");

		try {
			catHdr.PrintAllMainCategories();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		choice = Util.readIntFromUser(0, -1);

		try {
			while ((currCat = catHdr.checkIfCategoryExists(choice, 1, null)) == null && choice != 0) {
				System.out.println("Invalid choice, please choose again");
				choice = Util.readIntFromUser(0, -1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (choice == 0) {
			mainScreen();
			return;
		}

		// If the user chooses main category, handle it
		else {
			parentCategory = currCat;

			System.out.println("If you wish to update this category name press 0\nPress -1 to return to the main screen\nOtherwise choose sub-category id to update");
			currCat = updateDeleteHelper(parentCategory, 2);

			// If the user choose sub category, handle it as the same way as we done above
			if (currCat != null) {
				parentCategory = currCat;

				System.out.println("If you wish to update this category name press 0\nPress -1 to return to the main screen\nOtherwise choose sub-category id to update");
				currCat = updateDeleteHelper(parentCategory, 3);

				// If the user chooses sub-sub category
				if (currCat != null) {
					parentCategory = currCat;

					System.out.println("If you wish to update this category name press 0\nPress -1 to return to the main screen");
					choice = Util.readIntFromUser(-1, 0);

					switch(choice) {
						case -1:
							mainScreen();
							break;

//						case -1:
//							try {
//								CategoryHandler.DeleteCategory(parentCategory.get_id());
//							} catch (SQLException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//							System.out.println("The category was deleted successfully!\nPlesae note, all sub-categories and all products tagged with this category were deleted.");
//							mainScreen();
//							break;

						case 0:
							System.out.println("Please enter new category name");
							name = Util.readStringFromUser();
							try {
								catHdr.updateCategoryName(parentCategory, name);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("The category was updated successfully!");
							mainScreen();
							break;
					}
				}
			}
		}
	}

	private Category updateDeleteHelper(Category parentCategory, int level) {
		int choice;
		String name;
		Category currCat = null;

		try {
			catHdr.PrintAllSubCategories(parentCategory.get_id(), level);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		choice = Util.readIntFromUser(-1, -1);

		try {
			while ((currCat = catHdr.checkIfCategoryExists(choice, level, parentCategory)) == null && choice != 0 && choice != -1) {
				System.out.println("Invalid choice, please choose again");
				choice = Util.readIntFromUser(-1, -1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		switch (choice) {
			case -1:
				mainScreen();
				break;
//
//			case -1:
//				try {
//					CategoryHandler.DeleteCategory(parentCategory.get_id());
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				System.out.println("The category was deleted successfully!\nPlesae note, all sub-categories and all products tagged with this category were deleted.");
//				mainScreen();
//				break;

			case 0:
				System.out.println("Please enter new category name");
				name = Util.readStringFromUser();
				try {
					catHdr.updateCategoryName(parentCategory, name);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("The category was updated successfully!");
				mainScreen();
				break;

			default:
				break;
		}

		return currCat;
	}
}
