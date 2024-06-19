import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.InputMismatchException;
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

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        switch (input.charAt(0)) {
            case '1':
                ADMIN_RESOURCE.getAllCustomers();
                adminMenu();
                break;
            case '2':
                ADMIN_RESOURCE.getAllRooms();
                adminMenu();
                break;
            case '3':
                ADMIN_RESOURCE.displayAllReservations();
                adminMenu();
                break;
            case '4':
                createRoom();
                adminMenu();
                break;
            case '5':
                MainMenu.mainMenu();
                break;
            default:
                System.out.println("Invalid input! Enter a number between 1 and 5!\n");
                adminMenu();
                break;
        }

    }

    public static void createRoom(){

         String roomNum = getRoomNum();

         String roomP = getRoomPrice();
         Double roomPrice = Double.parseDouble(roomP);

         RoomType roomType = getRoomType();



            IRoom room = new Room(roomNum, roomPrice, roomType);

            try {
                ADMIN_RESOURCE.addRoom(room);
            }catch(Exception e){
                System.out.println("Please enter correct information!");
                createRoom();
            }

        System.out.println("Would you like to add another room? Y (YES) , N (NO)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String lowerCaseInput = input.toLowerCase();

        if(lowerCaseInput.equals("y")){
            createRoom();
        }else{
            adminMenu();
        }


    }

    public static String getRoomNum(){

        String roomNum = "";

        boolean isValid = false;
        while (!isValid) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter a room number: ");
            roomNum = sc.nextLine();

            if (!roomNum.matches("-?\\d+")) {
                System.out.println("You must supply a numerical value (no alpha characters allowed)!");
                continue;
            }
            isValid = true;
        }

        return roomNum;

    }

    public static String getRoomPrice(){
        String roomPrice= "";

        boolean isValid = false;
        while (!isValid) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter a room price: ");
            roomPrice = sc.nextLine();

            if (!roomPrice.matches("-?\\d+")) {
                System.out.println("You must supply a numerical value (no alpha characters allowed)!");
                continue;
            }

            isValid = true;
        }

        return roomPrice;
    }

    public static RoomType getRoomType(){

        RoomType roomType = null;
        String roomT ="";

        boolean isValid = false;
        while (!isValid) {
            Scanner sc2 = new Scanner(System.in);
            System.out.println("Please enter a number for Room Type: 1 for SINGLE, 2 for DOUBLE");
            roomT = sc2.nextLine();

            if(roomT.equals("1")){
                roomType = RoomType.SINGLE;
            }else if(roomT.equals("2")){
                roomType = RoomType.DOUBLE;
            }else{
                continue;
            }
            isValid =true;
        }



        return roomType;
        }
}
