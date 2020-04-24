package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.CommonConstant;
import edu.upenn.cit594.data.Population;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;

/**
 * Uses a scanner to parse a space separated text file for population data
 *
 * @author Chris Henry + Tim Chung
 */
public class PopulationReaderSSV extends Reader {
    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param filename an SSV filename for a population file
     */
    public PopulationReaderSSV(String filename) {
        super(filename);
    }

    /**
     * Maps a set of populations from a file by their zip codes
     *
     * @return a map of population objects by their zip codes
     */
    public Map<Integer, Integer> getAllPopulations() {
        Map<Integer, Integer> populationsMap = new TreeMap<>();

        while (readIn.hasNextLine()) {
            String population = readIn.nextLine();
            String[] populationArray = population.trim().split(CommonConstant.SPACE_REGEX);

            try {
                String zipCode = populationArray[0].trim();
                Matcher m = CommonConstant.ZIP_CODE_PATTERN.matcher(zipCode);
                if (m.find()) {
                    zipCode = m.group();
                } else {
                    continue;
                }

                String populationCount = populationArray[1].trim();
                if (populationCount.length() == 0) {
                    continue;
                }

                Population newPopulation = new Population(Integer.parseInt(zipCode), Integer.parseInt(populationCount));
                populationsMap.put(newPopulation.getZipCode(), newPopulation.getPopulation());

            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            }
        }
        return populationsMap;
    }
}
