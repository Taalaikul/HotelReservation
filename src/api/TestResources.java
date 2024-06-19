package api;

import model.*;

import java.util.Date;

import static api.AdminResource.ADMIN_RESOURCE;
import static api.HotelResource.HOTEL_RESOURCE;

public class TestResources {

    public static void main(String[] args){

        //Customer c  = new Customer("ALice", "Wonder", "alice@gmail.com");
        IRoom room = new Room("200", 100.00, RoomType.DOUBLE);
        IRoom room1 = new Room("300", 120.00, RoomType.SINGLE);


        HOTEL_RESOURCE.createACustomer("alice@gmail.com", "Alice", "Wonder");
        HOTEL_RESOURCE.createACustomer("maggie@gmail.com", "Maggie", "Hoop");


        ADMIN_RESOURCE.addRoom(room);
        ADMIN_RESOURCE.addRoom(room1);

        System.out.println("All the rooms:");
        ADMIN_RESOURCE.getAllRooms();
        System.out.println();

        System.out.println("The room is: " + HOTEL_RESOURCE.getRoom("200"));
        System.out.println();

        Customer c = ADMIN_RESOURCE.getCustomer("alice@gmail.com");
        System.out.println("Customer: " + c);
        System.out.println();

        HOTEL_RESOURCE.bookARoom("alice@gmail.com", room, new Date(01/01/2024), new Date(01/06/2024));

        System.out.println("All the reservations: ");
        ADMIN_RESOURCE.displayAllReservations();
        System.out.println();

        System.out.println("Customers reservation: " + HOTEL_RESOURCE.getCustomersReservation("alice@gmail.com"));
        System.out.println();

        System.out.println("All booked rooms");
        System.out.println(HOTEL_RESOURCE.bookedRooms());

        Date checkIn = new Date(01/01/2000);
        Date checkOut = new Date(01/02/2000);


        System.out.println("Available rooms");
        System.out.println( HOTEL_RESOURCE.findARoom(checkIn, checkOut));

        System.out.println("AlternateDate Rooms");
        System.out.println(HOTEL_RESOURCE.alternateDateRooms(checkIn, checkOut));

    }
}
