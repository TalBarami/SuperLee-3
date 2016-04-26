package Inventory.entities;

public class Category {
	private int _id;
	private Category _parent_cat;
	private String _name;
	private int _level;


	public Category(int _id, Category _parent_cat, String _name, int _level) {
		this._id = _id;
		this._parent_cat = _parent_cat;
		this._name = _name;
		this._level = _level;
	}

	
	public int get_id() {
		return _id;
	}

	public Category get_parent_cat() {
		return _parent_cat;
	}

	public String get_name() {
		return _name;
	}

	public int get_level() {
		return _level;
	}
	
}
