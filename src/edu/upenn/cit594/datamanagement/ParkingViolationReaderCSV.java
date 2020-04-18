package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.logging.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Uses a scanner to parse a comma separated text file for parking violation data
 *
 * @author Chris Henry + Tim Chung
 */
public class ParkingViolationReaderCSV implements ParkingViolationReader {
    private static final String FILE_ERR_MSG = "parking violation file must exist and be readable";
    private static final String DATE_PARSE_ERR_MSG = "parking violation date parse error";
    private static final String COMMA = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    protected String filename;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param filename a CSV filename for a parking violation file
     */
    public ParkingViolationReaderCSV(String filename) {
        this.filename = filename;
    }

    @Override
    public List<ParkingViolation> getAllParkingViolations() {
        List<ParkingViolation> parkingViolations = new LinkedList<ParkingViolation>();

        try {
            FileReader file = new FileReader(filename);
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), filename));
            Scanner in = new Scanner(file);

            while (in.hasNextLine()) {
                String parkingViolation = in.nextLine();
                String[] parkingViolationArray = parkingViolation.trim().split(COMMA);

                String timeString = parkingViolationArray[0].trim();
                if (timeString.length() == 0) {
                    continue;
                }

                String fine = parkingViolationArray[1].trim();
                if (fine.length() == 0) {
                    continue;
                }

                String violation = parkingViolationArray[2].trim();
                if (violation.length() == 0) {
                    continue;
                }

                String plateId = parkingViolationArray[3].trim();
                if (plateId.length() == 0) {
                    continue;
                }

                String state = parkingViolationArray[4].trim();
                if (state.length() == 0) {
                    continue;
                }

                String ticketNumber = parkingViolationArray[5].trim();
                if (ticketNumber.length() == 0) {
                    continue;
                }

                String zipCode = parkingViolationArray[6].trim();
                if (zipCode.length() > 4) {
                    zipCode = zipCode.substring(0, 5);
                } else {
                    continue;
                }

                Date timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timeString);

                parkingViolations.add(new ParkingViolation(timeStamp, Double.parseDouble(fine), violation, plateId,
                        state, Integer.parseInt(ticketNumber), Integer.parseInt(zipCode)));
            }
        } catch (FileNotFoundException e) {
            System.out.println(FILE_ERR_MSG);
        } catch (ParseException e) {
            System.out.println(DATE_PARSE_ERR_MSG);
        }

        return parkingViolations;
    }
}
