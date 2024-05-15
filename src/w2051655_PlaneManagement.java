
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class w2051655_PlaneManagement {

    static String row_a[] = new String[14];
    static String row_b[] = new String[12];
    static String row_c[] = new String[12];
    static String row_d[] = new String[14];

    static Ticket[] tickets = new Ticket[52];

    public static void main(String[] args) {


        for (int i = 0; i < row_a.length; i++) {
            row_a[i] = "0";
            row_d[i] = "0";
        }
        for (int i = 0; i < row_b.length; i++) {
            row_b[i] = "0";
            row_c[i] = "0";
        }

        System.out.println("Welcome to the Plane Management application");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            int input = 0;

            System.out.println("********************************************");
            System.out.println("*           MENU OPTION                    *");
            System.out.println("********************************************\n");

            System.out.println("1) Buy a Seat ");
            System.out.println("2) Cancel a Seat ");
            System.out.println("3) Find First available Seat ");
            System.out.println("4) Show Seating Plan ");
            System.out.println("5) Print Ticket Information and Total Sales");
            System.out.println("6) Search Ticket ");
            System.out.println("0) Quit \n");

            System.out.println("********************************************\n");
            System.out.println("Please Select an Option :");

            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please Enter a Number (0-6)");
                continue;
            }

            switch (input) {
                case 1:
                    buy_ticket();
                    break;
                case 2:
                    cancel_ticket();
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    search_ticket();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Please Select a valid Option ");
                    continue;
            }
        }
    }

    //Buy Ticket
    public static void buy_ticket() {

        while (true) {

            Scanner scanner = new Scanner(System.in);
            int seat_number = 0;
            int max_seat_count = 0;
            String[] arr;
            double ticket_price = 0;

            System.out.println("Please Enter Seat Row (A - D) :");
            String seat_row = scanner.next().toUpperCase();

            switch (seat_row) {
                case "A":
                    arr = row_a;
                    max_seat_count = 14;
                    break;
                case "B":
                    arr = row_b;
                    max_seat_count = 12;
                    break;
                case "C":
                    arr = row_c;
                    max_seat_count = 12;
                    break;
                case "D":
                    arr = row_d;
                    max_seat_count = 14;
                    break;
                default:
                    System.out.println("Please Enter Valid Row");
                    continue;
            }

            System.out.println("Please Enter Seat Number :");
            try {
                seat_number = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Valid Number is required");
                continue;
            }

            //Checking Seat Range
            if (max_seat_count < seat_number || seat_number <= 0) {
                System.out.println(seat_row + " Row have 1 to " + max_seat_count + " seats Only");
                continue;
            }

            //creating a ticket
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equalsIgnoreCase("0") && i == seat_number - 1) {

                    System.out.println("Please Enter Name :");
                    String name = scanner.next();
                    System.out.println("Please Enter Surname :");
                    String surname = scanner.next();
                    System.out.println("Please Enter Email :");
                    String email = scanner.next();

                    Person person = new Person(name, surname, email);
                    ticket_price = check_ticket_price(seat_number);

                    Ticket ticket = new Ticket(seat_row, seat_number, ticket_price, person);
                    if (tickets[tickets.length - 1] == null) {
                        for (int j = 0; j < tickets.length; j++) {
                            if (tickets[j] == null) {
                                tickets[j] = ticket;
                                save_file(ticket);
                                break;
                            }
                        }
                    }
                    arr[i] = "1";
                    System.out.println("Seat has been revered Successfully...!\n");
                    return;
                }
            }
            System.out.println("Sorry Seat is unavailable");
            continue;
        }

    }

    //Cancel Ticket
    public static void cancel_ticket() {

        while (true) {
            String[] arr;
            String seat_row;
            int seat_number;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please Enter Row :");
            seat_row = scanner.next().toUpperCase();
            switch (seat_row) {
                case "A":
                    arr = row_a;
                    break;
                case "B":
                    arr = row_b;
                    break;
                case "C":
                    arr = row_c;
                    break;
                case "D":
                    arr = row_d;
                    break;
                default:
                    System.out.println("Please Enter Valid Row\n");
                    continue;
            }
            System.out.println("Please Enter Seat Number :");
            try {
                seat_number = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Integer Number Is Require for Seat Number");
                continue;
            }

            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("1")) {
                    if (i == seat_number - 1) {

                        for (int j = 0; j < tickets.length; j++) {
                            if (tickets[j] != null) {
                                if (tickets[j].getRow().equals(seat_row) && tickets[j].getSeat() == seat_number) {
                                    tickets[j] = null;
                                    break;
                                }
                            }
                        }
                        arr[i] = "0";
                        System.out.println("Booking has been Cancelled Successfully..!");
                        return;
                    }
                }
            }
            System.out.println("Booking Can not be found...!"+"\n");
            return;

        }

    }

    //Find First Available Seat
    public static void find_first_available() {

        Object[][] arr = {row_a, row_b, row_c, row_d};
        String row_name = null;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (i == 0) {
                    row_name = "A";
                } else if (i == 1) {
                    row_name = "B";
                } else if (i == 2) {
                    row_name = "C";
                } else if (i == 3) {
                    row_name = "D";
                }

                if (arr[i][j].equals("0")) {
                    System.out.println(row_name + " row " + (j + 1) + " seat is available");
                    break;
                }

            }
        }
    }

    //show Seating plan
    public static void show_seating_plan() {

        Object[][] arr = {row_a, row_b, row_c, row_d};

        for (Object[] temp : arr) {
            for (Object temp_element : temp) {
                if (temp_element.toString().equalsIgnoreCase("1")) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    //print ticket information
    public static void print_tickets_info() {
        double total_price = 0;
        String[] arr = new String[52];

        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i] != null) {
                total_price += tickets[i].getPrice();
                if (arr[arr.length - 1] == null) {
                    if (arr[i] == null) {
                        arr[i] = tickets[i].getRow() + tickets[i].getSeat() + "=£" + tickets[i].getPrice();
                    }
                }
            }
        }
        System.out.print("The total amount would be £" + total_price + " (");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                System.out.print(arr[i] + "+");
            }
        }
        System.out.println("\b)\n");
    }

    //Search Ticket
    public static void search_ticket() {
        String seat_row = null;
        int seat_number = 0;
        int max_seat_count = 0;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please Enter Seat Row (A-D) :");
            seat_row = scanner.next().toUpperCase();

            switch (seat_row) {
                case "A":
                    max_seat_count = 14;
                    break;
                case "B":
                    max_seat_count = 12;
                    break;
                case "C":
                    max_seat_count = 12;
                    break;
                case "D":
                    max_seat_count = 14;
                    break;
                default:
                    System.out.println("Please Enter Valid Row");
                    continue;
            }
            System.out.println("Please Enter Seat Number :");
            try {
                seat_number = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("An Integer is required");
                continue;
            }
            if (seat_number > max_seat_count || seat_number <= 0) {
                System.out.println("This row have 1 to " + max_seat_count + " seats only");
                continue;
            }
            for (int i = 0; i < tickets.length; i++) {
                if (tickets[i] != null) {
                    if (tickets[i].getRow().equalsIgnoreCase(seat_row)) {
                        if (tickets[i].getSeat() == seat_number) {
                            tickets[i].print_information();
                            return;
                        }
                    }
                }
            }
            System.out.println("This seat is Available");
            break;
        }

    }

    //check ticket prices
    public static double check_ticket_price(int seat_number) {
        double seat_price = 0;
        if (seat_number >= 1 && seat_number <= 5) {
            seat_price = 200;
        } else if (seat_number >= 6 && seat_number <= 9) {
            seat_price = 150;
        } else if (seat_number >= 10) {
            seat_price = 180;
        }
        return seat_price;

    }

    //Create file
    public static void save_file(Ticket ticket) {

        String file_name = ticket.getRow() + ticket.getSeat();
        try {
            FileWriter fileWriter = new FileWriter(file_name + ".txt");
            fileWriter.write("Row : " + ticket.getRow() + "\n");
            fileWriter.write("Seat : " + ticket.getSeat() + "\n");
            fileWriter.write("Price : " + "£"+ticket.getPrice() + "\n");
            fileWriter.write("Name : " + ticket.getPerson().getName() + "\n");
            fileWriter.write("Surname : " + ticket.getPerson().getSurname() + "\n");
            fileWriter.write("Email : " + ticket.getPerson().getEmail() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
