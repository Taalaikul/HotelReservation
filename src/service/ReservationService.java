package service;

import model.*;

import java.util.*;


public class ReservationService {

    private static ReservationService RESERVATION_SERVICE;

    private ReservationService() {
    }

    Map<String, Reservation> reservationMap = new HashMap<>();
    Map<String, IRoom> roomsMap = new HashMap<>();


    public void addRoom(IRoom room){
        roomsMap.put(room.getRoomNumber(), room);
    };

    public void getAllRooms(){

        if(roomsMap.isEmpty()){
            System.out.println("No rooms were found!");
            System.out.println();
        }else {
            for (Map.Entry<String, IRoom> entry : roomsMap.entrySet()) {
                IRoom room = entry.getValue();
                Double price = room.getRoomPrice();
                Enum e = room.getRoomType();
                String roomNumber = entry.getKey();
                System.out.println("Room Number: " + roomNumber + " " + "Room price: " + price + " " + "Room Type: " + e);
            }
        }
   }



    public IRoom getARoom(String roomId){
        return roomsMap.get(roomId);
    };

    public Collection<IRoom> getAllReservedRooms(){
        Collection<IRoom> reservedRooms = new LinkedList<>();

        for (Map.Entry<String, Reservation> entry : reservationMap.entrySet()) {
            Reservation reservation = entry.getValue();
            reservedRooms.add( reservation.getRoom());
        }

        return reservedRooms;
    }

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

                System.out.println("Name: " +c.getFirstName() + " " + c.getLastName() + " Dates: " +reservation1.getCheckInDate()+" - "+reservation1.getCheckOutDate());
            }
        }
    };

    public Collection<Reservation> getReservations (){

        Collection<Reservation> reservations = new ArrayList<>();

        for(Map.Entry<String, Reservation> reservation : reservationMap.entrySet()){
            reservations.add(reservation.getValue());
        }
        return reservations;
    }

    public Collection<IRoom> getAllNotReservedRoom(){

        Collection<IRoom> notReservedRooms = new ArrayList<>();
        Collection<IRoom> allReservedRooms = getAllReservedRooms();

       for(IRoom room: roomsMap.values()){
           if(!allReservedRooms.contains(room)){
               notReservedRooms.add(room);
           }
       }

        return notReservedRooms;
    }

    public Collection<IRoom> getAvailableRooms(Date checkIn, Date checkOut){

        Collection<Reservation> reservations = getReservations();
        Collection<IRoom> availableRooms = getAllNotReservedRoom();

        if(reservationMap.isEmpty()){
            availableRooms.addAll(roomsMap.values());
        }else{
            for(Reservation reservation: reservations){
                if(reservation.getCheckInDate().after(checkOut) || reservation.getCheckOutDate().before(checkIn)){
                    availableRooms.add(reservation.getRoom());
                }
            }
        }



        return availableRooms;
}

    public static ReservationService getInstance() {
        if(RESERVATION_SERVICE == null) {
            RESERVATION_SERVICE = new ReservationService();
        }

        return RESERVATION_SERVICE;
    }


}
