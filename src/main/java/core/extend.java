package core;

//gradle fix
import javax.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Server implement
 */
public class extend extends base implements Runnable{

    private ServerSocket server;

    private core.group.base group;

    static final int portnum = 8000;

    /**
     * mode: Dev or Dep
     */
    public extend(){
        super();
        group = new core.group.base("test-group");
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
                protocol_switch(client);
                print("A client]\t"+ client.getRemoteSocketAddress() +"\tconnected.");

                group.add_client(client);
                core.client.base b = new core.client.base(client,this.getLog());
                set_client(b);

                Thread t = new Thread(b);
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void set_client(core.client.base b){
        b.callback_del = group.del_client;
        b.callback_send = group.send_callback;
    }

    private void protocol_switch(Socket client){
        /**
         * Websocket protocol switching
         */
        InputStream in = null;
        OutputStream out = null;

        try {
            in = client.getInputStream();
            out = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try{
            String data = new Scanner(in,"UTF-8").useDelimiter("\\r\\n\\r\\n").next();
            Matcher get = Pattern.compile("^GET").matcher(data);

            if (get.find()) {
                Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
                match.find();

                //if required browser cookie, here cookie set.
                cookie_flow(data);
                String e = get_expiredtime();

                byte[] response = ("HTTP/1.1 101 Switching Protocols\r\n"
                        + "Set-Cookie: test-cookie=test;Expires=" +e+"\r\n"
                        + "Connection: Upgrade\r\n"
                        + "Upgrade: websocket\r\n"
                        + "Sec-WebSocket-Accept: "
                        + DatatypeConverter
                        .printBase64Binary(
                                MessageDigest
                                        .getInstance("SHA-1")
                                        .digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
                                                .getBytes("UTF-8")))
                        + "\r\n\r\n")
                        .getBytes("UTF-8");

                out.write(response, 0, response.length);
                // System.out.println("hand shake run");
            } else {
                System.out.println("handshake error");
                System.exit(0);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void cookie_flow(String data){
        System.out.println(data);

        if(data.contains("Cookie:")){
            System.out.println(data.indexOf("test-cookie="));
            //cookie lang
            System.out.println(data.substring(416,420));
        }
    }

    /*
        cookie expired time set
     */
    private String get_expiredtime(){
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour

        return cal.getTime().toString();
    }
}
