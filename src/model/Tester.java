package model;

import java.util.Date;

public class Tester {

    public static void main(String[] args){

        Customer customer = new Customer("first", "second", "j@domain");

        IRoom room = new Room("123", 12.0, RoomType.DOUBLE);

        Reservation reservation = new Reservation(customer,room, new Date(12/02/2024),  new Date(12/03/2024));

        System.out.println(customer);
        System.out.println(room);
        System.out.println(reservation);

        System.out.println(reservation.toString());
    }
}
