import java.util.Scanner;

public class PlaneManagement {
    private static Ticket[] tickets = new Ticket[100];
    private static int ticketCount = 0;

    public static void main(String[] args) throws Exception {

        // Creatng a scanner object to scan user input
        Scanner scan = new Scanner(System.in);

        // Creating the plan seats in a 2D array by calling the planSeats method
        char[][] seatStructure = planSeats();

        // Printing the User Menue
        System.out.println("\n\t    Welcome to the Plane Management application");
        userMenue();

        // Getting the user input and validate it by userInputValidator method
        int userInput = userInputValidator(scan);

        // By handleUserInput method handle the user input
        handleUserInput(userInput, seatStructure, scan);
        scan.close();
    }

    private static void handleUserInput(int userInput, char[][] seatStructure, Scanner scan) {

        // With the help of Switch handled the user input to call relevet methods
        switch (userInput) {
            case 0:
                System.out.println("--------------------------------------------------------------");
                System.out.println("\tThank you for using  Plane Management application");
                System.out.println("--------------------------------------------------------------");
                System.exit(0);
                break;
            case 1:
                buySeat(scan, seatStructure);
                break;
            case 2:
                cencelSeat(scan, seatStructure);
                break;

            case 3:
                findFirstAvailable(seatStructure, scan);
                break;

            case 4:
                showSeatingPlane(seatStructure, scan);
                break;

            case 5:
                printTicketsInfo(tickets, scan, seatStructure);
                break;

            case 6:
                searchTicket(scan, seatStructure);
                break;

            default:
                System.out.println("Please Enter valid option");
                userMenue(); // Display the menu again
                userInput = userInputValidator(scan); // Get new user input
                handleUserInput(userInput, seatStructure, scan);
                break;

        }

    }

    private static void userMenue() {

        // useds print statemetns to print the user menue in console
        System.out.println("\n*****************************************************************");
        System.out.println("*\t\t\t\tMENUE\t\t\t\t*");
        System.out.println("*****************************************************************\n");
        System.out.println("""
                        1) Buy a seat.
                        2) Cencel a seat.
                        3) Find first available seat.
                        4) Show seating plan.
                        5) Print ticket information and total sale.
                        6) Search ticket.
                        0) Quit

                """);
        System.out.println("*****************************************************************");
        System.out.print("Please Select An Option : ");

    }

    private static int userInputValidator(Scanner scan) {

        // try : catch used to handle number format exeption and other possible erros
        try {
            int userInput = scan.nextInt();
            scan.nextLine();
            if (userInput >= 0 && userInput <= 6) {
                return userInput;
            } else {
                System.out.println("\n---------------------------------------------------------------");
                System.out.println("\tPlease Enter intiger values shown in the Menue.");
                System.out.println("---------------------------------------------------------------\n");
                userMenue();
                return userInputValidator(scan);
            }

        } catch (Exception e) {
            System.out.println("\n---------------------------------------------");
            System.out.println("Please Enter intiger values shown in the Menue.");
            System.out.println("---------------------------------------------\n");
            scan.nextLine();
            userMenue();
            return userInputValidator(scan);
        }
    }

    private static void buySeat(Scanner scan, char[][] seatStructure) {

        // user to enter the desired seat number

        System.out.print("\nEnter desired Seat Number (eg:A3,B6):");
        String seatIndex = scan.nextLine();

        try {

            // Extract the row and column from the user input

            int column = Integer.parseInt(seatIndex.substring(1));
            int row = Character.toUpperCase(seatIndex.charAt(0)) - 65;
            // System.out.println(row + " " + column);

            // Checking for valid seat Index
            if (row < 0 || row >= seatStructure.length || column < 0 || column >= seatStructure[0].length) {
                System.out.println("\n---------------------------------------------");
                System.out.println("\tPlease Enter valid Seat number");
                System.out.println("---------------------------------------------\n");
                buySeat(scan, seatStructure); // recuring the same method
            }

            // checking for seat availability by this method

            if (isSeatAvailable(seatStructure, row, column)) {

                seatStructure[row][column] = '1';
                Person person = createPerson(scan);

                int seatPrice = seatPriceChecker(column); // gettting seat price from seat price method
                Ticket ticket = new Ticket(row, column, seatPrice, person);
                // System.out.println(tickets.length);

                tickets[ticketCount++] = ticket;
                System.out.println("\n--------------------------------------------------");
                ticket.save();

                /*
                 * These codes used to debug
                 * System.out.println("Tickets:");
                 *
                 * testing Tickets Array
                 *
                 * for (int i = 0; i < tickets.length; i++) {
                 * if (tickets[i] == null) {
                 * break;
                 * }
                 * tickets[i].printTicketInfo();
                 * }
                 *
                 * ticket.printTicketInfo();
                 * System.out.println(ticket.getRow());
                 * ticket.setRow(0);
                 * System.out.println(ticket.getRow());
                 *
                 */

                System.out.printf("\tSeat %c%d has Succesfully reserved ", (char) (row + 65), column);
                System.out.println("\n--------------------------------------------------\n");

            } else {
                System.out.println("\n---------------------------------------------");
                System.out.printf("\tSeat %c%d has Already reserved ", (char) (row + 65), column);
                System.out.println("\n---------------------------------------------\n");
                // calling the same method when seat is already booked
                buySeat(scan, seatStructure);
            }
        } catch (NumberFormatException e) {
            // handling numberFormat error by recalling the same method
            System.out.println("\n---------------------------------------------------------------------");
            System.out.println("\tPlease Enter Disired Seat number in the given formet.");
            System.out.println("---------------------------------------------------------------------\n");
            buySeat(scan, seatStructure);
        }
        // calling the main menuw after user done with buying seat
        userMenue();
        int userInput = userInputValidator(scan);
        handleUserInput(userInput, seatStructure, scan);

    }

    private static boolean isSeatAvailable(char[][] seatStructure, int row, int column) {

        // A simple if condition to check the availabiity of seats
        if (seatStructure[row][column] == '0') {
            return true;
        } else {
            return false;
        }

    }

    private static char[][] planSeats() {

        // creting the plane seats
        char[][] seatStructure = new char[4][15];

        // iterate through the 2D array and create the seats and initialize em to 0
        for (int i = 0; i < seatStructure.length; i++) {
            // adding row to 0th index
            seatStructure[i][0] = (char) ('A' + i);
            for (int j = 1; j < seatStructure[i].length; j++) {
                // row b and c have only 12 seats handling that by a if condition
                if (i == 1 || i == 2) {
                    for (int k = 1; k <= 12; k++)
                        seatStructure[i][k] = (char) '0';
                } else {
                    seatStructure[i][j] = (char) '0';

                }

            }
        }
        return seatStructure;

    }

    /*
     * private static void seatViewer(char[][] seatView) {
     *
     * // to print the number of columns
     *
     * for (int i = 0; i < seatView[1].length; i++) {
     * if (i == 0) {
     * System.out.print("   ");
     * } else {
     * System.out.printf("%-3d", i);
     * }
     *
     * }
     * System.out.println();
     *
     * for (char[] row : seatView) {
     * for (char column : row) {
     * System.out.printf("%-3c", column);
     * }
     * System.out.println();
     * }
     *
     * }
     */
    private static void showSeatingPlane(char[][] seatStructure, Scanner scan) {
        System.out.println("\n---------------------------------------------------\n");
        // to print the number of columns
        for (int i = 0; i < seatStructure[1].length; i++) {
            if (i == 0) {
                System.out.print("   ");// In 1st row theres a gap to get that gap
            } else {
                System.out.printf("%-3d", i);
            }
        }
        System.out.println();
        // By iterating through the 2D array printing the seat
        for (int i = 0; i < seatStructure.length; i++) {
            System.out.printf("%-3c", (char) ('A' + i)); // Print row label
            for (int j = 0; j < seatStructure[i].length; j++) {
                if (seatStructure[i][j] == '0') {
                    System.out.printf("%-3c", 'O');
                } else if (seatStructure[i][j] == '1') {
                    System.out.printf("%-3c", 'X');
                }
            }
            System.out.println();
        }
        System.out.println("\n---------------------------------------------------\n");

        // Display the menu again
        userMenue();
        int userInput = userInputValidator(scan);
        handleUserInput(userInput, seatStructure, scan);

    }

    private static void cencelSeat(Scanner scan, char[][] seatStructure) {

        // getting user input
        System.out.print("\nEnter The Seat Number To Cencel The Seat (eg:A3,B6):");
        String seatIndex = scan.nextLine();

        // used try:catch to handle user input
        try {
            int column = Integer.parseInt(seatIndex.substring(1));

            /*
             * i have substract 65 cz in our array index start at 0 but when we cast char
             * "A" to int
             * it become 65 in value to make it zero the 65 substracted
             */

            int row = Character.toUpperCase(seatIndex.charAt(0)) - 65;

            // a simple if conditon to check the validity
            if (row < 0 || row >= seatStructure.length || column < 0 || column >= seatStructure[0].length) {
                System.out.println("\n---------------------------------------------");
                System.out.println("\tPlease Enter valid Seat number");
                System.out.println("---------------------------------------------\n");
                cencelSeat(scan, seatStructure); // recursing the same method if user input is wrong!!
            }
            // isSeatavailable method return False when seat is available so here ! (not) is
            // used
            if (!isSeatAvailable(seatStructure, row, column)) {

                // updaating the 2d array
                seatStructure[row][column] = '0';
                tickets = cancelTicket(row, column);
                // testing weather ticket has removed or not
                /* for (int i = 0; i < tickets.length; i++) {
                    if (tickets[i] == null) {
                        break;
                    }
                    tickets[i].printTicketInfo();
                } */
                System.out.println("\n---------------------------------------------");
                System.out.printf("\tSeat %c%d has Successfully Cencled.", (char) (row + 65), column);
                System.out.println("\n---------------------------------------------\n");
            } else {
                System.out.println("\n-----------------------------------------------------");
                System.out.printf("\tSeat %c%d is already free to be booked.%n", (char) (row + 65), column);
                System.out.println("-----------------------------------------------------\n");
            }

        } catch (NumberFormatException e) {
            System.out.println("\n-------------------------------------------------------------");
            System.out.println("\tEnter valid seat number in the given Format.");
            System.out.println("-------------------------------------------------------------\n");
            cencelSeat(scan, seatStructure); // recursively callig the same method to handle user input
        }

        // calling the mmain menue
        userMenue();
        int userInput = userInputValidator(scan);
        handleUserInput(userInput, seatStructure, scan);

    }

    private static Ticket[] cancelTicket(int row, int column) {
        // creating a new ticket array to clone the updated array
        Ticket[] updatedTicketArray = new Ticket[tickets.length - 1];

        int ticketIndex = seatIndexFinder(row, column); // Find the index of the ticket to be canceled

        // If ticket not found, return the original ticket array
        if (ticketIndex == -1) {
            System.out.println("\n---------------------------------------------");
            System.out.println("\tTicket not found in the ticket array");
            System.out.println("---------------------------------------------\n");
            return tickets;
        }

        // Shift tickets in the array to remove the canceled ticket
        for (int i = 0, k = 0; i < ticketCount; i++) {
            if (i == ticketIndex) {
                continue; // Skip the canceled ticket
            }
            updatedTicketArray[k++] = tickets[i];
        }

        // Decrease ticket count
        ticketCount--;

        return updatedTicketArray;
    }

    private static int seatIndexFinder(int row, int column) {
        // this methods finds the ticket index by iterating thorough Tickets Array
        int ticketIndex = -1;
        for (int i = 0; i < ticketCount; i++) {
            // System.out.println("Row :"+row);
            // System.out.println("column :"+column);
            // System.out.println(tickets[i].getRow());
            // System.out.println(tickets[i].getSeat());

            // a simple if condition to check the seat index
            if (tickets[i].getRow() == row + 1 && tickets[i].getSeat() == column) {
                ticketIndex = i;
                break;
            }
        }
        // finnally returning the index
        return ticketIndex;
    }

    private static void findFirstAvailable(char[][] seatStructure, Scanner scan) {

        // initailizing a boolen for initial return
        boolean found = false;

        // looping throung 2d array to find the availability of seat with nested for
        // loop
        for (int row = 0; row < seatStructure.length; row++) {

            // this inner loop check the column number one by one with the relevent row
            for (int column = 0; column < seatStructure[row].length; column++) {
                if (seatStructure[row][column] == '0') {
                    found = true;
                    System.out.println("\n---------------------------------------------");
                    System.out.printf("\tFist available seat is : %c%d%n", (char) (row + 65), column);
                    System.out.println("---------------------------------------------\n");
                    break;
                }
            }
            if (found) {
                break;
            }

        }
        // handling if all seats are reserved condition
        if (!found) {
            System.out.println("\t---------------------------------------------------");
            System.out.println("\tAll seats have been reserved!");
            System.out.println("---------------------------------------------------\n");
        }
        // calling the main menue
        userMenue();
        int userInput = userInputValidator(scan);
        handleUserInput(userInput, seatStructure, scan);

    }

    private static int seatPriceChecker(int column) {

        // with a simple if condition tree i have return the releven price
        if (column <= 5) {
            return 200;
        } else if (column > 5 && column <= 9) {
            return 150;
        } else {
            return 180;
        }
    }

    private static Person createPerson(Scanner scan) {
        String name = "";
        String surname = "";
        String email = "";

        try {
            // Getting details from user :)

            System.out.print("\nEnter Name: ");
            name = scan.nextLine();

            System.out.print("Enter Surname: ");
            surname = scan.nextLine();

            System.out.print("Enter Email:");
            email = scan.nextLine();
        } catch (Exception e) {

            System.out.println("An error occurred while inputting person information. Please try again.");
            // Cleaing the scanner
            scan.nextLine();
            return createPerson(scan); // Recursively calling
        }
        // After successfully getting user input returing it as a class object
        return new Person(name, surname, email);
    }

    private static void printTicketsInfo(Ticket[] tickets, Scanner scan, char[][] seatStructure) {
        // creating a variable to save total amount
        double totalAmountOfTicketsSold = 0;

        // looping through tickets array to get the available ticket prices
        if (tickets[0] == null) {
            System.out.println("\t---------------------------------------------------");
            System.out.println("\tNo tickets sold during this session");
            System.out.println("---------------------------------------------------\n");
        } else {

            for (int i = 0; i < tickets.length; i++) {
                if (tickets[i] == null) {
                    break;
                }
                totalAmountOfTicketsSold += tickets[i].getPrice();
                // after finding the ticket index printing the ticket infor using,
                // printTicketInfo method in ticket class
                tickets[i].printTicketInfo();
            }

            System.out.println("Total amount of tickets sold is :" + totalAmountOfTicketsSold);
            System.out.println("---------------------------------------------------\n");

        }

        // after pring the inportmation
        userMenue();
        int userInput = userInputValidator(scan);
        handleUserInput(userInput, seatStructure, scan);

    }

    private static void searchTicket(Scanner scan, char[][] seatStructure) {
        System.out.print("\nEnter the Seat Number To Search The Ticket (eg:A3,B6):");
        String seatIndex = scan.nextLine();

        try {
            // extracting userinput
            int column = Integer.parseInt(seatIndex.substring(1));
            int row = Character.toUpperCase(seatIndex.charAt(0)) - 65;

            // checking for validity input
            if (row < 0 || row >= seatStructure.length || column < 0 || column >= seatStructure[0].length) {
                System.out.println("\n---------------------------------------------");
                System.out.println("\tPlease Enter valid Seat number");
                System.out.println("---------------------------------------------\n");
                searchTicket(scan, seatStructure);
            }
            /*
             * System.out.println(isSeatAvailable(seatStructure, row, column)); used for
             * debug
             */
            if (!isSeatAvailable(seatStructure, row, column)) {
                // getting seat index from seatIndexFinder method
                int ticketSeatIndex = seatIndexFinder(row, column);
                // printing tickect information using printTicketinfor method in tickect class
                tickets[ticketSeatIndex].printTicketInfo();

            } else {
                System.out.println("\n-----------------------------------------------------");
                System.out.printf("\tSeat %c%d is free to be booked.%n", (char) (row + 65), column);
                System.out.println("-----------------------------------------------------\n");
            }

        } catch (NumberFormatException e) {
            System.out.println("\n---------------------------------------------");
            System.out.println("\tEnter valid seat number in the given Format.");
            System.out.println("---------------------------------------------\n");
            cencelSeat(scan, seatStructure);
        }
        // calling to main menue
        userMenue();
        int userInput = userInputValidator(scan);
        handleUserInput(userInput, seatStructure, scan);

    }
}
