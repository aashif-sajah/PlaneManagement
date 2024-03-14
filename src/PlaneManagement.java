
import java.util.Scanner;

public class PlaneManagement {
    private static Ticket[] tickets = new Ticket[100];
    private static int ticketCount = 0;

    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        char[][] seatStructure = planSeats();
        userMenue();
        int userInput = userInputValidator(scan);
        handleUserInput(userInput, seatStructure, scan);
        scan.close();
    }

    private static void handleUserInput(int userInput, char[][] seatStructure, Scanner scan) {
        switch (userInput) {
            case 0:
                System.out.println("Thank you for using  Plane Management application");
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

            default:
                System.out.println("Please Enter valid option");
                userMenue(); // Display the menu again
                userInput = userInputValidator(scan); // Get new user input
                handleUserInput(userInput, seatStructure, scan);
                break;

        }

    }

    public static void userMenue() {
        System.out.println("\n\t\t\t\t\tWelcome to the Plane Management application\t\t\t\n");
        System.out.println("\t\t\t*****************************************************************");
        System.out.println("\t\t\t*\t\t\t\tMENUE\t\t\t\t*\t\t\t");
        System.out.println("\t\t\t*****************************************************************\n");
        System.out.println("""
                        \t\t\t1) Buy a seat.
                        \t\t\t2) Cencel a seat.
                        \t\t\t3) Find first available seat.
                        \t\t\t4) Show seating plan.
                        \t\t\t5) Print ticket information and total sale.
                        \t\t\t6) Search ticket.
                        \t\t\t0) Quit

                """);
        System.out.println("\t\t\t*****************************************************************");
        System.out.print("\t\t\tPlease Select An Option : ");

    }

    public static int userInputValidator(Scanner scan) {

        try {
            int userInput = scan.nextInt();
            scan.nextLine();
            if (userInput >= 0 && userInput <= 6) {

                return userInput;
            } else {

                System.out.println("\n\t\t\tPlease Enter intiger values shown in the Menue.\n");
                userMenue();

                return userInputValidator(scan);
            }

        } catch (Exception e) {
            System.out.println("\n\t\t\tPlease Enter intiger values shown in the Menue.\n");
            scan.nextLine();
            userMenue();
            return userInputValidator(scan);
        }
    }

    public static void buySeat(Scanner scan, char[][] seatStructure) {

        seatViewer(seatStructure);
        System.out.print("\nEnter disired Seat nuber (eg:A3,B6):");
        String seatIndex = scan.nextLine();

        try {

            int column = Integer.parseInt(seatIndex.substring(1));
            int row = Character.toUpperCase(seatIndex.charAt(0)) - 65;
            // System.out.println(row + " " + column);

            // Checking for valid seat Index
            if (row < 0 || row >= seatStructure.length || column < 0 || column >= seatStructure[0].length) {
                System.out.println("\nPlease Enter valid Seat number\n");
                buySeat(scan, seatStructure);
            }

            if (isSeatAvailable(seatStructure, row, column)) {

                seatStructure[row][column] = '1';
                Person person = createPerson(scan);

                int seatPrice = seatPriceChecker(column);
                Ticket ticket = new Ticket(row, column, seatPrice, person);
                // System.out.println(tickets.length);

                tickets[ticketCount++] = ticket;
                System.out.println("Tickets:");

                // testing Tickets Array
                for (int i = 0; i < tickets.length; i++) {
                    if (tickets[i] == null) {
                        break;
                    }
                    tickets[i].printTicketInfo();
                }

                // ticket.printTicketInfo();
                // System.out.println(ticket.getRow());
                // ticket.setRow(0);
                // System.out.println(ticket.getRow());

                System.out.printf("Seat %c%d has Succesfully reserved %n", (char) (row + 65), column);

            } else {
                System.out.printf("Seat %c%d has Already reserved %n", (char) (row + 65), column);
                buySeat(scan, seatStructure);
            }
        } catch (NumberFormatException e) {
            System.out.println("\nPlease Enter Disired Seat number in the given formet.\n");
            buySeat(scan, seatStructure);
        }
        userMenue();
        int userInput = userInputValidator(scan);
        handleUserInput(userInput, seatStructure, scan);

    }

    public static boolean isSeatAvailable(char[][] seatStructure, int row, int column) {

        if (seatStructure[row][column] == '0') {
            return true;
        } else {
            return false;
        }

    }

    public static char[][] planSeats() {

        char[][] seatStructure = new char[4][15];

        for (int i = 0; i < seatStructure.length; i++) {
            seatStructure[i][0] = (char) ('A' + i);
            for (int j = 1; j < seatStructure[i].length; j++) {
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

    private static void seatViewer(char[][] seatView) {

        // to print the number of columns

        for (int i = 0; i < seatView[1].length; i++) {
            if (i == 0) {
                System.out.print("   ");
            } else {
                System.out.printf("%-3d", i);
            }

        }
        System.out.println();

        for (char[] row : seatView) {
            for (char column : row) {
                System.out.printf("%-3c", column);
            }
            System.out.println();
        }

    }

    public static void showSeatingPlane(char[][] seatStructure, Scanner scan) {
        // to print the number of columns

        for (int i = 0; i < seatStructure[1].length; i++) {
            if (i == 0) {
                System.out.print("   ");
            } else {
                System.out.printf("%-3d", i);
            }
        }
        System.out.println();
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

        userMenue(); // Display the menu again
        int userInput = userInputValidator(scan); // Get new user input
        handleUserInput(userInput, seatStructure, scan);

    }

    public static void cencelSeat(Scanner scan, char[][] seatStructure) {
        System.out.print("\nEnter the Seat number to cencel the seat (eg:A3,B6):");
        String seatIndex = scan.nextLine();

        try {
            int column = Integer.parseInt(seatIndex.substring(1));

            /*
             * i have substract 65 cz in our array index start at 0 but when we cast char
             * "A" to int
             * it become 65 in value to make it zero the 65 substracted
             */

            int row = Character.toUpperCase(seatIndex.charAt(0)) - 65;
            if (row < 0 || row >= seatStructure.length || column < 0 || column >= seatStructure[0].length) {
                System.out.println("\nPlease Enter valid Seat number\n");
                cencelSeat(scan, seatStructure);

            }
            if (!isSeatAvailable(seatStructure, row, column)) {
                seatStructure[row][column] = '0';
                tickets = cancelTicket(row, column);
                // testing weather ticket has removed or not
                for (int i = 0; i < tickets.length; i++) {
                    if (tickets[i] == null) {
                        break;
                    }
                    tickets[i].printTicketInfo();
                }

                System.out.printf("Seat %c%d has Successfully Cencled.%n", (char) (row + 65), column);
            } else {
                System.out.printf("Seat %c%d is already free to be booked.%n", (char) (row + 65), column);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nEnter valid seat number in the given Format\n");
            cencelSeat(scan, seatStructure);
        }
        userMenue();
        int userInput = userInputValidator(scan);
        handleUserInput(userInput, seatStructure, scan);

    }

    public static Ticket[] cancelTicket(int row, int column) {
        Ticket[] updatedTicketArray = new Ticket[tickets.length - 1];
        int ticketIndex = -1;

        // Find the index of the ticket to be canceled
        for (int i = 0; i < ticketCount; i++) {
            // System.out.println("Row :"+row);
            // System.out.println("column :"+column);
            // System.out.println(tickets[i].getRow());
            // System.out.println(tickets[i].getSeat());
            if (tickets[i].getRow() == row + 1 && tickets[i].getSeat() == column) {
                ticketIndex = i;
                break;
            }
        }

        // If ticket not found, return the original ticket array
        if (ticketIndex == -1) {
            System.out.println("Ticket not found in the ticket array");
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

    public static void findFirstAvailable(char[][] seatStructure, Scanner scan) {
        boolean found = false;
        for (int row = 0; row < seatStructure.length; row++) {
            for (int column = 0; column < seatStructure[row].length; column++) {
                if (seatStructure[row][column] == '0') {
                    found = true;
                    System.out.printf("%nFist available seat is :%c%d%n", (char) (row + 65), column);
                    break;
                }
            }
            if (found) {
                break;
            }

        }
        if (!found) {
            System.out.println("All seats have been reserved!");
        }

        userMenue();
        int userInput = userInputValidator(scan);
        handleUserInput(userInput, seatStructure, scan);

    }

    public static int seatPriceChecker(int column) {
        if (column <= 5) {
            return 200;
        } else if (column > 5 && column <= 9) {
            return 150;
        } else {
            return 180;
        }
    }

    public static Person createPerson(Scanner scan) {
        String name = "";
        String surname = "";
        String email = "";

        try {
            System.out.print("Enter name: ");
            name = scan.nextLine();

            System.out.print("Enter surname: ");
            surname = scan.nextLine();

            System.out.print("Enter email: ");
            email = scan.nextLine();
        } catch (Exception e) {
            System.out.println("An error occurred while inputting person information. Please try again.");
            // Clear the scanner buffer
            scan.nextLine();
            return createPerson(scan); // Recursively call createPerson method to retry input
        }

        return new Person(name, surname, email);
    }

    private static void printTicketsInfo(Ticket[] tickets, Scanner scan, char[][] seatStructure) {

        double totalAmountOfTicketsSold = 0;

        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i] == null) {
                break;
            }
            totalAmountOfTicketsSold += tickets[i].getPrice();
            tickets[i].printTicketInfo();
        }

        System.out.println("Total amount of tickets sold is :" + totalAmountOfTicketsSold);
        userMenue();
        int userInput = userInputValidator(scan);
        handleUserInput(userInput, seatStructure, scan);

    }
}
