package models;

public class Room {
    private int id;
    private String number;
    private String type;
    private double price;
    private String status;

    public Room() {}

    public Room(int id, String number, String type, double price, String status) {
        this.id = id; this.number = number; this.type = type; this.price = price; this.status = status;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return id + ") Room " + number + " | " + type + " | ₹" + price + " | " + status;
    }
}
