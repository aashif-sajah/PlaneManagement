import java.util.Scanner;

public class PlaneManagement {
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
            if (isSeatAvailable(seatStructure, row, column)) {
                System.out.println("Your Seat been reserved");

            } else {
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

        if (row < 0 || row >= seatStructure.length || column < 0 || column >= seatStructure[0].length) {
            System.out.println("\nPlease Enter valid Seat number\n");
            return false;
        }

        if (seatStructure[row][column] == '0') {
            seatStructure[row][column] = '1';
            return true;
        } else {
            System.out.println("\nSeat is not available !\n");
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

}
