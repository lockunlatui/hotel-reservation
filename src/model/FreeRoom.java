package model;

public class FreeRoom extends Room {
    private Double price;

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "FreeRoom [price=" + price + "]";
    }
}
