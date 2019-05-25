package core.log;

import core.key.LogKey;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TimeZone;
import java.util.logging.*;

public class Base {

    private static final Logger logger = Logger.getLogger(Base.class.getName());

    {
        try {

            FileHandler fileHandler = new FileHandler(LogKey.logPath, false);
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


    public Base(){
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
