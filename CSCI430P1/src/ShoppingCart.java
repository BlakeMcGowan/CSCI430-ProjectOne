import java.io.Serializable;
import java.util.*;

public class ShoppingCart implements Serializable {
    private String client;
    private Warehouse currentWarehouse;
	private List<Entry> items = new List<Entry>();
	
    public void addProduct(String productID, int quantity){
        items.add(new Entry(productID, quantity));
    }

    public void removeProduct(String productID, int quantity){
        //remove product
    }
	
	public String GetClient()
	{
		return client;
	}

    public List<Entry> getItems()
    {
        return items;
    }
}
