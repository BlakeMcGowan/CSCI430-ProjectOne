import java.io.Serializable;
import java.util.*;

public class Order {
    List<Entry> items;
    float total;

    Order(ShoppingCart cart, Warehouse warehouse)
    {
        items = cart.getItems();
        //Inventory inventory = warehouse.Getinventory();
        for (Entry entry : items) {
            
        }
    }
}
