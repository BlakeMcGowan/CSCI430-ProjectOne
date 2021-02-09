import java.io.Serializable;

public class Product implements Serializable {
    private String prodcutID;
    private int purchasePrice;
    private int quantityAvailable;
    private String productName;
    public static void main(String[] args) {

    }

    //Constructor
    public Product(int purchasePrice, int quantityAvaliable, String prodcutName){
        this.purchasePrice = purchasePrice;
        this.quantityAvailable = quantityAvaliable;
        this.productName = prodcutName;
    }

    public int getPurchasePrice(){
        return purchasePrice;
    }

    public int getQuantityAvailable(){
        return quantityAvailable;
    }

    public String getProductName(){
        return productName;
    }

    public void setPurchasePrice(int newPurchasePrice){
        purchasePrice = newPurchasePrice;
    }

    public void setQuantityAvailable(int newQuantityAvailable){
        quantityAvailable = newQuantityAvailable;
    }

    public void setProductName(String newProductName){
        productName = newProductName;
    }


}
