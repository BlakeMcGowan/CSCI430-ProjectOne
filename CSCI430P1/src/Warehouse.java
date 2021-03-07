import java.util.*;
import java.io.*;

public class Warehouse implements Serializable{
    private static final long serialVersionUID = 1L;
    public static final int SUPPLIER_NOT_FOUND = 1;
    public static final int PRODUCT_NOT_FOUND = 2;
    public static final int CLIENT_NOT_FOUND = 3;

    private Inventory inventory;
    private SupplierList supplierList;
    private ClientList clientList;
    private OrderList orderList;

    private static Warehouse warehouse;

    private Warehouse() {
      inventory = Inventory.instance();
      supplierList = SupplierList.instance();
      clientList = ClientList.instance();
      orderList = OrderList.instance();
    }

  public static Warehouse instance() {
    if (warehouse == null) {
        SupplierIDServer.instance(); //instatiates singletons
        ClientIDServer.instance(); //instatiates singletons
        return (warehouse = new Warehouse());
    }
    else{
        return warehouse;
    }
  }

  public Product addProduct(String Name)
  {
    Product product = new Product(Name);
    if (inventory.insertProduct(product))
    {
      return (product);
    }
    else
    {
      return null;
    }
  }

  public Supplier addSupplier(String name, String address, String description){
    Supplier supplier = new Supplier (name, address, description);
    if (supplierList.insertSupplier(supplier)){
        return(supplier);
    }
    return null;
  } 

  public Client addClient(String name, String phone, String address, double billing){
    Client client = new Client (name, phone, address, billing);
    if (clientList.insertClient(client)){
        return(client);
    }
    return null;
  }

  public Product assignProdToSupplier (String productId, String supplierId, double price, int quantity){
    Product product = inventory.search(productId);
    if (product == null)
    {
      return null;
    }

    Supplier supplier = supplierList.search(supplierId);
    if (supplier == null)
    {
      return null;
    }

    SupplierShipment Ship = product.SearchSupplyList(supplier);
    if (Ship != null)
    {
      return null;
    }
    boolean success = product.link(supplier, quantity, price);
    success = supplier.assignProduct(product);
    if (success) {
      return product;
    }
    else {
      return null;
    }
  }

  public Product removeProdFromSupplier (String productId, String supplierId){
    Product product = inventory.search(productId);
    if (product == null)
    {
      return null;
    }

    Supplier supplier = supplierList.search(supplierId);
    if (supplier == null)
    {
      return null;
    }
    SupplierShipment Ship = product.SearchSupplyList(supplier);
    if (Ship == null)
    {
      System.out.println("Product already isn't assigned to this supplier.");
      return null;
    }

    boolean success = product.unlink(Ship);
    success = supplier.removeProduct(product);
    if (success) {
      return product;
    }
    else {
      System.out.println("Error 4");
      return null;
    }
  }

  public Client searchClient(String cID)
  {
    return clientList.search(cID);
  }

  public Product searchProduct(String productId){
    return inventory.search(productId);
  }

  public Supplier searchSupplier(String supplierId){
    return supplierList.search(supplierId);
  }

  public boolean addSuppOrder(Order order)
  {
    return OrderList.addOrder(order);
  }

  public Iterator getProducts() {
    return inventory.getProducts();
  }

  public Iterator getSuppliers() {
    return supplierList.getSuppliers();
  }
  
  public Iterator getClients() {
    return clientList.getClients();
  }

  public Iterator<SupplierShipment> getSuppliersOfProduct (Product p){
    return p.getSupplier();
  }

  public Iterator<Product> getProductBySupplier (Supplier s){
    return s.getProducts();
  }

  public Iterator<Float> getProductPrices (Product p){
    return p.getPrices();
  }

  public SupplierShipment searchProductSupplier(Product product, Supplier supp){
    return product.SearchSupplyList(supp);
  }

  public void FulfillWaitlist(Product p, int NewQ, SupplierShipment supplier){
    Iterator<Waitlist> iterator = p.getWaitlistedClients();
    Waitlist w;
    Client c;
    int WaitlistedQ;
    while ((iterator.hasNext()) && (NewQ >= 0))
    {
      w = iterator.next();
      WaitlistedQ = w.getQuantity();
      c = w.getClient();
      if ((NewQ - WaitlistedQ) >= 0)
      {
        NewQ = NewQ - WaitlistedQ;
        double price = WaitlistedQ * supplier.getPrice();
        c.updateBalance(price);
        iterator.remove(); 
        Waitlist Wlist = c.searchWaitListOnProduct(p);
        boolean success = c.removeWaitlistedProduct(Wlist);
      }
      else
      {
        double price = NewQ * supplier.getPrice();
        w.updateQuantity(WaitlistedQ - NewQ);
        NewQ = NewQ - NewQ;
        c.updateBalance(price);
      }

    }
    supplier.setNewQuantity(supplier.getQuantity() - NewQ);
  }

  public boolean AddProdToOrder(Product product, int quantity, ClientOrder order, Client c)
  {
    return order.AddProdToOrder(product, quantity, c);
  }

  public boolean AddProductsToSuppOrder(Product prod, int q, Order o)
  {
    return o.addProductToOrder(prod, q);
  }

  public boolean AddOrderToSupplier(Supplier s, Order o)
  {
    return s.add_Order(o);
  }

  public Order CreateOrder(Supplier s)
  {
    Order order = new Order(s);
    return order;
  }

  public ClientOrder CreateClientOrder(Client client)
  {
    return client.newOrder();
  }

  public double GetOrderTotal(ClientOrder order)
  {
    return order.getTotal();
  }

  public double updateClientBalance(Client c, Double orderTotal)
  {
    return c.updateBalance(orderTotal);
  }

  public Iterator<Waitlist> getWaitlistedClients(Product p)
  {
    return p.getWaitlistedClients();
  }

  public Iterator<Waitlist> getWaitlistedProducts(Client c)
  {
    return c.getWaitlistedProducts();
  }

  public Iterator<Order> getSuppOrders(Supplier s)
  {
    return s.getOrders();
  }

  public Order searchSuppOrders(String oID)
  {
    return OrderList.search(oID);
  }
  public static Warehouse retrieve() {
    try {
      FileInputStream file = new FileInputStream("WarehouseData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      ClientIDServer.retrieve(input);
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
      output.writeObject(ClientIDServer.instance());
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
    return clientList + "\n" + supplierList + "\n" + inventory + "\n" + orderList;
  }
}