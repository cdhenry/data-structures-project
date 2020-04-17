package edu.upenn.cit594.data;

/**
 * A class for property values which includes their market value, the total livable area, and the zipCode in which
 * the property is located
 *
 * @author Chris Henry + Tim Chung
 *
 */
public class PropertyValue {
    double marketValue, totalLivableArea;
    String zipCode;

    /**
     * @param marketValue
     * @param totalLivableArea
     * @param zipCode
     */
    public PropertyValue(double marketValue, double totalLivableArea, String zipCode) {
        this.marketValue = marketValue;
        this.totalLivableArea = totalLivableArea;
        this.zipCode = zipCode;
    }

    /**
     * @return marketValue
     */
    public double getMarketValue() {
        return marketValue;
    }

    /**
     * @return totalLivableArea
     */
    public double getTotalLivableArea() {
        return totalLivableArea;
    }

    /**
     * @return zipCode
     */
    public String getZipCode() {
        return zipCode;
    }
}
