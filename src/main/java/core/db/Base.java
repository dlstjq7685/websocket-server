package core.db;

import core.key.DataBaseKey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Base {

    private Connection c;
    private PreparedStatement p;

    public Base() {

    }


    private void connectionDB(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(DataBaseKey.dbPath);
            c.setAutoCommit(false);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    private  void closeDB(){
        try{
            c.close();
        }catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    private boolean createTable(){
        try {
            String sql = "CREATE TABLE COMPANY " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " ADDRESS        CHAR(50), " +
                    " SALARY         REAL)";

            p = c.prepareStatement(sql);
            p.setString(1,sql);
            ResultSet rs = p.executeQuery(sql);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");

        return true;
    }


    private void searchTable(String sql){

    }

}
