package api;

import model.Customer;
import model.IRoom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static service.CustomerService.CUSTOMER_SERVICE;
import static service.ReservationService.RESERVATION_SERVICE;

public class AdminResource {

    public static final AdminResource ADMIN_RESOURCE = new AdminResource();

    public Customer getCustomer(String email){

        return CUSTOMER_SERVICE.getCustomer(email);
    }

    public void addRoom(IRoom room){
        RESERVATION_SERVICE.addRoom(room);
    }

    public void getAllRooms(){
        RESERVATION_SERVICE.getAllRooms();
    }
    public Collection<Customer>getAllCustomers(){

        Collection<Customer> customers = CUSTOMER_SERVICE.getAllCustomers();

        for(Customer c: customers){
            System.out.println("First Name: " + c.getFirstName()+ "  Last Name: " + c.getLastName() + "  Email: "+ c.getEmail());
        }
        return null;
    }

    public void displayAllReservations(){
        RESERVATION_SERVICE.printAllReservation();
    };


}
