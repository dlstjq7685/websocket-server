package core.log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TimeZone;
import java.util.logging.*;

public class base {

    static final Logger logger = Logger.getLogger(base.class.getName());
    static final String filePath = "./log/server.log";

    {
        try {

            FileHandler fileHandler = new FileHandler(filePath, false);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.FINE);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.CONFIG);
            logger.addHandler(consoleHandler);

            logger.setUseParentHandlers(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public base(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Default Fine logger
     * @param message
     */
    public void print(String message){
        logger.fine(message);
    }

    public void wn_print(String message){
        logger.warning(message);
    }

    public void error_print(String message){
        logger.severe(message);
    }

    public void config_print(String message){
        logger.config(message);
    }

    public void info_print(String message){
        logger.info(message);
    }
}
