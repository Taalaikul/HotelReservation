import model.IRoom;
import model.Room;
import model.RoomType;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static api.AdminResource.ADMIN_RESOURCE;

public class Tester {

    public static void main(String[] args){

        IRoom room = new Room("100", 120.0, RoomType.DOUBLE);

        ADMIN_RESOURCE.addRoom(room);

        Date checkIn = new Date(2000, 01, 01);
        Date checkOout = new Date(2000, 01, 02);

//        System.out.println("all available rooms");
//        System.out.println(MainMenu.getAvailableRooms(checkIn, checkOout));
//        System.out.println();


//        System.out.println("Alternate days");
//        System.out.println(MainMenu.getAvailableRoomsForAlternateDays(checkIn, checkOout));
//        System.out.println();
//
//        System.out.println("Printing");
//        MainMenu.printAvailableRooms(checkIn, checkOout);
//        System.out.println();
//
//        System.out.println("available rooms print");
//        MainMenu.printAvailableRooms(checkIn, checkOout);
//
//        System.out.println(MainMenu.inputYesNo("Enter yes no"));

        System.out.println(checkIn);

        DateTimeFormatter myObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        LocalDate localDate = checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = formatter.format(checkIn);

        System.out.println("Formatted date: " + formattedDate);

       // System.out.println(localDate);

    }
}
