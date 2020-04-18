package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.PropertyValue;
import edu.upenn.cit594.logging.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Uses a scanner to parse a comma separated text file for property value data
 *
 * @author Chris Henry + Tim Chung
 */
public class PropertyValueReaderCSV {
    private static final String COMMA = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    protected File file;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param file an existing file opened with FileReader
     */
    public PropertyValueReaderCSV(File file) {
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
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), file.getName()));

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
                if (marketValue.length() == 0) {
                    continue;
                }
                String totalLivableArea = propertyValueArray[totalLivableAreaIndex];
                if (totalLivableArea.length() == 0) {
                    continue;
                }
                String zipCode = propertyValueArray[zipCodeIndex];
                if (zipCode.length() == 0) {
                    continue;
                }

                propertyValues.add(new PropertyValue(Double.parseDouble(marketValue),
                        Double.parseDouble(totalLivableArea), zipCode));

            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return propertyValues;
    }
}
