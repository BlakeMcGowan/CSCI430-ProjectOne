import java.util.*;

import jdk.jfr.Description;

import java.text.*;
import java.io.*;
public class UserInterface {
  private static UserInterface userInterface;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private static final int EXIT = 0;
  private static final int SAVE = 1;
  private static final int RETRIEVE = 2;
  private static final int ADD_SUPPLIERS = 3;
  private static final int SHOW_SUPPLIERS = 4;
  private static final int ADD_PRODUCT = 5;
  private static final int SHOW_PRODUCTS = 6;
  private static final int ADD_CLIENT = 7;
  private static final int SHOW_CLIENTS = 8;
  private static final int ASSIGN_PRODUCT = 9;
  private static final int UNASSIGN_PRODUCT = 10;
  private static final int LIST_SUPP_OF_PROD = 11;
  private static final int LIST_PROD_BY_SUPP = 12;
  private static final int HELP = 13;
  
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
    System.out.println("Enter a number between 0 and 16 as explained below:");
    System.out.println(EXIT + " to Exit.");
    System.out.println(SAVE + " to save the warehouse.");
    System.out.println(RETRIEVE + " to retrieve the warehouse.");
    System.out.println(ADD_SUPPLIERS + " to add a manufacturer.");
    System.out.println(SHOW_SUPPLIERS + " to show the manufacturers list.");
    System.out.println(ADD_PRODUCT + " to add a product.");
    System.out.println(SHOW_PRODUCTS + " to show the product list.");
    System.out.println(ADD_CLIENT + " to add a client.");
    System.out.println(SHOW_CLIENTS + " to show the client list.");
    System.out.println(ASSIGN_PRODUCT + " to assign a product to a manufacturer");
    System.out.println(UNASSIGN_PRODUCT + " to unassign a product from manufacturer");
    System.out.println(LIST_SUPP_OF_PROD + " to list suppliers of a specified product");
    System.out.println(LIST_PROD_BY_SUPP + " to list products supplied by a specified manufacturer");
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
  public void addClient() {
     Client result;
     do {
      String name = getToken("Enter Client Name: ");
      String address = getToken("Enter Address: ");
      String phone = getToken("Enter Phone No.: ");
      result = warehouse.addClient(name, address, phone);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Client could not be added. ");
      }
      if (!yesOrNo("Add more client?")) {
        break;
      }
    } while (true);
  }
  
  public void addProduct() {
     Product result;
     do {
      String name = getToken("Enter Product Name: ");
      String Quantity = getToken("Enter quantity: ");
      String Price = getToken("Enter the price: ");
      int quantity = Integer.parseInt(Quantity);
      int price = Integer.parseInt(Price);
      result = warehouse.addProduct(price, quantity,name);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Product could not be added. ");
      }
      if (!yesOrNo("Add more product?")) {
        break;
      }
    } while (true);
  }

  public void assignProduct()
  {
    String productID = getToken("Enter product ID: ");
    Product product;
    if ((product=warehouse.searchProduct(productID)) == null)
    {
      System.out.println("Product does not exist.");
      return;
    }

    String supplierID = getToken("Enter supplier ID: ");
    Supplier supplier;
    if ((supplier=warehouse.searchSupplier(supplierID)) == null)
    {
      System.out.println("No such supplier.");
      return;
    }

    Float p;
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


    product = warehouse.assignProdToSupplier(productID, supplierID, p);
    if (product != null)
    {
      System.out.println("Product " + product.getProductName() + " assigned to " + supplier.getSupplierName() + " successfully.");
    }
    else
    {
      System.out.println("Product could not be assigned.");
    }
  }

  public void unassignProduct()
  {
    String productID = getToken("Enter product ID: ");
    Product product;
    if ((product=warehouse.searchProduct(productID)) == null)
    {
      System.out.println("Product does not exist.");
      return;
    }

    String supplierID = getToken("Enter supplier ID: ");
    Supplier supplier;
    if ((supplier=warehouse.searchSupplier(supplierID)) == null)
    {
      System.out.println("No such supplier.");
      return;
    }

    product = warehouse.removeProdFromSupplier(productID, supplierID);
    if (product != null)
    {
      System.out.println("Product " + product.getProductName() + " unassigned from " + supplier.getSupplierName() + " successfully.");
    }
    else
    {
      System.out.println("Product could not be unassigned.");
    }


  }

   public void showSupplier() {
      Iterator <Supplier> allSuppliers = warehouse.getSuppliers();
      System.out.println("---------------------------------------------------------------");
      while (allSuppliers.hasNext()){
    Supplier supplier = allSuppliers.next();
          System.out.println(supplier.toString());
      }
      System.out.println("---------------------------------------------------------------\n");
  }
   
   public void showProducts() {
      Iterator <Product> allProducts = warehouse.getProducts();
      System.out.println("---------------------------------------------------------------");
      while (allProducts.hasNext()){
    Product product = allProducts.next();
          System.out.println(product.toString());
      }
      System.out.println("---------------------------------------------------------------\n");
  }
   
  public void showClient() {
      Iterator <Client> allClient = warehouse.getClients();
      System.out.println("---------------------------------------------------------------");
      while (allClient.hasNext()){
    Client client = allClient.next();
          System.out.println(client.toString());
      }
      System.out.println("---------------------------------------------------------------\n");
  }
  
  public void listSuppliersOfProduct()
  {
    Supplier supplier;
    float price;
    String pID = getToken("Enter the product ID: ");
    Product product = warehouse.searchProduct(pID);
    if (product != null)
    {
      Iterator<Supplier> s_traversal = warehouse.getSuppliersOfProduct(product);
      Iterator<Float> price_traversal = warehouse.getProductPrices(product);
      while (((s_traversal.hasNext()) != false) && ((price_traversal.hasNext()) != false))
      {
        supplier = s_traversal.next();
        price = price_traversal.next();
        System.out.println("Supplier: " + supplier.getSupplierName() + ". Supply Price: $" + price);
      }
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
        

        case SAVE              :  save();
                                	break;

        case RETRIEVE          :  retrieve();
                                	break;
        
        case ADD_SUPPLIERS  :  addSupplier();
                                	break;

        case SHOW_SUPPLIERS:  showSupplier();
                                        break;
                                        
        case ADD_PRODUCT       :  addProduct();
                                	break;

        case SHOW_PRODUCTS     :  showProducts();
                                        break;
                                        
        case ADD_CLIENT        :  addClient();
                                	break;

        case SHOW_CLIENTS       :  showClient();
                                        break;
        case ASSIGN_PRODUCT     :  assignProduct();
                                        break;
                                        
        case UNASSIGN_PRODUCT   :  unassignProduct();
                                        break;
        
        case LIST_SUPP_OF_PROD  :  listSuppliersOfProduct();
                                        break;
                                        
        case LIST_PROD_BY_SUPP  :  listProductsBySupplier();
                                        break;
                                        
        case HELP               :  help();
                                	break;
      }
    }
  }

  public static void main(String[] s) {
    UserInterface.instance().process();
  }
}