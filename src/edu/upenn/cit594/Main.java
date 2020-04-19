package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.ParkingViolationProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyValueProcessor;
import edu.upenn.cit594.ui.CommandLineUserInterface;

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
    private PopulationReaderSSV popReader;
    private PropertyValueReaderCSV propReader;
    private ParkingViolationReader parkReader;
    private PopulationProcessor popProcessor;
    private PropertyValueProcessor propValProcessor;
    private ParkingViolationProcessor parkProcessor;
    private CommandLineUserInterface UI;

    private void createReaders(String parkFileType, String parkFileName, String propFileName, String popFileName) {
        //Determine parking violation file type
        String parkingViolationsFileFormat = parkFileType.toLowerCase();
        boolean isParkingViolationsJSON = parkingViolationsFileFormat.equals("json");
        boolean isParkingViolationsCSV = parkingViolationsFileFormat.equals("csv");

        if (!(isParkingViolationsJSON || isParkingViolationsCSV)) {
            System.out.println("tweet format must be either 'json' or 'csv'");
            System.exit(0);
        }

        //Create Readers
        try {
            parkReader = isParkingViolationsCSV ?
                    new ParkingViolationReaderCSV(new FileReader(parkFileName)) :
                    new ParkingViolationReaderJSON(new FileReader(parkFileName));
            popReader = new PopulationReaderSSV(new FileReader(popFileName));
            propReader = new PropertyValueReaderCSV(new FileReader(propFileName));

        } catch (FileNotFoundException e) {
            System.out.println("tweet file and state file must exist and must be readable");
            System.exit(0);
        }
    }

    private void createProcessors() {
        parkProcessor = new ParkingViolationProcessor(parkReader);
        popProcessor = new PopulationProcessor(popReader);
        propValProcessor = new PropertyValueProcessor(propReader);
    }

    private void createLogger(String logFileName) {
        File logFile = new File(logFileName);
        if (logFile.exists() && !logFile.canWrite()) {
            System.out.println("log file must be writeable");
            System.exit(0);
        }
        Logger.init(logFile);
    }

    public static void main(String[] args) {
//        if (args.length < 5) {
//            System.out
//                    .println("Usage: java Main [parking violations file format] [parking violations filename] " +
//                            "[property values filename] [population filename] [log filename]");
//            System.exit(0);
//        }

        String arg[] = {"json", "parking.json", "properties.csv", "population.txt", "log.txt"};
        Main main = new Main();
        main.createReaders(arg[0], arg[1], arg[2], arg[3]);
        main.createProcessors();
        main.createLogger(arg[4]);
        main.UI = new CommandLineUserInterface(main.parkProcessor, main.popProcessor, main.propValProcessor);
        main.UI.start();
    }
}
