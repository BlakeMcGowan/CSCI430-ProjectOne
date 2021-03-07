import java.io.Serializable;
import java.util.*;
import java.io.*;

public class ClientList implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<Client> clients = new LinkedList();
    private List<ClientOrder> Orders = new LinkedList<ClientOrder>();
    private static ClientList clientList;

    private ClientList(){}

    public static ClientList instance()
    {
        if (clientList == null) {
            return (clientList = new ClientList());
        } else {
            return clientList;
        }
    }

    public boolean insertClient(Client client) {
        clients.add(client);
        return true;
      }

    public Iterator getClients()
    {
        return clients.iterator();
    }

    public Client search(String clientID) {
        for (Iterator iterator = clients.iterator(); iterator.hasNext(); ) {
            Client client = (Client) iterator.next();
            if (client.getClientId().equals(clientID)) {
                return client;
            }
        }
        return null;
    }
    
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
          output.defaultWriteObject();
          output.writeObject(clientList);
        } catch(IOException ioe) {
          ioe.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream input)
    {
      try{
        if(clientList != null)
        {
          return;
        }
        else
        {
          input.defaultReadObject();
          if(clientList == null)
          {
            clientList = (ClientList) input.readObject();
          }
          else
          {
            input.readObject();
          }
        }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String toString() {
        return clients.toString();
    }
}