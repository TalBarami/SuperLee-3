package Employees_Transports.Backend;

public class station {

	private String address;
	private String phone_Number;
	private String contact_Name;
	private area area;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone_Number() {
		return phone_Number;
	}

	public void setPhone_Number(String phone_Number) {
		this.phone_Number = phone_Number;
	}

	public String getContact_Name() {
		return contact_Name;
	}

	public void setContact_Name(String contact_Name) {
		this.contact_Name = contact_Name;
	}

	public area getArea() {
		return area;
	}

	public void setArea(area area) {
		this.area = area;
	}

	public station(String address, String phone_Number, String name, area areasname) {
		this.address = address;
		this.phone_Number = phone_Number;
		this.contact_Name=name;
		this.area=areasname;
	}

	@Override
	public String toString() {
		return "Station [address = " + address + ", phone_Number = 0" + phone_Number + ", contact_Name = " + contact_Name
				+ ", area=" + area.toString() + "]";
	}
	
	

}
