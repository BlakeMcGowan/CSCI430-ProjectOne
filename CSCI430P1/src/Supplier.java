import java.io.Serializable;
import java.util.*;
public class Supplier implements Serializable {
    private String supplierID;
    private String supplierDescription;
    private String supplierAddress;
    private String supplierName;
    public static void main(String[] args) {


    }

    //Constructor
    public Supplier(String supplierDescription, String supplierAddress, String supplierName){
        this.supplierDescription = supplierDescription;
        this.supplierAddress = supplierAddress;
        this.supplierName = supplierName;
    }

    public String getSupplierDescription(){
        return supplierDescription;
    }

    public String getSupplierAddress(){
        return supplierAddress;
    }

    public String getSupplierName(){
        return supplierName;
    }

    public void setSupplierDescription(String newDescription){
        supplierDescription = newDescription;
    }

    public void setSupplierAddress(String newSupplierAddress){
        supplierAddress = newSupplierAddress;
    }

    public void setSupplierName(String newSupplierAddress){
        supplierAddress = newSupplierAddress;
    }
}
