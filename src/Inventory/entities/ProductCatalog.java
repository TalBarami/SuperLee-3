package Inventory.entities;

import Inventory.dbHandlers.ProductHandler;

import java.sql.SQLException;

public class ProductCatalog {
	private int _id;
	private String _name;
	private int _manufacture;
	private int _minimal_amount;
	private Category _main_cat;
	private Category _sub_cat;
	private Category _ssub_cat;

	public ProductCatalog(int _id, String _name, int _manufacture,
						  int _minimal_amount, Category _main_cat, Category _sub_cat,
						  Category _ssub_cat) {
		this._id = _id;
		this._name = _name;
		this._manufacture = _manufacture;
		this._minimal_amount = _minimal_amount;
		this._main_cat = _main_cat;
		this._sub_cat = _sub_cat;
		this._ssub_cat = _ssub_cat;
	}

	public int get_id() {
		return _id;
	}

	public String get_name() {
		return _name;
	}

	public String get_manufacture_name() {
		String result = "";
		ProductHandler proHdr = new ProductHandler();
		try{
			result = proHdr.getManufactureNameByID(_manufacture);
		}catch(SQLException e){
			e.printStackTrace();
		}

		return result;
	}

	public int get_manufacture() {
		return _manufacture;
	}

	public int get_minimal_amount() {
		return _minimal_amount;
	}

	public Category get_main_cat() {
		return _main_cat;
	}

	public Category get_sub_cat() {
		return _sub_cat;
	}

	public Category get_ssub_cat() {
		return _ssub_cat;
	}

	@Override
	public String toString() {
		String ans="";
		ProductHandler proHdr = new ProductHandler();
		try {
			ans=  "\tID: " + _id +
                    "\t\tName: " + _name +
                    "\t\tManufacturer: " + proHdr.getManufactureNameByID(_manufacture);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProductCatalog that = (ProductCatalog) o;

		return _id == that._id;
	}

}