public class test {
    
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
