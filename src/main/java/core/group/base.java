package core.group;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class base {

    private ArrayList<Socket> clients;
    private static final int MAX_AVAILABLE = 1;
    private Semaphore available;

    /*
        Group id
     */
    private String GID;
    private String name;

    {
        clients = new ArrayList<>();
        available = new Semaphore(MAX_AVAILABLE);
    }
    public base(String gid){
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

    /*
    public Function<byte[], Boolean> send_callback = (byte[] message)-> {
        group_send(message);
        return true;
    };

    public Function<Socket, Boolean> del_client = (Socket client)-> {
        del_client(client);
        return true;
    };
    */

    public void add_client(Socket c){

        while (!available.tryAcquire());
        clients.add(c);
        available.release();
    }

    public void del_client(Socket c){
        try {
            available.acquire();
            clients.remove(c);
            available.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public String getGID() {
        return GID;
    }

    public void setGID(String GID) {
        this.GID = GID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
