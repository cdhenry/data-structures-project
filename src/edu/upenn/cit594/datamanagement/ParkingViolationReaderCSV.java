package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

                String timeString = (String) parkingViolationArray[0];
                Date timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timeString);
                double fine = Double.parseDouble(parkingViolationArray[1]);
                String violation = parkingViolationArray[2];
                String plateId = parkingViolationArray[3];
                String state = parkingViolationArray[4];
                String ticketNumber = parkingViolationArray[5];
                String zipCode = parkingViolationArray[6];

                parkingViolations.add(new ParkingViolation(timeStamp, fine, violation, plateId, state, ticketNumber,
                        zipCode));
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return parkingViolations;
    }
}
