
import java.util.*;
import java.text.*;
import java.io.*;

public class SupplierTester {
    public static void main(String[] s) {
     Supplier S1 = new Supplier("kushal","310 8th Ave S","sells books");
     Supplier S2 = new Supplier("charlie","311 8th Ave S","sells phones");
     Supplier S3 = new Supplier("Ashish","312 8th Ave S","sells clothes");
     Supplier S4 = new Supplier("Linh","313 8th Ave S","sells games");

     System.out.println("------------------------------------");
     System.out.println(S1.getSupplierName() + " should be kushal");
     System.out.println(S2.getSupplierName() + " should be charlie");
     System.out.println(S3.getSupplierName() + " should be Ashish");
     System.out.println(S4.getSupplierName() + " should be Linh");
     System.out.println("------------------------------------");
     
     System.out.println(S1.getSupplierAddress() + " should be 310 8th Ave S");
     System.out.println(S2.getSupplierAddress() + " should be 311 8th Ave S");
      System.out.println(S3.getSupplierAddress() + " should be 312 8th Ave S");
     System.out.println(S4.getSupplierAddress() + " should be 313 8th Ave S");
     System.out.println("------------------------------------");

     System.out.println(S1.getSupplierDescription() + " should be sells books");
     System.out.println(S2.getSupplierDescription() + " should be sells phones");
     System.out.println(S3.getSupplierDescription() + " sells clothes");
     System.out.println(S4.getSupplierDescription() + " sells games");
     System.out.println("------------------------------------");
     
  }
}