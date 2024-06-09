import java.util.Scanner;

import static api.AdminResource.ADMIN_RESOURCE;

public class HotelReservation {

    public static void main(String[] args){

        MainMenu.mainMenu();

        System.out.println("Please select a number for the menu option");

        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();

        try{
            if(userChoice == 1){

            }else if(userChoice == 2){

            }else if(userChoice == 3){

            }else if(userChoice == 4){
                AdminMenu.adminMenu();

            }else if(userChoice == 5){

            }else{
                System.out.println("Please select a number from 1 to 5!");
            }

        }catch(Exception e){
            e.getLocalizedMessage();
        }




    }
}
