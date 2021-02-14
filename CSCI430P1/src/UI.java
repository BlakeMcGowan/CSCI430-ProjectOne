import java.util.*;
import java.text.*;
import java.io.*;

import static com.sun.glass.events.DndEvent.EXIT;
import static com.sun.webkit.CursorManager.HELP;

public class UI {
    private static UI ui;
    private static Warehouse warehouse;
    private BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
    private static final int ADD_PRODUCT = 0;
    private static final int REMOVE_PRODUCT = 1;
    private static final int ADD_SUPPLIER = 2;
    private static final int REMOVE_SUPPLIER = 3;
    private static final int ADD_CUSTOMER = 4;
    private static final int REMOVE_CUSTOMER = 5;
    private static final int SHOW_PRODUCT = 6;
    private static final int SHOW_SUPPLIER = 7;
    private static final int SHOW_CLIENT = 8;




    public void Display(){
        System.out.println("Enter number of the task you wish to perform: ");
        System.out.println(ADD_PRODUCT + " Add a product");
        System.out.println(REMOVE_PRODUCT + " Remove a product");
        System.out.println(ADD_SUPPLIER + " Add a supplier");
        System.out.println(REMOVE_SUPPLIER + " Remove a supplier");
        System.out.println(ADD_CUSTOMER + " Add a customer");
        System.out.println(REMOVE_CUSTOMER + " Remove a customer");
        System.out.println(SHOW_PRODUCT + " Display a products details");
        System.out.println(SHOW_SUPPLIER + " Show a suppliers details");
        System.out.println(SHOW_CLIENT + " Show a customers details");
    }

    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = read.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
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
    
    public void addProduct(){
        Product result;
        int purchasePrice = getNumber("Enter Purchase Price");
        int quantityAvaliable =  getNumber("Enter the Quanittiy Avaliable");
        String productName = getToken("Enter Product Name");
        result = warehouse.addProduct(purchasePrice, quantityAvaliable, productName);
        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("Product could not be added");
        }
    }

    public void addSupplier(){
        Supplier result;
        String supplierDescription = getToken("Enter Supplier Description");
        String supplierAddress =  getToken("Enter the Suppliers Address");
        String supplierName = getToken("Enter Suppliers Name");
        result = warehouse.addSupplier(supplierDescription, supplierAddress, supplierName);
        if (result == null) {
            System.out.println("Could not add Supplier");
        }
        System.out.println(result);
    }

    public void addClient(){
        Client result;
        String clientName = getToken("Please Enter Clients Name");
        String clientPhone = getToken("Please enter phone clients number");
        String clientAddress = getToken("Please enter clients Address");
        result = warehouse.addClient(clientName, clientPhone, clientAddress);
        if (result == null) {
            System.out.println("Could not add Client");
        }
        System.out.println(result);
    }

    public void removeProudct(){
        System.out.println("Fill in later");
    }
    public void removeSupplier(){
        System.out.println("Fill in later");
    }

    public void removeClient(){
        System.out.println("Fill in later");
    }
    public void showProudct(){
        System.out.println("Fill in later");
    }
    public void showSupplier(){
        System.out.println("Fill in later");
    }

    public void showClient(){
        System.out.println("Fill in later");
    }

    public void process() {
       // Display();
        int command;
        while ((command = getCommand()) != EXIT) {
            switch (command) {
                case ADD_PRODUCT:        addProduct();
                    break;
                case REMOVE_PRODUCT:         removeProudct();
                    break;
                case ADD_SUPPLIER:       addSupplier();
                    break;
                case REMOVE_SUPPLIER:      removeSupplier();
                    break;
                case ADD_CUSTOMER:      addClient();
                    break;
                case REMOVE_CUSTOMER:       removeClient();
                    break;
                case SHOW_PRODUCT:        showProudct();
                    break;
                case SHOW_SUPPLIER:       showSupplier();
                    break;
                case SHOW_CLIENT:      showClient();
                    break;
            }
        }
    }

    public static void main(String[] s) {
        ui.process();
        ui.Display();
    }
}


