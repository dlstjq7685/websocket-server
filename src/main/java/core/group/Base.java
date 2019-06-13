package core.group;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Base {

    private final ArrayList<Socket> clients;

    private String GID;

    {
        clients = new ArrayList<>();
    }
    public Base(String gid){
        this.GID = gid;
    }

    public void group_send(byte[] message){

        clients.stream()
                .forEach(c ->{
                    try {
                        OutputStream o = c.getOutputStream();
                        o.write(message,0,message.length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    public synchronized void add_client(Socket c){
        clients.add(c);
    }

    public synchronized void del_client(Socket c){
        clients.remove(c);
    }

    public String getGID() {
        return GID;
    }
}
