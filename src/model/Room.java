package model;

public class Room implements IRoom {

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

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setEnumeration(RoomType enumeration) {
        this.enumeration = enumeration;
    }
}
