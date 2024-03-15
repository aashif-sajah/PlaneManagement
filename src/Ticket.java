import java.io.*;

public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    // Constructor
    public Ticket(int row, int seat, double price, Person person) {
        this.row = (char) (row + 65);
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getter and Setter methods
    public int getRow() {
        return Character.toUpperCase(row) - 64;
    }

    public void setRow(int row) {
        this.row = (char) (row + 65);
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to print ticket information
    public void printTicketInfo() {
        System.out.println("\nRow: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + price + "\n");
        if (person != null) {
            System.out.println("\nPerson Information:\n");
            person.displayPersonInfo();
        }
    }

    public void save() {
        String directoryPath = "../tickets/";
        String fileName = String.format("%c%d.txt", (char) row, seat);
        String filePath = directoryPath + fileName;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("Ticket Information:\n");
            writer.write("Row: " + (char) row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: " + price + "\n");
            writer.write("\n");
            writer.write("Passenger Information:\n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSureName() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
            System.out.println("\nTicket Information Saved Yo " + fileName);
            writer.close();
        } catch (Exception e) {
            System.out.println("File does not Exist...");
        }

    }
}
