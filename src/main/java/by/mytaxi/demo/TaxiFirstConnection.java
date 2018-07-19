package by.mytaxi.demo;

import jdk.nashorn.internal.objects.annotations.Property;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class TaxiFirstConnection {
   private static Logger log = LogManager.getLogger(TaxiFirstConnection.class); // создание логера

    /*private static final String URL = "jdbc:mysql://localhost:3306/mytaxi?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "pass";
    private static final String QUERY = "CALL getAllShifts";*/

   // private static final String DROP_QUERY_TABLE = "DROP TABLE user";

    public static void main(String[] args)  {
       /* Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;*/
        try (Connection connection = getConnection();){
            //log.info("Database catalog is " + getConnection().getCatalog());
           CallableStatement statement = connection.prepareCall(DatabaseQueries.CALL_getAll_Shifts); //создание объекта запроса
           ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                log.info(rs.getString("id"));
            }
            /*for (int i = 0; i < 3000; i++){
                statement.setInt(1, i);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    log.info(resultSet.getString("car_make"));
                }

            }*/
        } catch (SQLException e) {
            e.printStackTrace();
        } /*finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                    System.out.println("Close0");
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (statement != null){
                try {
                    statement.close();
                    System.out.println("Close1");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                    System.out.println("Close2");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }*/



    }

    private static Connection getConnection() throws SQLException{
        Properties info = new Properties();
        try (InputStream inputStream = new FileInputStream("src\\main\\resources\\database.properties")){
            info.load(inputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
        /*info.setProperty("user", USER_NAME);
        info.setProperty("password", PASSWORD);
     *//*   info.setProperty("useSSL", "false");
        info.setProperty("serverTimezone", "UTC");*/
        return DriverManager.getConnection(info.getProperty("jdbc.url"),
                info.getProperty("jdbc.user"),
                info.getProperty("jdbc.password")); // Создание подключения, через создание объекта класса Connection

    }
}
