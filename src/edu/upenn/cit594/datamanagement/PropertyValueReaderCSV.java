package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.CommonConstant;
import edu.upenn.cit594.data.PropertyValue;
import edu.upenn.cit594.logging.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;

/**
 * Uses a scanner to parse a comma separated text file for property value data
 *
 * @author Chris Henry + Tim Chung
 */
public class PropertyValueReaderCSV {
    private static final String FILE_ERR_MSG = "property value file must exist and be readable";
    protected Scanner readIn;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param filename a CSV filename for a property value file
     */
    public PropertyValueReaderCSV(String filename) {
        try {
            FileReader file = new FileReader(filename);
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), filename));
            readIn = new Scanner(file);
        } catch (IOException e) {
            System.out.println(FILE_ERR_MSG);
            System.exit(4);
        }
    }

    /**
     * Gets a list of property values from a file
     *
     * @return a list of property value objects
     */
    public Map<Integer, List<PropertyValue>> getAllPropertyValues() {
        Map<Integer, List<PropertyValue>> propertyValuesMap = new HashMap<>();
        String headers = readIn.nextLine();
        String[] headerArray = headers.trim().split(CommonConstant.COMMA_REGEX);
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

        while (readIn.hasNextLine()) {
            String propertyValue = readIn.nextLine();
            String[] propertyValueArray = propertyValue.trim().split(CommonConstant.COMMA_REGEX);

            if (propertyValueArray.length != headerArray.length) {
                continue;
            }

            String marketValue = propertyValueArray[marketValueIndex].trim();
            if (marketValue.length() == 0) {
                continue;
            }

            String totalLivableArea = propertyValueArray[totalLivableAreaIndex].trim();
            if (totalLivableArea.length() == 0) {
                continue;
            }

            String zipCode = propertyValueArray[zipCodeIndex].trim();
            Matcher m = CommonConstant.ZIP_CODE_PATTERN.matcher(zipCode);
            if (m.find()) {
                zipCode = m.group();
            } else {
                continue;
            }

            //Only add if all fields are correct data type
            try {
                double marketValueDouble = Double.parseDouble(marketValue);
                double totalLivableAreaDouble = Double.parseDouble(totalLivableArea);
                int zipCodeInt = Integer.parseInt(zipCode);

                PropertyValue newPropertyValue = new PropertyValue(++id, marketValueDouble,
                        totalLivableAreaDouble, zipCodeInt);

                if (propertyValuesMap.containsKey(zipCodeInt)) {
                    propertyValuesMap.get(zipCodeInt).add(newPropertyValue);
                } else {
                    List<PropertyValue> propertyValues = new LinkedList<>();
                    propertyValues.add(newPropertyValue);
                    propertyValuesMap.put(zipCodeInt, propertyValues);
                }

            } catch (NumberFormatException e) {}
        }

        return propertyValuesMap;
    }
}
