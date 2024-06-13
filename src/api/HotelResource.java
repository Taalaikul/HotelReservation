package api;

import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public class HotelResource {

    CustomerService CUSTOMER_SERVICE = CustomerService.getInstance();

    ReservationService RESERVATION_SERVICE = ReservationService.getInstance();

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

        Collection<IRoom> availableRooms = RESERVATION_SERVICE.getAvailableRooms(checkIn, checkout);


        return availableRooms;
    }

    public static Date additionalDates (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 10);

        return calendar.getTime();
    }

    public static Date additionalOneDay (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);

        return calendar.getTime();
    }

    public static Date minusDates (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -10);

        return calendar.getTime();
    }

    public static Date minusOneDay (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }

    public Collection<IRoom> alternateDateRooms(Date checkIn, Date checkOut){

        Collection<IRoom> alternateDateRooms = new LinkedList<>();

        Date checkInDate = additionalOneDay(checkOut);
        Date checkOutDate = additionalDates(checkOut);

        Date ch = minusDates(checkIn);
        Date chOut = minusOneDay(checkIn);

        alternateDateRooms.addAll(findARoom(checkInDate, checkOutDate));
        alternateDateRooms.addAll(findARoom(ch, chOut));


        return alternateDateRooms;
    }



}
