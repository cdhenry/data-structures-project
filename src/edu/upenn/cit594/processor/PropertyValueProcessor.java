package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;
import edu.upenn.cit594.datamanagement.MappableByInteger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores a map of PropertyValue objects by their zip codes and
 * contains methods for performing various operations on the map
 *
 * @author Chris Henry + Tim Chung
 */
public class PropertyValueProcessor implements Runnable {
    protected MappableByInteger<List<PropertyValue>> propertyValueReader;
    protected Map<Integer, List<PropertyValue>> propertyValuesMap;
    protected Map<Integer, ListSumSizePair> totalLivableAreaByZip;
    protected Map<Integer, ListSumSizePair> totalMarketValueByZip;

    /**
     * Constructs a PropertyValueProcessor to store a set of PropertyValue objects created by the
     * PropertyValueReader class
     *
     * @param propertyValueReader reader for property value data
     */
    public PropertyValueProcessor(MappableByInteger<List<PropertyValue>> propertyValueReader) {
        this.propertyValueReader = propertyValueReader;
        this.totalMarketValueByZip = new HashMap<>();
        this.totalLivableAreaByZip = new HashMap<>();
    }

    @Override
    public void run() {
        this.propertyValuesMap = propertyValueReader.getIntegerMap();
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
    public double getTotalMarketValuePerCapita(int zipCode, int populationCount) {
        if (populationCount < 1) {
            return 0.0;
        }

        double total = getTotalMarketValueByZip(zipCode);
        return total / populationCount;
    }

    /**
     * @param zipCode          zip code in which to get average market value
     * @param avgFinePerCapita average fine per capita
     * @return average market value over average fine per capita
     */
    public double getAvgMktValOverAvgFinePerCap(int zipCode, double avgFinePerCapita) {
        if (avgFinePerCapita > 0) {
            double avgMarketVal = getAvgMarketValue(zipCode);
            return avgMarketVal / avgFinePerCapita;
        }

        return 0.0;
    }

    /**
     * @param zipCode zip code in which to search
     * @return Average Residential Market Value
     */
    public double getAvgMarketValue(int zipCode) {
        ListSumSizePair pair = getListSumSizePair(zipCode, new MarketValueReducer(), totalMarketValueByZip);
        return (pair == null) ? 0.0 : (pair.getSum() / pair.getSize());
    }

    /**
     * @param zipCode zip code in which to search
     * @return Average Residential Total Livable Area
     */
    public double getAvgLivableArea(int zipCode) {
        ListSumSizePair pair = getListSumSizePair(zipCode, new TotalLivableAreaReducer(), totalLivableAreaByZip);
        return pair.getSum() / pair.getSize();
    }

    /**
     * @return total market value for all residences in zip code
     */
    public double getTotalMarketValueByZip(int zipCode) {
        return getListSumSizePair(zipCode, new MarketValueReducer(), totalMarketValueByZip).getSum();
    }

    /**
     * @param reducer strategy pattern for acquiring totals by zip code and the number of objects to create said total
     * @return a SumSizePair object
     */
    private ListSumSizePair getListSumSizePair(int zipCode, PropertyValueReducer reducer, Map<Integer, ListSumSizePair> memo) {
        if (memo.containsKey(zipCode)) {
            return memo.get(zipCode);
        }

        List<PropertyValue> properties = getPropertyValuesMap().get(zipCode);
        ListSumSizePair newPair;

        if (properties == null || properties.isEmpty()) {
            newPair = new ListSumSizePair();
            memo.put(zipCode, newPair);
            return newPair;
        }

        newPair = new ListSumSizePair(reducer.reduce(properties), properties.size());
        memo.put(zipCode, newPair);

        return newPair;
    }

    /**
     * A basic value pair class for storing a sum and the number of values used to create said sum
     */
    private class ListSumSizePair {
        protected int size;
        protected double sum;

        /**
         * Constructs an empty SumSizePair
         */
        public ListSumSizePair() {
            this.sum = 0.0;
            this.size = -1;
        }

        /**
         * @param sum  a double sum value
         * @param size the number of values used to create sum
         */
        public ListSumSizePair(double sum, int size) {
            this.sum = sum;
            this.size = size;
        }

        /**
         * @return size
         */
        public int getSize() {
            return size;
        }

        /**
         * @return sum
         */
        public double getSum() {
            return sum;
        }
    }
}
