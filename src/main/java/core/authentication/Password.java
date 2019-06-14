package core.authentication;


import serverException.socketError;
import util.Hash;

import java.io.UnsupportedEncodingException;

public class Password {

    private String password;
    private byte[] salt;

    public Password(String password) {
        this.password = password;
        salt = null;
    }

    public String getPassword() throws socketError, UnsupportedEncodingException {
        return new String(Hash.generateHash(password),"UTF-8");
    }

}
