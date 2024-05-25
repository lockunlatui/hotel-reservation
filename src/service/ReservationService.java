package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;

public class ReservationService {
    private final static Set<Room> rooms = new HashSet<>();
    private final static List<Reservation> reservations = new ArrayList<>();

    private static ReservationService reservationServiceInstance;

    private ReservationService() {}

    public static ReservationService getInstance(){
        if(reservationServiceInstance == null) {
            reservationServiceInstance = new ReservationService();
        }
        return reservationServiceInstance;
    }


    public void addRoom(Room room) {
        if (!rooms.contains(room)) {
            boolean isAdded = rooms.add(room);

            if(isAdded) {
                System.out.println("Created a new room " +room);
            } else {
                throw new IllegalArgumentException("Room already existes");
            }
        } else {
            throw new IllegalArgumentException("Room already exists");
        }
    }

    public IRoom getARoom(String roomId) {
        if (rooms.isEmpty()) {
            throw new IllegalArgumentException("There is no room with id " + roomId);
        }

        for(IRoom room : rooms) {
            if(room.getRoomNumber().equals(roomId)){
                return room;
            }
        }
        return null;
    }

    public Reservation reservationARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if(customer == null || room == null || checkInDate == null || checkOutDate == null){
            throw new IllegalArgumentException("Information reservation cannot be null");
        }

        if(checkOutDate.before(checkInDate)){
            throw new IllegalArgumentException("Check out date cannot be before check in date " + checkOutDate);
        }

        if(!isRoomAvailable(room, checkInDate, checkOutDate)){
            throw new IllegalArgumentException("Room is not available");
        }

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        reservations.add(reservation);

        return reservation;
    }

    private boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        for(Reservation existingReservation : reservations){
            boolean isBeforeInDate = checkInDate.after(existingReservation.getCheckInDate()) && checkInDate.before(existingReservation.getCheckOutDate());
            boolean isAfterOutDate = checkOutDate.after(existingReservation.getCheckInDate()) && checkOutDate.before(existingReservation.getCheckInDate());
            boolean isExistingRoom = existingReservation.getRoom() == room;
            if(isExistingRoom){
                if(isBeforeInDate) {
                    return false;
                } else if (isAfterOutDate) {
                    return false;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<>();


        if(checkInDate == null || checkOutDate == null){
            throw new IllegalArgumentException("Information reservation cannot be null");
        }

        for(IRoom room : getRooms()){
            if(isRoomAvailable(room, checkInDate, checkOutDate)){
                System.out.println("Room " + room.getRoomNumber() + " is available");
                availableRooms.add(room);
            } else {
                System.out.println("Room " + room.getRoomNumber() + " is not available");
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {

        if(customer == null){
            throw new IllegalArgumentException("Information reservation cannot be null");
        }

        Collection<Reservation> customersReservations = new ArrayList<>();

        for(Reservation reservation : reservations){
            if(reservation.getCustomer() == customer){
                customersReservations.add(reservation);
            }
        }

        return customersReservations;
    }

    public void printAllReservations() {
        if(reservations.isEmpty()){
            System.out.println("No reservations found");
        }

        for(Reservation reservation : reservations){
            System.out.println("List reservation: " +reservation);
        }
    }

    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public Collection<Room> getRooms() {
        return rooms;
    }


}
