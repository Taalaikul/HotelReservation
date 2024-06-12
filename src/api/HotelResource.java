package api;

import model.IRoom;
import model.Reservation;

import java.util.*;

import static service.CustomerService.CUSTOMER_SERVICE;
import static service.ReservationService.RESERVATION_SERVICE;

public class HotelResource {

    public static final HotelResource HOTEL_RESOURCE = new HotelResource();

    public void getCustomer(String email){
        CUSTOMER_SERVICE.getCustomer(email);
    };


    public void createACustomer(String email, String firstName, String lastName){
        CUSTOMER_SERVICE.addCustomer(email, firstName, lastName);
    };


    public IRoom getRoom(String roomNumber){
        return RESERVATION_SERVICE.getARoom(roomNumber);
    }
    ;

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        return  RESERVATION_SERVICE.reserveARoom(CUSTOMER_SERVICE.getCustomer(customerEmail), room, checkInDate, checkOutDate);
    };

    public Collection<Reservation>getCustomersReservation(String customerEmail){

        return RESERVATION_SERVICE.getCustomersReservation(CUSTOMER_SERVICE.getCustomer(customerEmail));
    }

    public Collection<IRoom> bookedRooms (){
        Collection<IRoom> bookedRooms = new HashSet<>();

        Collection<Reservation> reservationCollection = RESERVATION_SERVICE.getReservations();

        for(Reservation reservation: reservationCollection){
                bookedRooms.add(reservation.getRoom());
        }

        return bookedRooms;

    }


    public Collection<IRoom> findARoom(Date checkIn, Date checkout){

        Collection<IRoom> availableRooms = new LinkedList<>();

       Collection<Reservation> reservationCollection = RESERVATION_SERVICE.getReservations();
       Collection<IRoom> bookedRooms = bookedRooms();

       if(bookedRooms.isEmpty()){
            RESERVATION_SERVICE.getAllRooms();
       }else{
           for(Reservation reservation: reservationCollection){
            if(reservation.getCheckInDate().after(checkout) && reservation.getCheckOutDate().before(checkIn) ) {
                availableRooms.add(reservation.getRoom());
            }
            }
       }

        return availableRooms;
    }



}
