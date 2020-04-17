package edu.upenn.cit594.data;

/**
 * A class for population based on zip code
 *
 * @author Chris Henry + Tim Chung
 *
 */
public class Population {
    String zipCode;
    long population;

    /**
     * @param zipCode
     * @param population
     */
    public Population(String zipCode, long population) {
        this.zipCode = zipCode;
        this.population = population;
    }

    /**
     * @return zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @return population
     */
    public long getPopulation() {
        return population;
    }
}
