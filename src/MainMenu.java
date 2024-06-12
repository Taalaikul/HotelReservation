import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static api.AdminResource.ADMIN_RESOURCE;
import static api.HotelResource.HOTEL_RESOURCE;
import static service.ReservationService.RESERVATION_SERVICE;

public class MainMenu {

    public static final MainMenu MAIN_MENU = new MainMenu();

    public static void mainMenu() {

        System.out.println(

                        "1. Find and reserve a room" + "\n" +
                        "2. See my reservations" + "\n" +
                        "3. Create an account" + "\n" +
                        "4. Admin" + "\n" +
                        "5. Exit" + "\n" +
                        "--------------------------------------------"

        );

        System.out.println("Please select a number for the menu option");



        try {

            Scanner scanner = new Scanner(System.in);
           int  userChoice = scanner.nextInt();

            if (userChoice == 1) {
                haveAccount();
                reserveARoom();
                System.out.println();
                MainMenu.mainMenu();
            } else if (userChoice == 2) {
                System.out.println("Please enter your email: ");
                Scanner scanner1 = new Scanner(System.in);
                String email = scanner1.nextLine();

                if(ADMIN_RESOURCE.getCustomer(email) == null){
                    System.out.println("Please enter correct email address");
                    System.out.println();
                } else if (HOTEL_RESOURCE.getCustomersReservation(email) == null) {
                    System.out.println("No reservations were found!");
                    System.out.println();
                }
                printCustomersReservations(email);
               // System.out.println(HOTEL_RESOURCE.getCustomersReservation(email));
                System.out.println();
                MainMenu.mainMenu();
            } else if (userChoice == 3) {

                createAnAccount();
                
                MainMenu.mainMenu();
            } else if (userChoice == 4) {
                AdminMenu.adminMenu();
            } else if (userChoice == 5) {
                System.out.println("Good Bye!!!");
            } else {
                System.out.println("Please select a number from 1 to 5!");
                System.out.println();
            }

        }catch (Exception e){
            System.out.println("Please enter a number between 1 and 5!");
            System.out.println();
        }finally {
            MainMenu.mainMenu();
        }

        }


    public static void reserveARoom(){

        try {

            String email = getCustomersAccount();

            System.out.println("Enter CheckIn Date in the format mm/dd/yyyy");
            Date checkInDate = getInputDate();

            System.out.println("Enter CheckOut Date in the format mm/dd/yyyy");
            Date checkOutDate = getInputDate();

            System.out.println("Enter room number");
            Scanner scanner2 = new Scanner(System.in);
            IRoom room = HOTEL_RESOURCE.getRoom(scanner2.nextLine());

            HOTEL_RESOURCE.bookARoom(email, room, checkInDate, checkOutDate);
        } catch (Exception e){
        System.out.println("Your account was not found!");
            System.out.println();
        mainMenu();
    }

        System.out.println("Your reservation was successful!");
        System.out.println();
        mainMenu();

    }

    public static String getCustomersAccount(){

        System.out.println("Please enter your email address");

        Scanner sc = new Scanner(System.in);
        String entry = sc.nextLine();

        try{
            ADMIN_RESOURCE.getCustomer(entry);
        }catch(Exception e){
            System.out.println("The account was not found!");
            getCustomersAccount();
        }

        return entry;
    }

    public static String getCustomer(){
        String email = "";
        try {
            email = getCustomersAccount();
        }catch (Exception e){

            System.out.println("Your account was not found! Would you like to try again? Y (Yes), N (No) ");
            Scanner sc1 = new Scanner(System.in);
            String a = sc1.nextLine().toLowerCase();

            if(a.equals("y")){
                reserveARoom();
            }else if(a.equals("n")){
                MainMenu.mainMenu();
            }
        }
        return email;
    }

    public static String createAnAccount(){

            System.out.println("Please enter your email (format: email@email.com) for creating an account: ");
            Scanner sc = new Scanner(System.in);
            String email = sc.nextLine();

            System.out.println("Please enter your first name: ");
            Scanner sc1 = new Scanner(System.in);
            String firstName = sc1.nextLine();

            System.out.println("Please enter your last name: ");
            Scanner sc2 = new Scanner(System.in);
            String lastName = sc2.nextLine();

            try{
                HOTEL_RESOURCE.createACustomer(email, firstName, lastName);
                System.out.println("Your account was created successfully!");
                System.out.println();
            }catch(Exception e){
                System.out.println("Please enter your email correctly!");
                System.out.println();
                createAnAccount();
        }

            return email;
    }

    public static void haveAccount(){
        System.out.println("Do you have an account with us? Enter Y (YES), N (NO)");

        Scanner sc = new Scanner(System.in);
        String haveAcc = sc.nextLine();
        String yesNo = haveAcc.toLowerCase();

        if(yesNo.equals("n")){
             createAnAccount();
        }else if(yesNo.equals("y")){

            System.out.println("Please enter your info:");
            reserveARoom();
        }

    }


    private static Date getInputDate() {
        Scanner scanner = new Scanner(System.in);
        try {
            return new SimpleDateFormat("mm/dd/yyyy").parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Error: Invalid date.");
            reserveARoom();
        }

        return null;
    }

    private static void printCustomersReservations(String email){

       Collection< Reservation> reservations = HOTEL_RESOURCE.getCustomersReservation(email);

       for(Reservation reservation: reservations){
           Customer c = reservation.getCustomer();
           Date date = reservation.getCheckInDate();
           Date date1 = reservation.getCheckOutDate();

           System.out.println(
                   "Name: " + c.getFirstName() + " " +  c.getLastName() + "\n" +
                   "CheckIn Date " + date + "\n" +
                   "CheckOut Date " + date1
           );
       }

    }

}