package service;

import model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;



public class TestServices {

    public static void main(String[] args){

        CustomerService CUSTOMER_SERVICE = CustomerService.getInstance();
        ReservationService RESERVATION_SERVICE = ReservationService.getInstance();

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

        Date date1 = new Date(2000, 01, 01);
        Date date2 = new Date(2000, 01, 02);

        Date date3 = new Date(2000, 01, 03);
        Date date4 = new Date(2000, 01, 04);


        System.out.println( RESERVATION_SERVICE.reserveARoom(c, room, date1, date2));
        System.out.println( RESERVATION_SERVICE.reserveARoom(c, room2, date3, date4));


        System.out.println("Reservation: " + RESERVATION_SERVICE.getCustomersReservation(c));
        System.out.println();

        System.out.println("All the reservations");
        RESERVATION_SERVICE.printAllReservation();
        System.out.println();

        System.out.println("Reservation collection: ");
        System.out.println(RESERVATION_SERVICE.getReservations());

        System.out.println();

        System.out.println("Reservation by Room number");
        System.out.println(RESERVATION_SERVICE.getReservationsByRoomNumber("100"));

//        System.out.println("ALl reserved rooms: " + RESERVATION_SERVICE.getAllReservedRooms());
//        System.out.println();
//
//        System.out.println("All not reservedRooms: " + RESERVATION_SERVICE.getAllNotReservedRoom());
//        System.out.println();




//        Calendar cal1 = Calendar.getInstance();
//        Calendar cal2 = Calendar.getInstance();
//        cal1.setTime(date1);
//        cal2.setTime(date2);
//
//        if (date1.compareTo(date2) > 0) {
//            System.out.println("Date1 is after Date2");
//        } else if (date1.compareTo(date2) < 0) {
//            System.out.println("Date1 is before Date2");
//        } else {
//            System.out.println("Date1 is equal to Date2");
//        }


//
//        Date date3 = new Date(2000, 01, 03);
//        Date date4 = new Date(2000, 01, 04);
//
//        Collection<IRoom> res = RESERVATION_SERVICE.getAvailableRooms(date3, date4);
//
//        System.out.println("Available rooms are: " + res);


    }
}
