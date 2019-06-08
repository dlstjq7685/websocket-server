package util;

import org.json.JSONObject;
import serverException.socketError;

public class MessageFactory {

    public static byte[] readMessage(byte[] buffer) throws socketError {

        if (opcodeDecoder(buffer[0])) {
           throw new socketError("E3001");
        }

        long payloadlen = getLength(buffer);
        int payload_byte = getPayloadByte(payloadlen);
        int[] temp = makeUnsigned(buffer,6 + payloadlen + payload_byte);
        int[] key = makeKey(temp,payload_byte);
        int[] encoded = makeencoded(temp,payloadlen,payload_byte);

        byte[] send = decode(key,encoded,payloadlen);

        // StringtoJson(new String(send));
        // System.out.println(t.get("data"));

        byte[] message = makemessage(send,payloadlen);

        return message;
    }

    private static JSONObject StringtoJson(String str){
        return new JSONObject(str);
    }

    private static String JsontoString(JSONObject json){
        return json.toString();
    }

    /**
     * get packet payload_byte length
     * @param len: payload
     * @return
     */
    private static int getPayloadByte(long len){

        int byte_len = 0;

        if(len > 65535){ byte_len = 8; }
        else if(len > 125){ byte_len = 2; }

        return byte_len;
    }

    /**
     * get packet message length
     * @param len: packet
     * @return: length
     */
    private static long getLength(byte...len){

        long leng = len[1] & 0x7F;
        // System.out.println(leng);
        if(leng == 126){ leng = (Byte.toUnsignedInt(len[2]) << 8) + Byte.toUnsignedInt(len[3]); }
        else if(leng == 127){
            leng = 0;
            for(int idx = 0; idx < 8;idx++){
                leng += (Byte.toUnsignedLong(len[2 + idx]) << (56 - 8*idx));
            }
        }
        return leng;
    }

    /**
     * @param opcode: websocket opcode byte= buffer[0]
     *
     */
    public static boolean opcodeDecoder(byte opcode){
        byte decoded = (byte) (opcode &  0x0F);
        // System.out.println("OPCODE: "+Byte.toUnsignedInt(opcode));

        switch (decoded){
            case 0x1:
                break;
            case 0x2:
                break;
            case 0x8:
                return false;
            case 0x9:
                break;
            case 0xA:
                break;
            default:
                break;
        }

        return true;
    }

    /**
     *  Decode client message
     *
     * @param key
     * @param encoded
     * @param len
     * @return
     */
    private static byte[] decode(int[] key,int[] encoded, long len){

        int[] decoded = new int[Math.toIntExact(len)];
        byte[] dump = new byte[Math.toIntExact(len)];

        try{
            for (int i = 0; i < len; i++) {
                decoded[i] = (encoded[i] ^ key[i & 0x3]);
                dump[i] = (byte) decoded[i];
            }
        } catch (Exception e){
            System.out.println("Message decode error");
        }

        return dump;
    }

    /**
     * convert signed byte to unsigned int
     *
     * @param data came from client byte data
     * @param len payload data length in byte data
     * @return
     */
    public static int[] makeUnsigned(byte[] data, long len){
        int[] dump = new int[Math.toIntExact(len)];

        int mask = 0xFF;

        for(int i = 0; i < len;i++){
            int temp = data[i] & mask;
            dump[i] = temp;
        }

        return dump;
    }

    /**
     *  find mask key 4byte
     *
     * @param data converted client data
     * @return
     */
    public static int[] makeKey(int[] data,int payload_byte_len){

        switch (payload_byte_len){
            case 0:
                return new int[]{data[2], data[3], data[4], data[5]};
            case 2:
                return new int[]{data[4], data[5], data[6], data[7]};
            case 8:
                return new int[]{data[10], data[11], data[12], data[13]};
            default:
                return new int[]{data[2], data[3], data[4], data[5]};
        }
    }

    /**
     * find payload data
     *
     * @param data converted client data
     * @param len payload data length in client data[1]
     * @return
     */
    public static int[] makeencoded(int[] data, long len,int payload_byte_len){

        int[] dump = new int[Math.toIntExact(len)];
        int mesg_len = 6 + payload_byte_len;

        for(int i = 0; i < len; i++)
            dump[i] = data[mesg_len + i];

        return dump;
    }

    /**
     * prepare send message from decode message
     *
     * @param message decoded message
     * @param len decoded message length
     * @return
     */
    public static byte[] makemessage(byte[] message,long len){

        byte[] meg = new byte[Math.toIntExact(len + 2)];
        int len_frame = 2;

        if(len > 65535){
            meg = new byte[Math.toIntExact(len + 10)];
            meg[1] = (byte) 127;
            for(int idx = 0; idx < 8;idx++){
                meg[2+idx] = (byte)((len >> (56 - 8*idx)) & 0xFF);
            }
            len_frame = 10;
        }
        else if(len > 126){
            meg = new byte[Math.toIntExact(len + 4)];
            meg[1] = (byte) 126;
            meg[3] = (byte)(len & 0xFF) ;
            meg[2] = (byte)((len >> 8) & 0xFF);
            len_frame = 4;
        }
        else{
            meg[1] = (byte) len;
        }

        meg[0] = (byte) 129;

        for(int i = 0; i < len; i++)
            meg[len_frame + i] = message[i];

        return meg;
    }

}


