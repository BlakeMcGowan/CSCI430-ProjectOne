import java.io.Serializable;

public class Client implements Serializable {
    private String clientID;
    private String name;
    private String phone;
    private String address;

    public static void main(String[] args) {

    }

    //Constructor
    public Client(String name, String phone, String address){
        this.name = name;
        this.phone = phone;
        this.address = address;
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

    public void setName(String newName){
        name = newName;
    }

    public void setPhone(String newPhone){
        phone = newPhone;
    }

    public void setAddress(String newAddress){
        address = newAddress;
    }
}
