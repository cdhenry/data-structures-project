package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;
import edu.upenn.cit594.datamanagement.PropertyValueReaderCSV;

import java.util.List;
import java.util.Map;

/**
 * Stores a list of PropertyValue objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class PropertyValueProcessor {
    protected PropertyValueReaderCSV propertyValueReader;
    protected Map<Integer, List<PropertyValue>> propertyValuesMap;

    /**
     * Constructs a PropertyValueProcessor to store a set of PropertyValue objects created by the
     * PropertyValueReader class
     *
     * @param propertyValueReader reader for property value data
     */
    public PropertyValueProcessor(PropertyValueReaderCSV propertyValueReader) {
        this.propertyValueReader = propertyValueReader;
        this.propertyValuesMap = propertyValueReader.getAllPropertyValues();
    }

    /**
     * @return property values by zip code
     */
    public Map<Integer, List<PropertyValue>> getPropertyValuesMap() {
        return propertyValuesMap;
    }

    /**
     * @param zipCode         zip code in which to search
     * @param populationCount population count for provided zip code
     * @return Total Residential Market Value Per Capita
     */
    public double getTotalResidentialMarketValuePerCapita(int zipCode, int populationCount) {
        List<PropertyValue> properties = getPropertyValuesMap().get(zipCode);

        return properties == null ? 0.0 : getAverage(getTotalMarketValueByZip(zipCode), populationCount);
    }

    /**
     * @param zipCode zip code in which to search
     * @return Average Residential Market Value
     */
    public double getAverageResidentialMarketValue(int zipCode) {
        List<PropertyValue> properties = getPropertyValuesMap().get(zipCode);

        return properties == null ? 0.0 : getAverage(getTotalMarketValueByZip(zipCode), properties.size());
    }

    /**
     * @param zipCode zip code in which to search
     * @return Average Residential Total Livable Area
     */
    public double getAverageResidentialTotalLivableArea(int zipCode) {
        List<PropertyValue> properties = getPropertyValuesMap().get(zipCode);

        return properties == null ? 0.0 : getAverage(getTotalLivableByZip(zipCode), properties.size());
    }

    /**
     * @return an average based on a zip code and a comparator
     */
    private double getAverage(double total, int size) {
        return total / size;
    }


    /**
     * @return total market value for all residences in zip code
     */
    public double getTotalMarketValueByZip(int zipCode) {
        List<PropertyValue> properties = getPropertyValuesMap().get(zipCode);

        return getTotal(new MarketValueReducer(properties));
    }

    /**
     * @return total livable area for all residences in zip code
     */
    public double getTotalLivableByZip(int zipCode) {
        List<PropertyValue> properties = getPropertyValuesMap().get(zipCode);

        return getTotal(new TotalLivableAreaReducer(properties));
    }

    /**
     * @param reducer strategy pattern for acquiring totals by zip code
     * @return total livable area for all residences in zip code
     */
    private double getTotal(PropertyValueReducer reducer) {
        return reducer.getTotal();
    }


}
