package core.client;

import core.group.Controller;
import core.key.ClientKey;
import serverException.socketError;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

import static util.MessageFactory.*;

/**
 * Client implement
 * TODO LIST
 */
public class Base extends Thread{

    private Socket client;
    private boolean run;
    private core.log.Base log;

    private InputStream in;

    private ArrayList<String> groupEntry;
    private String currentChannel;
    private Controller sendManager;

    {
        client = null;
        in = null;
        run = false;
        groupEntry = new ArrayList<>();
        currentChannel = ClientKey.TESTCHANNEL;
        groupEntry.add(currentChannel);
    }

    public Base(Socket c, core.log.Base log, Controller m)
    {
        setClient(c);
        this.log = log;
        this.sendManager = m;
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

    @Override
    public void run() {

        this.run = true;

        try {
            while (this.run){

                byte[] set = new byte[ClientKey.BUFFERSIZE];
                in.read(set);

                boolean flag = opcodeDecoder(set[0]);

                if(flag){
                    byte[] message = readMessage(set.clone());
                    sendManager.sendMeg(currentChannel,message);

                }else{
                    sendManager.disconnectClient(groupEntry,client);

                    client.close();
                    this.run = false;
                    this.print("client closed");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (socketError e1) {
            e1.printStackTrace();
        }

        client = null;

    }

    private void print(String message){
        log.info_print(client.getRemoteSocketAddress() + "]\t"+ message);
    }
}
