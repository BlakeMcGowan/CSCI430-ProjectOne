import java.util.*;
import java.text.*;
import java.io.*;

public class ClientTester {
    public static void main(String[] s) {
     Client C1 = new Client("John","123 Computer Street","123-456-7890");
     Client C2 = new Client("Jane","456 Science Street","098-765-4321");

     
     System.out.println(C1.getName() + " should be John");
     System.out.println(C2.getName() + " should be Jane");
     
     System.out.println(C1.getAddress() + " should be 123 Computer Street");
     System.out.println(C2.getAddress() + " should be 456 Science Street");
     
     System.out.println(C1.getPhone() + " should be 123-456-7890");
     System.out.println(C2.getPhone() + " should be 098-765-4321");
     
  }
}