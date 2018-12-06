package newGame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;

/**
 * Class provides functionality to log
 * to an external file.
 */
public class Logger {

    // The output stream that writes to the log file.
    private static OutputStream logOut;

    /**
     * Generates a new log file and closes
     * the previous one if the output stream
     * is not null and was open.
     * @param logdir Directory in which the log file will be stored in.
     * @param name Name of the log file before the date specification.
     */
    public static void newLog(String logdir, String name) {
        if(logOut != null) {
            try { logOut.close(); }
            catch(IOException e) { e.printStackTrace(); }
        }
        final File directory = new File(logdir);
        if(!directory.exists() || !directory.isDirectory()) {
            if(!directory.mkdir())
                return;
        }
        final String fileName = name + getDate();
        final File outputFile = new File(directory.toString() + "/" + fileName + "_" + genId(logdir, fileName) + ".log");
        if(!outputFile.exists() || outputFile.isDirectory()) {
            try {
                if (!outputFile.createNewFile())
                    return;
            }
            catch(IOException e) {
                return;
            }
        }
        try {
            logOut = new FileOutputStream(outputFile, true);
        }
        catch(IOException e) {
            // Do nothing.
        }
    }

    /**
     * Prints a message to the log file with an info tag
     * bound to it.
     * @param message Message to print to the log file.
     */
    public static void info(String message) {
        println("[INFO]" + getFormattedTime() + " " + message);
    }

    /**
     * Prints a message to the log file with a warning tag
     * bound to it.
     * @param message Message to print to the log file.
     */
    public static void warning(String message) {
        println("[WARN]" + getFormattedTime() + " " + message);
    }

    /**
     * Prints a message to the log file with an error tag
     * bound to it.
     * @param message Message to print to the log file.
     */
    public static void error(String message) {
        println("[ERR]" + getFormattedTime() + " " + message);
    }

    /**
     * Prints a message to the log file with a fatal tag
     * bound to it.
     * @param message Message to print to the log file.
     */
    public static void fatal(String message) {
        println("[FATAL]" + getFormattedTime() + " " + message);
    }

    /**
     * Creates a unique ID for a specified file within
     * a directory. This may be used to make sure that
     * the files will not be overwritten, instead a new
     * one would be created.
     * @param dir Directory in which the file is located in.
     * @param filename The current file name.
     * @return Id of the file.
     */
    private static int genId(String dir, String filename) {
        if(dir == null || filename == null)
            return 0;
        final File directory = new File(dir);
        if(!directory.exists() || !directory.isDirectory())
            return 0;
        final File[] similar = directory.listFiles(f -> f.getName().startsWith(filename));
        return similar.length;
    }

    /**
     * Prints a line of text to the log file.
     * @param text Text to print to the file.
     */
    private static void println(String text) {
        for(int i = 0; i < text.length(); i++) {
            try { logOut.write((int) text.charAt(i)); }
            catch(IOException e) { e.printStackTrace(); }
        }
        try {
            logOut.write(10);
            logOut.flush();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats the current time into a log-type
     * format (ex. '[4:43:27 PM]'
     * @return Formatted current time.
     */
    private static String getFormattedTime() {
        final Date currentDate = new Date();
        int hours = currentDate.getHours();
        boolean isPm = hours >= 12;
        if(isPm) {
            hours %= 12;
        }
        String hour = padWithZeroes(hours, 2);
        String minute = padWithZeroes(currentDate.getMinutes(), 2);
        String second = padWithZeroes(currentDate.getSeconds(), 2);
        String amPm = isPm ? "PM" : "AM";
        return "[" + hour + ":" + minute + ":" + second + " " + amPm + "]";
    }

    /**
     * Gets today's date in a log-file format
     * (ex. '17-8-19' - 'YY-MM-DD')
     * @return A formatted date as a string.
     */
    private static String getDate() {
        final Date cdate = new Date();
        final DateFormat df = DateFormat.getDateInstance();
        return df.format(cdate);
    }

    /**
     * Pads out the number with the specified
     * amount of zeroes (via the desired length
     * of the number).
     * @param num Number to pad.
     * @param len Length to pad to.
     * @return Padded number with zeroes.
     */
    private static String padWithZeroes(int num, int len) {
        int numLen = (int) Math.floor(Math.log10(num) + 1);
        if(numLen >= len) {
            return Integer.toString(num);
        }
        final StringBuilder padded = new StringBuilder();
        for(int i = 0; i < len - numLen; i++) {
            padded.append('0');
        }
        return padded.toString() + Integer.toString(num);
    }
}
