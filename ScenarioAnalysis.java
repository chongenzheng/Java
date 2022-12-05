import java.util.Vector;

/*************************************************************************
 *  Compilation:  javac ScenarioAnalysis.java
 *  Execution:    java ScenarioAnalysis
 *
 *  @author:
 *
 *************************************************************************/
public class ScenarioAnalysis {

    // Instance variables
    private Vehicle[] vehicles;       // all vehicless being analyzed 
    private double  gasPrice;         // price of one gallon of gas in dollars
    private double  electricityPrice; // price of 1 kWh in cents of a dollar, c$/kWh

    /*
     * Constructor
     */ 
    public ScenarioAnalysis ( double gasPrice, double electricityPrice ) {
        this.gasPrice = gasPrice;
        this.electricityPrice = electricityPrice;
    }

    /*
     * Updates the price of gas
     * Call computeCO2EmissionsAndCost() whenever there is an update on gas prices
     */
    public void setGasPrice ( double gasPrice ) {
        this.gasPrice = gasPrice;
        computeCO2EmissionsAndCost();
    }

    /*
     * Returns the gas price
     */ 
    public double getGasPrice () {
        return gasPrice;
    }

    /*
     * Updates the price of electricity
     * Call computeCO2EmissionsAndCost() whenever there is an update on electricity prices
     */
    public void setElectricityPrice ( double electricityPrice ) {
        this.electricityPrice = electricityPrice;
    }
    
    /*
     * Returns electricity price
     */
    public double getElectricityPrice () {
        return electricityPrice;
    }

    /*
     * Computes and updates the CO2 emissions, fuel cost and total cost for each 
     * vehicle in the vehicles array.
     */
    public void computeCO2EmissionsAndCost () {
        for(Vehicle v : vehicles){
            double totalMiles = v.getLease().getMileageAllowance() * (v.getLease().getNumberOfMonths()/12);
            double leaseCost = v.getLease().getNumberOfMonths() * v.getLease().getMonthlyCost() + v.getLease().getDueAtSigning();
            double usage = v.getFuel().getUsage();
            double kwhPerCharge = v.getFuel().getKWhPerCharge();
            double otherCost = v.getOtherCost();
            if(v.getFuel().getType() == 1){
                double co2Emitted = totalMiles / usage * Fuel.CO2EMITTED_GASCOMBUSTION;
                double fuelCost = totalMiles / usage * gasPrice;
                double totalCost = leaseCost + fuelCost + otherCost;
                v.setCO2Emission(co2Emitted);
                v.setFuelCost(fuelCost);
                v.setTotalCost(totalCost);
            }
            else if(v.getFuel().getType() == 2){
                double co2Emitted = (totalMiles / usage * kwhPerCharge) * (Fuel.CO2EMITTED_GENERATEmWh/1000*0.45);
                double fuelCost = totalMiles / usage * kwhPerCharge * electricityPrice/100;
                double totalCost = leaseCost + fuelCost + otherCost;
                v.setCO2Emission(co2Emitted);
                v.setFuelCost(fuelCost);
                v.setTotalCost(totalCost);
            }
        }
    }

    /*
     * Returns vehicles array
     */
    public Vehicle[] getVehicles () {
        return vehicles;
    }

    /*
     * Prints all vehicles
     */
    public void printVehicles () {
        for ( Vehicle v : vehicles ) {
            StdOut.println(v);
        }
    }

    /*
     * Populates the array vehicles from file vehicles.txt
     * 
     * File Format: The file can have either gas or electric lines
     * 
     * gas,      name, cash due at signing lease,lease length in months, monthly lease cost, mileage allowance per 12 months, miles per gallon, cost of oil change
     * electric, name, cash due at signing lease,lease length in months, monthly lease cost, mileage allowance per 12 months, miles per kWh/charge, kWh per charge, cost of home charger
     */ 
    public void populateVehicleArray () {
        StdIn.setFile("vehicles.txt");

        // read the number of car models and allocate array
        int numberOfCars = StdIn.readInt();
        vehicles = new Vehicle[numberOfCars];

        for (int i = 0; i < numberOfCars; i++) {
            String fuelType = StdIn.readString();
            String name     = StdIn.readString();

            // Lease information
            double dueAtSigning  = StdIn.readDouble();
            int numberOfMonths = StdIn.readInt();
            double montlyCost  = StdIn.readDouble();
            int mileageAllowance = StdIn.readInt();
            Lease lease = new Lease(dueAtSigning,numberOfMonths,montlyCost,mileageAllowance);

            // Fuel
            double usage = StdIn.readDouble();
            Fuel fuel = null; 
            if ( fuelType.toLowerCase().equals("electric")) {
                double kWhPerCharge = StdIn.readDouble();
                fuel = new Fuel (usage, kWhPerCharge);
            } else {
                fuel = new Fuel (usage);
            }

            // other cost include oil change for gas-powered or home charger for eletric-powered
            double otherCost = StdIn.readDouble();

            // Instatiate the new vehicle
            vehicles[i] = new Vehicle (name, fuel, lease, otherCost);
        }
    }

    /*
     * Test client
     */
    public static void main (String[] args) {
        ScenarioAnalysis sa = new ScenarioAnalysis(3.45, 0.3);
        sa.populateVehicleArray();//read all information from the text.file
        sa.setGasPrice(2.23);           // $2.23 per gallon of gas
        sa.setElectricityPrice(16.14);  // c$16.14 per kWh
        sa.computeCO2EmissionsAndCost();
        sa.printVehicles();//print all vehicles
    }
}