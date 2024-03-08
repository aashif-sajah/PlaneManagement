
public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    // Constructor
    public Ticket(int row, int seat, double price, Person person) {
        this.row = (char)(row + 65);
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getter and Setter methods
    public int getRow() {
        return Character.toUpperCase(row) - 64;
    }

    public void setRow(int row) {
        this.row = (char)(row + 65);
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
}
