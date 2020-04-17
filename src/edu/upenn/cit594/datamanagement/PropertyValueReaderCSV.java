package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.PropertyValue;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Uses a scanner to parse a comma separated text file for property value data
 *
 * @author Chris Henry + Tim Chung
 *
 */
public class PropertyValueReaderCSV {
    private static final String COMMA = ",";
    protected FileReader file;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param file an existing file opened with FileReader
     */
    public PropertyValueReaderCSV(FileReader file) {
        this.file = file;
    }

    /**
     * Gets a list of property values from a file
     *
     * @return a list of property value objects
     */
    public List<PropertyValue> getAllPropertyValues() {
        List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

        try (Scanner in = new Scanner(file)) {

            while (in.hasNextLine()) {
                String propertyValue = in.nextLine();
                String[] propertyValueArray = propertyValue.split(COMMA);

            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return propertyValues;
    }
}
