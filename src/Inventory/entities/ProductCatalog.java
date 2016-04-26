package Inventory.entities;

public class ProductCatalog {
	private int _id;
	private String _name;
	private String _manufacture;
	private int _minimal_amount;
	private Category _main_cat;
	private Category _sub_cat;
	private Category _ssub_cat;
	
	public ProductCatalog(int _id, String _name, String _manufacture,
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

	public String get_manufacture() {
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

}
