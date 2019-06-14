package core.db;

import core.key.DataBaseKey;

import java.sql.*;

/**
 *  TODO List
 *      - createLockSql
 *      - createSelectSql
 *      - createUpdateSql
 *      - createInsertSql
 *      -!createDeleteSql
 *
 */
public class Base {

    private Connection c;
    private PreparedStatement p;


    public Base() {
        this.connectionDB();
    }

    private void connectionDB(){
        try {
            Class.forName(DataBaseKey.dbSQLITE);
            c = DriverManager.getConnection(DataBaseKey.dbPath);
            c.setAutoCommit(false);

        } catch (SQLException | ClassNotFoundException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void closeDB(){
        try{
            if( c != null) {
                c.close();
            }

            if ( p != null) {
                p.close();
            }
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    private boolean createTable(String tableName){
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("CREATE TABLE ? (  \n");

            sql.append("");
            sql.append("");
            sql.append("");
            sql.append("");
            sql.append("    )");

            String sql1 = "CREATE TABLE COMPANY " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " ADDRESS        CHAR(50), " +
                    " SALARY         REAL)";

            p = c.prepareStatement(sql.toString());
            // p.setString(1,sql);
            ResultSet rs = p.executeQuery();
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
