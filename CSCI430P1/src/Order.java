import java.io.Serializable;
import java.util.*;

public class Order implements Serializable{
    private List<Entry> items;
    private float total;

    Order(ShoppingCart cart, Warehouse warehouse)
    {
        total = 0;
        items = cart.getItems();
        Inventory inventory = warehouse.Getinventory();
        for (Entry entry : items) {
            Product product = inventory.search(entry.productID);
            total = total + (product.getPurchasePrice() * entry.count);
        }
    }

    public String GenerateInvoice()
    {
        String invoice = "Purchased Items";

        for (Entry entry : items) {
            invoice = invoice + "\n"
            + "Item: " + entry.productID + " Count: " + entry.count;
        }
        invoice = invoice + "\n\n"
        + "Total: " + total;

        return invoice;
    }
}
