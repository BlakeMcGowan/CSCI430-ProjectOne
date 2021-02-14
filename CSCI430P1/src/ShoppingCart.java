import java.io.Serializable;
import java.util.*;
class Entry implements Serializable
{
  public Entry(String product, int quantity)
  {
      productID = product;
      count = quantity;
  }
  String productID;
  int count;
}
public class ShoppingCart implements Serializable {
    private String client;
    private Warehouse currentWarehouse;
	private List<Entry> cart = new List<Entry>();
	
    //Constructor
    public ShoppingCart(String clientID, Warehouse thisWarehouse){
        this.client = clientID;
		this.currentWarehouse = thisWarehouse;
    }

    public void addProduct(String productID, int quantity){
        cart.add(new Entry(productID, quantity));
    }

    public void removeProduct(String productID, int quantity){
        //remove product from cart
    }
	
	public String GetClient()
	{
		return client;
	}
}
