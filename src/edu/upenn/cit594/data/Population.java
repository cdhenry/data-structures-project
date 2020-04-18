package edu.upenn.cit594.data;

/**
 * A class for population based on zip code
 *
 * @author Chris Henry + Tim Chung
 */
public class Population { // implements Comparable<Object>, Comparator<Object> {
    protected int zipCode;
    protected int population;

    /**
     * @param zipCode    zip code of population
     * @param population population count
     */
    public Population(int zipCode, int population) {
        this.zipCode = zipCode;
        this.population = population;
    }

    /**
     * @return zipCode
     */
    public int getZipCode() {
        return zipCode;
    }

    /**
     * @return population
     */
    public int getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return zipCode + " " + population;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Population) {
            Population other = (Population) obj;
            return zipCode == other.zipCode;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
//
//    @Override
//    public int compare(Object o1, Object o2) {
//        if (o1 instanceof Population && o2 instanceof Population) {
//            Population p1 = (Population) o1;
//            Population p2 = (Population) o2;
//            return Integer.compare(p1.getZipCode(), p2.getZipCode());
//        }
//
//        return 0;
//    }
//
//    @Override
//    public int compareTo(Object obj) {
//        Population p1 = this;
//
//        if (obj instanceof Population) {
//            Population p2 = (Population) obj;
//            return Integer.compare(p1.getZipCode(), p2.getZipCode());
//        }
//
//        return 0;
//    }
}
