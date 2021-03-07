import java.util.*;
import java.text.*;
import java.io.*;

public class UserInterface {
  private static UserInterface userInterface;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int ADD_PRODUCT = 2;
  private static final int ADD_SUPPLIERS = 3;
  private static final int ASSIGN_PRODUCT = 4;
  private static final int UNASSIGN_PRODUCT = 5;
  private static final int SHOW_CLIENTS = 6;
  private static final int SHOW_PRODUCTS = 7;
  private static final int SHOW_SUPPLIERS = 8;
  private static final int LIST_SUPP_OF_PROD = 9;
  private static final int LIST_PROD_BY_SUPP = 10;
  private static final int ADD_CLIENT_ORDER = 11;
  private static final int PLACE_ORDER_WITH_SUPPLIER = 12;
  private static final int ACCEPT_CLIENT_PAYMENT = 13;
  private static final int OUTSTANDING_BALANCE_LIST  = 14;
  private static final int GET_WAIT_ORD_FOR_PROD  = 15;
  private static final int GET_WAIT_ORD_FOR_CLIENT = 16;
  private static final int GET_LIST_ORDERS_SUPP = 17;
  private static final int RECEIVE_A_SHIPMENT = 18;
  private static final int SAVE = 19;
  private static final int RETRIEVE = 20;
  private static final int HELP = 21;
  
  private UserInterface() {
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      warehouse = Warehouse.instance();
    }
  }
  public static UserInterface instance() {
    if (userInterface == null) {
      return userInterface = new UserInterface();
    } else {
      return userInterface;
    }
  }
  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line);
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }
  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }
  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }

  public Calendar getDate(String prompt)
  {
    do {
      try{
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      } catch (Exception fe){
        System.out.println("Please input a date as mm/dd/yy");
      }
    } while (true);
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("Enter a number between 0 and 21 as explained below:");
    System.out.println(EXIT + " to Exit.");
    System.out.println(ADD_CLIENT + " to add a client.");
    System.out.println(ADD_PRODUCT + " to add a product.");
    System.out.println(ADD_SUPPLIERS + " to add a supplier.");
    System.out.println(ASSIGN_PRODUCT + " to assign a product to a supplier");
    System.out.println(UNASSIGN_PRODUCT + " to unassign a product from supplier");
    System.out.println(SHOW_CLIENTS + " to show the client list.");
    System.out.println(SHOW_PRODUCTS + " to show the product list.");
    System.out.println(SHOW_SUPPLIERS + " to show the suppliers list.");
    System.out.println(LIST_SUPP_OF_PROD + " to list suppliers of a specified product");
    System.out.println(LIST_PROD_BY_SUPP + " to list products supplied by a specified supplier");
    System.out.println(ADD_CLIENT_ORDER + " to add and process a client order");
    System.out.println(PLACE_ORDER_WITH_SUPPLIER + " to place an order with a supplier");
    System.out.println(ACCEPT_CLIENT_PAYMENT + " to accept a payment from a client");
    System.out.println(OUTSTANDING_BALANCE_LIST + " to list clients with an outstanding balance");
    System.out.println(GET_WAIT_ORD_FOR_PROD + " to get a list of waitlisted orders for a product");
    System.out.println(GET_WAIT_ORD_FOR_CLIENT + " to get a list of waitlisted orders for a client");
    System.out.println(GET_LIST_ORDERS_SUPP + " to get a list of orders placed with a supplier");
    System.out.println(RECEIVE_A_SHIPMENT + " to receive a Shipment from the supplier");
    System.out.println(SAVE + " to save the warehouse.");
    System.out.println(RETRIEVE + " to retrieve the warehouse.");
    System.out.println(HELP + " for help");
  }

  public void addSupplier() {
     Supplier result;
     do {
      String name = getToken("Enter Supplier Name: ");
      String address = getToken("Enter Address: ");
      String description = getToken("Enter Description: ");
      result = warehouse.addSupplier(name, address, description);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Supplier could not be added. ");
      }
      if (!yesOrNo("Add more suppliers?")) {
        break;
      }
    } while (true);
  }
  public void addClient()
  {
    String Name = getToken("Enter client name: ");
    String Address = getToken("Enter client address: ");
    String Phone = getToken("Enter client phone: ");
    double Billing;
    while (true)
    {
      String balance = getToken("Enter client billing info(balance): ");
      try {
        Billing = Double.parseDouble(balance);
        break; // will only get to here if input was a double
        } catch (NumberFormatException ignore) {
        System.out.println("Invalid input");
      }
    }
    Client result;
    result = warehouse.addClient(Name, Phone, Address, Billing);
    if (result == null)
    {
      System.out.println("Could not add client.");
    }
    else
    {
      System.out.println(result);
    }
  }
  
  public void addProduct()
  {
    String Name = getToken("Enter product name: ");
    Product result;
    result = warehouse.addProduct(Name);
    if (result == null)
    {
      System.out.println("Could not add product.");
    }
    else
    {
      System.out.println(result);
    }
  }

  public void assignProduct()
  {
    int Scount=1;
    int Pcount=1;
    
    String pID = getToken("Enter product ID: ");
    Product product;
    while((product = warehouse.searchProduct(pID)) == null){
      System.out.println("Product doesn't exist.");
      if(Pcount++ == 3){
        System.out.println("You have reached the maximum try. Try next time.\n");
        return;
      }
     pID = getToken("Enter valid product ID: ");
    }

    String supplierID = getToken("Enter supplier ID: ");
    Supplier s;
    while((s = warehouse.searchSupplier(supplierID)) == null){
      System.out.println("Supplier doesn't exist.");
      if(Scount++ == 3){
        System.out.println("You have reached the maximum try. Try next time.\n");
        return;
      }
     supplierID = getToken("Enter valid ID: ");
    }
    double p;
    while (true)
    {
      String price = getToken("Enter product unit price: ");
      try {
        p = Float.parseFloat(price);
        break; // will only get to here if input was a double
        } catch (NumberFormatException ignore) {
        System.out.println("Invalid input");
      }
    }

    int q;
    while (true)
    {
      String quantity = getToken("Enter product quantity:  (if unknown or NA, enter 0)");
      try {
        q = Integer.parseInt(quantity);
        break; // will only get to here if input was an int
        } catch (NumberFormatException ignore) {
        System.out.println("Invalid input");
      }
    }


    product = warehouse.assignProdToSupplier(pID, supplierID, p, q);
    if (product != null)
    {
      System.out.println("Product " + product.getProductName() + " assigned to " + s.getSupplierName() + " successfully.");
    }
    else
    {
      System.out.println("Product could not be assigned.");
    }
  }

  public void unassignProduct()
  {
    int Scount=1;
    int Pcount=1;
    String pID = getToken("Enter product ID: ");
    Product product;
    while((product = warehouse.searchProduct(pID)) == null){
      System.out.println("Product doesn't exist.");
      if(Pcount++ == 3){
        System.out.println("You have reached the maximum try. Try next time.\n");
        return;
      }
     pID = getToken("Enter valid product ID: ");
    }

    String sID = getToken("Enter manufacturer ID: ");
    Supplier s;
    while((s = warehouse.searchSupplier(sID)) == null){
      System.out.println("Manufacturer doesn't exist.");
      if(Scount++ == 3){
        System.out.println("You have reached the maximum try. Try next time.\n");
        return;
      }
     sID = getToken("Enter valid ID: ");
    }

    product = warehouse.removeProdFromSupplier(pID, sID);
    if (product != null)
    {
      System.out.println("Product " + product.getProductName() + " unassigned from " + s.getSupplierName() + " successfully.");
    }
    else
    {
      System.out.println("Product could not be unassigned.");
    }


  }
   public void showSupplier()   {
    Iterator<Supplier>  allSuppliers = warehouse.getSuppliers();
    if(allSuppliers.hasNext() == false){
      System.out.println("No Supplier in the System.\n");
      return;
    }else{
      System.out.println("-------------------------------------------------");
      while ((allSuppliers.hasNext()) != false)
      {
        Supplier supplier = allSuppliers.next();
        System.out.println(supplier.toString());
      }
      System.out.println("-------------------------------------------------\n");
    }
  }
   
   public void showProducts()   {
    Iterator<Product> allProducts = warehouse.getProducts();
    if(allProducts.hasNext()== false){
      System.out.println("No products in the System.\n");
      return;
    }else{
      System.out.println("------------------------");
        while ((allProducts.hasNext()) != false)
        {
          Product product = allProducts.next();
          System.out.println(product.toString());
        }
        System.out.println("------------------------\n");
      }
  }
   
  public void showClient()   {
    Iterator<Client>  allClients = warehouse.getClients();
    if(allClients.hasNext()==false){
      System.out.println("No Clients exist in the system. \n");
    }else{
      System.out.println("------------------------------------------------------------------------------------");
        while ((allClients.hasNext()) != false)
        {
          Client client = allClients.next();
          System.out.println(client.toString());
        }
        System.out.println("------------------------------------------------------------------------------------\n");
      }
  }

  
  public void listSuppliersOfProduct()
  {
    Double price;
    String pID = getToken("Enter the product ID: ");
    Product product = warehouse.searchProduct(pID);
    System.out.println("-----------------------------------------------");
    if (product != null)
    {
      SupplierShipment supplierShipment;
      Iterator<SupplierShipment> allSuppliers = warehouse.getSuppliersOfProduct(product);
      while ((allSuppliers.hasNext()) != false)
      {
        supplierShipment = allSuppliers.next();
        System.out.println("Supplier: " + supplierShipment.getSupplier().getSupplierName() + ". Price: $" + supplierShipment.getPrice() + " Quantity: " + supplierShipment.getQuantity());
      }
      System.out.println("-----------------------------------------------\n");
    }
    else
    {
      System.out.println("Product not found");
    }
  }
  
  public void listProductsBySupplier()
  {
    String s = getToken("Please enter supplier ID: ");
    Supplier supplier = warehouse.searchSupplier(s);
    if (supplier != null)
    {
      Product p_temp;
      Iterator<Product> p_traversal = warehouse.getProductBySupplier(supplier);
      while (p_traversal.hasNext() != false)
      {
          p_temp = p_traversal.next();
          System.out.println(p_temp.getSupplier());
      }
    }
    else
    {
      System.out.println("Supplier doesn't exist");
    }
  }

  public void AddClientOrder()
  {
    String cID = getToken("Please enter client ID: ");
    Client client = warehouse.searchClient(cID);
    if (client != null)
    {
      ClientOrder order = warehouse.CreateClientOrder(client);

      do{
        String pID = getToken("Enter product ID: ");
        Product product = warehouse.searchProduct(pID);
        while (product == null)
        {
          System.out.println("Could not find product.");
          pID = getToken("Enter product ID: ");
          product = warehouse.searchProduct(pID);
        }
        int q;
        while (true)
        {
          String quantity = getToken("Enter quantity: ");
          try {
            q = Integer.parseInt(quantity);
            break; // will only get to here if input was an int
            } catch (NumberFormatException ignore) {
              System.out.println("Invalid input");
          }
        }
        boolean success = warehouse.AddProdToOrder(product, q, order, client);
        if (success)
        {
          System.out.println("Product added to order successfully.");
        }
        else
        {
          System.out.println("Product couldn't be added to order.");
        }
      }while(yesOrNo("Add another product to order? "));
      double Total = warehouse.GetOrderTotal(order);
      System.out.println("Order total is: $" + Total);
      double NewClientBalance = warehouse.updateClientBalance(client, Total);
      System.out.println("Client " + client.getName() + " new balance is " + NewClientBalance);
    }
    else
    {
      System.out.println("Client doesn't exist");
    }
  }

  public void PlaceOrder()
  {
    String pID;
    int quantity;
    Product product;
    SupplierShipment supplierShipment;
    Boolean success;
    int Scount=1;
    int Pcount=1;
    int PTcount=1;

    String sID = getToken("Enter supplier ID: ");
    Supplier supplier;
    while((supplier = warehouse.searchSupplier(sID)) == null){
      System.out.println("Supplier doesn't exist.");
      if(Scount++ == 2){
        System.out.println("You have reached the maximum try. Try next time.");
        return;
      }
     sID = getToken("Enter valid ID: ");
    }

    Order order = warehouse.CreateOrder(supplier);
    if (order == null){
      return;
    }
    do {
      pID = getToken("Enter product ID: ");
      while ((product = warehouse.searchProduct(pID)) == null)
      {
        System.out.println("Product doesnt exist in the warehouse.");
        if(Pcount++ == 2){
          System.out.println("You have reached the maximum try. Try next time. Thank you.");
          return;
      }
        pID = getToken("Enter a Valid ID: ");
      }

      while ((supplierShipment = warehouse.searchProductSupplier(product, supplier)) == null)
      {
        System.out.println("Product isn't supplied by specified supplier");
        pID = getToken("Enter product ID: ");
        while ((product = warehouse.searchProduct(pID)) == null){
          System.out.println("Product doesn't exist.");
          if(PTcount++ == 3){
            System.out.println("You have reached the maximum try. Try next time.\n");
          return;
          }
          pID = getToken("Enter valid product ID: ");
        }
      }

      while (true)
      {
        String q = getToken("Enter quantity: ");
        try {
          quantity = Integer.parseInt(q);
          break; // will only get to here if input was an int
          } catch (NumberFormatException ignore) {
            System.out.println("Invalid input");
        }
      }
      success = warehouse.AddProductsToSuppOrder(product, quantity, order);
      if (!success){
        System.out.println("Couldn't add to order.");
        return;
      }
    }while (yesOrNo("Add another product to order? "));
    success = warehouse.AddOrderToSupplier(supplier, order);
    success = warehouse.addSuppOrder(order);
    if (success)
    {
      System.out.println("Order added successfully");
      System.out.println("Order ID: " + order.getID());
    }

    else
    {
      System.out.println("Failed to add order");
    }
  }

  public void AcceptClientPayment()
  {
    int C_count = 1;
    Client client;
    String cID = getToken("Enter client ID: ");
    while((client = warehouse.searchClient(cID)) == null){
      System.out.println("Client doesn't exist.");
      if(C_count++ == 3){
        System.out.println("You have reached the maximum try. Try next time.\n");
        return;
      }
     cID = getToken("Enter valid Client ID: ");
    }
    Double Payment;
    while (true)
    {
      String p = getToken("Enter payment amount: ");
      try {
        Payment = Double.parseDouble(p);
        break;
        } catch (NumberFormatException ignore) {
          System.out.println("Invalid input");
      }
    }
    Payment = -1 * Payment; //used so we can use the same updateBalance method in client
    Double newTotal = warehouse.updateClientBalance(client, Payment);
    System.out.println("Client " + client.getName() + "'S new balance is $" + newTotal);
  }

  public void ListClientsWithOutstandingBalance()
  {
    Iterator<Client> allClients = warehouse.getClients();
    Client c;
    int i = 1;
     System.out.println("---------------------------------------");
        while (allClients.hasNext())
        {
          c = allClients.next();
          if (c.getBilling() < 0)
          {
            System.out.println(i + ".) " + c.getClientId() + ", Remaining Balance: $" + c.getBilling());
            i++;
          }
        }
        System.out.println("---------------------------------------\n");
  }

  public void GetWaitlistedOrdersForProd()
  {
    String pID = getToken("Enter product ID: ");
    Product p = warehouse.searchProduct(pID);
    int i = 1;
    if (p == null)
    {
      System.out.println("Product doesn't exist");
      return;
    }
    Iterator<Waitlist> waitlist = warehouse.getWaitlistedClients(p);
    Waitlist w;
    System.out.println("---------------------------------");
    while (waitlist.hasNext())
    {
      w = waitlist.next();
      System.out.println(i + ".) Client: " + w.getClient().getClientId() + ", Amount Waitlisted: " + w.getQuantity());
      i++;
    }
    System.out.println("---------------------------------\n");
  }

  public void GetWaitlistedOrdersForClient()
  {
    String cID = getToken("Enter client ID: ");
    Client c = warehouse.searchClient(cID);
    int i = 1;
    if (c == null)
    {
      System.out.println("Client doesn't exist");
      return;
    }
    System.out.println("------------------------------------");
    Iterator<Waitlist> waitlist = warehouse.getWaitlistedProducts(c);
    Waitlist w;
    while (waitlist.hasNext())
    {
      w = waitlist.next();
      System.out.println(i + ".) Product: " + w.getProduct().getId() + ", Amount Waitlisted: " + w.getQuantity());
      i++;
    }
    System.out.println("------------------------------------\n");
  }

  private void ListOrdersPlacedWithSupplier()
  {
    String sID = getToken("Enter supplier ID: ");
    Supplier s = warehouse.searchSupplier(sID);
    int i= 1;
    int k =1;
    while(s == null){
      System.out.println("Supplier doesn't exist.");
      if(k++ == 2){
        System.out.println("You have reached the maximum try. Try next time.");
        return;
      }
     sID = getToken("Enter valid ID: ");
     s = warehouse.searchSupplier(sID);
    }
    Iterator<Order> o_Traversal = warehouse.getSuppOrders(s);
    Order order;
    while (o_Traversal.hasNext())
    {
      System.out.println("ORDER NUMBER: " + i + "\n---------------");
      order = o_Traversal.next();
      System.out.println("Oder ID: " + order.getID());
      Iterator<Product> p_Traversal = order.getProds();
      Product p;
      Iterator<Integer> q_Traversal = order.getQs();
      int q;
      while (p_Traversal.hasNext() && q_Traversal.hasNext())
      {
        int j = 1;
        p = p_Traversal.next();
        q = q_Traversal.next();
        System.out.println("Product: " + p.getId() + ", Quantity: " + q);
        j++;
      }
      i++;
      System.out.println("");
    }
  }

  private void ReceiveShipment()
  {
    int O_count = 1;
    String oID = getToken("Enter the order ID: ");
    Order order ;
    while((order = warehouse.searchSuppOrders(oID)) == null){
      System.out.println("No such order found. ");
      if(O_count++ == 3){
        System.out.println("You have reached the maximum try. Try next time.\n");
      }
      oID = getToken("Enter the valild order ID: ");
    }

    if (order.getOrderStatus() == true)
    {
      System.out.println("Order has already been processed and received\n");
      return;
    }

    Supplier supplier = order.getSupplier();
    Iterator<Product> allProducts = order.getProds();
    Product p;
    Iterator<Integer> quantities = order.getQs();
    int q;
    System.out.println("Order details");
    System.out.println("-------------");
    while (allProducts.hasNext() && quantities.hasNext())
    {
      int j = 1;
      p = allProducts.next();
      q = quantities.next();
      System.out.println("Product: " + p.getId() + ", Quantity: " + q);
      j++;
    }
    boolean add = yesOrNo("Accept order?");
    if (add)
    {
      Iterator<Product> products = order.getProds();
      Product prod;
      Iterator<Integer> qtys = order.getQs();
      int quant;
      SupplierShipment supplierShipment;
      while (products.hasNext() && qtys.hasNext())
      {
        int j = 1;
        prod = products.next();
        quant = qtys.next();
        supplierShipment = warehouse.searchProductSupplier(prod, supplier);
        if (supplierShipment.getQuantity() == 0)
        {
          supplierShipment.setNewQuantity(-1 * quant);
          //fullfill the waitlist first
          warehouse.FulfillWaitlist(prod, quant, supplierShipment);
        }
        else
        {
          supplierShipment.setNewQuantity(-1 * quant);
        }
        j++;
      }
      order.receiveOrder();//shipment has been received
    }
    System.out.println("Remainig products successfully added to inventory.\n");
  }


  private void save() {
    if (Warehouse.save()) {
      System.out.println("The warehouse has been successfully saved in the file WarehouseData.\n" );
    } else {
      System.out.println("There has been an error in saving the warehouse.\n" );
    }
  }

  private void retrieve() {
    try {
      Warehouse tempWarehouse = Warehouse.retrieve();
      if (tempWarehouse != null) {
        System.out.println("The warehouse has been successfully retrieved from the file WarehouseData \n" );
        warehouse = tempWarehouse;
      } else {
        System.out.println("File doesnt exist; creating new warehouse" );
        warehouse = Warehouse.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        
        case ADD_CLIENT        :  addClient();
                                  break;
        case ADD_PRODUCT       :  addProduct();
                                	break;
        case ADD_SUPPLIERS     :  addSupplier();
                                	break;
        case ASSIGN_PRODUCT    :  assignProduct();
                                  break;                               
        case UNASSIGN_PRODUCT  :  unassignProduct();
                                  break;
        case SHOW_CLIENTS      :  showClient();
                                  break;                             
        case SHOW_PRODUCTS     :  showProducts();
                                  break;
        case SHOW_SUPPLIERS    :  showSupplier();
                                  break;
        case LIST_SUPP_OF_PROD  :  listSuppliersOfProduct();
                                  break;                         
        case LIST_PROD_BY_SUPP  :  listProductsBySupplier();
                                  break;    
        case ADD_CLIENT_ORDER  :  AddClientOrder();
                                  break;
        case PLACE_ORDER_WITH_SUPPLIER : PlaceOrder();
                                         break;   
        case ACCEPT_CLIENT_PAYMENT     : AcceptClientPayment();
                                         break;
        case OUTSTANDING_BALANCE_LIST  : ListClientsWithOutstandingBalance();
                                         break;
        case GET_WAIT_ORD_FOR_PROD     : GetWaitlistedOrdersForProd();
                                         break;
        case GET_WAIT_ORD_FOR_CLIENT   : GetWaitlistedOrdersForClient();
                                         break;                        
        case GET_LIST_ORDERS_SUPP : ListOrdersPlacedWithSupplier();
                                    break;
        case RECEIVE_A_SHIPMENT   : ReceiveShipment();
                                    break;
        case SAVE              :  save();
                                  break;
        case RETRIEVE          :  retrieve();
                                  break;
        case HELP              :  help();
                                	break;
      }
    }
  }

  public static void main(String[] s) {
    UserInterface.instance().process();
  }
}