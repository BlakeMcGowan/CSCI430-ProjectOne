import java.io.Serializable;
import java.util.*;
public class ShoppingCart implements Serializable {
    private String client;
    public static void main(String[] args) {


    }

    //Constructor
    public ShoppingCart(String clientID){
        this.client = clientID;
    }

    public void addProduct(String productID, int quantity){
        //add product to cart
    }

    public void removeProduct(String productID, int quantity){
        //remove product from cart
    }
	
	public string GetClient()
	{
		return client;
	}
}
