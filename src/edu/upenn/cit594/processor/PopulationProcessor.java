package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.PopulationReaderSSV;

import java.util.Map;

/**
 * Stores a list of Population objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class PopulationProcessor {
    protected PopulationReaderSSV populationReader;
    protected Map<Integer, Integer> populationsMap;
    protected int totalPopulation;

    /**
     * Constructs a Population to store a set of Population objects created by the PopulationReader class
     *
     * @param populationReader
     */
    public PopulationProcessor(PopulationReaderSSV populationReader) {
        this.populationReader = populationReader;
        this.populationsMap = populationReader.getAllPopulations(totalPopulation);
    }

    /**
     * @return total population, -1 for error
     */
    public int getTotalPopulation() {
        // Calculated during read
        return totalPopulation;
    }


    /**
     * @return population for zip code
     */
    public int getPopulationsByZip(int zipCode) {
        if (populationsMap.containsKey(zipCode)) {
            return populationsMap.get(zipCode);
        }
        return -1;
    }

    public Map<Integer, Integer> getPopulationsMap() {
        return populationsMap;
    }
}
