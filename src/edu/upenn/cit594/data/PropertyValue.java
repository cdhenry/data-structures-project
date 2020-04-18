package edu.upenn.cit594.data;

import java.util.Comparator;

/**
 * A class for property values which includes their market value, the total livable area, and the zipCode in which
 * the property is located
 *
 * @author Chris Henry + Tim Chung
 */
public class PropertyValue { // implements Comparator<Object>, Comparable<Object> {
    int id, zipCode;
    double marketValue, totalLivableArea;

    /**
     * @param id               unique identifier for property
     * @param marketValue      market value of property
     * @param totalLivableArea total livable area of property
     * @param zipCode          zip code of property
     */
    public PropertyValue(int id, double marketValue, double totalLivableArea, int zipCode) {
        this.id = id;
        this.marketValue = marketValue;
        this.totalLivableArea = totalLivableArea;
        this.zipCode = zipCode;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
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
    public int getZipCode() {
        return zipCode;
    }

//    @Override
//    public String toString() {
//        return id + " " + zipCode;
//    }
//
//    public boolean equals(Object obj) {
//        if (obj instanceof PropertyValue) {
//            PropertyValue other = (PropertyValue) obj;
//            return id == other.id;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return toString().hashCode();
//    }
//
//    @Override
//    public int compare(Object o1, Object o2) {
//        if (o1 instanceof PropertyValue && o2 instanceof PropertyValue) {
//            PropertyValue p1 = (PropertyValue) o1;
//            PropertyValue p2 = (PropertyValue) o2;
//            int zipCompare = Integer.compare(p1.getZipCode(), p2.getZipCode());
//
//            if (zipCompare == 0) {
//                return Integer.compare(p1.getId(), p2.getId());
//            } else {
//                return zipCompare;
//            }
//        }
//
//        return 0;
//    }
//
//    @Override
//    public int compareTo(Object obj) {
//        PropertyValue p1 = this;
//
//        if (obj instanceof PropertyValue) {
//            PropertyValue p2 = (PropertyValue) obj;
//            int zipCompare = Integer.compare(p1.getZipCode(), p2.getZipCode());
//
//            if (zipCompare == 0) {
//                return Integer.compare(p1.getId(), p2.getId());
//            } else {
//                return zipCompare;
//            }
//        }
//
//        return 0;
//    }
}
