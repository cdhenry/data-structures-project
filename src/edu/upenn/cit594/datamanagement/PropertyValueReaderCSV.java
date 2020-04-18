package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.PropertyValue;
import edu.upenn.cit594.logging.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Uses a scanner to parse a comma separated text file for property value data
 *
 * @author Chris Henry + Tim Chung
 */
public class PropertyValueReaderCSV {
    private static final String FILE_ERR_MSG = "property value file must exist and be readable";
    private static final String COMMA = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    protected String filename;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param filename a CSV filename for a property value file
     */
    public PropertyValueReaderCSV(String filename) {
        this.filename = filename;
    }

    /**
     * Gets a list of property values from a file
     *
     * @return a list of property value objects
     */
    public List<PropertyValue> getAllPropertyValues() {
        List<PropertyValue> propertyValues = new LinkedList<>();

        try {
            FileReader file = new FileReader(filename);
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), filename));
            Scanner in = new Scanner(file);

            String headers = in.nextLine();
            String[] headerArray = headers.trim().split(COMMA);
            int marketValueIndex = -1, totalLivableAreaIndex = -1, zipCodeIndex = -1, id = -1;

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

                String marketValue = propertyValueArray[marketValueIndex].trim();
                if (marketValue.length() == 0) {
                    continue;
                }
                String totalLivableArea = propertyValueArray[totalLivableAreaIndex].trim();
                if (totalLivableArea.length() == 0) {
                    continue;
                }
                String zipCode = propertyValueArray[zipCodeIndex].trim();
                if (zipCode.length() > 4) {
                    zipCode = zipCode.substring(0, 5);
                } else {
                    continue;
                }

                propertyValues.add(new PropertyValue(++id, Double.parseDouble(marketValue),
                        Double.parseDouble(totalLivableArea), Integer.parseInt(zipCode)));
            }
        } catch (FileNotFoundException e) {
            System.out.println(FILE_ERR_MSG);
        }

        return propertyValues;
    }
}
