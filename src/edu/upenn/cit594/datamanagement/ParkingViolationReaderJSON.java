package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.CommonConstant;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;

/**
 * Uses the json.simple library to parse a JSON file into a set of ParkingViolation objects
 *
 * @author Chris Henry + Tim Chung
 */
public class ParkingViolationReaderJSON implements ParkingViolationReader {
    private static final String FILE_ERR_MSG = "parking violation file must exist and be readable";
    private static final String DATE_PARSE_ERR_MSG = "parking violation date parse error";
    private static final String JSON_PARSE_ERR_MSG = "parking violation JSON parse error";
    protected JSONArray parkingViolationsJSON;

    /**
     * Takes in a filename and stores it for use on the json parser
     *
     * @param filename a JSON filename for a parking violation file
     */
    public ParkingViolationReaderJSON(String filename) {
        try {
            FileReader file = new FileReader(filename);
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), filename));
            JSONParser parser = new JSONParser();
            parkingViolationsJSON = (JSONArray) parser.parse(file);

        } catch (FileNotFoundException e) {
            System.out.println(FILE_ERR_MSG);
            System.exit(4);

        } catch (IOException | ParseException e) {
            System.out.println(JSON_PARSE_ERR_MSG);
            System.exit(4);
        }
    }

    @Override
    public Map<Integer, List<ParkingViolation>> getAllParkingViolations() {
        Map<Integer, List<ParkingViolation>>  parkingViolationsMap = new HashMap<>();

        for (Object o : parkingViolationsJSON) {
            JSONObject parkingViolation = (JSONObject) o;

            try {
                long ticketNumber = (Long) parkingViolation.get("ticket_number");
                long fine = (Long) parkingViolation.get("fine");

                String plateId = (String) parkingViolation.get("plate_id");
                if (plateId.length() == 0) {
                    continue;
                }

                String timeString = (String) parkingViolation.get("date");
                if (timeString.length() == 0) {
                    continue;
                }

                String zipCode = (String) parkingViolation.get("zip_code");
                Matcher m = CommonConstant.ZIP_CODE_PATTERN.matcher(zipCode);
                if (m.find()) {
                    zipCode = m.group();
                } else {
                    continue;
                }

                String violation = (String) parkingViolation.get("violation");
                if (violation.length() == 0) {
                    continue;
                }

                String state = (String) parkingViolation.get("state");
                if (state.length() == 0) {
                    continue;
                }

                Date timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timeString);

                ParkingViolation newParkingViolation = new ParkingViolation(timeStamp, new Long(fine).doubleValue(), violation,
                        plateId, state, new Long(ticketNumber).intValue(), Integer.parseInt(zipCode));

                updateMap(parkingViolationsMap, newParkingViolation, newParkingViolation.getZipCode());

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

            } catch (java.text.ParseException e) {
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
