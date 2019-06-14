package core.log;

import core.key.LogKey;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TimeZone;
import java.util.logging.*;

public class Base {

    public static final Logger logger = Logger.getLogger("WebSocket-Server");;

    public void initalLog() {

        try {

            FileHandler fileHandler = new FileHandler(LogKey.logPath, false);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.FINE);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.CONFIG);
            logger.addHandler(consoleHandler);

            logger.setUseParentHandlers(false);

            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
