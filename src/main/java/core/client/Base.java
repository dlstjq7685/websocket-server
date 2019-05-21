package core.client;

import core.group.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

import static util.Base.opcode_decoder;
import static util.Base.read_message;

/**
 * Client implement
 * TODO LIST
 *  group manager
 *      group-send
 *      group-exit
 */
public class Base implements Runnable{

    private Socket client;
    private int group_id;
    private boolean run;
    private core.log.base log;

    private InputStream in;

    /*
    public Function callback_send;
    public Function callback_del;
    */

    private ArrayList<String> Group_entry;
    private String current_channel;
    private Controller sysm;
    /**
     * buffer_size: 65535 byte
     */
    static final int buffer_size = 65535;

    {
        client = null;
        in = null;
        run = false;
        Group_entry = new ArrayList<>();
        current_channel = "lobby";
        Group_entry.add(current_channel);
    }

    public Base(Socket c, core.log.base log, Controller m)
    {
        setClient(c);
        this.log = log;
        sysm = m;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;

        try {
            this.in = this.client.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }


    @Override
    public void run() {

        this.run = true;

        while (this.run){

            byte[] set = new byte[buffer_size];
            try {
                in.read(set);
            } catch (IOException e) {
                e.printStackTrace();
            }

            boolean flag = opcode_decoder(set[0]);

            if(flag){
                byte[] message = read_message(set.clone());
                sysm.send_meg(current_channel,message);
                // this.callback_send.apply(message);
            }else{
                try {
                    sysm.disconnect_client(Group_entry,client);
                    // this.callback_del.apply(client);
                    client.close();
                    this.run = false;
                    this.print("client closed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        client = null;

    }

    public void setLog(core.log.base log) {
        this.log = log;
    }

    private void print(String message){
        log.print(client.getRemoteSocketAddress() + "]\t"+ message);
    }
}
