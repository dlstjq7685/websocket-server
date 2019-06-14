package core;

import core.group.Controller;
import core.group.Manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.ProtocolSwitch.protocolSwitch;

/**
 * Server implement
 */
public class Extend extends Base {

    private ServerSocket server;
    private Controller m;
    private static final int portnum = 8000;

    /**
     * mode: Dev or Dep
     */
    public Extend(){
        super();
        m = new Manager();
    }

    public void print(String msg){
        super.print(msg);
    }

    /**
     * Server main accept component
     */
    @Override
    public void run() {

        try {
            server = new ServerSocket(portnum);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        super.config_print("Server has started on 127.0.0.1:" + portnum);
        print("Waiting for a connection...");

        while (!Thread.interrupted()){
            try {
                Socket client = server.accept();
                protocolSwitch(client);
                print("A client]\t"+ client.getRemoteSocketAddress() +"\tconnected.");
                m.clientWellcome(client);

                core.client.Base b = new core.client.Base(client,this.getLog(),m);

                Thread t = new Thread(b);
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (serverException.socketError socketError) {
                socketError.printStackTrace();
            }
        }
    }

}
