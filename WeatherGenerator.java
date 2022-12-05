public class WeatherGenerator {
    static final int WET = 1; // Use this value for a wet day
    static final int DRY = 2; // Use this value for a dry day 
    static final int[] numberOfDaysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static int[] oneMonthGenerator(double longitute, double latitude, 
    int month, double[][] drywet, double[][] wetwet) {
        int numberOfDays = numberOfDaysInMonth[month-2];
        int[] forecast = new int [numberOfDays];
        double seed = StdRandom.uniform();
        for(int row = 0; row < drywet.length; row++){
            if (drywet[row][0] == longitute && drywet[row][1] == latitude){
                break;
            }
        }
        for(int row = 0; row < wetwet.length; row++){
            if(wetwet[row][0] == longitute && wetwet[row][1] == latitude){
                break;
            }
        }
        if(seed < 0.5){
            forecast[0] = WET;
        }
        else{
            forecast[0] = DRY;
        }
        for(int row = 1; row < numberOfDays; row++){
            if (forecast[row-1] == WET){
                if (seed <= wetwet[row][month]){
                    forecast[row] = WET;
                }
                else{
                    forecast[row] = DRY;
                }
            }
            else{    
                if(seed <= drywet[row][month]){
                    forecast[row] = WET;
                }
                else{
                    forecast[row] = DRY;
                }
            }
        }
        return forecast;
    }
    public static int numberOfWetDryDays (int[] forecast, int mode) {
        int days = 0;
        for(int i = 0; i < forecast.length; i++){
            if (forecast[i] == mode){
                days++;
            }
        }
        return days;
    }
    public static int lengthOfLongestSpell (int[] forecast, int mode) {
        int length = forecast[0] == mode ?1:0;
        int longest = length;
        for(int i = 1; i < forecast.length; i++){
            if(forecast[i] == mode && forecast[i-1] == mode){
                length++;
                longest = Math.max(length, longest);
            }
            if(forecast[i] == mode || forecast[i] == mode ){
                length++;
            }
            if(forecast[i] != mode && forecast[i-1] != mode){
                length++;
                longest = Math.max(length, longest);
            }
        }
        return longest;
    }
    public static void readTransitionProbabilities ( double[][] arrayToFill, 
    int numberOfLocations ) {
        int row = 0;
        while (row < numberOfLocations) {
            arrayToFill[row][0] = StdIn.readDouble();  // Longitute
            arrayToFill[row][1] = StdIn.readDouble();  // Latitude
            arrayToFill[row][2] = StdIn.readDouble();  // January
            arrayToFill[row][3] = StdIn.readDouble();  // February
            arrayToFill[row][4] = StdIn.readDouble();  // March
            arrayToFill[row][5] = StdIn.readDouble();  // April
            arrayToFill[row][6] = StdIn.readDouble();  // May
            arrayToFill[row][7] = StdIn.readDouble();  // June
            arrayToFill[row][8] = StdIn.readDouble();  // July
            arrayToFill[row][9] = StdIn.readDouble();  // August
            arrayToFill[row][10] = StdIn.readDouble(); // Septmber
            arrayToFill[row][11] = StdIn.readDouble(); // October
            arrayToFill[row][12] = StdIn.readDouble(); // November
            arrayToFill[row][13] = StdIn.readDouble(); // December
            row += 1;
        }
    }
    public static void populateTransitionProbabilitiesArrays(double[][] drywet, 
    double[][] wetwet, int numberOfLocations) {
        StdIn.setFile("drywet.txt");
        readTransitionProbabilities(drywet, numberOfLocations);
        StdIn.setFile("wetwet.txt");
        readTransitionProbabilities(wetwet, numberOfLocations);
    }
    public static void main (String[] args) {
        int numberOfRows    = 4001; // Total number of locations
        int numberOfColumns = 14;   // Total number of 14 columns in file 
        double[][] drywet = new double[numberOfRows][numberOfColumns];
        double[][] wetwet = new double[numberOfRows][numberOfColumns];
        populateTransitionProbabilitiesArrays(drywet, wetwet, numberOfRows);
        double longitute = Double.parseDouble(args[0]);
        double latitude  = Double.parseDouble(args[1]);
        int    month     = Integer.parseInt(args[2]);
        int[] forecast = oneMonthGenerator(longitute, latitude, month, drywet, wetwet);
        int drySpell = lengthOfLongestSpell(forecast, DRY);
        int wetSpell = lengthOfLongestSpell(forecast, WET);
        StdOut.println("There are " + forecast.length + " days in the forecast for month " + month);
        StdOut.println(drySpell + " days of dry spell.");
        for ( int i = 0; i < forecast.length; i++ ) {
            String weather = (forecast[i] == WET) ? "Wet" : "Dry";  
            StdOut.println("Day " + (i+1) + " is forecasted to be " + weather);
        }
    }
}