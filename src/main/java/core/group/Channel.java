package core.group;

import java.util.ArrayList;

/**
 * 1:n chat
 */
public class Channel extends Base {

    private String root;
    private ArrayList<String> child;

    public Channel(String gid) {
        super(gid);
    }



}
