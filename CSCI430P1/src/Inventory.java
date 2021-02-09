import java.io.Serializable;
import java.util.*;

public class Inventory implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static int IDGenerator = 0;
    private List productList = new LinkedList();
    private static Inventory inventory;

    private Inventory(){}

    public static Inventory instance()
    {
        if (inventory == null) {
            return (inventory = new Inventory());
        } else {
            return inventory;
        }
    }

    public boolean insertProduct(Product product) {
        product.add(product);
        return true;
      }

    private void writeObject(java.io.ObjectOutputStream output)
    {
        try
        {
            output.defaultWriteObject();
            output.writeObject(inventory);
        }
        catch (IOException ioe)
        {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input)
    {
        try
        {
            if (inventory != null)
            {
                System.out.println("inventory not null");
                return;
            }
            else
            {
                input.defaultReadObject();
                if (inventory == null)
                {
                    System.out.println("Read inventory data");
                    inventory = (Inventory) input.readObject();
                }
                else
                {
                    input.readObject();
                }
            }
        }
        catch (IOException ioe)
        {
            System.out.println("In SupplierList readObject" + ioe);
        }
        catch (ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
    }

    public Iterator getInventory()
    {
        return products.iterator();
    }

    public String toString() {
        return products.toString();
    }
}