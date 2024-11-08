package com.HotelReservations;

import java.sql.*;
import java.util.Scanner;

public class ReservationsHandler {
    public static void makeReservation(Connection con, Scanner sc) throws SQLException {
        System.out.println("Enter the guest name");
        String guestName = sc.next();
        sc.nextLine();
        System.out.println("Enter the room number");
        int roomNumber = sc.nextInt();
        System.out.println("Enter the contact number");
        String contactNumber = sc.next();
        sc.nextLine();

        String query="INSERT INTO reservations (guest_name,room_no,contact_no) VALUES(?,?,?)";
        try{
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,guestName);
            ps.setInt(2,roomNumber);
            ps.setString(3,contactNumber);
            int rows=ps.executeUpdate();
            if(rows>0){
                System.out.println("Reservation has been made successfully");
            }
            else{
                System.out.println("Reservation has been failed");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    public static void viewReservation(Connection con)throws SQLException {
        String sql = "select reservation_id,guest_name,room_no,contact_no,reservation_date from reservations";
        try(Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                int reservation_id = rs.getInt("reservation_id");
                String guest_name = rs.getString("guest_name");
                String room_no = rs.getString("room_no");
                String contact_no = rs.getString("contact_no");
                String reservation_date = rs.getTime("reservation_date").toString();
                System.out.println("reservation_id: "+reservation_id);
                System.out.println("guest_name: "+guest_name);
                System.out.println("room_no: "+room_no);
                System.out.println("contact_no: "+contact_no);
                System.out.println("reservation_date: "+reservation_date);

            }
        }
    }
    public static boolean reservationExists(Connection con, int reservationId) {
        try {
            String query = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservationId;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs.next();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void updateReservation(Connection con, Scanner sc) {
        try {
            System.out.println("Enter the reservation id: ");
            int reservationId = sc.nextInt();
            if (!reservationExists(con, reservationId)) {
                System.out.println("Reservation does not exist");
                return;
            }
            System.out.println("Enter the new guest name: ");
            String guestName = sc.next();
            System.out.println("Enter the new room number: ");
            int roomNumber = sc.nextInt();
            System.out.println("enter new contact number: ");
            String contactNumber = sc.next();

            String sql = "Update reservations set guest_name=? , room_no=?, contact_no=? where reservation_id=?";
            try(PreparedStatement ps = con.prepareStatement(sql)){
                ps.setString(1, guestName);
                ps.setInt(2, roomNumber);
                ps.setString(3, contactNumber);
                ps.setInt(4, reservationId);
                int rows= ps.executeUpdate();
                if(rows>0){
                    System.out.println("Reservation has been updated");
                }
                else{
                    System.out.println("failed to update reservation");
                }
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getRoomNumber(Connection con, Scanner sc) {
        try {
            System.out.println("Enter the reservation id:");
            int reservationId = sc.nextInt();
            System.out.println("Enter the Guest name:");
            String guestName = sc.next();
            sc.nextLine();
            String sql = "SELECT room_no FROM reservations WHERE reservation_id = ? AND guest_name = ?";
            try( PreparedStatement stmt = con.prepareStatement(sql)){
                stmt.setInt(1,reservationId);
                stmt.setString(2,guestName);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    int roomNumber = rs.getInt("room_no");
                    System.out.println("Room number found "+roomNumber);
                }
                else{
                    System.out.println("Room number not found");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void exit() throws InterruptedException{
        System.out.println("Exiting System");
        int i=5;
        while (i>0){
            System.out.print(".");
            Thread.sleep(500);
            i--;
        }
        System.out.println();
        System.out.println("Thank you for using Hotel Reservations");

    }

    public static void deleteReservation(Connection con, Scanner sc) {
        try {
            System.out.println("Enter the reservation id");
            int reservationId = sc.nextInt();
            if(!reservationExists(con,reservationId)){
                System.out.println("Reservation does not exist");
            }

            String sql = "DELETE FROM reservations WHERE reservation_id = ?";
            try( PreparedStatement ps = con.prepareStatement(sql)){
                ps.setInt(1,reservationId);
                int rows= ps.executeUpdate();
                if(rows>0){
                    System.out.println("Reservation has been deleted");
                }
                else{
                    System.out.println("Failed to delete reservation");
                }

            }


        }

        catch (Exception e){
            e.printStackTrace();
        }

    }
}
