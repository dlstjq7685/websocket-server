package core;

import java.util.ArrayList;

/**
    Server base
 */
abstract public class Base extends Thread {

    private ArrayList<Integer> group_id;

    public Base(){

        group_id = new ArrayList<>();
    }

    public void auto_gc(){

        try{
            group_id.trimToSize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.gc();
    }

    public void reset(){
        group_id = null;
        System.gc();

        group_id = new ArrayList<>();
    }

}
