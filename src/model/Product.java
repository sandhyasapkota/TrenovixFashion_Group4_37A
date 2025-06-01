package model;

public class Product {
    private int id;
    private String name;
    private String size;
    private double price;
    private String category;
    private String imageUrl;
    private int quantity;
    private String description;

    public Product() {}

    public Product(int id, String name, String size, double price, String category, String imageUrl, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.description = description;
    }
        public Product(String name, String size, double price, String category, String imageUrl, int quantity, String description) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.description = description;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}