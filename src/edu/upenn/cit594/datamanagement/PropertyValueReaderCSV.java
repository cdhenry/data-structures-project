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
    private static final String COMMA = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
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
            String headers = in.nextLine();
            String[] headerArray = headers.trim().split(COMMA);
            int marketValueIndex = -1, totalLivableAreaIndex = -1, zipCodeIndex = -1;

            for (int i = 0; i < headerArray.length; i++) {
                if (headerArray[i].equals("market_value")) {
                    marketValueIndex = i;
                } else if (headerArray[i].equals("total_livable_area")) {
                    totalLivableAreaIndex = i;
                } else if (headerArray[i].equals("zip_code")) {
                    zipCodeIndex = i;
                }
            }

            while (in.hasNextLine()) {
                String propertyValue = in.nextLine();
                String[] propertyValueArray = propertyValue.trim().split(COMMA);

                String marketValue = propertyValueArray[marketValueIndex];
                String totalLivableArea = propertyValueArray[totalLivableAreaIndex];
                String zipCode = propertyValueArray[zipCodeIndex];

                if(marketValue.length() > 0 && totalLivableArea.length() > 0 && zipCode.length() > 0) {
                    propertyValues.add(new PropertyValue(Double.parseDouble(marketValue),
                            Double.parseDouble(totalLivableArea), zipCode));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return propertyValues;
    }
}
