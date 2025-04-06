package com.learning.githubuseractivity;
import java.util.Scanner;

import com.learning.githubuseractivity.Service.UserActivity;


public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            displayOptions();
            int choice = sc.nextInt();
            if (choice == 2) {
                System.out.println("Exiting github user activity CLI.....");
                break;
            }
            try {
                switch (choice) {
                    case 1:
                        System.out.println("Please provide your github username:- ");
                        sc.nextLine();
                        String userName = sc.nextLine();
                        UserActivity userActivity = new UserActivity(userName);
                        userActivity.showActivity();
                        break;
                    default:
                        System.out.println("Please provide valid choice.");
                }
            } catch (Exception e) {
                System.out.println("Exception Occured, Error:- " + e.getMessage());
            }
        }
        sc.close();
    }


    private static void displayOptions() {
        System.out.println("""
                Please provide your input
                1. To view github user activity for a username
                2. Exit
                """);
    }
}