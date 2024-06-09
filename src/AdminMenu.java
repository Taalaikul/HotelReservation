import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Scanner;

import static api.AdminResource.ADMIN_RESOURCE;

public class AdminMenu {

    public static final AdminMenu ADMIN_MENU = new AdminMenu();

    public static void adminMenu(){

        System.out.println(
                "Admin Menu " +"\n"+
                "---------------------------------------" +"\n"+
                "1. See all Customers" +"\n"+
                "2. See all Rooms" +"\n"+
                "3. See all Reservations" +"\n"+
                "4. Add a Room" +"\n"+
                "5. Back to Main Menu" +"\n"+
                "---------------------------------------"
        );

        Scanner adminScanner = new Scanner(System.in);
        int adminChoice = adminScanner.nextInt();

        try {
            if (adminChoice == 1) {
                ADMIN_RESOURCE.getAllCustomers();
                AdminMenu.adminMenu();
            } else if (adminChoice == 2) {
                ADMIN_RESOURCE.getAllRooms();
                AdminMenu.adminMenu();
            } else if (adminChoice == 3) {
                ADMIN_RESOURCE.displayAllReservations();
                AdminMenu.adminMenu();
            } else if (adminChoice == 4) {
                createRoom();
                AdminMenu.adminMenu();
            } else if (adminChoice == 5) {
                MainMenu.mainMenu();
            } else {
                System.out.println("Please select a number between 1 and 5!");
            }
        }catch(Exception e){
            System.out.println("Invalid input!");
        }

    }

    public static void createRoom(){
           Scanner sc = new Scanner(System.in);
            System.out.println("Please enter a room number: ");
            String roomNum = sc.nextLine();


            Scanner sc1 = new Scanner(System.in);
            System.out.println("Please enter a room price: ");
            Double roomPrice = sc1.nextDouble();

            Scanner sc2 = new Scanner(System.in);
            System.out.println("Please enter a number for Room Type: 1 for SINGLE, 2 for DOUBLE");
            int roomT = sc2.nextInt();
            RoomType roomType;

            if(roomT == 1){
                roomType = RoomType.SINGLE;
            }else if(roomT == 2){
                roomType = RoomType.DOUBLE;
            }else{
                System.out.println("Invalid Value! Returning to the beginning of creating a room");
            }

            roomType = RoomType.DOUBLE;
            IRoom room = new Room(roomNum, roomPrice, roomType);

            ADMIN_RESOURCE.addRoom(room);

    }
}
