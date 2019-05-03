package core;

import java.util.ArrayList;

abstract public class base {

    private ArrayList<Integer> group_id;
    private core.log.base log;

    public base(){
        log = new core.log.base();

        group_id = new ArrayList<>();
    }

    public void auto_gc(){

        try{
            group_id.trimToSize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.gc();
        log.print("Excuting GC");
    }

    public void reset(){
        group_id = null;
        System.gc();

        group_id = new ArrayList<>();
        log.print("Reset all object");
    }

    public void print(String meg){
        log.print(meg);
    }

    public void config_print(String meg){
        log.config_print(meg);
    }

    public core.log.base getLog(){
        return this.log;
    }

}
