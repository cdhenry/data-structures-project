package edu.upenn.cit594.logging;

import java.io.File;
import java.io.PrintWriter;

/**
 * Singleton Logger class for logging output to a file
 *
 * @author Chris Henry + Tim Chung
 */
public class Logger {
    private PrintWriter out;
    private static Logger instance;

    /**
     * Creates or uses a file by the specified filename to be used for logging
     *
     * @param file the filename to use for logging
     */
    private Logger(File file) {
        try {
            out = new PrintWriter(file);
        } catch (Exception e) {
        }
    }

    /**
     * Initializes the logger singleton using filename provided
     *
     * @param file the filename to use for logging
     * @throws UnsupportedOperationException
     */
    public static void init(File file) throws UnsupportedOperationException {
        if (instance != null) {
            throw new UnsupportedOperationException(
                    "Once Logger Singleton has been instantiated, cannot re-instantiate");
        }
        instance = new Logger(file);
    }

    /**
     * Gets an instance of the Logger class
     *
     * @return a singleton instance of the logger class
     */
    public static Logger getInstance() {
        return instance;
    }

    /**
     * Prints and flushes messages to the initialized log file
     *
     * @param msg the message to be printed to the log file
     */
    public void log(String msg) {
        out.println(msg);
        out.flush();
    }
}

