package core.group;

import java.net.Socket;
import java.util.ArrayList;

public interface Controller {
    public void client_wellcome(Socket client);
    public void send_meg(String gname, byte[] message);
    void disconnect_client(ArrayList<String> groups, Socket client);
}
