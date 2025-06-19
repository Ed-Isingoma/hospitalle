package com.hospitalle.views;

import java.util.Scanner;

public class ConsoleView {
    public static int getIntInput() {
        try { return Integer.parseInt(new Scanner(System.in).nextLine()); }
        catch(Exception e){ return -1; }
    }

    public static Long getLongInput() {
        try { return Long.parseLong(new Scanner(System.in).nextLine()); }
        catch(Exception e){ return -1L; }
    }

    public static String getStringInput() {
        try { return new Scanner(System.in).nextLine(); }
        catch(Exception e) { return "";}
    }

    public static void showMainMenu(){
        System.out.println("Main Menu. Choose database");
        System.out.println("1. Creating");
        System.out.println("2. Viewing");
        System.out.println("3. Updating");
        System.out.println("4. Deleting");
        System.out.println("5. Exit");
        System.out.print("Select: ");
    }

    public static void showTables(){
        System.out.println("Tables:");
        System.out.println("1. Auth");
        System.out.println("2. PatientData");
        System.out.println("3. Admission");
        System.out.println("4. Appointment");
        System.out.println("5. Availability");
        System.out.println("6. Speciality");
        System.out.println("7. Payment");
        System.out.print("Select table: ");
    }
}

