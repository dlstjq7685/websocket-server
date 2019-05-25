package util;


//gradle fix
import core.key.CookieKey;

import javax.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.Cookie.cookieFlow;
import static util.Cookie.getExpiredtime;

public class ProtocolSwitch {

    public static void protocolSwitch(Socket client){

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
                cookieFlow(data, CookieKey.TESTCOOKIE);
                String e = getExpiredtime();

                /*
                    Set-Cookie: <cookieName>=<Value>;Expires=<Date>\r\m
                * */
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
}
