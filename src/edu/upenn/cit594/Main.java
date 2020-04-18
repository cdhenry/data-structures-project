package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.ParkingViolationProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyValueProcessor;
import edu.upenn.cit594.ui.CommandLineUserInterface;

import java.io.File;

/**
 * Main class takes in arguments from the caller, creates files, initializes logger and activates the CLI
 * <p>
 * Usage: java Main [parking violations file format] [parking violations filename] [property values filename]
 * [population filename] [log filename]
 *
 * @author Chris Henry + Tim Chung
 */
public class Main {

    private static final String JSON = "json";
    private static final String CSV = "csv";

    private static final String FILE_FORMAT_ERR_MSG = "parking violations file format must be either 'json' or 'csv'";
    private static final String LOG_FILE_ERR_MSG = "log file must be writeable";
    private static final String USAGE_ERR_MSG = "Usage: java Main [parking violations file format] " +
            "[parking violations filename] [property values filename] [population filename] [log filename]";

    private static ParkingViolationReader parkingViolationReader;
    private static PropertyValueReaderCSV propertyValueReader;
    private static PopulationReaderSSV populationReader;
    private static ParkingViolationProcessor parkingViolationProcessor;
    private static PropertyValueProcessor propertyValueProcessor;
    private static PopulationProcessor populationProcessor;
    private static CommandLineUserInterface ui;

    public static void createReaders(boolean isParkingViolationsJSON, String parkingViolationsFilename,
                                     String propertyValuesFilename, String populationFilename) {
        parkingViolationReader = isParkingViolationsJSON ? new ParkingViolationReaderJSON(parkingViolationsFilename) :
                new ParkingViolationReaderCSV(parkingViolationsFilename);
        propertyValueReader = new PropertyValueReaderCSV(propertyValuesFilename);
        populationReader = new PopulationReaderSSV(populationFilename);
    }

    public static void createProcessors() {
        parkingViolationProcessor = new ParkingViolationProcessor(parkingViolationReader);
        propertyValueProcessor = new PropertyValueProcessor(propertyValueReader);
        populationProcessor = new PopulationProcessor(populationReader);
    }

    public static void createCLI() {
        ui = new CommandLineUserInterface(parkingViolationProcessor, populationProcessor, propertyValueProcessor);
    }

    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println(USAGE_ERR_MSG);
            System.exit(1);
        }

        String parkingViolationsFileFormat = args[0].toLowerCase();
        boolean isParkingViolationsJSON = parkingViolationsFileFormat.equals(JSON);
        boolean isParkingViolationsCSV = parkingViolationsFileFormat.equals(CSV);

        if (!(isParkingViolationsJSON || isParkingViolationsCSV)) {
            System.out.println(FILE_FORMAT_ERR_MSG);
            System.exit(2);
        }

        File logFile = new File(args[4]);
        if (logFile.exists() && !logFile.canWrite()) {
            System.out.println(LOG_FILE_ERR_MSG);
            System.exit(3);
        }

        Logger.init(logFile);
        Logger.getInstance().log(String.format("%d %s %s %s %s %s\n", System.currentTimeMillis(), args[0], args[1],
                args[2], args[3], args[4]));

        createReaders(isParkingViolationsJSON, args[1], args[2], args[3]);
        createProcessors();
        createCLI();

        ui.start();
    }
}
