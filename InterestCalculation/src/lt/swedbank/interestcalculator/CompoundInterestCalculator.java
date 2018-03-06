package lt.swedbank.interestcalculator;
import java.util.Scanner;

/**
 * Created by p998feq on 2018.03.05.
 */

public class CompoundInterestCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        float originalAmount = getOriginalAmount();

/*        do {
            System.out.print("Amount: ");
            originalAmount = getFloat();

            if(originalAmount <= 0)
                System.out.println("Invalid input! Try again!");
        }while (originalAmount <= 0);*/

        int periodLength;
        do {
            System.out.print("Period length (years): ");
            periodLength = getInt();
            if(periodLength <= 0)
                System.out.println("Invalid input! Try again!");


        }while (periodLength <= 0);

        char compoundFrequency;
        do {
            System.out.print("Compound  frequency: ");
            compoundFrequency = scanner.next().charAt(0);

            //Not the best idea to do it like this
            //I would suggest integrating "compoundFrequency" validation in switch statement itself.
            //For example: you could throw an exception in "default" clause and handle it here, inside the loop
            if(compoundFrequency == 'D' || compoundFrequency == 'W' || compoundFrequency == 'M' || compoundFrequency == 'Q' || compoundFrequency == 'H' ||
                    compoundFrequency == 'Y' )
                break;
            else
                System.out.println("Invalid input! Try again!");
        }while (true);

        int interestRateCount = -1;
        double[] interestRateArray;
        interestRateArray = new double[10];     // kaip saugot cikle nezinant kiek galima interest rate ivest?
        float interestRate;
        do {
            System.out.print("Interest rate (%): ");
            interestRate = getFloat();
            ++interestRateCount;
            interestRateArray[interestRateCount] = interestRate;
            if(interestRate == 0)
                break;
            else if(interestRate <= 0 || interestRate > 100){
                System.out.println("Invalid input! Try again!");
                --interestRateCount;
            }
        }while (true);

        double[][] tempIntermediateInterestAmounts;
        tempIntermediateInterestAmounts = new double[interestRateCount][(int)periodLength * getCompoundFrequency(compoundFrequency)];
        double[][] intermediateInterestAmounts;
        intermediateInterestAmounts = new double[interestRateCount][(int)periodLength * getCompoundFrequency(compoundFrequency)];

        int pos;
        for (int j = 0; j < interestRateCount; j++){
            pos = 0;
            for (int i = 1; i <= periodLength; i++) {
                tempIntermediateInterestAmounts[j][pos] = getInterestAmount(originalAmount, interestRateArray[j], i, getCompoundFrequency(compoundFrequency));

                if (pos > 0)
                    intermediateInterestAmounts[j][pos] = tempIntermediateInterestAmounts[j][pos] - tempIntermediateInterestAmounts[j][pos - 1];
                else
                    intermediateInterestAmounts[j][pos] = tempIntermediateInterestAmounts[j][pos];
                pos++;
            }
        }

        for (double[] row : intermediateInterestAmounts) {
            for (double number : row) {
                System.out.print(String.format("%.2f", number) + " ");
            }
            System.out.println();
        }
    }

    private static double getInterestAmount(double originalAmount, double interestRate, double periodLength, int compoundFrequency) {
        return originalAmount * Math.pow((1 + (interestRate / 100) / compoundFrequency), periodLength * compoundFrequency) - originalAmount;
    }

    private static int getCompoundFrequency(char compoundFrequencyString) {
        switch (compoundFrequencyString) {
            case 'D':
                return 365;
            case 'W':
                return 52;
            case 'M':
                return 12;
            case 'Q':
                return 4;
            case 'H':
                return 2;
            case 'Y':
                return 1;
            default:
                return 1;
        }
    }

    //This is seems to be a "half-baked" solution (sorry for the expression :) )
    //You currently have loop inside a loop, which is actually not really necessary since same error message should be show in both case (when input is not int and value is not within a valid range)
    private static int getInt() {
        while(true){
            try {
                return Integer.parseInt(new Scanner(System.in).next());
            } catch(NumberFormatException ne) {
                System.out.println("Invalid input! Try again!");
            }
        }
    }

    private static Float getFloat() {
        while(true){
            try {
                return Float.parseFloat(new Scanner(System.in).next());
            } catch(NumberFormatException ne) {
                System.out.println("Invalid input! Try again!");
            }
        }
    }

    //Here is somewhat a more compact way of getting valid "originalAmount"
    private static float getOriginalAmount() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("Amount: ");

            try {
                float originalAmount = Float.parseFloat(scanner.next());

                if (originalAmount > 0) {
                    return originalAmount;
                }
            } catch (NumberFormatException ignored){}

            System.out.println("Invalid input! Try again!");
        }
    }
}
