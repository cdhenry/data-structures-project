package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.ParkingViolationReader;

import java.util.List;
import java.util.Map;

/**
 * Stores a list of ParkingViolation objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class ParkingViolationProcessor {
    ParkingViolationReader parkingViolationReader;
    List<ParkingViolation> parkingViolations;

    /**
     * Constructs a ParkingViolationProcessor to store a set of ParkingViolation objects created by the
     * ParkingViolationReader class
     *
     * @param parkingViolationReader
     */
    public ParkingViolationProcessor(ParkingViolationReader parkingViolationReader) {
        this.parkingViolationReader = parkingViolationReader;
        this.parkingViolations = parkingViolationReader.getAllParkingViolations();
    }

    /**
     *
     * @return total fine per capita
     */
    public Map<String, Double> getTotalFinesPerCapita(){
        return null;
    }
}
