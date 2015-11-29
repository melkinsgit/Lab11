package com.margaret;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Main {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cubes";
    // database created using command line for my sql
    static final String USER_NAME = "root";
    static final String PASS_WD = "itecitec";

    public static void main(String[] args) {

        try {
            Class.forName(JDBC_DRIVER);
        }
        catch (ClassNotFoundException cnfe){
            System.out.println("Can't instantiate driver class. Make sure driver string is correct.");
            cnfe.printStackTrace(); // output the error information
            System.exit(-1); // exit the program with non zero indicating problem
        }

        //createTable();
        addElement();
        searchForElement();
}

    private static void searchForElement() {
//        try {
//            Class.forName(JDBC_DRIVER);
//        }
//        catch (ClassNotFoundException cnfe){
//            System.out.println("Can't instantiate driver class. Make sure driver string is correct.");
//            cnfe.printStackTrace(); // output the error information
//            System.exit(-1); // exit the program with non zero indicating problem
//        }

        // TODO declare new vars for search input

        Scanner s = new Scanner(System.in);
        String solver = "";
        String solverTimeStr;
        float solverTime = 0;
        boolean gotSolver = false;
        boolean gotTime = false;
        System.out.println("Now you can enter your own Cube Table data."); // TODO ask correct question

        Statement statement = null;  // The object used for executing a static SQL statement and returning the results it produces.
        Connection conn = null;
        ResultSet rs = null;

        // TODO get legal solver name input from user
        while (!gotSolver) {
            try {
                System.out.println("Start with the name of the Rubik's cube solver you know:");
                solver = s.nextLine();
                gotSolver = true;
            } catch (Exception ioe) {
                // repeat request
            }
        }

        // TODO get legal solving time input from user
        while (!gotTime) {
            try {
                System.out.println("Enter the time it took " + solver + " to solve the Rubik's Cube:");
                solverTimeStr = s.nextLine();
                solverTime = Float.parseFloat(solverTimeStr);
                gotTime = true;
            } catch (Exception ioe) {
                // repeat request
            }
        }

        // TODO create select query
        try {
            conn = DriverManager.getConnection(DB_CONNECTION_URL, USER_NAME, PASS_WD); // make the connection
            statement = conn.createStatement(); // Creates a Statement object for sending SQL statements to the database
            String searchInDatabase = "SELECT " + " CubeTable VALUES ('" + solver + "', " + solverTime + ")";
            statement.executeUpdate(searchInDatabase);

            statement.close();
            conn.close();

        } // TODO fill this in
        catch (SQLException sqle) {

        } // TODO fill this in
        catch (Exception e){

        } // TODO fill this in
        finally {

        }

        // TODO execute query

        // TODO output user choices for rows to delete

        // TODO get user input for row to delete

        // TODO create update

        // TODO execute update

    }

    public static void createTable() {
//        try {
//            Class.forName(JDBC_DRIVER);
//        }
//        catch (ClassNotFoundException cnfe){
//            System.out.println("Can't instantiate driver class. Make sure driver string is correct.");
//            cnfe.printStackTrace(); // output the error information
//            System.exit(-1); // exit the program with non zero indicating problem
//        }

        Statement statement = null;  // The object used for executing a static SQL statement and returning the results it produces.
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_CONNECTION_URL, USER_NAME, PASS_WD); // make the connection
            statement = conn.createStatement(); // Creates a Statement object for sending SQL statements to the database
            String createCubesTable = "CREATE TABLE IF NOT EXISTS CubeTable (Rubiks_Solver VARCHAR (35), Solve_Time FLOAT)"; // String that will execute sql command to create a table with one column Rubiks_Solver that is a string of 35 characters or less and a second column Solve_Time that is a floating point number

            statement.executeUpdate(createCubesTable); // execute update for strings that alter a table
            System.out.println("Table created.");

            String addToDatabase = "INSERT INTO CubeTable VALUES ('Cubestormer II robot', 5.270)";
            statement.executeUpdate(addToDatabase);
            addToDatabase = "INSERT INTO CubeTable VALUES ('Fakhri Raihaan (using his feet)', 27.93)";
            statement.executeUpdate(addToDatabase);
            addToDatabase = "INSERT INTO CubeTable VALUES ('Ruxin Liu (age 3)', 99.33)";
            statement.executeUpdate(addToDatabase);
            addToDatabase = "INSERT INTO CubeTable VALUES ('Mats Valk (human record holder)', 6.27)";
            statement.executeUpdate(addToDatabase);

            statement.close();
            conn.close();

        }
        catch (SQLException sqle) {
            System.out.println("Can't connect to database.");
        }
        catch (Exception e){
            System.out.println("Another error.");
        }
        finally {
            // you're done
        }
    } // end createTable method

    public static void addElement() {
//        try {
//            Class.forName(JDBC_DRIVER);
//        }
//        catch (ClassNotFoundException cnfe){
//            System.out.println("Can't instantiate driver class. Make sure driver string is correct.");
//            cnfe.printStackTrace(); // output the error information
//            System.exit(-1); // exit the program with non zero indicating problem
//        }

        Scanner s = new Scanner(System.in);
        String solver = "";
        String solverTimeStr;
        float solverTime = 0;
        boolean gotSolver = false;
        boolean gotTime = false;
        System.out.println("Now you can enter your own Cube Table data.");

        Statement statement = null;  // The object used for executing a static SQL statement and returning the results it produces.
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_CONNECTION_URL, USER_NAME, PASS_WD); // make the connection
            statement = conn.createStatement(); // Creates a Statement object for sending SQL statements to the database

            // get legal solver name input from user
            while (!gotSolver) {
                try {
                    System.out.println("Start with the name of the Rubik's cube solver you know:");
                    solver = s.nextLine();
                    gotSolver = true;
                } catch (Exception ioe) {
                    // repeat request
                }
            }

            // get legal solving time input from user
            while (!gotTime) {
                try {
                    System.out.println("Enter the time it took " + solver + " to solve the Rubik's Cube:");
                    solverTimeStr = s.nextLine();
                    solverTime = Float.parseFloat(solverTimeStr);
                    gotTime = true;
                } catch (Exception ioe) {
                    // repeat request
                }
            }

            String addToDatabase = "INSERT INTO CubeTable VALUES ('" + solver + "', " + solverTime + ")";
            statement.executeUpdate(addToDatabase);

            statement.close();
            conn.close();

        } // TODO fill this in
        catch (SQLException sqle) {

        } // TODO fill this in
        catch (Exception e){

        } // TODO fill this in
        finally {

        }
    } // end addElement method
}

