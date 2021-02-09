import java.io.Serializable;

public class ClientList implements Serializable
{
    private static int IDGenerator = 0;
    private static final long serialVersionUID = 1L;
    private List clients = new LinkedList();
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
        client.add(client);
        return true;
      }

    public Iterator getClients()
    {
        return clients.iterator();
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (clientList != null) {
                return;
            } else {
                input.defaultReadObject();
                if (clientList == null) {
                    clientList = (ClientList) input.readObject();
                } else {
                    input.readObject();
                }
            }
        } catch(IOException ioe) {
            System.out.println("in ClientList readObject \n" + ioe);
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String toString() {
        return clients.toString();
    }
}