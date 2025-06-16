package model;

public class Product {
    private int productId;
    private String name;
    private String size;        // was 'description'
    private double price;
    private String category;
    private String imageUrl;
    private int quantity;       // was 'stock'

    public Product() {}

    public Product(int productId, String name, String size, double price, String category, String imageUrl, int quantity) {
        this.productId = productId;
        this.name = name;
        this.size = size;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    // For new products (without productId)
    public Product(String name, String size, double price, String category, String imageUrl, int quantity) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public int getId() {
        return productId;
    }
    public void setId(int id) {
        this.productId = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    // Optional: add description field to the main Product class if needed
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}