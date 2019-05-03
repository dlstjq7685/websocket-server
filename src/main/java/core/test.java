package core;

public class test {

    /*
        TODO List:
            1 data base connect, query
            2 required database table
                - session-key, expired, ip_address, user_id
                - user-profile
                    -- user_id
                    -- user_password
                    -- user-email


        Complete:
            Convert JDK version 8 -> 11
            Use Semaphore concurrency
            Bug fix: length 126 over cant read = 20190305 DONE, MAX 65500
            Json type support => 20190307 DONE
     */
    public static void main(String... args){

        extend main = new extend();
        Thread t = new Thread(main,"main");
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
