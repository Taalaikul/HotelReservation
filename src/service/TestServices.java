package service;

import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static service.CustomerService.CUSTOMER_SERVICE;
import static service.ReservationService.RESERVATION_SERVICE;

public class TestServices {

    public static void main(String[] args){

        CUSTOMER_SERVICE.addCustomer("tx@gx.com", "tasya", "jum");

        Customer c = CUSTOMER_SERVICE.getCustomer("tx@gx.com");

        System.out.println(c);
        System.out.println(CUSTOMER_SERVICE.getAllCustomers());

        IRoom room = new Room("100", 120.0, RoomType.DOUBLE);
        IRoom room2 = new Room("101", 130.0, RoomType.SINGLE);
        IRoom room3 = new Room("102", 140.0, RoomType.DOUBLE);

        RESERVATION_SERVICE.addRoom(room);
        RESERVATION_SERVICE.addRoom(room2);
        RESERVATION_SERVICE.addRoom(room3);
        System.out.println();

        System.out.println("Getting a room: ");
        System.out.println(RESERVATION_SERVICE.getARoom("100"));
        System.out.println();

        System.out.println("All the rooms");
        RESERVATION_SERVICE.getAllRooms();
        System.out.println();

        RESERVATION_SERVICE.reserveARoom(c, room, new Date(12/02/2024), new Date(12/04/2024));


        System.out.println("Reservation: " + RESERVATION_SERVICE.getCustomersReservation(c));
        System.out.println();

        System.out.println("All the reservations");
        RESERVATION_SERVICE.printAllReservation();

        System.out.println("Reservation collection: ");
        System.out.println(RESERVATION_SERVICE.getReservations());
    }
}
