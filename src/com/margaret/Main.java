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

    static final int SOLVER_NAME_COL = 1;
    static final int SOLVER_TIME_COL = 2;

    public static void main(String[] args) {

        // verify JDBC driver
        try {
            Class.forName(JDBC_DRIVER);
        }
        catch (ClassNotFoundException cnfe){
            System.out.println("Can't instantiate driver class. Make sure driver string is correct.");
            cnfe.printStackTrace(); // output the error information
            System.exit(-1); // exit the program with non zero indicating problem
        }

        // these can be commented for debugging and testing just one feature at a time
        // create a table with some data
        createTable();
        // add to the table
        addElement();
        // update the table
        searchForElement();
}

    // searches for a cube solver entered by the user
    private static void searchForElement() {

        Scanner s = new Scanner(System.in);
        String oldSolver = "";
        String oldSolverNewTimeStr;
        float oldSolverNewTime = 0;
        boolean gotSolver = false;
        boolean gotTime = false;
        System.out.println("Now you can update Cube Table data."); // ask correct question

        Statement statement = null;  // The object used for executing a static SQL statement and returning the results it produces.
        Connection conn = null;
        ResultSet rs = null;

        // get legal solver name input from user
        while (!gotSolver) {
            try {
                System.out.println("To update a solver's time, enter the Rubik's solver's full or partial name:");
                oldSolver = s.nextLine();
                gotSolver = true;
            } catch (Exception ioe) {
                // repeat request
            }
        }

        // verify connection to DB
        try {
            conn = DriverManager.getConnection(DB_CONNECTION_URL, USER_NAME, PASS_WD); // make the connection
            statement = conn.createStatement(); // Creates a Statement object for sending SQL statements to the database

            // create select query string
            String searchInDatabase = "SELECT Rubiks_Solver FROM cubetable WHERE Rubiks_Solver LIKE '%" + oldSolver + "%'";

            // execute select query
            rs = statement.executeQuery(searchInDatabase);
//            rs = statement.executeQuery(searchInDatabase);


            // output user choices for rows to delete
            ArrayList<String> resNames = new ArrayList<String>();
            ArrayList<Float> resTimes = new ArrayList<Float>(); // not using this but it's a away to read another column into an array list for easier user ID; I'll use it in my project
            int rsCount = 0;
            while (rs.next()){
                rsCount++;  // to make sure there is a result
                resNames.add(rs.getString(SOLVER_NAME_COL));
            }
            if (rsCount == 0){
                System.out.println("Nothing matches that solver entry.");
            }
            else { // then output the results once you know there is a result set
                int loopCount = 0;  // have to count again
                System.out.println("Choose one of the solver(s):");
                for (String resName : resNames) {
                    loopCount++;
                    System.out.println(loopCount + ". " + resName);
                }
            }
            // get user input for row to delete
            System.out.println("Enter the number you wish to update:");
            int updateList = Integer.parseInt(s.nextLine());  // I'm assuming the user inputs legal response; I could also include my class where I wrote a method for getting a legal response to a numbered list; this was for the TicketManager for the HVAC lab. The method was called private static int getNumChoice(Scanner scan, String menu, int i).

            // get legal solving time input from user
            while (!gotTime) {
                try {
                    System.out.println("Enter the new time in which " + oldSolver + " has solved the Rubik's Cube:");
                    oldSolverNewTimeStr = s.nextLine();
                    oldSolverNewTime = Float.parseFloat(oldSolverNewTimeStr);
                    gotTime = true;
                } catch (Exception ioe) {
                    // repeat request
                }
            }


            // create update - I know you suggested parameterized answers - I'll do them in the project
            String nameToUpdate = resNames.get(updateList - 1);
            String updateSolveTime = "UPDATE cubetable SET Solve_Time = " + oldSolverNewTime + " WHERE Rubiks_Solver " + " LIKE '%" + nameToUpdate + "%'";
            // execute update
            statement.executeUpdate(updateSolveTime);

            statement.close();
            conn.close();

        }
        catch (SQLException sqle) {
            System.out.println("SQL exception");
            System.out.println(sqle);

        }
        catch (Exception e){
            System.out.println("Another exception.");
            System.out.println(e);
        }
    }

    // method to create a table and fill it with starter data
    public static void createTable() {

        Statement statement = null;  // The object used for executing a static SQL statement and returning the results it produces.
        Connection conn = null;

        // verify connection to database - previously closed
        try {
            conn = DriverManager.getConnection(DB_CONNECTION_URL, USER_NAME, PASS_WD); // make the connection
            statement = conn.createStatement(); // Creates a Statement object for sending SQL statements to the database
            String createCubesTable = "CREATE TABLE IF NOT EXISTS CubeTable (Rubiks_Solver VARCHAR (35), Solve_Time FLOAT)"; // String that will execute sql command to create a table with one column Rubiks_Solver that is a string of 35 characters or less and a second column Solve_Time that is a floating point number

            statement.executeUpdate(createCubesTable); // execute update for strings that alter a table
            System.out.println("Table created.");

            // insert some data for the database at the beginning
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
            System.out.println("SQL exception");
            System.out.println(sqle);
        }
        catch (Exception e){
            System.out.println("Another exception.");
            System.out.println(e);
        }
    } // end createTable method

    // a method to add rows to the database
    public static void addElement() {

        Scanner s = new Scanner(System.in);
        String solver = "";
        String solverTimeStr;
        float solverTime = 0;
        boolean gotSolver = false;
        boolean gotTime = false;
        System.out.println("Now you can enter your own Cube Table data addElement.");

        Statement statement = null;  // The object used for executing a static SQL statement and returning the results it produces.
        Connection conn = null;

        // verify db connection
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

            // create update string
            String addToDatabase = "INSERT INTO CubeTable VALUES ('" + solver + "', " + solverTime + ")";
            // execute update string
            statement.executeUpdate(addToDatabase);

            statement.close();
            conn.close();

        } //
        catch (SQLException sqle) {
            System.out.println("SQL exception");
            System.out.println(sqle);

        }
        catch (Exception e){
            System.out.println("Another exception.");
            System.out.println(e);
        }
    } // end addElement method
}

