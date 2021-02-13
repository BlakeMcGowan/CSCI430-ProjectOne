import java.io.Serializable;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientid;
    private String name;
    private String phone;
    private String address;
    private static final String MEMBER_STRING = "C";

    //Constructor
    public Client(String name, String phone, String address){
        this.name = name;
        this.phone = phone;
        this.address = address;
        clientid = MEMBER_STRING + (ClientIDServer.instance()).getId();
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
      public String toString() {
        String string = "Client name " + name + " address " + address + " id " + clientid + " phone " + phone;
        return string;
    }
}
