package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.PropertyValue;
import edu.upenn.cit594.data.PropertyValueComparator;
import edu.upenn.cit594.datamanagement.PropertyValueReaderCSV;

import java.util.List;

/**
 * Stores a list of PropertyValue objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class PropertyValueProcessor {
    PropertyValueReaderCSV propertyValueReader;
    List<PropertyValue> propertyValues;

    /**
     * Constructs a PropertyValueProcessor to store a set of PropertyValue objects created by the
     * PropertyValueReader class
     *
     * @param propertyValueReader
     */
    public PropertyValueProcessor(PropertyValueReaderCSV propertyValueReader) {
        this.propertyValueReader = propertyValueReader;
        this.propertyValues = propertyValueReader.getAllPropertyValues();
    }

    /**
     *
     * @param zipCode
     * @param populations
     * @return Total Residential Market Value Per Capita
     */
    public double getTotalResidentialMarketValuePerCapita(String zipCode, List<Population> populations){
        return 0.0;
    }

    /**
     *
     * @param zipCode
     * @return  Average Residential Market Value
     */
    public double getAverageResidentialMarketValue(String zipCode){
        return 0.0;
    }

    /**
     *
     * @param zipCode
     * @return Average Residential Total Livable Area
     */
    public double getAverageResidentialTotalLivableArea(String zipCode) {
        return 0.0;
    }

    /**
     *
     * @param zipCode
     * @param comparator
     * @return an average based on a zip code and a comparator
     */
    private double getAverageByZip(String zipCode, PropertyValueComparator comparator) {
        return 0.0;
    }
}
