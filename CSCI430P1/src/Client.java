import java.util.*;
import java.io.*;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientid;
    private String name;
    private String phone;
    private String address;
    private double Client_billing;
    private static final String MEMBER_STRING = "C";
    private ShoppingCart cart;
	private List<Waitlist> waitlistedProducts = new LinkedList<Waitlist>();
    private Warehouse warehouse;

    //Constructor
    public Client(String name, String phone, String address, double billing){
        this.name = name;
        this.phone = phone;
        this.address = address;
		this.Client_billing = billing;
        clientid = MEMBER_STRING + (ClientIDServer.instance()).getId();
        cart = new ShoppingCart();
    }

    public String getClientId() {
        return clientid;
    }

    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }
    
    public String getAddress(){
        return address;
    }

    public Double getBilling()
    {
          return Client_billing;
    }
  
    public Double updateBalance(Double orderPrice)
    {
        this.Client_billing = Client_billing - orderPrice;
        return Client_billing;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setPhone(String newPhone){
        phone = newPhone;
    }

    public void setAddress(String newAddress){
        address = newAddress;
    }

    public boolean equals(String id) {
        return this.clientid.equals(id);
    }

    public void addToCart(String productID, int quantity)
    {
        cart.addProduct(productID, quantity);
    }

    public void removeFromCart(String productID, int quantity)
    {
        cart.removeProduct(productID, quantity);
    }

	public ClientOrder newOrder()
	{
		ClientOrder order = new ClientOrder();
		return order;
	}

	public boolean addProductToWaitlist(Waitlist w){
		return waitlistedProducts.add(w);
	}

	public boolean removeWaitlistedProduct(Waitlist w)
	{
		return waitlistedProducts.remove(w);
	}

	public Iterator<Waitlist> getWaitlistedProducts()
	{
		return waitlistedProducts.iterator();
	}

	public Waitlist searchWaitListOnProduct(Product p)
	{
		Iterator<Waitlist> iterator = waitlistedProducts.iterator();
		Waitlist w;
		while (iterator.hasNext())
		{
			w = iterator.next();
			if (w.getProduct() == p)
			{
				return w;
			}
		}
		return null;
	}

    public String toString() {
        String string = "Client name " + name + " address " + address + " id " + clientid + " phone " + phone + " Balance: $" + getBilling();
        return string;
    }

    /*
    public void checkOut()
    {
        orders.add(new Order(cart, warehouse));
    }
    */
}
