package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.CommonConstant;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.logging.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;

/**
 * Uses a scanner to parse a space separated text file for population data
 *
 * @author Chris Henry + Tim Chung
 */
public class PopulationReaderSSV {
    private static final String FILE_ERR_MSG = "population file must exist and be readable";
    protected Scanner readIn;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param filename an SSV filename for a population file
     */
    public PopulationReaderSSV(String filename) {
        try {
            FileReader file = new FileReader(filename);
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), filename));
            readIn = new Scanner(file);

        } catch (IOException e) {
            System.out.println(FILE_ERR_MSG);
            System.exit(4);
        }
    }

    /**
     * Gets a list of populations from a file
     *
     * @return a list of population objects
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

            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {}
        }
        return populationsMap;
    }
}
