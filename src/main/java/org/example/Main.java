package org.example;

public class Main {

    public static void main(String[] args) {

        String input = "ABC";

        try {
            int number = Integer.parseInt(input);
            System.out.println("Valid number: " + number);

        } catch (NumberFormatException ex) {
            System.out.println("Please enter digits only.");
        }

    }
}