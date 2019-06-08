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


    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public ArrayList<String> getChild() {
        return child;
    }

    public void setChild(ArrayList<String> child) {
        this.child = child;
    }
}
