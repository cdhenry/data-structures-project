package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.datamanagement.PopulationReaderSSV;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores a list of Population objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class PopulationProcessor {
    PopulationReaderSSV populationReader;
    List<Population> populationsList;
    HashMap<Integer, Integer> populationsByZip;

    /**
     * Constructs a Population to store a set of Population objects created by the PopulationReader class
     *
     * @param populationReader
     */
    public PopulationProcessor(PopulationReaderSSV populationReader) {
        this.populationReader = populationReader;
        this.populationsList = populationReader.getAllPopulations();
        this.populationsByZip = new HashMap<>();
    }

    /**
     * @return total population, -1 for error
     */
    public int getTotalPopulation() {
        int totalPopulation = -1;
        for (Population population : populationsList) {
            totalPopulation += population.getPopulation();
        }
        return totalPopulation;
    }

    /**
     * @return list of all populations
     */
    public List<Population> getPopulationsList() {
        return populationsList;
    }

    /**
     * @return list of all populations
     */
    public Map<Integer, Integer> getPopulationsByZip() {
        if (populationsByZip.isEmpty()) {
            for (Population population : populationsList) {
                populationsByZip.put(population.getZipCode(), population.getPopulation());
            }
        }

        return populationsByZip;
    }
}
