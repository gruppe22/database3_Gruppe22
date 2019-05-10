package dal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connector {
    public static Connection static_createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185132"
                + "?user=s185132&password=GL1TEuGDoXF6Lm1t2L7lQ");
    }
    public static void static_startTransAction(Connection con) throws SQLException{
        con.setAutoCommit(false);
    }
    public static void static_commitTransAction(Connection con) throws SQLException{
        con.commit();
    }
    public static void static_rollBack(Connection con)throws SQLException{
        con.rollback();
    }
    public static void static_lockTables(Connection con,String... args)throws SQLException{
           try{

               for (String tableName : args) {
                   PreparedStatement state = con.prepareStatement("LOCK TABLES ? WRITE");
               }

           }catch (SQLException e){
                // THIS HERE WANTS TO THROW A SQL EXCEPTION IN CASE OF ERROR HERE;
           }
    }
    public static void static_unlockTables(Connection con)throws SQLException{
        try{
                PreparedStatement state = con.prepareStatement("UNLOCK TABLES");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
