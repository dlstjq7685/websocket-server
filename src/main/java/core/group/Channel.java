package core.group;

import java.util.ArrayList;

import static core.log.Base.logger;

/**
 * 1:n chat
 */
public class Channel extends Base {

    private String root;
    private ArrayList<String> child;
    private final core.db.Base db;

    public Channel(String gid) {
        super(gid);

        db = new core.db.Base();
        logger.config(this.getGID() + "DB is configure success");
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

    public void close() {
        db.closeDB();
    }
}
