package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;
import edu.upenn.cit594.datamanagement.PropertyValueReaderCSV;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Stores a list of PropertyValue objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class PropertyValueProcessor {
    PropertyValueReaderCSV propertyValueReader;
    List<PropertyValue> propertyValues;
    Map<Integer, List<PropertyValue>> propertyValuesByZip;

    /**
     * Constructs a PropertyValueProcessor to store a set of PropertyValue objects created by the
     * PropertyValueReader class
     *
     * @param propertyValueReader reader for property value data
     */
    public PropertyValueProcessor(PropertyValueReaderCSV propertyValueReader) {
        this.propertyValueReader = propertyValueReader;
        this.propertyValues = propertyValueReader.getAllPropertyValues();
        this.propertyValuesByZip = new HashMap<>();
    }

    /**
     * @return property values by zip code
     */
    public Map<Integer, List<PropertyValue>> getPropertyValuesByZip() {
        if (propertyValuesByZip.isEmpty()) {

            for (PropertyValue propertyValue : propertyValues) {
                int zipCode = propertyValue.getZipCode();
                List<PropertyValue> propertyValues = propertyValuesByZip.containsKey(zipCode) ?
                        propertyValuesByZip.get(zipCode) : new LinkedList<>();

                propertyValues.add(propertyValue);
                propertyValuesByZip.put(zipCode, propertyValues);
            }
        }

        return propertyValuesByZip;
    }

    /**
     * @param zipCode         zip code in which to search
     * @param populationCount population count for provided zip code
     * @return Total Residential Market Value Per Capita
     */
    public double getTotalResidentialMarketValuePerCapita(int zipCode, int populationCount) {
        List<PropertyValue> properties = propertyValuesByZip.get(zipCode);

        return getAverage(new MarketValueReducer(properties), populationCount);
    }

    /**
     * @param zipCode zip code in which to search
     * @return Average Residential Market Value
     */
    public double getAverageResidentialMarketValue(int zipCode) {
        List<PropertyValue> properties = propertyValuesByZip.get(zipCode);

        return getAverage(new MarketValueReducer(properties), properties.size());
    }

    /**
     * @param zipCode zip code in which to search
     * @return Average Residential Total Livable Area
     */
    public double getAverageResidentialTotalLivableArea(int zipCode) {
        List<PropertyValue> properties = propertyValuesByZip.get(zipCode);

        return getAverage(new TotalLivableAreaReducer(properties), properties.size());
    }

    /**
     * @param reducer comparator to use when finding average
     * @return an average based on a zip code and a comparator
     */
    private double getAverage(PropertyValueReducer reducer, int listSize) {
        return reducer.reduce() / (double) listSize;
    }
}
