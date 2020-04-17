package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return parkingViolations;
    }
}
