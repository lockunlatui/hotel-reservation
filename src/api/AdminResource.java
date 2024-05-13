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
    private static AdminResource adminResource;


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

    public void addRoom(List<Room> rooms) {


        for(Room room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public Collection<Room> getAllRooms() {
        return reservationService.getRooms();
    }


    public Collection<Customer> getAllCustomers() {
        CustomerService customerService = new CustomerService();
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        System.out.println("Reservation List:" + reservationService.getReservations());
    }
}
