package edu.upenn.cit594;

import edu.upenn.cit594.logging.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Main class takes in arguments from the caller, creates files, initializes logger and activates the CLI
 *
 * Usage: java Main [parking violations file format] [parking violations filename] [property values filename]
 * [population filename] [log filename]
 *
 * @author Chris Henry + Tim Chung
 *
 */
public class Main {

    private static final String JSON = "json";
    private static final String CSV = "csv";

    public static void main(String[] args) {
        if (args.length < 5) {
            System.out
                    .println("Usage: java Main [parking violations file format] [parking violations filename] " +
                            "[property values filename] [population filename] [log filename]");
            System.exit(0);
        }

        String parkingViolationsFileFormat = args[0].toLowerCase();
        boolean isParkingViolationsJSON = parkingViolationsFileFormat.equals(JSON);
        boolean isParkingViolationsCSV = parkingViolationsFileFormat.equals(CSV);

        if (!(isParkingViolationsJSON || isParkingViolationsCSV)) {
            System.out.println("tweet format must be either 'json' or 'csv'");
            System.exit(0);
        }

        try {
            FileReader parkingViolationsFile = new FileReader(args[1]);
            FileReader propertyValuesFile = new FileReader(args[2]);
            FileReader populationFile = new FileReader(args[3]);
            File logFile = new File(args[4]);

            if (logFile.exists() && !logFile.canWrite()) {
                System.out.println("log file must be writeable");
                System.exit(0);
            }

            // create the objects and their relationships
            Logger.init(logFile);
//            Reader reader = new CSVFileReader(filename);
//            Processor processor = new Processor(reader);
//            CommandLineUserInterface ui = new CommandLineUserInterface(processor);
//
//            ui.start();
        } catch (FileNotFoundException e) {
            System.out.println("tweet file and state file must exist and must be readable");
            System.exit(0);
        }
    }
}
