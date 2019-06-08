package core.group;

import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * required
 *  group change
 */
public class Manager implements Controller {

    private TreeMap<String, Channel> groups;

    public Manager(){
        groups = new TreeMap<>();
        Channel wellcome = new Channel("lobby");
        groups.put("lobby",wellcome);
    }

    private void addGroup(String Group_name,Socket client){
       if(!groups.containsKey(Group_name)){
           Channel dump = new Channel(Group_name);
           dump.add_client(client);
           groups.put(Group_name,dump);
           //ok
       }else{
           //already exist group;
           groups.get(Group_name).add_client(client);
       }
    }

    private void channelSend(String key,byte[] meg){
        if(groups.containsKey(key)){
           groups.get(key).group_send(meg);
        }else{
            //Couldn't send message
        }
    }

    private void channelExit(String key, Socket client){
        if(groups.containsKey(key)) {
            groups.get(key).del_client(client);
        }
    }

    //almost not use
    private void emergency_send(byte[] meg){
        for(Channel e: groups.values()){
            e.group_send(meg);
        }
    }

    @Override
    public void clientWellcome(Socket client) {
        this.addGroup("lobby",client);
    }

    @Override
    public void sendMeg(String gname, byte[] message) {
        channelSend(gname,message);
    }

    @Override
    public void disconnectClient(ArrayList<String> groups, Socket client) {
        for(String name: groups){
            channelExit(name,client);
        }
    }
}
