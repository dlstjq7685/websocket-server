package core.authentication;

/**
 *  Todo List
 *      - user id check
 *      - passwd check
 *      - id, passwd authentic
 *      - cookie check
 *      - cookie authentic
 */
public class Base {

    public static boolean checkValid(String id) {
        return true;
    }

    public static boolean checkPassword(String id,Password password) {

        if(checkValid(id)) {

        }
        return true;
    }
}
