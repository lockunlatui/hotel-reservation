package model;

public abstract class Room implements IRoom {

    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    @Override
    public String toString() {
        return "Room [roomNumber=" + roomNumber + ", price=" + price + ", enumeration=" + enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return false;
    }
}
