package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;

import java.util.List;
import java.util.Map;

/**
 * An interface used to specify the methods for getting parking violations from a file
 *
 * @author Chris Henry + Tim Chung
 */
public interface ParkingViolationReader {
    /**
     * Gets a list of parking violations from a file
     *
     * @return a list of parking violation objects
     */
    Map<Integer, List<ParkingViolation>> getAllParkingViolations();
}
