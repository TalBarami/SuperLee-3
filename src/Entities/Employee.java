package Entities;

/**
 * Created by Tal on 10/04/2016.
 */
public class Employee {
    private String id;
    private String username;
    private String password;

    public Employee(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
