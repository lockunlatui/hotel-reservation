package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static validation.CustomerValidation.validateCustomer;

public class HotelResource {

    private static HotelResource hotelInstance;

    private HotelResource() {}

    public static HotelResource getInstance() {
        if(hotelInstance == null) {
            hotelInstance = new HotelResource();
        }
        return hotelInstance;
    }

    List<Customer> customers = new ArrayList<Customer>();

    CustomerService customerService = CustomerService.getInstance();
    ReservationService  reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        for(Customer customer : customerService.getAllCustomers()) {
            if(customer.getEmail().equals(email)) {
                return customer;
            }
        }

        return null;
    }

    public void createACustomer(String email, String firstName, String lastName) {
        validateCustomer(email, firstName, lastName);

        Customer customer = new Customer(email, firstName, lastName);
        customerService.addCustomer(email, firstName, lastName);
        System.out.println("Customer created: "+ customer.getEmail());
    }

    public IRoom getRoom(String roomNumber) {
        if(roomNumber == null || roomNumber.isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }

        for(IRoom room : reservationService.getRooms()) {
            if(room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }

        return null;
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        if (customerEmail == null || customerEmail.isEmpty()) {
            throw new IllegalArgumentException("Customer email cannot be null or empty");
        }

        if (room == null || room.getRoomNumber() == null || room.getRoomNumber().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }

        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check In Date and Check Out Date cannot be null or empty");
        }

        if(checkInDate.before(new Date()) || checkOutDate.before(new Date())) {
            throw new IllegalArgumentException("Check In Date and Check Out Date cannot before current date");
        }

        Customer customer = getCustomer(customerEmail);

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationService.reservationARoom(customer, room, checkInDate, checkOutDate);
        return reservation;
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        if(checkInDate == null || checkInDate.after(new Date())) {
            throw new IllegalArgumentException("Check In Date cannot be null or empty");
        }

        if(checkOutDate == null || checkOutDate.after(new Date())) {
            throw new IllegalArgumentException("Check Out Date cannot be null or empty");
        }

        Collection<IRoom> availableRooms = new ArrayList<>();

        for(Reservation reservation : reservationService.getReservations()) {
            if(reservation.getCheckInDate().equals(checkInDate) && reservation.getCheckOutDate().equals(checkOutDate)) {
                IRoom room = reservation.getRoom();
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }
}
