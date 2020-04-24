package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.PopulationReaderSSV;

import java.util.Map;

/**
 * Stores a list of Population objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class PopulationProcessor {
    private static final int TOTAL_POPULATION_UNINITIALIZED = -1;
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
        this.populationsMap = populationReader.getAllPopulations();
        this.totalPopulation = TOTAL_POPULATION_UNINITIALIZED;
    }

    /**
     * @return total population
     */
    public int getTotalPopulation() {
        if (totalPopulation == TOTAL_POPULATION_UNINITIALIZED) {
            totalPopulation++;

            for (Integer localPopulation : populationsMap.values()) {
                totalPopulation += localPopulation;
            }
        }

        return totalPopulation;
    }


    /**
     * @return population for zip code, -1 if not found
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
