package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminResource {
    ReservationService reservationService = ReservationService.getInstance();
    CustomerService customerService = CustomerService.getInstance();

    private static AdminResource adminInstance;

    private AdminResource() {}

    public static AdminResource getInstance() {
        if (adminInstance == null) {
            adminInstance = new AdminResource();
        }
        return adminInstance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<Room> rooms) {
        for(Room room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public Collection<Room> getAllRooms() {
        return reservationService.getRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        System.out.println("Reservation List:" + reservationService.getReservations());
    }
}
