package edu.upenn.cit594.ui;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.ParkingViolationProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyValueProcessor;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * A command line interface class for outputting text via the console to the user
 *
 * @author Chris Henry + Tim Chung
 */
public class CommandLineUserInterface {
    protected ParkingViolationProcessor parkingViolationProcessor;
    protected PopulationProcessor populationProcessor;
    protected PropertyValueProcessor propertyValueProcessor;
    protected Scanner in;

    /**
     * Constructs a CommandLineUserInterface class for interaction with the user.
     * Uses processor classes to deliver the desired information to the user.
     *
     * @param parkingViolationProcessor a parking violation processor
     * @param populationProcessor       a population processor
     * @param propertyValueProcessor    a property value processor
     */
    public CommandLineUserInterface(ParkingViolationProcessor parkingViolationProcessor,
                                    PopulationProcessor populationProcessor,
                                    PropertyValueProcessor propertyValueProcessor) {
        this.parkingViolationProcessor = parkingViolationProcessor;
        this.populationProcessor = populationProcessor;
        this.propertyValueProcessor = propertyValueProcessor;
        in = new Scanner(System.in);
    }

    /**
     * Starts the UI sequence
     */
    public void start() {
        int choice = -1;

        do {
            try {
                while (choice != 0) {
                    printInstructions();
                    choice = in.nextInt();
                    Logger.getInstance().log(String.format("%d %d\n", System.currentTimeMillis(), choice));
                    performAction(choice);
                }
                break;
            } catch (InputMismatchException e) {
                printError();
            }
        } while (true);

        in.close();
    }

    /**
     * Makes calls to print functions based on user input
     *
     * @param choice integer value entered by user
     */
    private void performAction(int choice) {
        switch (choice) {
            case 0:
                break;
            case 1:
                printTotalPopulation();
                break;
            case 2:
                printTotalFinesPerCapita();
                break;
            case 3:
                printAverageResidentialMarketValue(getZipCode());
                break;
            case 4:
                printAverageResidentialTotalLivableArea(getZipCode());
                break;
            case 5:
                printTotalResidentialMarketValuePerCapita(getZipCode());
                break;
            case 6:
                printCUSTOM();
                break;
            default:
                printError();
                break;
        }
    }

    /**
     * Prompt user for zip code
     *
     * @return zip code as string
     */
    private int getZipCode() {
        int zipCode;

        do {
            try {
                System.out.print("Enter zip code: ");
                zipCode = in.nextInt();
                Logger.getInstance().log(String.format("%d %d\n", System.currentTimeMillis(), zipCode));
                break;
            } catch (InputMismatchException e) {
                printError();
            }
        } while (true);

        return zipCode;
    }

    /**
     * Prints choices to command line
     */
    private void printInstructions() {
        System.out.println("Choose from the following options.");
        System.out.println("Enter:");
        System.out.println("\t1 for the total population for all ZIP Codes");
        System.out.println("\t2 for the total fines per capita");
        System.out.println("\t3 for the average residential market value");
        System.out.println("\t4 for the average residential total livable area");
        System.out.println("\t5 for the total residential market value per capita");
        System.out.println("\t6 for // TODO: CUSTOM FEATURE");
    }

    /**
     * Prints total population for all zip codes
     */
    private void printTotalPopulation() {
        System.out.println(populationProcessor.getTotalPopulation());
    }

    /**
     * Prints total fines per capita for each zip code
     */
    private void printTotalFinesPerCapita() {
        Map<Integer, Double> totalFinesPerCapita = parkingViolationProcessor.getTotalFinesPerCapita(
                populationProcessor.getPopulationsByZip());

        if (totalFinesPerCapita == null) {
            return;
        }

        for (Map.Entry<Integer, Double> entry : totalFinesPerCapita.entrySet()) {
            int key = entry.getKey();
            double value = entry.getValue();
            System.out.printf("%d %04.4f\n", key, value);
        }
    }

    /**
     * Prints average residential market value for provided zip code
     */
    private void printAverageResidentialMarketValue(int zipCode) {
        System.out.printf("%04.4f\n", propertyValueProcessor.getAverageResidentialMarketValue(zipCode));
    }

    /**
     * Prints average residential total livable area for provided zip code
     */
    private void printAverageResidentialTotalLivableArea(int zipCode) {
        System.out.printf("%d\n", (int) propertyValueProcessor.getAverageResidentialTotalLivableArea(zipCode));
    }

    /**
     * Prints average residential market value per capita for provided zip code
     */
    private void printTotalResidentialMarketValuePerCapita(int zipCode) {
        int populationCount = populationProcessor.getPopulationsByZip().get(zipCode);
        int total = (int) propertyValueProcessor.getTotalResidentialMarketValuePerCapita(zipCode, populationCount);
        System.out.printf("%d\n", total);
    }

    // TODO: CUSTOM FEATURE
    private void printCUSTOM() {

    }

    private void printError() {
        System.out.println("Invalid Input, please try again.");
    }
}
