package core.group;

import java.net.Socket;
import java.util.ArrayList;

public interface Controller {
    public void clientWellcome(Socket client);
    public void sendMeg(String gname, byte[] message);
    void disconnectClient(ArrayList<String> groups, Socket client);
}
