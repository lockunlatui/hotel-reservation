package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static AdminResource adminResource;
    private final List<Reservation> reservations = new ArrayList<>();

    ReservationService reservationService = new ReservationService();
    CustomerService customerService = new CustomerService();

    private AdminResource() {}

    public static AdminResource getInstance() {
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {


        for(IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getRooms();
    }


    public Collection<Customer> getAllCustomers() {
        CustomerService customerService = new CustomerService();
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        for (Reservation reservation : reservations) {
            System.out.println("Reservation Details:");
            System.out.println("Customer: " + reservation.getCustomer());
            System.out.println("Room: " + reservation.getRoom().getRoomNumber());
            System.out.println("Check-In Date: " + reservation.getCheckInDate());
            System.out.println("Check-Out Date: " + reservation.getCheckOutDate());
            System.out.println("----------------------------------");
        }


    }
}
