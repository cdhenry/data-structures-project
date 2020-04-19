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
    protected PropertyValueReaderCSV propertyValueReader;
    protected List<PropertyValue> propertyValues;
    protected Map<Integer, List<PropertyValue>> propertyValuesByZip;
    protected Map<Integer, Double> averageResidentialMarketValueByZip;
    protected Map<Integer, Double> averageResidentialTotalLivableAreaByZip;
    protected Map<Integer, Double> totalResidentialMarketValuePerCapitaByZip;

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
        this.averageResidentialMarketValueByZip = new HashMap<>();
        this.averageResidentialTotalLivableAreaByZip = new HashMap<>();
        this.totalResidentialMarketValuePerCapitaByZip = new HashMap<>();
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
        if (totalResidentialMarketValuePerCapitaByZip.containsKey(zipCode)) {
            return totalResidentialMarketValuePerCapitaByZip.get(zipCode);
        }

        if (getPropertyValuesByZip().containsKey(zipCode)) {
            List<PropertyValue> properties = getPropertyValuesByZip().get(zipCode);
            if (properties == null) {
                totalResidentialMarketValuePerCapitaByZip.put(zipCode, 0.0);
                return 0.0;
            }

            double total = getAverage(new MarketValueReducer(properties), populationCount);
            totalResidentialMarketValuePerCapitaByZip.put(zipCode, total);

            return total;
        }

        totalResidentialMarketValuePerCapitaByZip.put(zipCode, 0.0);
        return 0.0;
    }

    /**
     * @param zipCode zip code in which to search
     * @return Average Residential Market Value
     */
    public double getAverageResidentialMarketValue(int zipCode) {
        if (averageResidentialMarketValueByZip.containsKey(zipCode)) {
            return averageResidentialMarketValueByZip.get(zipCode);
        }

        if (getPropertyValuesByZip().containsKey(zipCode)) {
            List<PropertyValue> properties = getPropertyValuesByZip().get(zipCode);
            if (properties == null) {
                averageResidentialMarketValueByZip.put(zipCode, 0.0);
                return 0.0;
            }

            double total = getAverage(new MarketValueReducer(properties), properties.size());
            averageResidentialMarketValueByZip.put(zipCode, total);

            return total;
        }

        averageResidentialMarketValueByZip.put(zipCode, 0.0);
        return 0.0;
    }

    /**
     * @param zipCode zip code in which to search
     * @return Average Residential Total Livable Area
     */
    public double getAverageResidentialTotalLivableArea(int zipCode) {
        if (averageResidentialTotalLivableAreaByZip.containsKey(zipCode)) {
            return averageResidentialTotalLivableAreaByZip.get(zipCode);
        }

        if (getPropertyValuesByZip().containsKey(zipCode)) {
            List<PropertyValue> properties = getPropertyValuesByZip().get(zipCode);
            if (properties == null) {
                averageResidentialTotalLivableAreaByZip.put(zipCode, 0.0);
                return 0.0;
            }

            double total = getAverage(new TotalLivableAreaReducer(properties), properties.size());
            averageResidentialTotalLivableAreaByZip.put(zipCode, total);

            return total;
        }

        averageResidentialTotalLivableAreaByZip.put(zipCode, 0.0);
        return 0.0;
    }

    /**
     * @param reducer comparator to use when finding average
     * @return an average based on a zip code and a comparator
     */
    private double getAverage(PropertyValueReducer reducer, int listSize) {
        return reducer.reduce() / (double) listSize;
    }
}
