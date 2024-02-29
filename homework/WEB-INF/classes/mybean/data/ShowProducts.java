package mybean.data;

public class ShowProducts {
    private String productName;
    private String image;
    private double price;
    
    public ShowProducts(){
        
    }
  
    public ShowProducts(String productName, String image, double price) {
        this.productName = productName;
        this.image = image;
        this.price = price;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
}
