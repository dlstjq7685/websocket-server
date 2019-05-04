package core.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.function.Function;

import static util.base.opcode_decoder;
import static util.base.read_message;

/**
 * Client implement
 * TODO LIST
 *  group exchange func
 */
public class base implements Runnable{

    private Socket client;
    private int group_id;
    private boolean run;
    private core.log.base log;

    private InputStream in;

    public Function callback_send;
    public Function callback_del;

    /**
     * buffer_size: 65535 byte
     */
    static final int buffer_size = 65535;

    {
        client = null;
        in = null;
        run = false;
    }
    public base(Socket c,core.log.base log)
    {
        setClient(c);
        this.log = log;
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
                this.callback_send.apply(message);
            }else{
                try {
                    this.callback_del.apply(client);
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
