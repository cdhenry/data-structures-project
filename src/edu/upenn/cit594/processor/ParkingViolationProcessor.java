package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.ParkingViolationReader;

import java.util.*;

/**
 * Stores a list of ParkingViolation objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class ParkingViolationProcessor {
    protected ParkingViolationReader parkingViolationReader;
    protected List<ParkingViolation> parkingViolations;
    protected Map<Integer, List<ParkingViolation>> parkingViolationsByZip;

    /**
     * Constructs a ParkingViolationProcessor to store a set of ParkingViolation objects created by the
     * ParkingViolationReader class
     *
     * @param parkingViolationReader reader for parking violation data
     */
    public ParkingViolationProcessor(ParkingViolationReader parkingViolationReader) {
        this.parkingViolationReader = parkingViolationReader;
        this.parkingViolations = parkingViolationReader.getAllParkingViolations();
        this.parkingViolationsByZip = new HashMap<>();
    }

    /**
     * @return parking violations by zip code
     */
    public Map<Integer, List<ParkingViolation>> getParkingViolationsByZip() {
        if (parkingViolationsByZip.isEmpty()) {

            for (ParkingViolation violation : parkingViolations) {
                int zipCode = violation.getZipCode();
                List<ParkingViolation> violations = parkingViolationsByZip.containsKey(zipCode) ?
                        parkingViolationsByZip.get(zipCode) : new LinkedList<ParkingViolation>();

                violations.add(violation);
                parkingViolationsByZip.put(zipCode, violations);
            }
        }

        return parkingViolationsByZip;
    }

    /**
     * @return total fine per capita
     */
    public Map<Integer, Double> getTotalFinesPerCapita(Map<Integer, Integer> populations) {
        Map<Integer, Double> totalFinesPerCapita = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : populations.entrySet()) {
            int zipCode = entry.getKey();
            int population = entry.getValue();
            List<ParkingViolation> violations = getParkingViolationsByZip().get(zipCode);

            if (violations == null) {
                continue;
            }

            double totalFines = 0.0;

            for (ParkingViolation violation : violations) {
                totalFines += violation.getFineInDollars();
            }

            totalFinesPerCapita.put(zipCode, totalFines / population);
        }

        return totalFinesPerCapita;
    }
}
