package com.HotelReservations;
import java.sql.*;
import java.util.Scanner;

import static java.lang.System.exit;

public class Reservations {
    private static final String  url="jdbc:mysql://localhost:3306/hotel_db";
    private static final String  user="root";
    private static final String  password="8520";
    public static void main(String[] args) throws SQLException ,ClassNotFoundException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection con = DriverManager.getConnection(url,user,password);
            while (true){
                System.out.println("Welcome to Hotel Reservations");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View all reservations");
                System.out.println("3. Get Room number");
                System.out.println("4. Update reservation");
                System.out.println("5. Delete reservation");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        ReservationsHandler.makeReservation(con,sc);
                        break;
                    case 2:
                        ReservationsHandler.viewReservation(con);
                        break;
                    case 3:
                        ReservationsHandler.getRoomNumber(con,sc);
                        break;
                    case 4:
                        ReservationsHandler.updateReservation(con,sc);
                        break;
                    case 5:
                        ReservationsHandler.deleteReservation(con,sc);
                        break;
                    case 0:
                        ReservationsHandler.exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        }
        catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

    }












}
