package core.group;

import java.net.Socket;
import java.util.TreeMap;

/**
 * required
 *  group change
 */
public class manager{

    private TreeMap<String,channel> groups;

    public manager(){
        groups = new TreeMap<>();
    }

    private void add_group(String Group_name){
       if(!groups.containsKey(Group_name)){
           channel dump = new channel(Group_name);
           groups.put(Group_name,dump);
           //ok
       }else{
        //Couldn't create channel or group
       }
    }

    private void channel_send(String key,byte[] meg){
        if(groups.containsKey(key)){
           groups.get(key).send_callback.apply(meg);
        }else{
            //Couldn't send message
        }
    }

    private void channel_exit(String key, Socket client){
        if(groups.containsKey(key)) {
            groups.get(key).del_client.apply(client);
        }
    }

    //almost not use
    private void emergency_send(byte[] meg){
        for(channel e: groups.values()){
            e.send_callback.apply(meg);
        }
    }
}
