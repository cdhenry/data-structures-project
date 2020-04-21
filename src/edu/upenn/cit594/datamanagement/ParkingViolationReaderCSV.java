package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.CommonConstant;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.logging.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;

/**
 * Uses a scanner to parse a comma separated text file for parking violation data
 *
 * @author Chris Henry + Tim Chung
 */
public class ParkingViolationReaderCSV implements ParkingViolationReader {
    private static final String FILE_ERR_MSG = "parking violation file must exist and be readable";
    private static final String DATE_PARSE_ERR_MSG = "parking violation date parse error";
    protected Scanner readIn;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param filename a CSV filename for a parking violation file
     */
    public ParkingViolationReaderCSV(String filename) {
        try {
            FileReader file = new FileReader(filename);
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), filename));
            readIn = new Scanner(file);

        } catch (IOException e) {
            System.out.println(FILE_ERR_MSG);
            System.exit(4);
        }
    }

    @Override
    public Map<Integer, List<ParkingViolation>> getAllParkingViolations() {
        Map<Integer, List<ParkingViolation>> parkingViolationsMap = new HashMap<>();

        while (readIn.hasNextLine()) {
            String parkingViolation = readIn.nextLine();
            String[] parkingViolationArray = parkingViolation.trim().split(CommonConstant.COMMA_REGEX);

            try {
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
                Matcher m = CommonConstant.ZIP_CODE_PATTERN.matcher(zipCode);
                if (m.find()) {
                    zipCode = m.group();
                } else {
                    continue;
                }

                Date timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timeString);

                ParkingViolation newParkingViolation = new ParkingViolation(timeStamp, Double.parseDouble(fine) , violation,
                        plateId, state, Integer.parseInt(ticketNumber), Integer.parseInt(zipCode));

                updateMap(parkingViolationsMap, newParkingViolation, newParkingViolation.getZipCode());

            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {

            } catch (ParseException e) {
                System.out.println(DATE_PARSE_ERR_MSG);
            }
        }
        return parkingViolationsMap;
    }

    private void updateMap(Map<Integer, List<ParkingViolation>> map, ParkingViolation pv, int zip) {
        if (map.containsKey(zip)) {
            map.get(zip).add(pv);
        } else {
            List<ParkingViolation> parkingViolations = new LinkedList<>();
            parkingViolations.add(pv);
            map.put(zip, parkingViolations);
        }
    }

}
