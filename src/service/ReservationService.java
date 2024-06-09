package service;

import model.*;

import java.util.*;

public class ReservationService {

    public static final ReservationService RESERVATION_SERVICE = new ReservationService();

    Map<String, Reservation> reservationMap = new HashMap<>();
    Map<String, IRoom> roomsMap = new HashMap<>();

    public void addRoom(IRoom room){
        roomsMap.put(room.getRoomNumber(), room);
    };

    public void getAllRooms(){
        for(Map.Entry<String, IRoom> entry : roomsMap.entrySet()) {
            IRoom room = entry.getValue();
            Double price = room.getRoomPrice();
            Enum e = room.getRoomType();
            String roomNumber = entry.getKey();
            System.out.println("Room Number: " + roomNumber + " " + "Room price: " + price + " " +"Room Type: " + e);
        }
   }

    public IRoom getARoom(String roomId){

        return roomsMap.get(roomId);
    };

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationMap.put(customer.getEmail(), reservation);
        return reservation;
    }


    public Collection<Reservation> getCustomersReservation( Customer customer){
        String customersEmail = customer.getEmail();
        return Collections.singleton(reservationMap.get(customersEmail));
    }



    public void printAllReservation(){

        if(reservationMap.isEmpty()){
            System.out.println("No reservation found!");
        }else{
            for(Map.Entry<String, Reservation> reservation : reservationMap.entrySet()){
                Reservation reservation1 = reservation.getValue();
                Customer c = reservation1.getCustomer();
                String email = reservation.getKey();

                System.out.println("Name: " +c.getFirstName() + " " + c.getLastName() + "Dates: " +reservation1.getCheckInDate()+" - "+reservation1.getCheckOutDate());
            }
        }
    };


}
