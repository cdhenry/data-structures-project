package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Uses a scanner to parse a comma separated text file for parking violation data
 *
 * @author Chris Henry + Tim Chung
 *
 */
public class ParkingViolationReaderCSV implements ParkingViolationReader {
    private static final String COMMA = ",";
    protected FileReader file;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param file an existing file opened with FileReader
     */
    public ParkingViolationReaderCSV(FileReader file) {
        this.file = file;
    }

    @Override
    public List<ParkingViolation> getAllParkingViolations() {
        List<ParkingViolation> parkingViolations = new ArrayList<ParkingViolation>();

        try (Scanner in = new Scanner(file)) {

            while (in.hasNextLine()) {
                String parkingViolation = in.nextLine();
                String[] parkingViolationArray = parkingViolation.split(COMMA);

            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return parkingViolations;
    }
}
