import java.util.*;
import java.io.*;

public class Warehouse implements Serializable{
    private static final long serialVersionUID = 1L;
    public static final int PRODUCT_NOT_FOUND = 1;
    public static final int PRODUCT_NOT_ISSUED = 2;
    public static final int PRODUCT_HAS_HOLD = 3;
    public static final int PRODUCT_ISSUED = 4;
    public static final int HOLD_PLACED = 5;
    public static final int NO_HOLD_FOUND = 6;
    public static final int OPERATION_COMPLETED = 7;
    public static final int OPERATION_FAILED = 8;
    public static final int NO_SUCH_SUPPLIER = 9;
    public static final int NO_SUCH_CLIENT = 10;
    private Inventory inventory;
    private SupplierList supplierList;
    private ClientList clientList;
    private static Warehouse warhouse;
    private static Warehouse warehouse;
    private Warehouse() {
      inventory = Inventory.instance();
      supplierList = SupplierList.instance();
      clientList = ClientList.instance();
}

public static Warehouse instance() {
    if (warehouse == null) {
        SupplierIdServer.instance();
        ClientIdServer.instance(); //instatiates singletons
        return (warehouse = new Warehouse());
    }
    else{
        return warehouse;
    }
}

public Product addProduct(String name, String supplier, String id){
    Product product = new Product(name, supplier, id);
    if (inventory.insertProduct(product)) {
        return (product);
    }
    return null;
}

public Supplier addSupplier(String name, String address, String description){
    Supplier supplier = new Supplier (name, address, description);
    if (supplierList.insertSupplier(supplier)){
        return(supplier);
    }
    return null;
}

public Client addClient(String name, String phone, String address){
    Supplier supplier = new Supplier (name, phone, address);
    if (clientList.insertClient(client)){
        return(client);
    }
    return null;
}

public int placeHold(String clientId, string productId, int duration){
    Product product = inventory.search(productId);
    if (product == null){
        return(PRODUCT_NOT_FOUND);
    }
    if (product.getBorrower() == null){
        return(PRODUCT_NOT_ISSUED);
    }
    Client client = clientList.search(clientId);
    if (client == null){
        return(NO_SUCH_SUPPLIER);
    }
    Hold hold = new hold (client, product, duration);
    product.placeHold(hold);
    client.placeHold(hold);
    return(HOLD_PLACED);
}

public Supplier searchSupplier(String supplierId){
    return supplierList.search(supplierId);
}

public Supplier processHold(String productId){
    Product product = inventory.search(productId);
    if (product == null){
        return (null);
    }
    Hold hold = product.getNextHold();
    if (hold == null){
        return (null);
    }
    hold.getClient().removeHold(clientId);
    hold.getProduct().removeHold(hold.getClient().getId());
    return(hold.getClient());
}


public int removeHold(String clientId, String bookId) {
    Client client = clientList.search(clientId);
    if (client == null) {
      return (NO_SUCH_CLIENT);
    }
    Product Product = inventory.search(productId);
    if (product == null) {
      return(PRODUCT_NOT_FOUND);
    }
    return client.removeHold(productId) && product.removeHold(clientId)? OPERATION_COMPLETED: NO_HOLD_FOUND;
  }
  public void removeInvalidHolds() {
    for (Iterator inventoryIterator = inventory.getProducts(); inventoryIterator.hasNext(); ) {
      for (Iterator iterator = ((Product) inventoryIterator.next()).getHolds(); iterator.hasNext(); ) {
        Hold hold = (Hold) iterator.next();
        if (!hold.isValid()) {
          hold.getProduct().removeHold(hold.getClient().getId());
          hold.getClient().removeHold(hold.getProduct().getId());
        }
      }
    }
  }

  public Product issueProduct(String clientId, stringProductId){
      Product product = inventory.search(productId);
      if (product == null){
          return (null);
      }
      if (product.getBorrower() != null) {
          return (null);
      }
      Client client = clientList.search(clientId);
      if (client == null) {
          return null;
      }
      if (!(product.issue(client) && client.issue(product))){
          return null;
      }
      return (product);
  }


  public Iterator getProducts() {
    Client client = clientList.search(clientId);
    if (client == null) {
        return(null);
    }
    else {
        return (client.getProductsIssued());
    }
  }
  
  public int removeProduct(String productId) {
    Product product = inventory.search(productId);
    if (product == null) {
      return(PRODUCT_NOT_FOUND);
    }
    if (product.hasHold()) {
      return(PRODUCT_HAS_HOLD);
    }
    if ( product.getBorrower() != null) {
      return(PRODUCT_ISSUED);
    }
    if (inventory.removeProduct(productId)) {
      return (OPERATION_COMPLETED);
    }
    return (OPERATION_FAILED);
  }



  public Iterator getSuppliers() {
    return supplierList.getClient();
  }

  public Iterator getTransactions(String productId, Calendar date) {
    Client client = clientList.search(clientId);
    if (client == null) {
      return(null);
    }
    return client.getTransactions(date);
  }

  public static Warehouse retrieve() {
    try {
      FileInputStream file = new FileInputStream("WarehouseData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      ClientIdServer.retrieve(input);
      return warehouse;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return null;
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
      return null;
    }
  }

  public static  boolean save() {
    try {
      FileOutputStream file = new FileOutputStream("WarehouseData");
      ObjectOutputStream output = new ObjectOutputStream(file);
      output.writeObject(warehouse);
      output.writeObject(ClientIdServer.instance());
      return true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return false;
    }
  }

  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(warehouse);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }

  private void readObject(java.io.ObjectInputStream input) {
    try {
      input.defaultReadObject();
      if (warehouse == null) {
        warehouse = (Warehouse) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  public String toString() {
    return inventory + "\n" + clientList;
  }
}