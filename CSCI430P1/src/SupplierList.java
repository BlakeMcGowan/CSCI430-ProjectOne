import java.io.Serializable;

public class SupplierList {
    private static int IDGenerator = 0;
    private List suppliers = new LinkedList();
    private static SupplierList supplierList;

    private SupplierList(){}

    public static SupplierList instance()
    {
        if (supplierList == null) {
            return (supplierList = new SupplierList());
        } else {
            return supplierList;
        }
    }

    public Iterator getSuppliers()
    {
        return suppliers.iterator();
    }

    public boolean insertProduct(Product product) {
        product.add(product);
        return true;
      }

    private void writeObject(java.io.ObjectOutputStream output)
    {
        try {
            output.defaultWriteObject();
            output.writeObject(supplierList);
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (supplierList != null) {
                return;
            } else {
                input.defaultReadObject();
                if (supplierList == null) {
                    supplierList = (SupplierList) input.readObject();
                } else {
                    input.readObject();
                }
            }
        } catch(IOException ioe) {
            System.out.println("in SupplierList readObject \n" + ioe);
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String toString() {
        return suppliers.toString();
    }
}