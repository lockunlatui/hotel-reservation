package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ReservationService {
    private final static List<Room> rooms = new ArrayList<Room>();
    private final static List<Reservation> reservations = new ArrayList<>();

    public void addRoom(Room room) {
        if (!rooms.contains(room)) {
            rooms.add(room);
            System.out.println("Created a new room " +room);
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
            boolean isBeforeInDate = checkInDate.before(existingReservation.getCheckOutDate());
            boolean isAfterOutDate = checkOutDate.after(existingReservation.getCheckInDate());
            boolean isExistingRoom = existingReservation.getRoom() == room;
            if(isExistingRoom &&  isBeforeInDate && isAfterOutDate){
                return false;
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
                availableRooms.add(room);
                System.out.println("Room number " + room.getRoomNumber() + " is available");
            } else {
                System.out.println("Room is not available");
                return availableRooms;
            }
        }

        System.out.println("Available rooms: " + availableRooms);
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
