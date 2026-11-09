package org.example;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MarksImporter {

    // Logger stores technical details for debugging
    private static final Logger logger =
            Logger.getLogger(MarksImporter.class.getName());

    // Custom exception for duplicate student IDs
    static class DuplicateStudentException extends RuntimeException {

        public DuplicateStudentException(String message) {
            super(message);
        }
    }

    // Imports marks from the text file
    static void importMarks(String filename) throws IOException {

        Set<String> seen = new HashSet<>();

        // Try-with-resources automatically closes the reader,
        // even if an exception occurs.
        try (BufferedReader br =
                     new BufferedReader(new FileReader(filename))) {

            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {

                lineNumber++;

                try {

                    // TODO 1: Check that there are exactly 2 fields
                    String[] parts = line.split(",", -1);

                    if (parts.length != 2) {
                        throw new IllegalArgumentException(
                                "Record must contain student ID and mark");
                    }

                    String studentId = parts[0].trim();
                    String markText = parts[1].trim();

                    if (studentId.isEmpty()) {
                        throw new IllegalArgumentException(
                                "Student ID cannot be empty");
                    }

                    // TODO 2: Parse and validate the mark
                    int mark = Integer.parseInt(markText);

                    if (mark < 0 || mark > 100) {
                        throw new IllegalArgumentException(
                                "Mark must be between 0 and 100");
                    }

                    // TODO 3: Detect duplicate student IDs
                    if (seen.contains(studentId)) {
                        throw new DuplicateStudentException(
                                "Duplicate student ID: " + studentId);
                    }

                    seen.add(studentId);

                    // TODO 4: Display accepted record
                    System.out.println(
                            "Accepted: " + studentId + " = " + mark);

                } catch (NumberFormatException e) {

                    System.out.println(
                            "Invalid numeric mark on line " + lineNumber
                                    + ": " + line);

                    logger.log(Level.WARNING,
                            "NumberFormatException on line " + lineNumber,
                            e);

                } catch (DuplicateStudentException e) {

                    System.out.println(
                            "Duplicate record skipped: " + line);

                    logger.log(Level.WARNING,
                            "Duplicate student record on line "
                                    + lineNumber, e);

                } catch (IllegalArgumentException e) {

                    System.out.println(
                            "Invalid record on line " + lineNumber
                                    + ": " + e.getMessage());

                    logger.log(Level.WARNING,
                            "Invalid data on line " + lineNumber, e);
                }
            }
        }
    }

    // Main method: program execution starts here
    public static void main(String[] args) {

        try {

            // T5: Testing a missing file
            importMarks("absent.txt");

        } catch (FileNotFoundException e) {

            System.out.println(
                    "Marks file not found; check the file name");

            logger.log(Level.SEVERE,
                    "FileNotFoundException: " + e.getMessage(), e);

        } catch (IOException e) {

            System.out.println(
                    "An error occurred while reading the marks file");

            logger.log(Level.SEVERE,
                    "IOException while importing marks", e);

        } finally {

            System.out.println("Import attempt finished");
        }
    }
}

