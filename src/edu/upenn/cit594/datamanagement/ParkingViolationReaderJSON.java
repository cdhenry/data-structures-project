package edu.upenn.cit594.datamanagement;

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

/**
 * Uses the json.simple library to parse a JSON file into a set of ParkingViolation objects
 *
 * @author Chris Henry + Tim Chung
 */
public class ParkingViolationReaderJSON implements ParkingViolationReader {
    private static final String FILE_ERR_MSG = "parking violation file must exist and be readable";
    private static final String DATE_PARSE_ERR_MSG = "parking violation date parse error";
    private static final String JSON_PARSE_ERR_MSG = "parking violation JSON parse error";
    protected String filename;
    protected JSONParser parser;

    /**
     * Takes in a filename and stores it for use on the json parser
     *
     * @param filename a JSON filename for a parking violation file
     */
    public ParkingViolationReaderJSON(String filename) {
        this.filename = filename;
        this.parser = new JSONParser();
    }

    @Override
    public List<ParkingViolation> getAllParkingViolations() {
        List<ParkingViolation> parkingViolations = new LinkedList<ParkingViolation>();

        try {
            FileReader file = new FileReader(filename);
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), filename));

            JSONArray parkingViolationsJSON = (JSONArray) parser.parse(file);

            for (Object o : parkingViolationsJSON) {
                JSONObject parkingViolation = (JSONObject) o;

                String ticketNumber = (String) parkingViolation.get("ticket_number");
                if (ticketNumber.length() == 0) {
                    continue;
                }
                String plateId = (String) parkingViolation.get("plate_id");
                if (plateId.length() == 0) {
                    continue;
                }
                String timeString = (String) parkingViolation.get("date");
                if (timeString.length() == 0) {
                    continue;
                }
                String zipCode = (String) parkingViolation.get("zip_code");
                if (zipCode.length() == 0) {
                    continue;
                }
                String violation = (String) parkingViolation.get("violation");
                if (violation.length() == 0) {
                    continue;
                }
                String fine = (String) parkingViolation.get("fine");
                if (fine.length() == 0) {
                    continue;
                }
                String state = (String) parkingViolation.get("state");
                if (state.length() == 0) {
                    continue;
                }

                Date timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timeString);

                parkingViolations.add(new ParkingViolation(timeStamp, Double.parseDouble(fine), violation,
                        plateId, state, Integer.parseInt(ticketNumber), Integer.parseInt(zipCode)));
            }
        } catch (ParseException e) {
            System.out.println(DATE_PARSE_ERR_MSG);
        } catch (FileNotFoundException e) {
            System.out.println(FILE_ERR_MSG);
        } catch (IOException | java.text.ParseException e) {
            System.out.println(JSON_PARSE_ERR_MSG);
        }

        return parkingViolations;
    }
}
