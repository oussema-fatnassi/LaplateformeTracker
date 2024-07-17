package com.example.utility;
// AgeUtils class with a static method calculateAgeCategory
public class AgeUtils {
    // Method to calculate the age category
    public static String calculateAgeCategory(int age) {
        if (age >= 18 && age <= 25) {
            return "18-25";
        } else if (age >= 26 && age <= 30) {
            return "26-30";
        } else if (age >= 31 && age <= 35) {
            return "31-35";
        } else if (age >= 36 && age <= 40) {
            return "36-40";
        } else if (age >= 41 && age <= 45) {
            return "41-45";
        } else if (age >= 46 && age <= 50) {
            return "46-50";
        } else if (age >= 51 && age <= 60) {
            return "51-60";
        } else {
            return "Unknown";
        }
    }
}