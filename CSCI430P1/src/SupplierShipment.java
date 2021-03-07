import java.io.*;

public class SupplierShipment implements Serializable {
  private static final long serialVersionUID = 1L;
  private Supplier supp;
  private int quantity;
  private double price;

  public SupplierShipment(Supplier s, int quantity, double price){
    this.supp = s;
    this.quantity = quantity;
    this.price = price;
  }

  public Supplier getSupplier()
  {
    return supp;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public double getPrice()
  {
    return price;
  }

  public void setNewQuantity(int q)
  {
    this.quantity = quantity - q;
  }
}