package api;

import model.IRoom;
import model.Reservation;

import java.util.Collection;
import java.util.Date;

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
    };

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){

        return  RESERVATION_SERVICE.reserveARoom(CUSTOMER_SERVICE.getCustomer(customerEmail), room, checkInDate, checkOutDate);
    };

    public Collection<Reservation>getCustomersReservation(String customerEmail){

        return RESERVATION_SERVICE.getCustomersReservation(CUSTOMER_SERVICE.getCustomer(customerEmail));
    }


    public Collection<IRoom> findARoom(Date checkIn, Date checkout){

        return null;
    }






}
