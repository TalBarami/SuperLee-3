package Inventory.entities;

import java.util.LinkedList;
import java.util.List;

public class StockTicket {
	private int _id;
	private String _date;
	private List<ProductStock> _items;
	
	public StockTicket(int _id, String _date) {
		this._id = _id;
		this._date = _date;
		_items = new LinkedList<ProductStock>();
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_date() {
		return _date;
	}

	public List<ProductStock> get_items() {
		return _items;
	} 

	public void addProductToList(ProductStock item)
	{
		_items.add(item);
	}
}
