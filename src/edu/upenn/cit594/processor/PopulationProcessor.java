package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.datamanagement.PopulationReaderSSV;

import java.util.List;

/**
 * Stores a list of Population objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class PopulationProcessor {
    PopulationReaderSSV populationReader;
    List<Population> populations;

    /**
     * Constructs a Population to store a set of Population objects created by the PopulationReader class
     *
     * @param populationReader
     */
    public PopulationProcessor(PopulationReaderSSV populationReader) {
        this.populationReader = populationReader;
        this.populations = populationReader.getAllPopulations();
    }

    /**
     * @return total population, -1 for error
     */
    public int getTotalPopulation() {
        int totalPopulation = -1;
        for (Population population : populations) {
            totalPopulation += population.getPopulation();
        }
        return totalPopulation;
    }

    /**
     * @return list of all populations
     */
    public List<Population> getPopulations() {
        return populations;
    }
}
