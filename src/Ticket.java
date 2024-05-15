public class Ticket {
    private String row;
    private int seat;
    private double price;
    private Person person;


    public Ticket(String row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
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

    public void print_information(){
        System.out.println("Row :"+this.row);
        System.out.println("Seat :"+this.seat);
        System.out.println("Price :"+this.price);
        System.out.println();
        this.person.print_information();

    }
}
