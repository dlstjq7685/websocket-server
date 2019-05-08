package core.group;

import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * required
 *  group change
 */
public class manager implements controller{

    private TreeMap<String,channel> groups;

    public manager(){
        groups = new TreeMap<>();
        channel wellcome = new channel("lobby");
        wellcome.setName("lobby");
        groups.put("lobby",wellcome);
    }

    private void add_group(String Group_name,Socket client){
       if(!groups.containsKey(Group_name)){
           channel dump = new channel(Group_name);
           dump.add_client(client);
           groups.put(Group_name,dump);
           //ok
       }else{
           //already exist group;
           groups.get(Group_name).add_client(client);
       }
    }

    private void channel_send(String key,byte[] meg){
        if(groups.containsKey(key)){
           groups.get(key).group_send(meg);
        }else{
            //Couldn't send message
        }
    }

    private void channel_exit(String key, Socket client){
        if(groups.containsKey(key)) {
            groups.get(key).del_client(client);
        }
    }

    //almost not use
    private void emergency_send(byte[] meg){
        for(channel e: groups.values()){
            e.group_send(meg);
        }
    }

    @Override
    public void client_wellcome(Socket client) {
        this.add_group("lobby",client);
    }

    @Override
    public void send_meg(String gname, byte[] message) {
        channel_send(gname,message);
    }

    @Override
    public void disconnect_client(ArrayList<String> groups, Socket client) {
        for(String name: groups){
            channel_exit(name,client);
        }
    }

}
