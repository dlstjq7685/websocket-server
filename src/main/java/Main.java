import core.Extend;

public class Main {

    /*
        TODO List:
            1 data base connect, query
            2 required database table
                - session-key, expired, ip_address, user_id
                - user-profile
                    -- user_id
                    -- user_password
                    -- user-email
            3 model, command customize support
            4 custom property config
                database config, port config, etc...
     */
    public static void main(String... args){

        Extend main = new Extend();
        Thread t = new Thread(main,"main");
        t.start();

        try {
            t.join();
            System.out.println("Close Server");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
