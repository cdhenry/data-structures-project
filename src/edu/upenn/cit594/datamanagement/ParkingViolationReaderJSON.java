package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Uses the json.simple library to parse a JSON file into a set of ParkingViolation objects
 *
 * @author Chris Henry + Tim Chung
 *
 */
public class ParkingViolationReaderJSON implements ParkingViolationReader {
    protected FileReader file;
    protected JSONParser parser;

    /**
     * Takes in a filename and stores it for use on the json parser
     *
     * @param file the JSON file to be used for parsing data
     */
    public ParkingViolationReaderJSON(FileReader file) {
        this.file = file;
    }

    @Override
    public List<ParkingViolation> getAllParkingViolations() {
        List<ParkingViolation> parkingViolations = new ArrayList<ParkingViolation>();

        // create a parser JSONParser
        parser = new JSONParser();

        try {
            // get the array of JSON objects
            JSONArray parkingViolationsJSON = (JSONArray) parser.parse(file);

            // iterate while there are more objects in array
            for (Object o : parkingViolationsJSON) {
                // get the next JSON object
                JSONObject parkingViolation = (JSONObject) o;

                int ticketNumber = (int) parkingViolation.get("ticket_number");
                String plateId = (String) parkingViolation.get("plate_id");
                String timeString = (String) parkingViolation.get("date");
                Date timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timeString);
                String zipCode = (String) parkingViolation.get("zip_code");
                String violation = (String) parkingViolation.get("violation");
                double fine = (Double) parkingViolation.get("fine");
                String state = (String) parkingViolation.get("state");

                parkingViolations.add(new ParkingViolation(timeStamp, fine, violation, plateId, state,
                        Integer.toString(ticketNumber), zipCode));
            }
        } catch (IOException | ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        return parkingViolations;
    }
}
