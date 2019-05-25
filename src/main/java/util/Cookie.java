package util;

import core.key.CookieKey;

import java.util.Calendar;
import java.util.Date;

class Cookie {

    private final static int cookieLen = 4;

    static String cookieFlow(String data, String key){

        String contents = null;
        //System.out.println(data);

        if(data.contains("Cookie:")){

            int start = data.indexOf(key + "=") + key.length() + 1;

            //get client cookie
            contents = data.substring(start,start + CookieKey.COOKIELENGTH);

            //System.out.println(contents);

        }

        return contents;
    }

    /*
        cookie expired time set
     */
    static String getExpiredtime(){
        Calendar cal = Calendar.getInstance();      // creates calendar
        cal.setTime(new Date());                    // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 1);  // adds one hour

        return cal.getTime().toString();
    }

    static String getCookieContent(String key,boolean sha512){

        if(sha512){
            return Hash.getSHA512(key);
        }
        else{
            return Hash.getSHA256(key);
        }
    }
}
