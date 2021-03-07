import java.io.Serializable;
import java.util.*;
import java.io.*;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private int purchasePrice;
    private int quantityAvailable;
    private String productName;
    private List<SupplierShipment> productSupplier = new LinkedList<SupplierShipment>();
    private List<Waitlist> waitlistedClients = new LinkedList<Waitlist>();
    private List<Float> productPrices = new LinkedList<Float>();
    private static final String PRODUCT_STRING = "P";

    //Constructor
    public Product(String productName){
        this.productName = productName;
        id = PRODUCT_STRING + (ProductIDServer.instance()).getId();
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

    public String getId() {
        return id;
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

    public boolean equals(String id) {
        return this.id.equals(id);
    }
    
    public boolean link(Supplier supplierShipment, int q, double p){
        SupplierShipment Ship = new SupplierShipment (supplierShipment, q, p);
        return productSupplier.add(Ship) ? true: false;
    }

    public boolean unlink(SupplierShipment supplierShipment){
        return productSupplier.remove(supplierShipment) ? true: false;
    }

    public Iterator<SupplierShipment> getSupplier() {
        return productSupplier.iterator();
    }

    public SupplierShipment SearchSupplyList(Supplier supplierShipment)
    {
        int i = 0;
        for (; i <= productSupplier.size()-1; i++)
        {
            if((productSupplier.get(i).getSupplier()) == supplierShipment)
            {
                return productSupplier.get(i);
            }
        }
        return null;
    }

    public Iterator<Waitlist> getWaitlistedClients()
    {
      return waitlistedClients.iterator();
    }

    public Boolean addPrice(Float price){
        return productPrices.add(price) ? true : false;
    }

    public Boolean removePrice(int position){
        if (productPrices.remove(position) >= 0)
        {
          return true;
        }
        else
        {
          return false;
        }
      }
    
    public List<SupplierShipment> getList(){
        return productSupplier;
    }

    public Iterator<Float> getPrices(){
        return productPrices.iterator();
    }

    public boolean addClientToWaitlist(Waitlist w){
        return waitlistedClients.add(w);
    }
  
    public double moneyRound(float num) {
        return Math.round(num * 100.00) / 100.00;
    }

    public String toString() {
          return "Product: " + productName + " ID: " + id; 
    }

}
