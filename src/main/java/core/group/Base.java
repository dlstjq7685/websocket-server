package core.group;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Base {

    private ArrayList<Socket> clients;
    private static final int MAX_AVAILABLE = 1;
    private Semaphore available;

    private String GID;

    {
        clients = new ArrayList<>();
        available = new Semaphore(MAX_AVAILABLE);
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
}