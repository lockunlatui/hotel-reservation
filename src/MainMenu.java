import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import validation.CustomerValidation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class MainMenu {
    static HotelResource hotelResource = HotelResource.getInstance();
    static ReservationService reservationService = ReservationService.getInstance();
    static CustomerService customerService = CustomerService.getInstance();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = 0;

            try {
                 choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.next();
            }


            switch (choice) {
                case 1:
                    handleFindAndReserve();
                    break;
                case 2:
                    System.out.println("Enter email");
                    Scanner enterCustomerEmail = new Scanner(System.in);
                    boolean isEmailValid = false;
                    while(!isEmailValid) {
                        String email = "";

                        email = enterCustomerEmail.nextLine();
                        Customer getCustomer = null;

                        for(Customer customer : customerService.getAllCustomers()) {
                            if(Objects.equals(customer.getEmail(), email)){
                                getCustomer = customer;
                                isEmailValid = true;
                            } else {
                                System.out.println("Customer isn't exist");
                                enterCustomerEmail.next();
                            }
                        }

                        if(getCustomer != null && !getCustomer.getEmail().isEmpty()){
                            System.out.print("See my reservations: " +reservationService.getCustomersReservation(getCustomer));
                        } else {
                            System.out.println("Customer isn't exist");
                            break;
                        }
                    }
                    break;
                case 3:
                    handleCreateCustomer();
                    break;
                case 4:
                    AdminMenu.displayMenu();
                    break;
                case 5:
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        }


    }

    private static void displayMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleCreateCustomer() {
        boolean customerCreated = false;
        while (!customerCreated) {
            Scanner scannerCreateCustomer = new Scanner(System.in);
            System.out.print("Enter email: ");
            String email = scannerCreateCustomer.nextLine();
            System.out.print("Enter first name: ");
            String firstName = scannerCreateCustomer.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scannerCreateCustomer.nextLine();

            try {
                if (CustomerValidation.validateCustomer(email, firstName, lastName)) {
                    hotelResource.createACustomer(firstName, lastName, email);
                    customerCreated = true;
                }
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }
    }

    private static void handleFindAndReserve() {
        Scanner scannerFindAndReserve = new Scanner(System.in);
        boolean isBookAgain = false;
        while(true){

            System.out.println("Enter CheckIn Date mm/dd/yyyy example 08/07/1995");
            String checkInDate = scannerFindAndReserve.nextLine();

            System.out.println("Enter CheckOut Date mm/dd/yyyy example 08/09/1995");
            String checkOutDate = scannerFindAndReserve.nextLine();

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            try {

                Date checkInDateFormated = formatter.parse(checkInDate);
                Date checkOutDateFormated = formatter.parse(checkOutDate);

                Collection<IRoom> availableRooms = reservationService.findRooms(checkInDateFormated, checkOutDateFormated);

                if(availableRooms.isEmpty()) {
                    System.out.println("Would you like to add another room y/n");

                    while (true) {
                        String anotherChoice = scannerFindAndReserve.nextLine();

                        if (anotherChoice.equalsIgnoreCase("y")) {
                            isBookAgain = false;
                           break;
                        } else if (anotherChoice.equalsIgnoreCase("n")) {
                            return;
                        } else {
                            System.out.println("Please enter n or y");
                        }

                    }


                } else {
                    System.out.println("Would you like to book a room? y/n");

                    while (true) {
                        String bookRoom = scannerFindAndReserve.nextLine();

                        if (bookRoom.equalsIgnoreCase("y")) {

                            System.out.println("Do you have an account with us? y/n");


                            while (true) {
                                String doYouHaveAccount = scannerFindAndReserve.nextLine();

                                if (doYouHaveAccount.equalsIgnoreCase("y")) {

                                    System.out.println("Enter email format: name@domain.com");

                                    while (true) {
                                        String enterEmail = scannerFindAndReserve.nextLine();

                                        if (!enterEmail.isEmpty()) {

                                            System.out.println("What room number would you like to reserve?");

                                            while(true) {
                                                String enterRoomNumber = scannerFindAndReserve.nextLine();

                                                IRoom room = hotelResource.getRoom(enterRoomNumber);

                                                Reservation reservation =  hotelResource.bookARoom(enterEmail, room, checkInDateFormated, checkOutDateFormated);
                                                System.out.println("Booked with" + reservation);
                                                isBookAgain = true;
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }

                        } else if (bookRoom.equalsIgnoreCase("n")) {
                            break;
                        } else {
                            System.out.println("Please enter n or y");
                        }
                        break;
                    }
                }



            } catch (ParseException e) {
                System.out.println("Format date is wrong");
                break;
            }

            if(isBookAgain) {
                break;
            }

        }


    }


}
