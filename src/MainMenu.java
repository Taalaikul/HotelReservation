import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static api.AdminResource.ADMIN_RESOURCE;
import static api.HotelResource.HOTEL_RESOURCE;


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

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        switch (input.charAt(0)) {
            case '1':
                reserveARoom();
                mainMenu();
                break;
            case '2':
                printReservations();
                mainMenu();
                break;
            case '3':
                createAnAccount();
                mainMenu();
                break;
            case '4':
                AdminMenu.adminMenu();
                break;
            case '5':
                System.out.println("Good Bye!");
                System.out.println();
                break;
            default:
                System.out.println("Invalid input. Enter a number between 1 and 5!\n");
                mainMenu();
                break;
        }


    }


    public static void reserveARoom() {

        System.out.println("Enter CheckIn Date in the format mm/dd/yyyy");
        Date checkInDate = getInputDate();

        System.out.println("Enter CheckOut Date in the format mm/dd/yyyy");
        Date checkOutDate = getInputDate();

        System.out.println();
        printAvailableRooms(checkInDate, checkOutDate);



            System.out.println();
            String s = "Would you like to reserve a room? Y (YES), N (NO) ";
            String answer = inputYesNo(s);

        if(!getAvailableRooms(checkInDate, checkOutDate).isEmpty()) {
            String email = "";

            if (answer.equals("y")) {
                email = haveAccount();
            } else {
                mainMenu();
            }


            IRoom room = getRoomInput(checkInDate, checkOutDate);

            try {
                HOTEL_RESOURCE.bookARoom(email, room, checkInDate, checkOutDate);
            } catch (Exception e) {
                System.out.println("We couldn't reserve you a room");
            }

            System.out.println();
        }else{
            String email = "";

            if (answer.equals("y")) {
                email = haveAccount();
            } else {
                mainMenu();
            }
            IRoom room = getRoomInput(checkInDate, checkOutDate);

            reserveARoomForAlternateDates(checkInDate, checkOutDate, email, room);
        }

        System.out.println("Your reservation was successful!");
        System.out.println();
     mainMenu();

    }

    public static void reserveARoomForAlternateDates(Date checkIn, Date checkOut, String email, IRoom room){

        System.out.println("Enter CheckIn Date in the format mm/dd/yyyy");
        Date checkInDate = getInputDate();

        System.out.println("Enter CheckOut Date in the format mm/dd/yyyy");
        Date checkOutDate = getInputDate();


        try {
            HOTEL_RESOURCE.bookARoom(email, room, checkInDate, checkOutDate);
        }catch(Exception e){
            System.out.println("We couldn't reserve you a room");
        }

        System.out.println("Your reservation was successful!");
        System.out.println();

        mainMenu();


    }




    public static Collection<IRoom> getAvailableRooms(Date checkInDate, Date checkOutDate){
        Collection<IRoom> rooms = HOTEL_RESOURCE.findARoom(checkInDate, checkOutDate);
        return rooms;
    }

    public  static Collection<IRoom> getAvailableRoomsForAlternateDays(Date checkInDate, Date checkOutDate){

        Collection<IRoom> rooms = new ArrayList<>();

        Collection<IRoom> roomsForBeforeDates = new ArrayList<>();
        Collection<IRoom> roomsForAfterDates = new ArrayList<>();
        Date checkIn= HOTEL_RESOURCE.additionalOneDay(checkOutDate);
        Date checkOut = HOTEL_RESOURCE.additionalDates(checkOutDate);
        Date chIn = HOTEL_RESOURCE.minusDates(checkInDate);
        Date chOut = HOTEL_RESOURCE.minusOneDay(checkInDate);
        roomsForAfterDates.addAll(getAvailableRooms(checkIn, checkOut));
        roomsForBeforeDates.addAll(getAvailableRooms(chIn, chOut));

        rooms.addAll(roomsForBeforeDates);
        rooms.addAll(roomsForAfterDates);


        return rooms;
    }

    public static void printAvailableRoomsForAlternateDates(Date checkInDate, Date checkOutDate){

        Collection<IRoom> rooms = new ArrayList<>();

        Collection<IRoom> roomsForBeforeDates = new ArrayList<>();
        Collection<IRoom> roomsForAfterDates = new ArrayList<>();
        Date checkIn= HOTEL_RESOURCE.additionalOneDay(checkOutDate);
        Date checkOut = HOTEL_RESOURCE.additionalDates(checkOutDate);
        Date chIn = HOTEL_RESOURCE.minusDates(checkInDate);
        Date chOut = HOTEL_RESOURCE.minusOneDay(checkInDate);
        roomsForAfterDates.addAll(getAvailableRooms(checkIn, checkOut));
        roomsForBeforeDates.addAll(getAvailableRooms(chIn, chOut));

        rooms.addAll(roomsForBeforeDates);
        rooms.addAll(roomsForAfterDates);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

        for(IRoom room: roomsForBeforeDates){
            System.out.println("Available rooms for earlier dates: " + " Room number: " + room.getRoomNumber() + " Room price: " + room.getRoomPrice() + " Room type " +room.getRoomType() + " Dates available: " + formatter.format(chIn) + "-" + formatter.format(chOut));
        }

        for(IRoom room: roomsForAfterDates){
            System.out.println("Available rooms for later alternate dates: " + " Room number: " + room.getRoomNumber() + " Room price: " + room.getRoomPrice() + "Room type " +room.getRoomType() + " Dates available: " + formatter.format(checkIn) + "-" + formatter.format(checkOut));
        }
    }

    public static Collection<IRoom> getAllAvailableRooms(Date checkIn, Date checkOut){
        Collection<IRoom> allRooms = new LinkedList<>();
        allRooms.addAll(getAvailableRooms(checkIn, checkOut));
        allRooms.addAll(getAvailableRoomsForAlternateDays(checkIn, checkOut));
        return allRooms;
    }

    public static IRoom getRoomInput(Date checkIn, Date checkOut){
        IRoom room = null;
        boolean isValid = false;
        while(!isValid) {
            System.out.println("Enter room number");
            Scanner scanner2 = new Scanner(System.in);
            String roomNumber = scanner2.nextLine();

            Collection<IRoom> availableRooms = getAllAvailableRooms(checkIn, checkOut);

            IRoom room1 = HOTEL_RESOURCE.getRoom(roomNumber);

            if (!availableRooms.contains(room1)) {
               continue;
            }

            room = HOTEL_RESOURCE.getRoom(roomNumber);
            isValid = true;
        }
        return room;

    }



    public static void printAvailableRooms(Date checkIn, Date checkOut){

        Collection<IRoom> availableRooms = getAvailableRooms(checkIn, checkOut);

        if(availableRooms.size() > 0){
            for(IRoom room: availableRooms){
                System.out.println("Available rooms: " + " Room number: " + room.getRoomNumber() + " Room price: " + room.getRoomPrice() + "Room type " +room.getRoomType());
            }
        }else if(getAvailableRoomsForAlternateDays(checkIn, checkOut).size() > 0){
            System.out.println("No rooms available. Rooms available for alternate dates are: ");
            printAvailableRoomsForAlternateDates(checkIn, checkOut);
        }else{
            System.out.println("No rooms available for the period of time from 10 days before the dates you specified and 10 days after the dates you specified. Returning to the main menu");
            System.out.println();
            mainMenu();
        }

    }

    public static String getEmail(){

        String email ="";

        boolean isValid = false;

        String emailRegex = "^(.+)@(.+).(.+)$";

        while (!isValid) {
            System.out.println("Enter email format: name@domain.com");
            Scanner sc = new Scanner(System.in);
            email = sc.nextLine();

            if (!email.matches(emailRegex)) {
                continue;
            }
            isValid = true;
        }

        return email;

    }

    public static String createAnAccount(){

            String email = getEmail();

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

    public static String inputYesNo(String string){

        System.out.println(string);
        String input = "";


        boolean isValid = false;

        while (!isValid) {
            Scanner sc = new Scanner(System.in);
            input = sc.nextLine();

            String in = input.toLowerCase();

            if (!(in.equals("y") || in.equals("n"))) {
                System.out.println("You must enter Y for YES, N for NO!");
                continue;
            }
            isValid = true;
        }

        return input;
    }

    public static String haveAccount(){
        String s = "Do you have an account with us? Enter Y (YES), N (NO)";

        String email = "";

        String yesNo = inputYesNo(s);


        if(yesNo.equals("n")){
            email = createAnAccount();
        }else if(yesNo.equals("y")){

            try {
                email = getEmail();
            }catch(Exception e){
                System.out.println("Your account was not found: ");
                haveAccount();
            }
        }

        return email;

    }


    public static Date getInputDate() {
        String date = "";
        String regex = "((0[1-9]|1[0-2]|[1-9]))/(0[1-9]|1[0-9]|2[0-9]|3[0-1]|[1-9])/(([0-9]{4}))";
        boolean isValid = false;
        while (!isValid) {
            Scanner scanner = new Scanner(System.in);
            date = scanner.nextLine();

            if(!date.matches(regex)) {
                System.out.println("Please enter in the format mm/dd/yyyy");
                continue;
            }

            isValid =true;
        }
        int month = Integer.parseInt(date.substring(0, 2)) - 1;
        int day = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date inputDate = calendar.getTime();

        return inputDate;
    }

    public static void printReservations(){
        System.out.println("Please enter your email: ");
        Scanner scanner1 = new Scanner(System.in);
        String email = scanner1.nextLine();


       if(ADMIN_RESOURCE.getCustomer(email) == null){
            System.out.println("Your account was not found! Please enter correct email address!");
            printReservations();
           System.out.println();
        } else if (HOTEL_RESOURCE.getCustomersReservation(email) == null) {
           System.out.println();
            System.out.println("No reservations were found!");
        }
        System.out.println();
        printCustomersReservations(email);
    }




    private static void printCustomersReservations(String email){

        try{
       Collection< Reservation> reservations = HOTEL_RESOURCE.getCustomersReservation(email);

       if(!reservations.isEmpty()){
           for (Reservation reservation : reservations) {
               Customer c = reservation.getCustomer();
               Date date = reservation.getCheckInDate();
               Date date1 = reservation.getCheckOutDate();
               IRoom room = reservation.getRoom();

               SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
               String formattedCheckInDate = formatter.format(date);
               String formattedCheckOutDate = formatter.format(date1);


               System.out.println(
                       "Reservation: " + "\n" +
                               "Name: " + c.getFirstName() + " " + c.getLastName() + "\n" +
                               "Room: " + room.getRoomNumber() + " " + room.getRoomType() + "\n" +
                               "Price per night: " + room.getRoomPrice() + "\n" +
                               "CheckIn Date: " + formattedCheckInDate + "\n" +
                               "CheckOut Date: " + formattedCheckOutDate
               );
               System.out.println();
           }
       }}catch(Exception e) {
            mainMenu();
        }
    }

}