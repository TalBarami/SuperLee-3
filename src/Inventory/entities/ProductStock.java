package Inventory.entities;

public class ProductStock {

	private int _product_id;
	private int _amount;
	private String _date_of_exp;
	private boolean _is_valid;
	
	public ProductStock(int _product_id, int _amount,
			String _date_of_exp, boolean _is_valid) {
		this._product_id = _product_id;
		this._amount = _amount;
		this._date_of_exp = _date_of_exp;
		this._is_valid = _is_valid;
	}

	public int get_product_id() {
		return _product_id;
	}

	public int get_amount() {
		return _amount;
	}

	public String get_date_of_exp() {
		return _date_of_exp;
	}

	public boolean get_is_valid() {
		return _is_valid;
	}
}
