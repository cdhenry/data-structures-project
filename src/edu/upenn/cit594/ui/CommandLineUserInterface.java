package edu.upenn.cit594.ui;

import edu.upenn.cit594.processor.ParkingViolationProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyValueProcessor;

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
     * @param populationProcessor a population processor
     * @param propertyValueProcessor a property value processor
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

        while (choice != 0) {
            printInstructions();
            choice = in.nextInt();
            performAction(choice);
        }

        in.close();
    }

    private void performAction(int choice){
        switch (choice) {
            case 0:
                break;
            case 1: printTotalPopulation();
                break;
            case 2: printTotalFinesPerCapita();
                break;
            case 3: printAverageResidentialMarketValue();
                break;
            case 4: printAverageResidentialTotalLivableArea();
                break;
            case 5: printTotalResidentialMarketValuePerCapita();
                break;
            case 6: printCUSTOM();
                break;
            default: printError();
                break;
        }
    }

    private void printInstructions(){
        System.out.println("Choose from the following options.");
        System.out.println("Enter:");
        System.out.println("\t1 for the total population for all ZIP Codes");
        System.out.println("\t2 for the total fines per capita");
        System.out.println("\t3 for the average residential market value");
        System.out.println("\t4 for the average residential total livable area");
        System.out.println("\t5 for the total residential market value per capita");
        System.out.println("\t6 for // TODO: CUSTOM FEATURE");
    }

    private void printTotalPopulation() {

    }

    private void printTotalFinesPerCapita() {

    }

    private void printAverageResidentialMarketValue() {

    }

    private void printAverageResidentialTotalLivableArea() {

    }

    private void printTotalResidentialMarketValuePerCapita() {

    }

    // TODO: CUSTOM FEATURE
    private void printCUSTOM() {

    }

    private void printError() {

    }
}
