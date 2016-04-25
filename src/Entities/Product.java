package Entities;

/**
 * Created by Tal on 10/04/2016.
 */
public class Product {
    private String id;
    private String name;
    private String manufacturer;

    public Product(String id, String name, String manufacturer) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public String toString() {
        return  "\tID: " + id +
                "\t\tName: " + name +
                "\t\tManufacturer: " + manufacturer;
    }
}
