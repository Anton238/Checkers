package checks;

import coordinates.Coordinate;
import logger.ApplicationLogger;

import java.util.Scanner;

public class EnterData {
    /**
     * Convert entered cell coordinate to the coordinate that is used in the code
     * (a8 to 1 1, a7 to 2 1, b8 to 1 2, etc.)
     */
    public Coordinate enterCoordinates() {
        Scanner input = new Scanner(System.in);
        String stringInputNumber;
        char[] charArray;
        int x = 0;
        int y = 0;
        boolean checkOnException;

        do {
            stringInputNumber = input.nextLine();

            try {
                charArray = stringInputNumber.toCharArray();

                //take the first part of the coordinates and convert(a to 1, b to 2, etc.)
                x = (charArray[0] - (int) 'a') + 1;
                //take the second part of coordinates and convert it(1 to 8, 2 to 7, etc.)
                y = Character.getNumericValue(charArray[1]);
                checkOnException = false;
            } catch (Exception ex) {
                checkOnException = true;
            }

            //check on incorrect input
            if (checkOnMinMax(x, y) || checkOnException)
                ApplicationLogger.logger.info("\nIncorrect input. " +
                        "Format should be letter-number. Enter checker coordinate again: ");

            /* If the input is incorrect there will be a cyclic request
             to enter a coordinate until the input is correct */
        } while (checkOnMinMax(x, y) || checkOnException);

        return new Coordinate(x, y);
    }

    private boolean checkOnMinMax(int x, int y) {
        int min = 1;//minimum possible coordinate
        int max = 8;//maximum possible coordinate

        return x < min || x > max || y < min || y > max;
    }
}
