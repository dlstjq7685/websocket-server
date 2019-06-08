package core.authentication;

import static util.Hash.getSHA256;
import static util.Hash.getSHA512;

public class Password {

    private String password;
    private boolean type;


    public Password(String password) {
        this.password = password;
        type = true;
    }

    public String getPassword() {
        if (type) {
            return getSHA256(this.password);
        } else {
            return getSHA512(this.password);
        }
    }

}
