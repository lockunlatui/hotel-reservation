import api.AdminResource;
import api.HotelResource;
import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public class AdminMenu {
    static AdminResource adminResource = AdminResource.getInstance();

    private static void addRoom() {
        Scanner scannerAddRoom = new Scanner(System.in);
        System.out.println("Enter room number: ");
        String roomNumber = scannerAddRoom.nextLine();

        boolean isValidRoomPrice = false;
        Double roomPrice = 0.0;

        while(!isValidRoomPrice) {
            System.out.println("Enter room price: ");
            try {
                roomPrice = scannerAddRoom.nextDouble();
                isValidRoomPrice = true;
            } catch (InputMismatchException e) {
                System.out.println("please fill the number");
                scannerAddRoom.next();
            }
        }


        int roomType = 0;
        boolean isValidRoomType = false;
        while(!isValidRoomType) {
            System.out.println("Enter room type: 1 for single bed, 2 for double bed");
            try {
                roomType = scannerAddRoom.nextInt();
                isValidRoomType = true;

                if(roomType != 1 && roomType != 2) {
                    System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                    isValidRoomType = false;
                    scannerAddRoom.next();
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                scannerAddRoom.next();
            }
        }

        Double finalRoomPrice = roomPrice;
        int finalRoomType = roomType;

        IRoom room = new IRoom() {
            @Override
            public String getRoomNumber() {
                return roomNumber;
            }

            @Override
            public Double getRoomPrice() {
                return finalRoomPrice;
            }

            @Override
            public RoomType getRoomType() {
                return finalRoomType == 1 ? RoomType.SINGLE : RoomType.DOUBLE;
            }

            @Override
            public boolean isFree() {
                return false;
            }
        };

        List<IRoom> rooms = new ArrayList<>();
        rooms.add(room);

        adminResource.addRoom(rooms);

    }

    public static void displayMenu() {

        MainMenu mainMenu = new MainMenu();

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch(choice) {
                case 1:
                    System.out.println("All Customers "+ adminResource.getAllCustomers());
                    break;
                case 2:
                    System.out.println("All Rooms"+ adminResource.getAllRooms());
                    break;
                case 3:
                    adminResource.displayAllReservations();
                    break;
                case 4:
                    addRoom();
                    break;
                case 5:
                    MainMenu.main(null);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;

            }
        }


    }
}
